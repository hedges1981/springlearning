package ch2GroovyEssentials

/**
 * Script that demos using groovy to work with Strings, takes a quote and converts it into pig latin:
 */

for (int i in 0..<10) {
    def quote = "this is a quote happy sad no!"
    println quote
    def pigLatinWords = []   //gives us a simple empty list / array of size tbd.

    for (String word in quote.split(/\s+/)) {   //note this is a slashy string: strings that use / .... / to enclose the string
        //instead of "..." mean that you don't have to escape the \ character, which be useful for regular expressions
        pigLatinWords << pigLatinize(word)  // note use of << operator to put words into the list, this leftshift: calls like a<<b = a.leftShift(b);
    }
    println pigLatinWords.join(' ')  // join is a defaultGroovy, here creates a string joining the elements with ' '.
}

def pigLatinize(String word) {
    if (isVowel(word[0])) {        // note use of [0] on the string object, extracts the nth letter.
        return word + "way"
    }
    else {
        def pos = firstVowel(word)
        return word[pos..-1] + word[0..<pos] + "ay"
    }
}

def firstVowel(String word) {
    for (int i in 0..<word.size()) {      // again use of range expression, but with a variable rather than an int literal
        if (isVowel(word[i])) return i
    }
    return -1
}

boolean isVowel(String ch) {
    return ch.toLowerCase() in ["a", "e", "i", "o", "u"]  // in operator shortcut for seeing if a thing is inna collection.
}

