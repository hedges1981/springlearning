package scalacookbook.scalacookbook

/**
  * Created by rhall on 09/02/2017.
  */
object Ch6Objects extends App{

  //how to do casting:
  class Animal{}
  class Dog extends Animal {}

  val aDog : Dog = new Dog
  val aAnimal = aDog.asInstanceOf[Animal]

  //casting of lists and arrays to just be of type Object
  val stringList : List[String] = List("1","2","3")
  val objectList = stringList.asInstanceOf[List[Object]]

  // .class in scala.... use classOf[TYPE]
  println( classOf[String] ) //same as String.class in Java

  //and to get the class of an instance:
  val aString = "333"
  println( aString.getClass )

}
