package scalacookbook.scalacookbook

/**
  * Created by christine on 06/02/2017.
  */
object Ch4ClassesAndProperties extends App{

  //any code in a class body gets executed when the constructor is called:
  class SomeClass( val cantBeChanged : Int, var canBeChanged : Int, private val aPrivateField : Int ) {
    println( "hello")
  }

  val someClass = new SomeClass(1,2, 3)  // prints hello
  someClass.canBeChanged = 6  //can change a var, but not a val
  println( someClass.cantBeChanged ) //but val can be read
  // someClass.aPrivateField private field cant be read

  //Case classes
  case class ACaseClass( field1 : Int, field2 : Int ) //case class fields are val by default
  //Case classes designed for immutability, with the copy functionality good for partial changes
  var aCaseClass = new ACaseClass(1,2)
  aCaseClass = aCaseClass.copy( field2 = 3)
  println(aCaseClass.field1+":"+aCaseClass.field2 ) // 1:3

  aCaseClass= ACaseClass( 1, 4) //illustrates what goes on with case classes, in reality the compiler
  //uses the CompanionObject.apply method for building stuff, as shown

  //Auxiliary constructors:
  class Pizza( val topping: String, val size : Int ){

    //use this keyword
    def this( topping : String ){
      //println("")  //Note, this not allowed, like in Java, must call another constructor right away.
      this(topping, 12)
    }

    //a default no args one:
    def this(){
      this("cheese", 12)
    }
  }
  val aDefaultPizza = new Pizza
  println( aDefaultPizza.topping + aDefaultPizza.size )

  //Singleton pattern, class wih private constructor:
  //- have private constructor and use companion object to get at it:

  class ASingleton private {}

  object ASingleton{
    val aSingleton = new ASingleton  //companion object can get to constructor
    def getInstance = aSingleton
  }

  //new ASingleton ... wont compile
  println( ASingleton.getInstance )//get to it via the static method, like in Java

  //Use of default constructor values, provides lots of flexibility
  class AnotherClass( val ding : String = "ding", val dong : String = "dong" ){
    override def toString = s"AnotherClass($ding, $dong)"
  }

  println(new AnotherClass()) //uses all defaults
  println(new AnotherClass(dong = "DONG")) //uses default for ding
  println(new AnotherClass("DING","DONG")) //proper values given

  //Overriding default getter and setter methods for a class field
  //as shown above scala generates default getter and setter for fields, to override this, follow this pattern:
  class YetAnotherClass( private var _name : String ){

    //NOTE how we rename the variable slightly, to allow us to specify different getters and setters to what scala will generate
    def name ={
      println("getting name")
      _name
    }


    //name_ coresponds to the scala setter naming convention, so can be caled by object.name = XXX
    def name_ (aName: String ) = {
      println("setting name")
      _name = aName
    }
  }

  val yetAnotherClass = new YetAnotherClass("name")
  yetAnotherClass.name // prints getting name
  yetAnotherClass.name_("new name") //prints setting name

  //reached page 117





}
