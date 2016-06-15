package bookChapter3

/**
  * Created by rowland-hall on 15/06/16.
  */


  //trait is an abstract interface that might contain some implementations of the methods:
  //sealed means that all implementations must be declared in this file
  sealed trait List[+A]  //note: the + indicates that the data type is 'covariant', basically polymorphic, so a list[dog] can be considered a list[animal]
  //if there were no +, wouldn't be the case.

  //these 2 are 'data constructors', represeting the two forms a List can take.
  case object Nil extends List[Nothing]   // empty list
  case class Cons[+A](head:A, tail:List[A]) extends List[A] //constructor. Takes an A, a list of A, note the +, again covariant / polymorphic:
  //NOTE: this is completely different to a java style object, here a List[A] is a function,
  //so the 'Cons' object is actually a function.

  object List {

    def sum(ints: List[Int]) : Int = ints match {
      case Nil => 0                              // if it is Nill, i.e. List[Nothing]
      case Cons(x,xs)=> x+sum(xs)                // if this List funcObject is the 'cons' function, then add them all up.
    }

    //this is a case function, it takes ds and through the match keyword, tries to pick one of the cases, and therefore what to return.
    //if multiple cases could match ds, the first that matches is returned.
    def product(ds:List[Double]) : Double = ds match {
      case Nil => 1.0
      case Cons(0.0,_)=>0.0
      case Cons(x,xs)=> x * product(xs)
    }

    def apply[A] (as:A*):List[A] = {
      if(as.isEmpty) Nil
      else Cons(as.head, apply(as.tail:_*))
    }

    //custom method not in book, make a 'List' funcObject out of an array:
    def makeAList[A] (arr: Array[A]) : List[A] ={

      def loop( i:Int, list: List[A], inArr: Array[A] ): List[A] = {
        if( i == (arr.length -1 ))
          Cons(inArr(i), list )
        else
          loop( i+1, Cons(inArr(i), list), inArr)
      }

      loop(0, Nil, arr)
    }


    def  sumTest : Unit = {
      // we gonna make a list of 1,2,3 , then add it up:
      val list  = List.makeAList(Array(1,2,3))  //note how you don't declare a type or any thing like that, objects are functions:

      //now add it up:
      val sum = List.sum(list)
      println(sum)
    }

}


