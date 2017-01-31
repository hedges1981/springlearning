/**
  * Created by rhall on 30/08/2016.
  */
object ScalaPart2 extends App {

  //**************expressive scala, faking function calls

  //further use of the apply method:
  class SomeObject {

    def apply( str: String ) = {
      println( str )
    }

    def update(x :Int,y : String)  = {
      println(" update called with x="+x +" and y = "+y )
    }
  }

  val something = new SomeObject
  something( "hello") //prints hello.
  //NOTE from the above that you can use apply on classes as well as objects, and can call it on instances.

  //NOTE that apply works on an array:
  val numerals = Array( "i","ii", "iii", "iv")
  println( numerals(2)) //prints iii
  numerals(2) = "loadOfRubbish"
  println( numerals(2)) //prints "loadOfRubbish"

  //NOTE the reason why the above works is because if it sees an 'apply' like call with the =, it calls the update method on the class
  //so:
  numerals.update(2,"moreRubbish")//is the same thing

  //see this one:
  something(1)= "hello"  //prints:update called with x=1 and y = hello
  //NOTE above how you can make use of all this shorthand stuff in real life.
  //AGAIN the update method can be overloaded like the apply can.

  //***************expressive scala faking language constructs

  //passing in functions as arguments, higher order functions
  //e.g. lets process those numerals:

  numerals.foreach{ //anonymous inner function
    numeral => println("Hahahah"+numeral)
  }

  //can put in here any function that take a string:

  def someFunctionThatTakesAString( s: String ) = {
    println( "someFunctionThatTakesAString"+s)
  }

  numerals.foreach{
    someFunctionThatTakesAString
  }

  //Example of a higher order function:
  def runsAVoid( function : => Unit ): Unit ={ //so here function refers to 'a function with no args that returns void
    function //remember this is shorthand for function.apply  ..... you call apply on functions to run them
  }

  //SAME as the above but with more syntax:
  def runsAVoidMoreSyntax( function :() => Unit ): Unit ={
    function()
  }


  def someVoidFunction(): Unit = {
    println("in some void function")
  }

  runsAVoid( someVoidFunction ) //prints "in some void function"
  //ABOVE is a bit pointless, but illustrates the picture.

  //can also say:
  runsAVoid{
    () => someVoidFunction
  }

  //or
  println( "running as void with two voids in there:")
  runsAVoid{
    someVoidFunction
    someVoidFunction
  }

  //the above looks like you can pass a list of things to do to it?
  // suggests to use { } for higher order functions to make it look like a lambda function
  /*  but you can only use { } like runAsVoid { } when the runsAVoid function takes only one argument,
  so to use them with functions taking 2 arguments, need to use Currying:
   */

  def runsAVoidWithString( str :String, function: => Unit ) {
      println( str+" running void with String" )
      function
  }

  //NOW call:
  runsAVoidWithString( "hello", someVoidFunction )

  //or to keep the {} stuff:
  runsAVoidWithString("hello", {
    someVoidFunction
  } //prints hello once but calls some voidFunction twice...
  )

  /*CURRYING: process of taking a function with 2 or more arguments and making it into a series of functions with only one argument each:

  f(a,b) = a+b

  to curry we want to get:
  f(a) ->g(b) -> a+b

  so:  f(a) {
    b -> {  a + b }
  }
   */

  def adds( a :Int, b :Int ): Int = {
    a+b
  }

  println( adds(1,2) ) //prints 3:

  //curried:
  def aAdder( a :Int ) : ( Int) => Int  = {
    b => { a + b}
  }

  //now:
  def adds4ToIt = aAdder(4)
  println( adds4ToIt(3)) //prints 7:

  //or using chaining:
  println( aAdder(4)(3)) //prints 7:

  //NOTE: the above example is an example of 'closure', where aAdder captures a value to be used in later calls

  //NOTE above example could be done using scala conveinence, looks like it is very clever to help with currying
  def add( x:Int)(y:Int): Int = x+y

  println( add(1)(2))  //prints 3.
  //what is add(3)?
  def add3To = add(3)_    //note we need the _ to keep it happy, we are basically calling the function with a placeholder
  //for the second argument to get the 'partially' applied function
  println( add3To(8))  //prints 11

  //****** Pattern matching














}
