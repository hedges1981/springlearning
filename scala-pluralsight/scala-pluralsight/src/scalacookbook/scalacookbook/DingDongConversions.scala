package scalacookbook

/**
  * Created by christine on 30/01/2017.
  */
object DingDongConversions {

  //note, this is one of doing the implicit conversions stuff, i.e. an implicit static method, see StringImprovements for
  //a different way of doing it, using an implicit class:
  implicit def convertToDing( dong : Dong ) : Ding ={
    new Ding( dong.name )
  }

  //*** having the below caused an ambiguous implicit conversion situation, which caused a compile error.

//  implicit def convertToDing2( dong : Dong ) : Ding ={
//    new Ding( dong.name+"from convert to ding 2" )
//  }

}
