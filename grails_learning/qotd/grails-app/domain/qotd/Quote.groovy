package qotd

class Quote
{
    String content
    String author
    Date created = new Date()



    static constraints = {
        //Validation constraints go in hereski:
        author(blank:false)  //blank false is default, but blank:true on to actually allow blanks
        content(maxSize:1000, minSize:10, blank:false)   //NOTE that it appears that if you break validation that is done serverside,
        //it gives you a shite error message rather than one actually telling you what is the issue.
    }
}
