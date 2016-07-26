package qotd

/**
 * NOTE: see the file layouts/quote.gsp  , by convention this layout is applied to all pages handled by this controller
 */
class QuoteController {

    static defaultAction = "home" //means that any requests to http://localhost:8080/quote/ wil default to hitting home method:

    static scaffold = Quote  //note that in the book it says for this : scaffold = true, which would imply that this controller scaffolds
    //quote, by its name. Needs to be done this way in grails 3, to allow for scaffolding of the Quote class.
    //with this in place, certain views are auto generated, e.g. the index view at: http://localhost:8080/quote/index , which allows you to do CRUD on the
    //Quote entity

    def quoteService //automatically injects an instance of QuoteService in, for use:



    //this will ge accessed under http://localhost:8080/quote/home
    def home() {
        render "<h1>Real programmers do not eat Quiche</h1>"
    }

    /*
    Note that there is a view called random.gsp in  ...../views/quote/random.gsp
    config by convention means that a requst to /quote/random will show that view, with the model returned here:
    NOTE; that in ch1 we have added some data to the Quote entity (see notes.txt /book p.20) , this pulls out a random one
     */

    def random() {

        def randomQuote = quoteService.getRandomQuote()  //NOTE; refactored out to call the service
        [quote: randomQuote]
    }

}
