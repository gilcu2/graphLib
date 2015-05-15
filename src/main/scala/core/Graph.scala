package core

/**
 * Created by gil on 14/05/15.
 */

import org.graphstream.{graph=>GS}


class Node private(val id:Long,_label:String,val weight:Double,kk:Any)  {
  val label=if(_label=="") id.toString else _label

  def this(id:Long,label:String="",weight:Double=0.0)=this(id,label,weight,null)

  override def toString=label
}

class Edge private (val src:Long,val dst:Long,_label:String,val directed:Boolean,val weight:Double,kk:Any) {
  val label={
    val conn=if(directed) "->" else "--"
    if(_label=="") src.toString+conn+dst.toString  else _label
  }


  def this(src:Long,dst:Long,label:String="",directed:Boolean=false,weight:Double=0.0)=
    this(src,dst,label,directed,weight,null)

  def getId:String={
    val conn=if(directed) "->" else "--"
    src.toString+conn+dst.toString
  }

}


class Graph[TNode<:Node,TEdge<:Edge] {

  import org.graphstream.graph.implementations._
  import org.graphstream.algorithm.ConnectedComponents
  import scala.collection.mutable

  private val g=new SingleGraph("")
  private val nodes=mutable.HashMap[Long,TNode]()
  private val edges=mutable.HashMap[(Long,Long),TEdge]()

  def this(nodes:Iterator[TNode],edges:Iterator[TEdge])= {
    this
    addNodes(nodes)
    addEdges(edges)
  }

  def addNode(node:TNode):Unit={
    nodes(node.id)=node
    g.addNode(node.id.toString)
  }

  def addEdge(edge:TEdge):Unit={
    edges((edge.src,edge.dst))=edge
    g.addEdge(edge.getId,edge.src.toString,edge.dst.toString,edge.directed)
  }

  def addNodes(nodes:Iterator[TNode]):Unit={
    nodes.foreach(addNode(_))
  }

  def addEdges(edges:Iterator[TEdge]): Unit ={
    edges.foreach(addEdge(_))
  }

  def connectedComponentsCount:Int={
    val cc = new ConnectedComponents(g)
    cc.getConnectedComponentsCount
  }


}
