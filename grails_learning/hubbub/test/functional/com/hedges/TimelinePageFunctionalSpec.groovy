package.com.hedges

import com.hedges.pages.*
import geb.spock.GebReportingSpec


class TimelinePageFunctionalSpec extends GebReportingSpec {
    def "Check that timeline loads for user 'phil'"() {
        when: "we load phil's timeline"
        to TimelinePage, "phil"       //NOTE; see the url field of TimeLinePage, saying to Page takes you to the URL
        at TimelinePage     //NOTE: this is an at check to check we are on the TimelinePage, see the TimelinePage.at where the check is done,
        //see that it checks the title and the presence of an allPosts node.
        then: "the page displays Phil's full name"
        whatHeading.text() == "What is Phil Potts hacking on right now?"   //NOTE: see how it refers to the whatHeading of
        //the timelinePage object... presumably this reference is available cos we import com.hedges.pages.* ?
        //or is it the name of the test that does it , who knows?
    }


    def "Submitting a new post"() {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        to TimelinePage, "phil"
        when: "I enter a new message and post it"
        newPostContent.value("This is a test post from Geb")
        submitPostButton.click()           //NOTE here using stuff on the page object to do things, like click on the given button.
        then: "I see the new post in the timeline"
        waitFor { !posts("This is a test post from Geb").empty }
    }

    private login(String username, String password) {       //NOTE: this isn't going to compile, as no login page as we didn't bother
        //to implement the login functionality.
//        to LoginPage
//        loginIdField = username
//        passwordField = password
//        signInButton.click()
    }
}