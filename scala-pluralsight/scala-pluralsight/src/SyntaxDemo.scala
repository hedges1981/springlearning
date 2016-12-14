import java.util
import scala.util.control.Breaks._

/**
  * Created by christine on 29/08/2016.
  */

//NOTE: because this object extends App, it can be right clicked and run, basically means that all the code inside it will
//be executed as an instruction, so simple way to test out languate syntax:

object SyntaxDemo extends App {
  //App is a 'trait', extending it gets all the code executed that is inside here when the file is run:

  //simple declaration of varialble and print, note the way you assign the type
  val strValue: String = "Hello"
  //NOTE: that val makes it immutable, cannot be re assigned, e.g. this: aString = "changed" will cause a compile error. Think of it as 'value' not a variable.

  println(strValue)

  //var makes it a variable, i.e. can be changed. Note that you don't need the :String, scala can work it out, below it is typed as a String.
  var strVariable = "can be changed"
  println(strVariable)
  strVariable = "has bin changed"
  println(strVariable)

  //defining and calling a function, note where the return type goes, and also how the inputs are typed :
  def min(x: Int, y: Int): Int = {
    //****NOTE*** you can drop the return type declaration if it can be inferred, but need the = to make it return something,
    //else it will work like a void, always have = there to keep it functional.

    if (x < y) {
      x //NOTE no need to use return keyword
    }
    else {
      y
    }
  }

  var er : Int = min( 1,2)

  println(min(5, 6))

  //some stuff on operators, operators are functions and can be over loaded, e.g.
  var x = 4 * 5
  //is like saying:
//  x = 4.*(5)
  println(x)
  //but the 'infix' notation allows you to drop the dot, e.g.
  strVariable = (35 toString) + "hello";
  println(strVariable toUpperCase)

  //So because + is just the name of a fucntion, you could on a class define a function called +, e.g.
  //a train class might have a + function, so you could call train + passenger to add a passenger to train, same as train.+(passenger),
  //or could be in old skool java: train.add( passenger )

  //Define some immutable scala collections:
  val list = List("a", "b", "c")
  println(list)
  //NOTE that you can't add stuff to this list, no add method on it, so to create a list wit more in, do:
  val list2 = "d" :: list //adds d to the front returned list
  println(list2)

  //NOTE: use forEach, streaming style collection operations, like java8 and groovy
  list2.foreach(element => println("eltVal= " + element)) //NOTE the contents of the ( ) are a function, or a closure
  //above can be written as:
  list2.foreach(println) //auto connects the method, elements of list get auto passed in to println(..) as it is easily inferred.

  //traditional for loop, e.g. in reverse order:
  for (value <- list.reverse) {
    println(value)
  }

  //IMMUTABLE in line defined map:
  val map = Map("a" -> 1, "b" -> 2, "c" -> 3)
  println(map)

  //U CAN use 'interoperability' to bring in java classes:
  val javaList = new util.ArrayList[String]
  //and call their methods:
  javaList.add("a")
  javaList add "b"
  println(javaList)

  //NO primitives, only objects, but they match java types:
  val aFloat: Float = 2.3f
  println(aFloat.getClass)
  //Float is a class, so this works, if it was a type use classOf(...)

  // see notes abot anyRef, anyVal etc. == delegates to equasl on a ref object:
  var s1 = "a"
  var s2 = "a"
  println(s1 == s2) //prints true

  //USE eq for the java ==, to compare the references:
  println(s1 eq s2) //true cos they were defined as literals
  println(new String("a") == s2) //true due to == goes to .equals
  println(new String("a") eq s2)

  //gives false as new object created


  //VAR Args syntax:
  def concatStrings(names: String*) = {
    var retVal = ""
    names.foreach(s => retVal += s)

    retVal
  }

  println(concatStrings("z", "x", "y", "w"))

  //Named method arguments and edfault value, allow you to change order when you call it:

  def orderedConcatStrings(first: String, second: String = "jones") = {
    first + " " + second
  }

  println(orderedConcatStrings(second = "smith", first = "john")) //prints john smith

  println(orderedConcatStrings(first = "john")) //prints john jones due to default value given

  //Working over e.g. number ranges, note the to function on class RichInt
  (0 to 100).foreach(println)


  //WORKING WITH CLASSES:
  final class Customer(var firstName: String, val secondName: String, x: Int) {
    //NOTE: the final means it can't be extended

    //NOTE: to make this constructor private and force everything through e.g. a factory method on a conpanion object, :
    //class Customer private( var firstName :String, val secondName :String , x:Int)..companion object can still call the private constructor

    //NOTE how the class constructor seems to double up as i) the class definition and 2 an executable block of code.. this is the primary constructor
    println("varianle x only usable in this constructor" + x)
    //NOTE standard field that is not in the constructor
    var id: Int = 1
    private var priv = "aaa"
    private var _varWithOverriddenSetter = ""

    def varWithOverriddenSetter = _varWithOverriddenSetter

    //NOTE this wiered in direct assignement thing to override the setter,
    def varWithOverriddenSetter_=(value: String) {
      println("in overridden setter")
      _varWithOverriddenSetter = value
    }

    //NOTE: easy to just wack any old method on the class
    def getFullDetails: String = {
      firstName + secondName + id
    }

    //NOTE: Auxilary constructor
    def this(firstName: String) {
      this(firstName, "Smith", 1) //NOTE first line must always be this, so we return a customer object through chained constructor calls.
      //constructor calls must always be chained and have no side effects, to ensure that an actual object is returned.
    }
  }

  //this is all you need to have a 'pojo', the val keyword makes them immutable:
  val dong = new Customer("ding", "dong", 1)
  println(dong.firstName)
  dong.firstName = "changedName" //can change this cos it is a var, but second name is a val, so cant
  dong.id = 2
  //or long hand:
  dong.id_=(3)
  println(dong.id)
  //NOTE the private field, println(dong.priv) will not be allowed.
  //NOTE you could also put private infront of one of the constructor vars to make that private also

  dong.varWithOverriddenSetter = "aValue" //calls the overridden setter, note how we hd to use the wierd two variables thing, and teh _ and def pattern

  println(dong.getFullDetails)

  println(new Customer("john").getFullDetails)


  /** **SINGLETON OBJECTS*******************
    * use object keyword to make a thing a singleton
    */

  object Singleton {
    var x = 2

    def doThing = {
      println("Singleton Doing something, x set to" + x)
    }
  }

  //NOTE , can't make a new instance, already made up, as it is a singleton
  Singleton.doThing
  //but can change stuff on it:
  Singleton.x = 3
  Singleton.doThing

  //NOTE from the above, scala doesn't have static stuff, so a Singleton Achieves the same thing;

  /** *********COMPANION OBJECTS *************,
    * USE THESE WHEN YOU NEED TO COMBINE instance and static style stuff
    */

  class Person {

    val id = Person.nextId

    //need to wrap the static stuff in a companion object

  }

  //companion object lives in same file as other object, but defined out side of class def.
  object Person {
    private var idSeq = 0

    def nextId = {
      //Person instance can call this even if it is private, due to the name being same.
      idSeq += 1
      idSeq
    }

    def apply = new Person();

    //u can define an apply method on the companion object, see below for how it is used:

    //apply can be overlaoded, scala uses the arguments to work out the call.
    def apply(str: String) = {
      println(str + "in overloaded apply method ")
    }
  }

  println(new Person().id)
  println(new Person().id)
  println(new Person().id) //gets incremented
  println(Person.nextId) //can call both static and non static methods on the 'Person object',
  println(Person) //calls the apply method on the Person companion object.
  println(Person("sssss"))

  /** *** Inheritance ************/
  class Dog(val breed: String) {
    def getName = {
      "Not named"
    }
  }

  class NamedDog(breed: String, val name: String) extends Dog(breed) {
    //NOTE no val on breed, else it thinks we are trying to override breed, we are inheriting it!
    override def getName = {
      //NOTE overridden method.
      name
    }
  }

  println(new NamedDog("labrador", "Mavis").getName)

  //NOTE: above inheritance example is good as it shows adding new fields, inheriting exising ones and overriding methods

  //Example of anonymous inner class, these can be used to the same ends as java:
  def wierdDog: Dog = new NamedDog("aaa", "bbb") {
    override def getName = {
      "Ha Ha, anonymous inner class, with it overridden ha ha"
    }
  }

  println(wierdDog.getName)


  /** **********Interfaces, these are called Trait in Scala ****************/
  trait StringProcessor {

    var numStringsProcessed: Int = 0 //trait allows you to define fields for the objects that extend it, this is not a static field, all instances
    //of the trait get their own copy:

    var abstractFieldThatMustBeOverrided: Int //note that if this is not given a default value, it must also be implemented in sub classes.

    def processString(x: String): String //method is implicitly abstract as no method body, just the sig given

    def hardToImplement

    def providedMethod(x: String): String = {
      "String from provided method"
    }
  }

  class ToLowerCaseProcessor extends StringProcessor {
    override def processString(x: String): String = {
      numStringsProcessed = numStringsProcessed + 1
      x.toLowerCase
    }

    override def hardToImplement: Unit = ???

    //note the syntax for not implementing a method, will throw Error at runtime
    override var abstractFieldThatMustBeOverrided: Int = 6
  }


  def toLowerCaseProcessor = new ToLowerCaseProcessor

  println(toLowerCaseProcessor.processString("hello"))
  println(toLowerCaseProcessor.processString("hello"))
  //NOTE how you can call the default 'provided' method on the object, that comes from the trait.
  println(toLowerCaseProcessor.providedMethod("hello"))
  //NOTE access of the provided field from the trait:
  println("Have processed this amount of strings " + toLowerCaseProcessor.numStringsProcessed)

  //NOTE use with XInterface to add another trait to implement, use extends for the first one, then with.

  try {
    new ToLowerCaseProcessor().hardToImplement //exception as not implemented
  }
  catch {
    case e: Error => println("*******EXCEPTION**********" + e.getMessage) //NOTE the switch statement with a closure, can have a few switches for different exceptions
    //see match bit below, to see how you could catch > 1 type on the same line, use |
  }
  finally {
    //this bit works same as java
  }

  //U can override the default provided method on the trait if you want to:
  class DefaultMethodOverridden extends ToLowerCaseProcessor {

    override def providedMethod(x: String): String = {
      "String from overridden provided method"
    }

  }

  println(new DefaultMethodOverridden().providedMethod("q"))


  /** ** IF STATEMENTS ******/
  x = 10
  //can write them standard java way with braces:
  if (x > 9) {
    println("X gt 9")
  }
  else {
    println("x !gt 9")
  }

  //or can get into mindset of thinking about if statements as expressions, if we set them to return something instead of
  // having a side effect, so write them on the same line when doing this:
  val someString : String = if(x > 9 ) "x gt 9" else "x !gt 9"
  println( someString )

  /***** Match expression, like switch in java: note how its an expression, functional!*/
  def isWeekend( day: String ):Boolean ={
    day match {
      case "Sunday" | "Saturday" => true  //NOTE the different syntax, no break and no fall throughs, so use | to make it work
      case _ =>  false  //NOTE: _ is a general thing in Scala to mean 'unknown'.
    }
  }

  println( isWeekend("Sunday"))
  println( isWeekend("Monday"))

  /**WHILE LOOPS, SAME AS IN JAVA, as are DO - WHILE LOOPS **/

  //FOR loops not quite same as in java, need to use generators, e.g.

  for( i <- 0 to 9 ){
    println(i)
  }

  //or could write the above as:
  (0 to 9).foreach( i => println(i))  //note the lambda passed into the for each, como groovy:

  //Breaking out of loops, not encouraged, need to import a special class , Breaks
  breakable {
      for( i <- 0 to 100 ){
        println(i)
        if( i == 10 ) break
      }
  }


  /************ GENERICS + COLLECTIONS **********/

  //Collection stuff:
  var customers :List[Customer] = List()
  var customers2 = new Customer("a","b",1) :: customers  //Note how when you add stuff to the scala list, it is immutable, so you need to assign a new list
  var badList = new Person :: customers //this works, so it looks like you can still put the wrong thing in there
  //NOTE how you use operators to add stuff to the list, there are others such as :+, etc, look em up.
  println(customers)  //original empty list
  println(customers2) //list with a customer in it
  println( badList )

  //demo a few operations on list:
  val list_1 = List(1,2,3)
val list_2 = List(3,4,5)

  val list_3 = list_1 ++ list_2  // returns a concatenated list.

  /****NOTE: see here for the scala doc for the immutable list: http://www.scala-lang.org/api/2.10.3/#scala.collection.immutable.List */

  println( list_3 )

  //to get the nth element from the list:
  println (list_3 (1) )
  //see documentation here for the things you can do with lists, etc, e.g. the traversable trait... can process lists in lots of ways.
  //http://docs.scala-lang.org/overviews/collections/trait-traversable


  /**** GENERIC METHODS **/
  def genericMethod[A]( a: A): String = {
    a.getClass.toString
  } //etc, could have lots of parameters, etc:

  println( genericMethod("q"))
  println( genericMethod( 1 ))


  /*** BOUNDED GENERICS **/
  def genericMethodUpperBounded[A <: String](a: A): String = {  //this one is upper bounded, like 'extends' in java
    a.getClass.toString
  }

  println( genericMethodUpperBounded("q")) //is ok as a String 'extends' a string
  //println( genericMethodBounded(2)) //not going to work, will not compile


  def genericMethodLowerBounded[A >: String](a: A): String = {  //this one is lower bounded, like 'super' in java, this method must be given a super class of String
    a.getClass.toString
  }








}



