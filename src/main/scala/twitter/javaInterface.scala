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
import bds.twitter.model._
import bds.twitter.model.enums.VertexType

class GraphFollowers extends  twitter.follower.Graph {

  def this(jNodes:List[UserVertex],jEdges:List[Edge])= {
    this
    import twitter.follower._
    val nodes=jNodes.map(x=>new User(x.getId,x.getScreenName,x.isRoot,x.getFollowersCount,x.getTweetsCount,
      x.getLang,x.getTimeZone)).toIterator
    val edges=jEdges.map(x=>new Following(x.getSource,x.getTarget)).toIterator

    addNodes(nodes)
    addEdges(edges)
  }
}

class GraphHashtag extends
  twitter.hashtag.Graph {
  def this(jNodes:List[Vertex],jEdges:List[Edge])={
    this
    import twitter.hashtag._
    val nodes=jNodes.map(x=>x.getVertexType match {
      case VertexType.USER=>{
        val x1=x.asInstanceOf[UserVertex]
        new User(x1.getId,x1.getScreenName,x1.isRoot,x1.getFollowersCount,x1.getTweetsCount,
        x1.getLang,x1.getTimeZone)
      }
      case VertexType.HASHTAG=>{
        val x1=x.asInstanceOf[HashtagVertex]
        new Hashtag (x.getId, x1.getHashtagName, x1.getTweetsCount, x1.getRetweetsCount)
      }

    }).toIterator
    val edges=jEdges.map(x=>new Twittering(x.getSource,x.getTarget,x.getTweetsCount,x.getRetweetsCount)).toIterator

    addNodes(nodes)
    addEdges(edges)
  }
}
