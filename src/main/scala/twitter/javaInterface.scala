package twitter.javaInterface

/**
 * Created by gil on 14/05/15.
 */
trait UserVertex {
  def getId:Long
  def getScreenName:String
  def getFollowersCount:Long
  def getTweetsCount:Long
  def isRoot:Boolean
}


