/**
 * Created by gil on 16/05/15.
 */

package coreTests

import org.scalatest._
import core._

class ConnectedComponents extends FlatSpec with Matchers {
  "Empty Graph" should "have 0 connected components" in {
    val g=new Graph[Int,Int]()
    g.connectedComponentsCount should be (0)
  }

}

