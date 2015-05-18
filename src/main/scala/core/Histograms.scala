package core

/**
 * Created by gil on 18/05/15.
 */
object Histograms {

  case class HistoBin(label: String, count: Long)

  /**
   *
   * @param label for
   * @param bins
   */

  case class Histogram(label: String, bins: Iterable[HistoBin])

  /**
   *
   * Count how many member of each subset are in container
   *
   * @param container
   * @param subsets
   * @param nbins : if we want nbins and subsets.size>nbins we create the maximums nbins and one additionar with the rest
   * @tparam T
   * @return
   */

  def histogramSubsets[T](container: Seq[T], subsets: IndexedSeq[Set[T]], nbins: Int=10,label:String="x"): Histogram = {

    val nContainer = container.size
    if (subsets.isEmpty)
      Histogram(label, Iterable(HistoBin("empty", nContainer)))
    else if (subsets.size == 1)
      Histogram(label, Iterable(HistoBin(subsets.head.toString, nContainer)))
    else {
      var bins0 = container.map(x => (insideWhich(x, subsets), 1)).groupBy(_._1).map(x => (x._1, x._2.map(y => y._2))).
        map(x => (x._1, x._2.reduce((x1, x2) => x1 + x2)))

      var nEmpty = 0
      val emptyPos = subsets.indexOf(Set(""))

      if (emptyPos != -1) {
        nEmpty = bins0(emptyPos)
        bins0 = bins0 - emptyPos
      }

      val bins = bins0.toArray.sortWith((x, y) => x._2 > y._2).take(nbins)
      val nInBins = bins.map(x => x._2).sum
      var histoBins = bins.map(x => HistoBin(subsets(x._1).mkString(","), x._2))

      if (nEmpty > 0)
        histoBins = histoBins ++ Array(HistoBin("No Value", nEmpty))
      if (nContainer - nInBins > 0)
        histoBins = histoBins ++ Array(HistoBin("Others", nContainer - nInBins - nEmpty))
      Histogram("x", histoBins)
    }

  }

  def histogram[T](container: Seq[T], nbins: Int = 10,label:String="x"): Histogram = {

    val subsets = container.distinct.map(Set(_)).toIndexedSeq

    histogramSubsets(container, subsets, nbins)
  }

  private def insideWhich[T](x: T, sets: IndexedSeq[Set[T]]): Int = {
    for (i <- 0 to sets.length - 1)
      if (sets(i).contains(x))
        return i
    -1
  }

}
