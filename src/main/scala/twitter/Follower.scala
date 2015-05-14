package twitter.follower

/**
 * Created by gil on 14/05/15.
 */
case class NodeUser(override val id:Long,screen_name:String,followerCount:Long,tweetsCount:Long,
                    lang:String,timeZone:String)
  extends core.Node(id,screen_name)

case class EdgeFollower(override val src:Long,override val dst:Long,retweetCount:Long,mentionCount:Long)
  extends core.Edge(src,dst)

case class NodeHashtag(override val id:Long,screen_name:String) extends
