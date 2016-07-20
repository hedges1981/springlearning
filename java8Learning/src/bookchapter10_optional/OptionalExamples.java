package bookchapter10_optional;

import java.util.Optional;

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
        Vet v = new Vet("ShitVet")




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
