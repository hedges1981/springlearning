package ch2GroovyEssentials

/**
 * NOTE: copied and pasted from book p.49
 *
 * DEMOS SOME EXAMPLES OF WORKING WITH A FILE:
 *
 * CREATES A FILE CALLED dummy.txt, with the specified contents inside.
 */


try
{

    def reportFile = new File("dummy.txt")

    //NOTE: the shortcut GDM ( GroovyDefaultMethod ) for getting a print writer to do stuff to the file.
    //withPrintWriter is very good, opens the file, evaulates the function passed in to put stuff in the file,
    // then closes it again, amazing!
    reportFile.withPrintWriter   //NOTE: how the """  """ can be used to enclose a multi line String.
    { w ->
        w.println """
      note this is a multi line string,
      achievable in groovy by using tripple "
"""
    }
    println reportFile.text             //Again, a shortcut GDM to get the textual content of the file as a string.
}
catch (IOException ex)
{
    println "Unable to write to the 'report.txt' file!"
}
