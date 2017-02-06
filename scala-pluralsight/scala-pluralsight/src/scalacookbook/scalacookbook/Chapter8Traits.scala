package scalacookbook.scalacookbook

/**
  * Created by christine on 05/02/2017.
  */
object Chapter8Traits extends App{

  //Traits can do all the things that you can do in normal java, to implement  > 1 trait, use extends with...

  //Traits can declare abstract and concrete fields:
  trait Pizza {
    def numToppings : Int //this one is abstract, so Must be overridden in a concrete Impl
    def countryOfOrigin = "Italy"

    def doSomethingPizzaLike = {
      println("Pizza trait doing somethin")
    }
  }

  trait ContainsFish

  trait FishFood {
    this : ContainsFish =>  //Note how this means that only something that has trait ContainsFish can
    //have this trait
    def doSomethingFishLike = println("FishFood trait doing something")
  }

  class TunaPizza extends Pizza with FishFood with ContainsFish{  //Note: has ContainsFish trait, so
    //can also have the FishFood trait
    override def numToppings: Int = 2//Note how we have implemented the necessary abstract field:

    override def doSomethingPizzaLike = {   //note that the override modifier is mandatory when doing an override
      println("Pizza trait doing somethin")
    }
  }

  trait MustHaveSpecifiedMethods {
    this : {
    //Here we speficy that an implementor must have these two methods:
        def aStringMethod(s: String )
        def anIntMethod( i : Int )
    } =>
  }

  class BeefPizza extends Pizza with MustHaveSpecifiedMethods{ // with  FishFood { NOTE: cant have trait fish food, as does not have trait
    //ContainsFish
    override def numToppings: Int = 1

    //NOTE: how these two methods allow for it to have the MustHaveSpecifiedMethods trait
    def aStringMethod(s: String ) = {}
    def anIntMethod( i : Int ) ={}
  }

  val tunaPizza = new TunaPizza
  println( tunaPizza.numToppings)
  println( tunaPizza.countryOfOrigin)
  //Note how it can also call behaviours from the FishFood trait, this is a 'mixIn'
  tunaPizza.doSomethingFishLike


  //Adding a trait to a specific object at run time:
  trait SaysHello {
    def sayHello = println("helloooooooooooooooooooooooooooooo")
    sayHello  // this gets executed when anything that has this trait is constructed

  }

  var beefPizza = new BeefPizza with SaysHello //prints hello on construction
  //and it can access methods on the runtime trait also:
  beefPizza.sayHello

  //Can implement or extend a Java interface very easily, e.g:
  class ExtendsJavaInterface extends AJavaInterface{
    override def doAJavaInterfaceMethod(): Unit = println( " doing the java interface method ")
  }

  new ExtendsJavaInterface().doAJavaInterfaceMethod()





}
