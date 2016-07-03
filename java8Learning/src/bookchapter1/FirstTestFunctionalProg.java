package bookchapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by christine on 03/07/2016.
 */
public class FirstTestFunctionalProg {

    public static class Apple
    {
        int weight;
        boolean isGreen;



        public Apple(int weight, boolean isGreen)
        {
            this.weight = weight;
            this.isGreen = isGreen;
        }

        public static boolean isHeavy( Apple a )
        {
            return a.weight > 100;
        }
    }

    List<Apple> filter(List<Apple> in, Predicate<Apple> p)
    {
        List<Apple> retList = new ArrayList<Apple>();
        for (Apple a: in)
        {
            if(p.test(a))
            {
                retList.add(a);
            }
        }
        return retList;
    }




    public static void main( String[] args )
    {
        List<Apple> in = new ArrayList<Apple>();
        in.add(new Apple(200, false));
        in.add(new Apple(20, true));

        //NOTE; here how an inline function is defined , note that the filter method takes a predicate, which baslically
        //maean 'a one arg function that returns a boolean.
       prt( new FirstTestFunctionalProg().filter(in, (Apple a)->a.isGreen) );

        //NOTE; we are also passing in a boolean retFunction that takes an apple, but here it is referenced by Class::functionName
        //NOTE that it has to be a static function
        prt(new FirstTestFunctionalProg().filter(in, Apple::isHeavy));
    }

    static void prt( Object o )
    {
        System.out.println(o);
    }
}