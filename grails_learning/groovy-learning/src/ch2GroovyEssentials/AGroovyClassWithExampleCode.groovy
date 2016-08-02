package ch2GroovyEssentials
/**

 Rubbish class just to demo groovy language features


 */
class AGroovyClassWithExamplesOfLanguageConstruction {

    static def authors = [
            "Peter Ledbrook",
            "Glen Smith"
    ]
    static def quoteParts = [
            ["Time", "waits", "for no man"],
            ["The roundhouse kick", "solves", "all problems"],
            ["Groovy", "is", "the bees knees"]
    ]

    static String createQuote(List quoteParts, List authors) {
        def rand = new Random()
        def n = quoteParts.size()
        def m = authors.size()
        return quoteParts[rand.nextInt(n)][0] + ' ' +
                quoteParts[rand.nextInt(n)][1] + ' ' +
                quoteParts[rand.nextInt(n)][2] + ' by ' +
                authors[rand.nextInt(m)]
    }

    static String doCreateQuote()
    {
        createQuote( quoteParts, authors );
    }

    static void printInt( int x )
    {

    }

    static void main( String[] args )
    {
        //note possible options for printing ln, looks like things like println are in GroovyDefaultMethods

        println doCreateQuote();

        System.out.println( doCreateQuote() );

        //example of a loop:

        for( int i in 0..<10) //note the 0..<10  , a literal of type groovy.lang.Range
        {
            println doCreateQuote();
        }

        //but this java style looping also ok
        for( int i =0; i<10; i++ )
        {
            println "in java style loop";
        }

        //this works, converts the 0..<10 into strings, "1","2", etc. is 'autocoercion'. Only occurs between number types
        //and when the target type is String, as anyting can be represented as a String:
        for( String s in 0..<10) //note the 0..<10  , a literal of type groovy.lang.Range
        {
            println s;
        }


        //OPTIONAL TYPES:
        // can declare variables like below:
        def aWeaklyTypedString = "aWeakString";
        String typeString = "typedString" ;

        //this is ok at compile time:
        // printInt( aWeaklyTypedString )
        // but will throw an exception at runtime, as types are ultimately enforced then.

        List l; //NOTE AUTO import of classes, don't need to explicity import 'standard'stuff like List, groovy has auto import of some things


        //EASY ACCESS OF OBJECT PROPERTIES
        //
        def quote =  new Quote(author: "Peter Ledbrook",
                content: "Time waits for no man")    //note there is no actual constructor, this is a "GroovyBeans constructor, note how it wokrs.

        // groovy dynamically can create getters and setters , even if the methods are not actually on the bean class:
        println quote.getAuthor()  //note no getAuthor method on Quote


        //PRIMITIVE TYPES, can have methods called on them, again through the GroovyDefaultMethods thingo e.g.:

        int i = -100
        i = i.abs()
        println i; //PRINTS 100;

        //LITTLE BIT ABOUT STRING LITERALS:
        //$ in a string, lets you evaluate groovy expressions:
        def name = "kingKong";
        println "hello ${name}" //prints hello kingKing
        //use tripple quote for multi line string literals, note how the \ allows one line to go over many in the editor:
        println """
this is on line 1
this is on line 2
this is on line 3 \
this also is on line 3
"""

        //CLOSURES:
        //examples in book are a bit like java 8 streams:
        def someStrings = ["aaa","bbb","ccc","dog","dead"]
        //e.g. use a 'stream with a closure to sum up the length of all the strings:
        int totalLength = someStrings.sum { it.length() } //basically do a sum, adding up the length of each string as you
        //go along:
        println totalLength

        //e.g. of using it like a filter:
        def stringsContainingA = someStrings.findAll {it.contains("a")}  //NOTE: looks like you have to use it as the var name,
        //didn't work when called something else
        println stringsContainingA     //prints aaa , dead

        //find: finds the first one, better than using a loop with a break:
        println someStrings.find {it.contains("d")}


        //GROOVY allows you to add functions at Runtime, bit like in javascript:
        //add a method called dong to the String class:

        String.metaClass.dong =
            {
                -> "dong"       //function returns the string
            }


        println "a".dong();    //prints dong..

        //MAP LITERAL: very nifty way of making up a map
        def mappo = [a:1,b:2]
        println mappo["a"]   //prints 1


        //DEMO OF * OPERATOR FOR EXPLODING A LIST:
        def strings = ["1","2","3"]
        def hashCodes = strings*.hashCode()
        println hashCodes //prints:  [49, 50, 51]

        //BIT OF RANDOM NUMBER GENERATION:
        def x = new Random().nextBoolean()
    }
}
