package com.hedges

import spock.lang.*

class PostIntegrationSpec extends Specification {

    def "Adding posts to user links post to user"() {

        given: "A brand new user"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)

        when: "Several posts are added to the user"
        user.addToPosts(new Post(content: "First post... W00t!"))   //NOTE this dynamic method to shove stuff straight into the Posts field:
        user.addToPosts(new Post(content: "Second post...")) //NOTE: p.84, apparently when done this way, the Post objs are automatically
        //NOTE: p.87, these addToXXX methods can only be used when there is the right belongs to. Posts belongs to User, so it works
        //with no belongsTo, it no work.
        //persisted to the DB. No need to call save() on a post object here.
        user.addToPosts(new Post(content: "Third post..."))

        then: "The user has a list of posts attached"
        3 == User.get(user.id).posts.size()
    }

    def "Ensure posts linked to a user can be retrieved"() {

        given: "A user with several posts"
        def user = new User(loginId: 'joe', password: 'secret')
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))
        user.save(failOnError: true)

        when: "The user is retrieved by their id"
        def foundUser = User.get(user.id)
        def sortedPostContent = foundUser.posts.collect { it.content }.sort()   //NOTE this shorthand way of iterating over the posts
        //and grabbing the contents... like with the Java 8 Streams.
        //NOTE: they have to be sorted for the comparison in the test, the return is of type set,i.e. un-sorted, so collapse them to a definite order
        then: "The posts appear on the retrieved user"
        sortedPostContent == ['First', 'Second', 'Third']

    }

    def "Exercise tagging several posts with various tags"() {

        given: "A user with a set of tags"
        def user = new User(loginId: 'joe', password: 'secret')
        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)
        user.save(failOnError: true)

        when: "The user tags two fresh posts"
        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)

        def bothPost = new Post(content: "A groovy and grails post")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)

        then:
        user.tags*.name.sort() == [ 'grails', 'groovy'] //NOTE here the use of the * operator, for exploding a collection. see other note in groovy-learning
        1 == groovyPost.tags.size()
        2 == bothPost.tags.size()

    }

}