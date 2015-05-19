/**
 * Created by gil on 19/05/15.
 */

package core



object numericHistogram {

  import scala.math.Numeric

  private def histNumericArr0[T](container: Seq[T], escala: Scale)(implicit n: Numeric[T]): Histogram = {
    import n._
    import scala.collection.immutable.TreeMap

    val hi0 = container.map(x => ((escala.getBin(x.toDouble), 1L))).groupBy(_._1).map(x =>
      (x._1, x._2.map(y => y._2))).map(x => (x._1, x._2.reduce((x1, x2) => x1 + x2)))
    val hi = TreeMap(hi0.toSeq: _*)

    val bins = hi.map(x => HistoBin(escala.getLeftBorderBinLabel(x._1) + "<->" +
      escala.getLeftBorderBinLabel(x._1 + 1), x._2)).toArray

    Histogram(escala.getScaleLabel, bins)

  }

  def apply[T](container: Seq[T], nBreaks: Int = 10, percentLog: Double = 0.15)(implicit n: Numeric[T]): Histogram = {

    if (container.length == 0)
      return new Histogram("x", new Array[HistoBin](0))

    import n._
    val max = container.max.toDouble()
    val min = container.min.toDouble()

    if (max == min)
      return Histogram("x", Array(HistoBin(container.head.toString, container.length)))

    val escala = new ScaleLineal(nBreaks, min, max)

    val hi = histNumericArr0(container, escala)
    val bins = hi.bins

    val maxBin = bins.reduce((x, y) => if (x.count > y.count) x else y).count

    if (maxBin.toDouble / container.length > percentLog) {
      val escala = new ScaleLogarithm(nBreaks, min, max)
      return histNumericArr0(container, escala)
    }

    hi

  }


}

import scala.math._

abstract class Scale(nBins: Int, min: Double, max: Double) {
  var min1 = BigDecimal(min)
  var max1 = max
  var nBins1 = nBins
  var delta = BigDecimal((max - min) / nBins)

  def prettyLimits(nBins: Int, min: Double, max: Double): (Double, Double, Double, Int) = {
    val h = 1.5
    val h5 = 0.5 + 1.5 * h
    val rounding_eps = 1e-7
    val dx = max - min
    val cell = dx / nBins
    val base = pow(10.0, floor(log10(cell)))
    var unit = base
    var U = 2 * base
    if (U - cell < h * (cell - unit)) {
      unit = U
      U = 5 * base
      if (U - cell < h5 * (cell - unit)) {
        unit = U
        U = 10 * base
        if (U - cell < h * (cell - unit)) unit = U
      }
    }

    var ns = floor(min / unit + rounding_eps)
    var nu = ceil(max / unit - rounding_eps)

    while (ns * unit > min) ns -= 1
    while (nu * unit < max) nu += 1

    val k = (0.5 + nu - ns).toInt

    val min1 = ns * unit
    val max1 = nu * unit
    (min1, max1, unit, k)
  }

  def getBin(x: Double): Int

  def getLeftBorderBin(bin: Int): Double

  def getLeftBorderBinLabel(bin: Int): String

  def getScaleLabel(): String
}

class ScaleLineal(nBins: Int, min: Double, max: Double) extends Scale(nBins, min, max) {

  val a = prettyLimits(nBins, min, max)

  min1 = BigDecimal(a._1)

  max1 = a._2
  delta = BigDecimal(a._3)

  nBins1 = a._4


  val factor = 1 / delta


  def getBin(x: Double): Int = {
    val bin = ((x - min1) * factor).toInt
    if (bin < 0 || bin >= nBins1)
      -1
    else
      bin
  }

  def getLeftBorderBin(bin: Int): Double = {
    (min1 + bin * delta).toDouble
  }

  def getLeftBorderBinLabel(bin: Int): String = {
    getLeftBorderBin(bin).toString
  }

  def getScaleLabel(): String = "x"

}

class ScaleLogarithm(nBins: Int, min: Double, max: Double) extends Scale(nBins, min, max) {

  import scala.math._

  //println("Limits originals:" + min + " " + max)
  val a = prettyLimits(nBins, log10(1.0), log10(max - min + 1.0))
  //println("Log pretty:" + a)
  val min1D = a._1
  min1 = BigDecimal(a._1)
  max1 = a._2
  delta = BigDecimal(a._3)
  nBins1 = a._4
  val factor = 1 / a._3
  val formater=new PrettyNumber(2)


  def getBin(x: Double): Int = {
    //println(log10((x - min1D +1.0)) * factor)
    (log10(x - min1D + 1.0) * factor).toInt
  }

  def getLeftBorderBin(bin: Int): Double = {
    pow(10.0, (delta * bin).toDouble) + min1D - 1.0
  }

  def getLeftBorderBinLabel(bin: Int): String = {
    //(min1-1).toString+"+10^("+(delta*bin).toString()+")"
    //(delta * bin).toString()
    formater.format(getLeftBorderBin(bin))
  }

  def getScaleLabel(): String = "x" //(min1 - 1).toString + "+10^(x)"

}

class PrettyNumber(maxDecimal:Int=0) {
  val formatStr=if(maxDecimal>0) "#."+"#"*maxDecimal else "#"
  val formater=new java.text.DecimalFormat(formatStr)
  def format(value:Double)=formater.format(value)
}
