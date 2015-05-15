package twitter.follower

/**
 * Created by gil on 14/05/15.
 */
class User(id:Long,screen_name:String,val isRoot:Boolean=false,val followerCount:Long=0,val tweetsCount:Long=0,
                    val lang:String="",val timeZone:String="")
  extends core.Node(id,screen_name)

class Following(src:Long,dst:Long,val retweetCount:Long=0,val mentionCount:Long=0)
  extends core.Edge(src,dst,directed = true)

class Graph extends core.Graph[User,Following]




