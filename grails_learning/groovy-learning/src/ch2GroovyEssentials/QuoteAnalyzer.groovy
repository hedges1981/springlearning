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
        quotes.size();
    }
}
