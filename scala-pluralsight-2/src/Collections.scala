import java.util

import collection.JavaConverters._
import scala.collection.mutable

/**
  * Created by rhall on 13/12/2016.
  */
object Collections extends App{

  //List stuff *****

  //treating scala colls as java, and vice versa, see the JavaConverters import at the top.
  val javaList : util.List[Int] = List(1,2,3).asJava  // asJava makes it java
  javaList.asScala.foreach( i => println(i) )  //applying asScala allows you to perform scala ops on it.

  //getting mutable and immutable collections:
  //default is immutalbe:
  val imList : List[Int] = List(1,2,3)
  println( imList.getClass)
  //this gives a mutable list:
  val mList : mutable.MutableList[Int] = mutable.MutableList(3,4,5)
  println( mList.getClass)

  //Using map to do stuff to each element of the list, e.g. add 1 to them:
  val imList2: List[Int] = imList.map( _ +1 ) //note the need for reassignment, due to immutability
  println( imList2 )

  //map using a function, see the syntax:
  val imList3: List[Int] = imList.map( it => it + 2 )

  //Summing the list, will work for any list of numerical stuff:
  val dubList : List[Double] = List(2.2,3.3,4.4)
  println( dubList.sum )

  //zipping two lists, by summing the values:
  println (( imList, imList2).zipped.map( _ + _ ) )

  //filtering a List:
  println( imList.filter( _>1) )//only values > 1 come out

  //nice way to loop over a list with an index:
  //note using until is same as saying 0 to imList.size()-1
  (0 until imList.size).foreach( i => println( "index="+i+" val="+imList(i) ))

  //finding minimum element in a list:
  val someObjectList : List[SomeObject] = List( new SomeObject(1,2), new SomeObject(3,4))

  val minByA = someObjectList.minBy( _.a )

  println( minByA.a )



  //maps stuff:
  val imMap : Map[Int,Double] = Map( 1->1.0, 2->2.0, 3-> 3.0)

  //sum up all the values: (0.0 = starting value)
  //fold left means go from left-> right applying the operation to the tuples.
  val sumValues : Double = imMap.foldLeft(0.0)(_ + _._2)
  //note this syntax, _2 means second element of a tuple, so the _ + _._2 here means take last sum and add it to the tuple second element,
  //remember that when a map is passed over, you pass over its tuples.

  //adds 3 to each element in the map
  val imMap2 = imMap.map {
    case(k, v) => (k, v + 3)  //saying that for each k,v pair, give back a tuple as specified.
  }

  //filter a map:
  println( imMap.filter {
    case(k, v) => v > 1
  })

  //same as above, but using the _ to get a reference to the map.
  println( imMap.filter(
    _._2 > 1
  )   )



  class SomeObject( val a : Int, val b : Int )



}
