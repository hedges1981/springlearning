package com.hedges
import geb.spock.GebReportingSpec


class TimelineFunctionalSpec extends GebReportingSpec {


    def "Check that timeline loads for user 'phil'"() {

        when: "we load phil's timeline"

        go "users/phil"       //NOTE how easy it is to go to the link to test:
        then: "the page displays Phil's full name"
        $("#newPost h3").text() ==
                "What is Phil Potts hacking on right now?"
    }


    def "Submitting a new post"() {
        given: "I log in and start at my timeline page"
        login "frankie", "testing"
        go "users/phil"
        when: "I enter a new message and post it"
        $("#postContent").value("This is a test post from Geb") //NOTE: #postContent referrs to the id of the text area in this case here:
        $("#newPost").find("input", type: "button").click() //NOTE the click to click on a button
        then: "I see the new post in the timeline"
        waitFor {
            $("div.postText", text: "This is a test post from Geb").present
        }

    }

    //NOTE; commonly used case in separate method.
    private login(String username, String password) {
        go "login/form"
        $("input[name='loginId']").value(username)
        $("input[name='password']").value(password)
        $("input[type='submit']").click()
    }
}