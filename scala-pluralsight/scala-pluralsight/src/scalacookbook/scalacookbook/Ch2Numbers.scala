package scalacookbook.scalacookbook

/**
  * Created by rhall on 31/01/2017.
  */
object Ch2Numbers extends App {

  val anInt : Int = 123
  println( anInt.getClass)

  val aDouble : Double = 123.33
  println( aDouble.getClass )

  //parse string into numbers, again using implicit conversions:
  println("100".toInt)
  println("100.23".toDouble)
  println("100".toLong)
  //this one gives error:
  try{
    println("aaa".toInt)
  } catch {
    case ex: NumberFormatException => println("Exception occurred:"+ ex )
  }

  //BigInt class:
  val aBigInt = BigInt( 1 )
  //can be used to deal with daft daft numbers:
  val aBigInt2 = BigInt("999999999999999999999999999999999999999999999")
  println( aBigInt2 - 1 )//prints 999999999999999999999999999999999999999999998

  //BigDecimal, is a scala.math.BigDecimal
  println( BigDecimal("123.333").getClass)

  //convering between numberic types: in java u cast but in scala just use to*
  println( 1.toDouble.getClass)

  //overriding the default numeric type: same as java:

  val x = 1d //makes it a double,
  //or just declare the type:
  val x1 = 1 : BigDecimal
  println(x1.getClass)
  //or:
  val x3 : Double = 2

  //incrementing / decrementing numbers:
  var x4 = 1
  x4 += 1
  println( x4 )//x4 incremented , has been mutated as it is a var not a val

  //comparing numbers with tolerance, e.g. use this method:
  def ~=( x: Double, y: Double, precision: Double ) = {
    if ((x - y).abs < precision) true else false
  }

  //now e.g.
  println(~=(0.01, 0.02, 0.1))//true
  println(~=(0.01, 0.02, 0.005))//false

  //concept of infinity:
  val infinity = Double.PositiveInfinity
  println(infinity)
  println( infinity < infinity+1 )//false.....

  println( (1d / 0) == infinity ) // true...

  //generate random doubles and Ints with upper bounds:
  val r = scala.util.Random
  println( r.nextInt(10))//int between 0 and 10
  println( r.nextDouble)//can't give it a bounds, is always between 0 and 1

  //example of using yield to generate a collection of numbers:
  val listOfSqrdNums : IndexedSeq[Int] = for( i <- 0 to 10 ) yield i * i
  println( listOfSqrdNums )

  //Use of ranges to create ranges of numbers:
  val range1 = 1 to 3
  for( i <- range1 ) println(i)

  //range of even numbers:
  val range2 = 2 to 50 by 2
  println( range2 )

  //ranges can be converted to Arrays, List etc:
  println( range2.toList)
  println( range2.toArray)
}
