package scalacookbook.scalacookbook

/**
  * Created by rhall on 08/02/2017.
  */
object Ch5Methods {

  //The various levels of method scope:
  class ScopeDemo {

    //object private:
    private[this] def doSomething() :Unit = {}
    //private, works like java private
    private val privVal = "aaaaa"
    //protected, like java in that sub classes can get it, BUT unlike java, other classes in same package can't
    protected val protectedVal = "bbbbb"

    //package level scope:
    //NOTE: how you specify the package it is private to.
    //this allows really complex fine grained access control, however why on earth would that be wanted?
    private[scalacookbook] val packageVal = "ccccc"

    //default is public scope, seems like there is no public keyword, but no need of course...
    val publicVal = "dddd"
  }

  //Calling method on a super class / trait
  trait Human{ def hello = "humanHello"}
  trait Mother extends Human{override def hello = "motherHello"}
  trait Father extends Human { override def hello = "fatherHello" }

  class OtherPerson extends Human with Mother with Father{

    //NOTE: the way the thing to use is specified via super[xxxxx]
    println(hello)  //prints fatherHello, as it is last in the extends list
    println(super[Mother].hello) //does the mother one
    println(super[Human].hello)  // does the human one
  }

  new OtherPerson

  //default method parameters demo:
  def methodWithDefaultParams( s1 : String = "hello", s2 : String = " good bye"): Unit ={
    println(s1+s2)
  }

  //calls it with pure defaults:
  methodWithDefaultParams()
  //provide only one named parameter:
  methodWithDefaultParams(s2=" adios")
  //and with all the parameters:
  methodWithDefaultParams("bonjour"," aurevoir")
  //NOTE, when naming the parameters, you can change the order:
  methodWithDefaultParams(s2 = "bbb", s1 = "aaa")

  //returning tuples from method, looks like they can go as big as you want?
  def tupleMethod :( Int, Int, Int, Int, Int ) = {
    (1,2,3,4, 5)
  }

  val tuple = tupleMethod
  println( tuple._5) //access the elements here:

  //looks like tuples have nifty copy as well:
  val copyOfTuple = tuple.copy(_5=5555)  //does a copy changing only the 5th element:
  println( copyOfTuple )

  //it is convention that if a getter method has no side effect, make it have no ()...
  //e.g.
  class GetterClass{
    def noSideEffects : Int = 12

    def hasSideEffects() : Int = {
      println("getting with side effect")
      14
    }
  }

  //note how you call the getters:
  new GetterClass().noSideEffects //would not compile with ()
  new GetterClass().hasSideEffects //this ok
  //and also this:
  new GetterClass().hasSideEffects()

  //Var args example:
  def printTheLot( strings : String * ){ strings.foreach(println) }
  val stringList = List("1","2","3")
  printTheLot( stringList :_* )  //note the use of :_* , short hand to make it pass the list elements in
  //sequence instead of the list as one go


  //Declaring that methods throw exceptions:
  @throws( classOf[IllegalArgumentException] )
  @throws ( classOf[IllegalStateException ])
  def exceptionThrowingMethod( i : Int ): Unit ={

    if(i<5) throw new IllegalArgumentException else throw new IllegalStateException()

  }

  //also, scala doesn't care if exceptions are checked or not, u don't need to declare them and
  //nor are you forced to catch them

  //Method chaining, fluent programming style:
  class MethodChainingClass {

    var firstName :String = ???
    var secondName : String = ???

    def setFirstName( s : String ):MethodChainingClass = {
      this.firstName = s
      this
    }

    def setSecondName( s : String ):MethodChainingClass = {
      this.secondName = s
      this
    }
  }

  //note how you can chain the methods
  val aMethodChainingClass = new MethodChainingClass().setFirstName("king").setSecondName("kong")

}
