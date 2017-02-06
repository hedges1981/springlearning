package scalacookbook.scalacookbook

import scala.annotation.switch
import scala.util.Random
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


  //p.72 Switch statement, use match:

  val i = 1

  val decisionTreeSwitch = i match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case _ => ???    //use _ for the default catch all,
    // note that returning ??? from something causes a NotImplementedError
  }

  println( decisionTreeSwitch )


  /* p.73 Using @Switch to make it compile to a table switch rather than a decision tree
  -- the above match statement compiles to a decision tree, e.g. if i == 1, else if i == 2, etc
  --using @sitch belopw tells it to compile it to a table look up, where it can jump straight in
  and grab the result, like a hash map.... is faster this way...
   */
  val tableLookUpSwitch = (i : @switch) match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case _ => ???    //use _ for the default catch all,
    // note that returning ??? from something causes a NotImplementedError
  }
  println( tableLookUpSwitch)

  //match statements very flexible, don't need to be integers or enums like java:
  val aString = "hello"
  val stringSwitch = aString match{
    case "hello" => "hola"
    case "goodbye" => "adios"
  }
  println( stringSwitch )

  //match based on type, basically equivalent of using instanceof:
  def printAThingForTheClass( thing : Any ) = {

    thing match {
      case s : String => println( s+" is a String ")
      case i : Int => println( i+" is an Int ")
      case _ => println( " is something else")
    }
  }

  printAThingForTheClass( "" )
  printAThingForTheClass(1)
  printAThingForTheClass(1d)

  //mapping multiple things to the same result:
  val aNumber = 3

  val oddOrEven = aNumber match {
    case 1 | 3 | 5 | 7 | 9 => "isOdd"
    case 2 | 4 | 6 | 8 | 10 => "isEven"
    case _ => "not catered for"
  }

  println( oddOrEven )

  //using match to define nifty functions, result of match can be used for a function:
  def oddOrEvenAsFuction( x : Any ) = x match {
    case 1 | 3 | 5 | 7 | 9 => "isOdd"
    case 2 | 4 | 6 | 8 | 10 => "isEven"
    case _ => "not catered for"
  }

  println( oddOrEvenAsFuction(1))
  println( oddOrEvenAsFuction(2))

  //actually grabbing the value of the thing that fell through to the default case:
  //above we used _ for the unknown or default case, but that way we lost the value
  //of the thing, what if we want to actually do something with it?

  val ssss = "gogogogogogod"

  val aResult = ssss match {
    case "hello" => "hola"
    case thingNotMatched => "couldn't match: "+thingNotMatched //can use any valid variable name here,
    //note that by having a variable name instead of _, it can be grabbed and used.
  }

  println( aResult )

  //pattern matching, very powerful in making match statements, the below apparently demos all
  //the various powers of it:
  case class Dog( name : String )
  case class Person( firstName : String, secondName: String )

  def complicatedPatternMatch( x : Any ) = x match {
    //can use for constants, as demoed above:
    case "hello" =>"u said hello"
    case Nil => "was empty List"
    //sequence patterns:
    case List(1,2,3) => " list of 1,2,3"
    case List(1, _, _ ) =>" any 3 element list that has 1 as the head "
    case List(2,_*) => " a list of any length, so long as it has 2 at the head "
    //tuples
    case (a,b) => s" tuple of $a and $b"
    case( 1,2,thirdElt ) => s" tuple of 1,2 and $thirdElt"
    //constructor patterns:
    case Dog("Mavis") => " a dog called mavis"
    case Person( firstName, "Hall") => s" a person called $firstName Hall"
    //typed patterns as seen above:
    case i : Int => s" an Int of value $i"
    //and of course the good old default one:
    case default => s" an un-matched thing of value: $default"
  }

  println( complicatedPatternMatch(new Dog("Mavis")))
  println( complicatedPatternMatch(new Person("Mavis", "Hall")))
  println( complicatedPatternMatch((1,2,3)))
  println( complicatedPatternMatch(List(2,2,3,3,3,3,3,3)))
  //etc etc:

  //using variables with patterns to grab hold of the thing that matched the pattern:

  def grabVariableFromPatternDemo( x : Any ) = x match {

    case thing @ ( _,_ ) => s"Tuple matched, thing is: $thing"
    case thing @ List(1,_) => s" List with first elt =1 matched, second elt = $thing(2) "
    case _ => ???
    //note how we have used @
    //where we might have expeted ':' , this is because : is used to attach variables to a
    // fully defined type but we need to use @ for attaching it to a pattern, as they are different

  }

  println( grabVariableFromPatternDemo((1,2)))
  println( grabVariableFromPatternDemo( List(1,4)))

  //Options: Use of Some and None for things that can potentially be null:

  def convertToInt( s : String ) : Option[Int] = {
    try{
      Some( s.toInt )
    } catch {
      case e : Exception => None
    }
  }

  val optionWithVal : Option[Int] = convertToInt( "42")
  println( "Option with val holds value"+optionWithVal.get)

  val optionWithNoVal : Option[Int] = convertToInt("notAnInt")
  try{
    println( "Option with no val holds value"+optionWithNoVal.get)
  } catch {
    case e : NoSuchElementException => println("When you try to do get on an Option with no val (None) you get this " +
      "exception")
  }

  //can use isEmpty / isDefined to check that the option has a value:
  println(optionWithVal.isDefined) //true
  println( optionWithNoVal.isEmpty ) //true

  //can use Some and None in match expressions, e.g:
  convertToInt("2") match {
    case Some(i) => println( s"was converted to $i")
    case None => println(" didn't convert")
  }


  //using if statement 'guards' on a case expression:
  /*
  Note, that the if statement is evaluated as part of the case making decision,
  can make code cleaner that having wirting it round in a different way.
   */
  1 match {
    case a if a < 10 => println( s" $a is less than ten")
    case b if b < 20 => println( s"$b is < 20")
    case default => println(s"$default was passed ")
  }

  //Some nifty workings with list, use of :: operator and Nil:
  //remember that :: can be used to define a list:

  def listToString( list : List[Any]) : String = {
    list match {
      case elt :: rest => ""+elt + listToString( rest )
      //Note use of :: to divide up the list with the List pattern match:
      case Nil => "" //takes advantage of fact that last elt in the list is Nil
    }
  }

  println( listToString( List(1,2,3)))

  //using try / catch to match different exceptions:
  def randomException : Unit = {
    val randomInt = new Random().nextInt()
    if ((randomInt % 2) == 0 )throw new IllegalArgumentException else throw new IllegalStateException
  }

  try {
    randomException
  } catch {
    case e : IllegalArgumentException => println( "threw IllegalArgument")
    case e : IllegalStateException => println( " threw IllegalState ")
  }

  //use of a while loop:
  var x = 0
  while( x < 10 ){
    println(s"$x < 10")
    x +=1
  }

  //defining own whilst function illustrates a nifty bit of functional programming, is this currying?:
  def whilst(condition: => Boolean)(codeBlock: => Unit) {
    if (condition) { //note how => is used in the variable declaration thing, means that it gets executed,
      codeBlock //note that codeBLock also gets executed, thanks to it being defined as using =>
      //in the method parameter declarations.
      whilst(condition)(codeBlock)
    }
  }

  var y = 0
  whilst ( y < 10 ){
    println(s"$y < 10")
    y +=1
  }

  //other example of this currying style functional pattern, double if :

  //note that the condition parameters are 'functions that return a boolean'
  def doubleIf( condition1 : => Boolean )( condition2 : => Boolean )( codeBlock : => Unit )= {
    if( condition1 && condition2 ) codeBlock
  }

  doubleIf( true )(true ){
    println("Double IF has said true!!!")
  }


}
