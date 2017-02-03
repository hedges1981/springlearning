package scalacookbook.scalacookbook

import util.control.Breaks._
import scala.util.control._

/**
  * Created by rhall on 01/02/2017.
  */
object Ch3ControlStructures extends App {

  //use of if statement like ternary operator
  var a = true
  println(if (a) "isTrue" else "isFalse ")


  val list1 = List(1, 2)
  val list2 = List(3, 4)
  val listOfLists = List(list1, list2)

  //example of how to do a nested for loop:
  for {
    list <- listOfLists
    i <- list
  } println(i)

  //use of yield to short cut making a new list in a loop:
  val list1Plus1 = for (i <- list1) yield i + 1
  println(list1Plus1)

  //yield for a multiline more complex algo:
  val anotherList = for (i <- list1) yield {
    if (i < 1) {
      i
    } else {
      i * i
    }
  }

  println(anotherList)

  //map is as good as yield:
  val list1Plus2 = list1.map(_ + 2)


  //loop with index example:
  for ((e, index) <- anotherList.zipWithIndex) {
    println(s"element is:$e with index $index")
  }

  //looping over map
  val aMap = Map(1 -> "one", 2 -> "two")

  for ((k, v) <- aMap) println(s"$k:$v")

  //even easier looping over a list,
  list1Plus1.foreach(println)

  //with function used in it:
  list1Plus1.foreach {
    e =>
      val ssssss = e * e
      println(e)
  }

  //looping with multiple counters (i.e. nested loop) over a 2d array
  val array = Array.ofDim[Int](2, 2)
  array(0)(0) = 0
  array(0)(1) = 1
  array(1)(0) = 2
  array(1)(1) = 3

  //print em with nested loop
  for {
    i <- 0 to 1
    j <- 0 to 1
  } println(s"$i:$j= ${array(i)(j)}")

  //looping with embedded if statements, e.g. you want to loop over some things but miss some of them out:
  for {//prints all the even numbers between 1 and 10
    i <- 1 to 10
    if i % 2 == 0
  } println(i)

  //using break and continue in looping:, note the need to import util.control.Breaks._ for this stuff
  //see book p.67, scala actually breaks loops by throwing a BreakException, that is caught in the breakable method
  breakable {
    for (i <- 1 to 20) {
      println(i)
      if (i > 10) break //stops when i hits 11
    }
  }

  //doing a continue is more complex, see book p.67.... never do them in java anyway....
  val Inner = new Breaks
  val Outer = new Breaks

  Outer.breakable {  //note how we can break out of both the inner and outer loop this
    for (i <- 1 to 5) {
      Inner.breakable {
        for (j <- 'a' to 'e') {
          if (i == 1 && j == 'c') Inner.break else println(s"i: $i, j: $j")
          if (i == 2 && j == 'b') Outer.break
        }
      }
    }
  }

  //basically , breaks and continue are shit , like they are in other languages.


}
