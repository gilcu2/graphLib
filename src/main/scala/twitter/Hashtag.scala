package twitter.hashtag

/**
 * Created by gil on 14/05/15.
 */

trait HashtagNode extends core.Node

class Hashtag(id:Long,name:String,val tweetsCount:Long,val retweetsCount:Long)
  extends core.Node(id,name) with HashtagNode

import twitter.follower
class User(id:Long,screen_name:String, isRoot:Boolean=false,followerCount:Long=0, tweetsCount:Long=0,
           lang:String="", timeZone:String="") extends follower.User(id,screen_name,isRoot,
           followerCount,tweetsCount,lang,timeZone ) with HashtagNode



class Twittering(src:Long,dst:Long,val tweetCount:Long,val retweedCount:Long)
  extends core.Edge(src,dst,directed = true)

class Graph extends core.Graph[HashtagNode,Twittering]