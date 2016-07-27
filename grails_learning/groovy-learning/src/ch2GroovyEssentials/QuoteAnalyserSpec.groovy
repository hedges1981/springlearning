package ch2GroovyEssentials
import spock.lang.*
/**
 * CODE COPIED FROM BOOK P41
 *
 * DEMOS SOME BASICAL FEATURES OF A SPOCK TEST
 *
 */
class QuoteAnalyserSpec extends Specification    //note that a Spock test extends Specification always.
{
        //@Shared... means that this variable can be shared between feature methods
        @Shared quotes = [
                new Quote(author: "Peter Ledbrook",
                        content: "Time waits for no man"),
                new Quote(author: "Glen Smith",
                        content: "Groovy solves all problems")]


    //THIS IS known as a 'feature method', note that it has a string for a name.
    //looks like the idea is that a feature method tests a 'feature' of the thing that is under test.
        def "Total number of quotes"() {
            given: "An analyzer initialized with known quotes"
            //Looks like SPOCK test allows you to say given, when, etc outside of comments,
            def analyzer = new QuoteAnalyzer(quotes)
            when: "I ask for the quote count"
            def quoteCount = analyzer.quoteCount
            //this actually calls the method getQuoteCount()... looks like when called this way, groovy implicitly adds the get to the fnt.
            then: "The number of quotes in the test list is returned"
            quoteCount == 2
        }

    //DIRECT COPY OF CODE ON BOOK PAGE 44
    @Unroll
    def "Total number of quotes using parameterised test:"() {
        given: "An analyzer initialized with known quotes"
        def analyzer = new QuoteAnalyzer(inputQuotes)
        when: "I ask for the quote count"
        def quoteCount = analyzer.quoteCount
        then: "The number of quotes in the test list is returned"
        quoteCount == expected
        where:
        inputQuotes | expected
        [] | 0
        quotes | 2
    }
}

}
