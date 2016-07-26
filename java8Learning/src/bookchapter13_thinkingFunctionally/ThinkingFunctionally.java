package bookchapter13_thinkingFunctionally;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by rowland-hall on 22/07/16
 */
public class ThinkingFunctionally
{
    static void p( Object o)
    {
        System.out.println(o);
    }

    public static void main( String[] args )
    {
        /**
         * NOTE: some rules for decent functional programming:
         * - function can only mutate local variables
         * - function cannot throw exceptions, as this gives a result by other means than returning a value
         * - if the function is partial, i.e. doesn't have return values for all possible inputs, don't throw exception,
         * return something, such as an empty optional.
         * - use a marker interface to show that a function is a pure function
         * - is suggested that a pure function can put out log statements for debugging, even though that is strictly a side effect,
         * it is conceptually allowable.
         * - book p.371: referential transparency, always same result for same input.
         */

        //EXAMPLE OF FUNCTIONAL GENERATION OF SUBSETS OF A SET OF INTEGERS:
        p( subsets( Arrays.asList(1,4,9) ));

        //DEMOS ITERATIVE FUNCTIONS VS RECURSIVE FUNCTIONS VS TAIL RECURSIVE FUNCTIONS:

        p( factorialViaIteration( 5 ));
        p( factorialViaRecursion( 5 ));
        p( factorialTailRecursive( 5 ));
    }

    //note use of marker interface to make sure that it is functional:

    /**
     *  E.g. if called with {1,4,9}  gives:
     *  {1,4,9}, (1,4), (1,9) (4,9), {1},{4},{9}  and {}
     */
    @Functional
    public static List<List<Integer>> subsets( List<Integer> list )
    {
        List<List<Integer>> retList = new ArrayList<List<Integer>>();

        if( list.isEmpty() )
        {
            //only valid sub set element is the empty list:
            retList.add( Collections.EMPTY_LIST );
        }
        else
        {
            //algorithm is recursive, takes first int from list, gets the subsets of what is left, then creates a new set by adding the
            //first int to each of them, then combining the two sets to get the complete subset of the input list.
            Integer first = list.get(0);
            List<Integer> rest = list.subList( 1, list.size() );

            List<List<Integer>> subans = subsets( rest );
            List<List<Integer>> subans2 = insertAll( first, subans );

            retList = concat( subans, subans2 );

        }

        return retList;
    }


    //Takes the supplied first Integer, returns a list of integers that = subans but with first added to each of the
    //sub lists of integers.
    //NOTE: @Fucntional - new lists created, no modification done on the inputs:

    @Functional
    static List<List<Integer>> insertAll( Integer first, List<List<Integer>> lists )
    {
        List<List<Integer>> retVal = new ArrayList<>();

        for( List<Integer> intList : lists )
        {
            List<Integer> newList = new ArrayList<> ();
            newList.add(first);
            newList.addAll( intList );
            retVal.add( newList );
        }

        return retVal;
    }

    @Functional //note again input list copied, not modfified:
    static List<List<Integer>> concat( List<List<Integer>> lists, List<List<Integer>> lists2 )
    {
        List<List<Integer>> retList = new ArrayList<>(  );

        retList.addAll( lists );
        retList.addAll( lists2 );

        return retList;
    }

    @Functional
    //note this one is functional, but may raise doubts as it still modifies internal variables.
    static int factorialViaIteration( int x )
    {
        int retVal =1;

        for( int i =1; i<=x; i++ )
        {
            retVal = retVal * i;
        }

        return retVal;
    }

    //NOTE: this is not tail recursive, look at where the recursive call is. The stack for this call must still be kept
    //when the recursive call is made, so that a reference to retVal is held onto, to be returned:
    //NOTE: java isn't currently compiler optimised to take advantage of this, but it might be soon...
    @Functional
    static int factorialViaRecursion( int x )
    {
        int retVal;

        if( x ==1 )
        {
            retVal = 1;
        }
        else
        {
            retVal = x * factorialViaRecursion( x -1 );
        }

        return retVal;
    }

    @Functional
    static int factorialTailRecursive( int x )
    {
        return factorialHelper( 1, x );
    }


    @Functional
    static int factorialHelper( int accumulated, int x )
    {
       if ( x == 1)
       {
           return accumulated;
       }
       else
       {
           //note the tail recursion, no need to hold onto any thing on the stack when we make the recursive call:
           return factorialHelper( x * accumulated, x-1 );
       }
    }

}