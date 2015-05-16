package twitter.javaInterface

/**
 * Created by gil on 14/05/15.
 */

//trait Vertex {
//  def getId:Long
//  def getVertexType:Int
//}
//
//trait UserVertex extends Vertex{
//  def getScreenName:String
//  def getFollowersCount:Long
//  def getTweetsCount:Long
//  def isRoot:Boolean
//  def getLang:String
//  def getTimeZone:String
//}
//
//trait HashtagVertex {
//  def getHashtagName:String
//  def getTweetsCount:Long
//  def getRetweetsCount:Long
//}
//
//
//trait Edge {
//  def getSource:Long
//  def getTarget:Long
//  def getTweetsCount:Long
//  def getRetweetsCount:Long
//}


import java.util.List
import collection.JavaConversions._
import scala.collection.mutable
import bds.twitter.{model=>jModel}

class GraphFollowers extends  twitter.follower.Graph {

  def this(jNodes:List[jModel.UserVertex],jEdges:List[jModel.Edge])= {
    this
    import twitter.follower._
    jNodes.foreach(x=>addUser(new User(x.getId,x.getScreenName,x.isRoot,x.getFollowersCount,x.getTweetsCount,x.getLang,x.getTimeZone)))

    jEdges.foreach(x=>addFollowing(new Following(x.getSource,x.getTarget)))

  }
}

class GraphHashtag extends
  twitter.hashtag.Graph {
  def this(jNodes:List[jModel.Vertex],jEdges:List[jModel.Edge])={
    this
    import twitter.hashtag._
    jNodes.foreach(_ match {
      case x1:jModel.UserVertex=>addUser(new User(
        x1.getId,x1.getScreenName,x1.isRoot,x1.getFollowersCount,x1.getTweetsCount,x1.getLang,x1.getTimeZone))

      case x1:jModel.HashtagVertex=>addHashtag( new Hashtag (x1.getId, x1.getHashtagName, x1.getTweetsCount, x1.getRetweetsCount))
    })
    val edges=jEdges.map(x=>new Twittering(x.getSource,x.getTarget,x.getTweetsCount,x.getRetweetsCount)).toIterator

  }
}
