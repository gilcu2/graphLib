package core



/**
 * Created by gil on 18/05/15.
 */

case class HistoBin(label: String, count: Long)

/**
 *
 * @param label for variable
 * @param bins
 */

case class Histogram(label: String, bins: Iterable[HistoBin])

object Histogram {


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

  def apply[T](container: Seq[T], nbins: Int=10, subsets: IndexedSeq[Set[T]]=IndexedSeq(), label:String="x"): Histogram = {



    val nContainer = container.size
    if (container.isEmpty)
      Histogram(label, Iterable(HistoBin("Empty", nContainer)))
    else if (subsets.size == 1)
      Histogram(label, Iterable(HistoBin(subsets.head.toString, nContainer)))
    else {
      val subsets1=if(subsets.isEmpty) container.distinct.map(Set(_)).toIndexedSeq else subsets

      val bins0 = container.map(x => (insideWhich(x, subsets1), 1)).groupBy(_._1).map(x => (x._1, x._2.map(y => y._2))).
        map(x => (x._1, x._2.reduce((x1, x2) => x1 + x2)))


      val bins = bins0.filter(_._2>0).toList.sortWith((x, y) => x._2 > y._2).take(nbins)
      val nInBins = bins.map(x => x._2).sum
      var histoBins = bins.map(x => HistoBin(subsets1(x._1).mkString(","), x._2))

      if (nContainer - nInBins > 0)
        histoBins = histoBins ++ List(HistoBin("Others", nContainer - nInBins))
      Histogram(label, histoBins)
    }

  }


  private def insideWhich[T](x: T, sets: IndexedSeq[Set[T]]): Int = {
    for (i <- 0 to sets.length - 1)
      if (sets(i).contains(x))
        return i
    -1
  }

}


