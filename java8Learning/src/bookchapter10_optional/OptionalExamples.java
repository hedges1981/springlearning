package bookchapter10_optional;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by rowland-hall on 20/07/16
 */
public class OptionalExamples
{
    static void p(Object o)
    {
        System.out.println(o);
    }

    public static void main( String[] args )
    {
        //Creating Optional objects:
        //empty optional:
        Optional<String> optStr = Optional.empty();

        //create one from an actual object:
        String s="wwww";
        Optional<String> optStr2 = Optional.of( s ); //gives exn if s = null:

        //this one doesn't matter if s= null:
        Optional<String> optStr3 = Optional.ofNullable( s );

        //EXAMPLE OF USAGE:

        //  person.getDog().getVet().getName();           //something like this will be prone to null pointer exceptions, optionals can help:

        //so what if we want to get a persons vets name, normally ud need to do a shed load of null checking, but.....

        Person p = new Person();

        Optional<Dog> dog = p.getDog();

        String vetName = dog.flatMap( Dog::getVet ).map( Vet::getName).orElse( "No Vet Name!" );

        p(vetName);  // note that this pattern doesn't go wrong due to nulls, it gives "No Vet Name!".

        //but lets set up a vet:
        Vet v = new Vet("aVet");
        Optional<Vet> vetOpt =  Optional.of( v );

        Dog dog2 = new Dog("Mavis", vetOpt);

        p.setDog( Optional.of(dog2) );

        //note the  functional syntax here: as it goes down, it applies the function passed to flatMap to the optional. As soon as a
        // empty optional is found, it collapses to the orElse, or gives the real thing if no empty optionals are applied.
        //note that the last call is to map, not flatMap, to map the result out.
        vetName = p.getDog().flatMap( Dog::getVet ).map( Vet::getName).orElse( "No Vet Name!" );

        p(vetName);    // now it gives "aVet".

        //Some ways of unwrapping an optional:
        Optional<Dog>  dOpt = Optional.empty();
        //empty so:

        try
        {
            dOpt.get();
        }
        catch( Exception e )
        {
            //get throws an exeption if it is empty:
        }

        p(dOpt.isPresent()) ;// note: is present function boolean to tell if it is empty.

        dOpt.orElse( new Dog() );  //decent one, gives a new Dog if Empty.

        //lazy version of orElse, don't need a pre-existing Dog:
        dOpt.orElseGet( new Supplier<Dog>()
        {
            @Override
            public Dog get()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        } ) ;

        //Note can use a lambda, here as Supplier is a functional interface.
        dOpt.orElseGet( ()-> new Dog() );

        //FlatmAP, etc is a bit wierd, but it makes sense, basically FlatMap returns an optional:
        Optional<Vet> aVetOpt = p.getDog().flatMap( Dog::getVet );   //so this line gives an Optional<Vet>, if there is a dog the Optional will contain
        //the result of Dog::getVet, else it will be an empty Optional<Vet>
        Optional <String> optStr4 = p.getDog().flatMap( Dog::getVet ).map( Vet::getName ); //Note this gives an Optional<String>..... as is potential for the Dog
        //or the vet not to exist:
        //finally we can get the
        String aString = p.getDog().flatMap( Dog::getVet ).map( Vet::getName ).get();

        //NOTE the difference between flatMap and map is that flatMap wants a function to give another Optional, Map wants a function that returns the String String (in this case).
        //***USE flat map all the way down the chain, then use map to actually get the thing you want out.


        //*****NOTE:::: FILTERING*****
        //see book p.301,  you can use filter( aFunction) to filter the optionals, to only return a value if a criteria is matched.

        //********NOTE::: see book p.306 for a practical example where using Optionals with map and filter greatly reduces code to work on some data.
    }

    //class follows pattern: use actual references for stuff that you should expect to always be set, every person has a name,
    //but not all have a dog, so use Optional for that stuff:
    static class Person
    {
        String name;
        Optional<Dog> dog = Optional.empty();

        Person( String name, Optional<Dog> dog )
        {
            this.name = name;
            this.dog = dog;
        }

        Person()
        {
        }

        Optional<Dog> getDog()
        {
            return dog;
        }

        void setName( String name )
        {
            this.name = name;
        }

        void setDog( Optional<Dog> dog )
        {
            this.dog = dog;
        }

        String getName()
        {
            return name;
        }
    }

    static class Dog
    {
        String name;
        Optional<Vet> vet =Optional.empty();

        Dog( String name, Optional<Vet> vet )
        {
            this.name = name;
            this.vet = vet;
        }

        public Dog()
        {
            //To change body of created methods use File | Settings | File Templates.
        }

        String getName()
        {
            return name;
        }

        void setName( String name )
        {
            this.name = name;
        }

        Optional<Vet> getVet()
        {
            return vet;
        }

        void setVet( Optional<Vet> vet )
        {
            this.vet = vet;
        }
    }

    static class Vet
    {
        String name;

        Vet( String name )
        {
            this.name = name;
        }

        String getName()
        {
            return name;
        }

        void setName( String name )
        {
            this.name = name;
        }
    }
}
