package twitter.hashtag

/**
 * Created by gil on 14/05/15.
 */

trait HashtagNode

case class Hashtag(id:Long,name:String,tweetsCount:Long,retweetsCount:Long) extends  HashtagNode

import twitter.follower
class User(id:Long,screen_name:String, isRoot:Boolean=false,followerCount:Long=0, tweetsCount:Long=0,
           lang:String="", timeZone:String="") extends follower.User(id,screen_name,isRoot,
           followerCount,tweetsCount,lang,timeZone ) with HashtagNode

class Twittering(src:Long,dst:Long,val tweetCount:Long,val retweedCount:Long)


class Graph extends core.Graph[HashtagNode,Twittering] {
  def addUser(u:User):Unit=addNode(u.id,u,u.screen_name)
  def addHashtag(h:Hashtag)=addNode(h.id,h,h.name)
  def addTwittering(src:Long,dst:Long,tweetCount:Long,retweedCount:Long): Unit ={
    assert(getNode(src).isInstanceOf[User])
    addEdge(src,dst,new Twittering(src,dst,tweetCount,retweedCount),directed = true)

  }
}