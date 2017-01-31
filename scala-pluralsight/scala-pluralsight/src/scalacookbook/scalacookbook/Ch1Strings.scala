package scalacookbook

/**
  *
  */
object Ch1Strings extends App{

  //Strings are java strings:
  println("hello ".getClass.getName) //prints java.lang.String

  //implicit conversions allow use of methods on String ops, note how String ops has a toCollection( scala.Predef.String )
  "hello".foreach( println )

  //some other collection like operations available due to the implicit conversion:
  for ( c <-"hello") println(c)

  //filter out all the l s in the string:
  println( "hello".filter( _!="l"))

  //comparing string equality:
  val s1 = "hello"
  val s2 = "hello"
  //just use ==, is same as java.equals
  println( s1 == s2 )
  //but it is null safe on the left hand side:
  println( null == s2 )

  //Multi line String,
  //use """   """ to enclose them, note can contain e.g. " inside,
  //and can be done with stripMargin, using |, e.g.

  val mls =
    """|this is a
      |multi line string
      |on 3 lines with " and "" in it...
    """.stripMargin

  //when printed, is all lined up to the left:
  println( mls )

  //splitting strings, same as java, note this way of doing it on white space:
  println (" a b c d e      f".split("\\s+")(6).trim)

  //String interpolation... to sub in variables to a String:
  val name = "dong"
  val age = 23
  //note the use of 's':
  val interpolatedString = s"$name is $age years of age"
  println( interpolatedString )

  //can even use an expression in the string literal, e.g.
  println(s"Dong will be ${age + 1 } next year" )
  println(s"Is dong 23 years of age? ${age == 23}")

  //use raw to make it not escape anything in the string:
  println( raw"string\n with \n lots of \n new \n lines in \n it")//comes out on one line with \n still there:

  //note the use of the implicit class StringImprovements to allow this to be done:
  val aaa = "ding "
  println( aaa.dongify )
}
