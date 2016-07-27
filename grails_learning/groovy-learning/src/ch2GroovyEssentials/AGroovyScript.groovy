package ch2GroovyEssentials

/**
 * The exact same script as on p.34 of book
 * NOTE: it is a script, not a class, is effectively read from top to bottom and executed.
 *
 * Is basically run like a main script, this is also the kind of thing that you can run in the groovy console.
 *
 */

def authors = [
        "Peter Ledbrook",
        "Glen Smith"
]
def quoteParts = [
        ["Time", "waits", "for no man"],
        ["The roundhouse kick", "solves", "all problems"],
        ["Groovy", "is", "the bees knees"]
]
for (int i in 0..<10) {
    def quote = createQuote(quoteParts, authors)
    println quote
}
String createQuote(List quoteParts, List authors) {
    def rand = new Random()
    def n = quoteParts.size()
    def m = authors.size()
    return quoteParts[rand.nextInt(n)][0] + ' ' +
            quoteParts[rand.nextInt(n)][1] + ' ' +
            quoteParts[rand.nextInt(n)][2] + ' by ' +
            authors[rand.nextInt(m)]
}
