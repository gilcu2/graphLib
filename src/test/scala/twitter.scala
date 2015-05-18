/**
 * Created by gil on 18/05/15.
 */

package twitterTests

import org.scalatest.{Matchers, FlatSpec}
import twitter.hashtag._

class OnlyUserRetwids extends FlatSpec with Matchers {
  it should " throw AssertionError if a hashtag retwitt" in {
    val g=new Graph()

    val u=new User(1,"user")
    val h=new Hashtag(2,"hashtag")

    g.addUser(u)
    g.addHashtag(h)

    intercept[AssertionError] {
      g.addTwittering(2,1,1,1)
    }

  }

}
