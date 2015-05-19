/**
 * Created by gil on 16/05/15.
 */

package coreTests


import org.scalatest._
import core._


class TGraph extends FlatSpec with Matchers {
  "Empty Graph" should "have 0 connected components" in {
    val g=new Graph[Int,Int]()
    g.connectedComponentsCount should be (0)
  }

}


class THistogram extends FlatSpec with Matchers {
  "this collecion" should "have histogram of two bins" in {
    val container=Seq(2,3,2)
    Histogram(container) should be ( Histogram("x",Iterable(HistoBin("2",2),HistoBin("3",1))))
  }

}
