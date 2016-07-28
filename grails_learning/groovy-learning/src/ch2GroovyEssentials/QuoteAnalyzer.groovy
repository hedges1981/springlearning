package ch2GroovyEssentials

/**
 * Created by rowland-hall on 27/07/16
 */
class QuoteAnalyzer {

    List<Quote> quotes

    QuoteAnalyzer(ArrayList<Quote> quotes) {

        this.quotes = quotes;
    }

    def getQuoteCount() {
        quotes.size();      //NOTE that with the Groovy, no need to say return, can just state the thing and it gets returned.
    }

    //THIS ONE DEMOS USE OF MAPS:
    Map<String, Integer> getQuoteCountPerAuthor() {
        def result = [:]               //NOTE: short hand for setting up an empty map

        for (Quote quote in quotes) {
            if (result.containsKey(quote.author)) {
                result[quote.author] = result[quote.author] + 1     //NOTE: here how you can use the [ ] to work with the map,
                //instead of calling its actual methods.
            }
            else {
                result[quote.author] = 1
            }
        }
        return result
    }
}

