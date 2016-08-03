package com.hedges.pages


import geb.Page

/**
 * NOTE how this wraps the looking up of page elements so that the actual references to the markup are only defined in
 * one place. This is so that if e.g. someone refactors the page, the stuff relating to its markup needs only changing on one
 * place, rather than throughout all the tests.
 */

class TimelinePage extends Page {
    static url = "users"
    static content = {
        whatHeading { $("#newPost h3") }    //NOTE: so this field is the h3 element of div #newPost
        newPostContent { $("#postContent") }
        submitPostButton { $("#newPost").find("input", type: "button") }
        posts { content ->
            if (content) $("div.postText", text: content).parent()  //NOTE here, content is not a boolean, but a string,
                //seems like you can in groovy do if checks on objects like in java script.
            else $("div.postEntry")
        }
    }
    static at = {
        title.startsWith("Timeline for ")
        $("#allPosts")
    }
}