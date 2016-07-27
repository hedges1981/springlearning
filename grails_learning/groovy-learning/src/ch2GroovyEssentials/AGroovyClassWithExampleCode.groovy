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

    }
}
