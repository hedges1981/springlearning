package com.hedges

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification


/**
 * NOTE!
 *
 * lots of good test stuff here:
 * see; @TestFor.... brings in the controller property for testing the actual controller.
 *
 * @Mock is very use full, does all of rigging up an 'in-memory database (really just a hash map) and
 * using it to implement any persistence calls on the mocked domain classes.
 */
@TestFor(PostController)
@Mock([User, Post])
class PostControllerSpec extends Specification {

    //NOTE that this tests the PostController fetches teh right stuff from the DB and puts it in the model
    def "Get a users timeline given their id"() {

        given: "A user with posts in the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password")
        chuck.addToPosts(new Post(content: "A first post"))
        chuck.addToPosts(new Post(content: "A second post"))
        chuck.save(failOnError: true)

        and: "A loginId parameter"
        params.id = chuck.loginId

        when: "the timeline is invoked"

        def model = controller.timeline()     //NOTE: Controller brought in by the @TestFor ....
        then: "the user is in the returned model"
        model.user.loginId == "chuck_norris"
        model.user.posts.size() == 2
    }

    def "Check that non-existent users are handled with an error"() {
        given: "the id of a non-existent user"
        params.id = "this-user-id-does-not-exist"
        when: "the timeline is invoked"
        controller.timeline()
        then: "a 404 is sent to the browser"
        response.status == 404    //NOTE: remember evaluated as a test check as is after then, looks like the @TestFor also
        //injects an available response object to check?
    }


    //NOTE: see here how flash scope object is available, looks like in a controller test like this, you get access to
    //all of the various scope objects.
    def "Adding a valid new post to the timeline"() {

        given: "a mock post service"
        def mockPostService = Mock(PostService)         //NOTE mocking the postService here
        1 * mockPostService.createPost(_, _) >> new Post(content: "Mock Post")
        //TELLING the mock what to return _,_ is like arument matchers.ANY
        //putting something definite in there means it has to match the definite thing,
        //1 * means that we only can have one invocation.
        controller.postService = mockPostService
        when: "controller is invoked"
        def result = controller.addPost(
                "joe_cool",
                "Posting up a storm")
        then: "redirected to timeline, flash message tells us all is well"
        flash.message ==~ /Added new post: Mock.*/
        response.redirectedUrl == '/post/timeline/joe_cool'
    }

}
