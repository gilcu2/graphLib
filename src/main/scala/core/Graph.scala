package core

/**
 * Created by gil on 14/05/15.
 */

import org.graphstream.{graph=>GS}


class Node[Data] private(val id:Long,_label:String,val weight:Double,val data:Data,kk:Any)  {
  val label=if(_label=="") id.toString else _label

  def this(id:Long,data:Data,label:String="",weight:Double=0.0)=this(id,label,weight,data,null)

  override def toString=label
}

class Edge[Data] private (val src:Long,val dst:Long,_label:String,val directed:Boolean,val weight:Double,data:Data,kk:Any) {
  val label={
    val conn=if(directed) "->" else "--"
    if(_label=="") src.toString+conn+dst.toString  else _label
  }

  def this(src:Long,dst:Long,data:Data,label:String="",directed:Boolean=false,weight:Double=0.0)=
    this(src,dst,label,directed,weight,data,null)

  def getId:String={
    val conn=if(directed) "->" else "--"
    src.toString+conn+dst.toString
  }

}


class Graph[NodeData,EdgeData] {

  import org.graphstream.graph.implementations._
  import org.graphstream.algorithm.ConnectedComponents
  import scala.collection.mutable

  type TNode=Node[NodeData]
  type TEdge=Edge[EdgeData]

  private val g=new SingleGraph("")
  private val nodes=mutable.HashMap[Long,TNode]()
  private val edges=mutable.HashMap[(Long,Long),TEdge]()


  def addNode(id:Long,data:NodeData,label:String="",weight:Double=0.0):Unit=addNode(new Node(id,data,label))

  def addNode(node:TNode):Unit={
    nodes(node.id)=node
    g.addNode(node.id.toString)
  }

  def addEdge(src:Long,dst:Long,data:EdgeData,label:String="",directed:Boolean=false,weight:Double=0.0):Unit=
    addEdge(new Edge(src,dst,data,label,directed,weight))

  def addEdge(edge:TEdge):Unit={
    edges((edge.src,edge.dst))=edge
    g.addEdge(edge.getId,edge.src.toString,edge.dst.toString,edge.directed)
  }

  def connectedComponentsCount:Int={
    val cc = new ConnectedComponents(g)
    cc.getConnectedComponentsCount
  }


}
