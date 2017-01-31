package scalacookbook

import scalacookbook.DingDongConversions._

/**
  * Created by christine on 30/01/2017.
  */
object ImplicitConversionsExample extends App{


  val aDong = new Dong( "dong ")

  //note how we cal call a ding method on the dong, see the import to DingDongConversions._  .. DingDong has a method
  //convertToDing that is implicitly called to do the conversion before doing the ding method.
  aDong.doADingThing



}

class Dong( val name : String )

class Ding( val name: String ) {

  def doADingThing: Unit = {
    println("this is a ding "+name)
  }
}
