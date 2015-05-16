package twitter.follower

/**
 * Created by gil on 14/05/15.
 */
case class User(id:Long,screen_name:String,isRoot:Boolean=false,followerCount:Long=0,tweetsCount:Long=0,
                    lang:String="",timeZone:String="")
 
case class Following(src:Long,dst:Long,retweetCount:Long=0,mentionCount:Long=0)
  

class Graph extends core.Graph[User,Following] {
  def addUser(u:User):Unit=addNode(u.id,u,u.screen_name)
  def addFollowing(f: Following)=addEdge(f.src,f.dst,f,directed = true)
}




