
package bookChapter2

import scala.annotation.tailrec


/*
having it as 'object' makes it a singleton class and also declares its only instance
 */
object MyModule {

    /*
    defnes a function that takes an Int param called n and returns an Int.
    Note, it looks like you don't need {} around the function definition.
     */
    def abs(n:Int) : Int =
      if (n < 0) -n     //returns (-n)
      else n            // returns n

    /*
    NOTE: the return type is not stated in the defn, scala is smart enough to infer it from the return type of the method.
    But of course best practice to delcare it, esp for public methods.
     */
    private def formatAbs( x:Int )= {
      val msg = "The absolute value of %d is %d";  //val means an immutable variable, like with the keyword final.
      /*note that you can separate statements with new lines or ; */
      msg.format(x, abs(x))
    }

  /**
    * Does the factorial of n. Note how it effects a loop by using an inner recursive helper function.
    */
    def factorial( n: Int): Int = {
      @annotation.tailrec   //try changing the else go(n-1, n*acc) to else 1+ go(n-1, n*acc), the annotation causes a compile error to get returned.
      def go(n:Int, acc:Int):Int ={  // recursive helper function that effects looping. Convention is to call it 'go' or 'loop'
        if( n<=0) acc
        else go(n-1, n*acc)
      }

      go(n,1)
    }

    /*
    HIGHER ORDER FUNCTION:
    note that it takes a function that is basically int f(int) as a parameter, good concept if f is a pure function,
    has referential transparency.
    TO test, call e.g.

    MyModule.formatResult("Factorial", 10, MyModule.factorial )

     */
    def formatResult( name: String, n: Int, f: Int => Int): String ={
      val msg = "The %s of %d is % d."
      msg.format(name, n, f(n))
    }

    /*
    POLYMORPHIC FUNCTION

    NOTE: in this sense, polymorphic refers to a function with a generic type, rather than traditional 'inheritance polymorphism'

    takes a 'generic' array of type A. the function p is basically an equals function
    so to call this for a string:

    MyModule.findIndexOfFirstOccurrenceInArray( Array("A","B","B","C") , (s:String) => s.equals("B"))

    to call this for an integer:

    var intarr: Array[Int] = Array(1,2,3,4,4)   note how this is a way of definfing an array literal
    findIndexOfFirstOccurrenceInArray( intarr, (x:Int) x==9 )    .... the value of p is basically a fucntion that takes an int (x) and returns true if x ==9
    this is called a 'function literal'
     */
    def findIndexOfFirstOccurrenceInArray[A](as: Array[A], p: A => Boolean): Int = {
      @annotation.tailrec
      def loop(n: Int): Int = {
        if( n >= (as.length-1) ) -1
        else if (p(as(n))) n
        else(loop(n+1))
      }

      loop(0)
    }

    /*
    ordered, a function that takes two params, and decides if they are in order or not
    Some tests:
    MyModule.isSorted( Array(1,2,3), MyModule.twoIntsInOrder) -> true
    MyModule.isSorted( Array(1,2,2), MyModule.twoIntsInOrder) -> true
    MyModule.isSorted( Array(1,2,2,3,2), MyModule.twoIntsInOrder) -> false

    NOTE:

     */
    def isSorted[A] (as: Array[A], ordered:(A,A) => Boolean ) : Boolean = {
      @annotation.tailrec
      def loop( n: Int): Boolean ={
        //if we have hit the end of the array, must be sorted, return true:
        if( n == (as.length -1 )) true
        else if (ordered( as(n),as(n+1) )) loop(n+1)
        else false
      }

      loop(0)
    }

    /*
    function that can be passed in as a variable to the isSorted method, when working with ints.
     */
    def twoIntsInOrder(x: Int, y: Int ) : Boolean ={
      !(y<x)
    }

    /*
    this method is not a pure function, it is 'outer shell' that calls into the 'functional core'
    it is a 'procedure' or 'impure function', as it has a side effect of printing something out.
     */
    def main ( args: Array[String]) : Unit = {
      println(formatAbs(-42))
      println(factorial(10))
      println(
        MyModule.isSorted( Array(1,2,3), MyModule.twoIntsInOrder) )
    }

  // f is afunction, like : C = f(A,B) , B=>C means : C=f(B)
  def partial1[A,B,C](a:A, f:(A,B)=>C): B=>C= {
    (b: B ) => f(a,b)   // so we have delared that we create a function of b that returns f(a,b), i.e. a c.
  }


    /************************  EXERCISES AT END OF CHAPTER 2 ****************************/

  //ex 2.3
    //NOTE: curry and uncurry partially apply f, i.e. they take f, but combine it with another external input.
  def curry[A,B,C](f:(A,B)=>C): A=>(B => C)=
  {
    // needs to return a function of A that returns a function of (B,C)
        // we get a C by f (A,B)
        //
    (a:A ) => ((b:B)=>f(a,b))  // so returns a function of a:A, that returns a function which takes a b:B, which calls f(a,b)
      //how fucking complicated?!
  }

  def uncurry[A,B,C] (f: A=>B=>C) : (A,B) => C =
  {
    // takes a fucntion f(A) => g(B,C) and returns h(A,B) =>C
    (a:A, b:B) => f(a).apply(b)
    //  explained: f(a) -> g(b) -> c
      // so we pass a into f, to generate a function of b that returns a C, and call apply(b) on that function to get the c
  }

  def compose[A,B,C]( f: B=>C, g:A=>B) : A=> C ={

        // example of composition, passing the value of another function to another, wrapped calls.
    (a:A)=> f(g(a))
  }

  def composeUsingAndThenKeyWord [A,B,C]( f: B=>C, g:A=>B) : A=> C ={

    (a:A)=> g.andThen(f)(a)  // g.andThen(f) is basically a function that calls g, and the f on a
  }

  def composeUsingComposeKeyWord [A,B,C]( f: B=>C, g:A=>B) : A=> C ={

    (a:A)=> f.compose(g)(a)  // same as the above, compose and and then same, but other way found.
  }

}
