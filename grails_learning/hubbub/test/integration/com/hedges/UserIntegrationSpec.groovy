package com.hedges



import spock.lang.*

/**
 *  Decent test class that contains examples of CRUD stuff
 */
class UserIntegrationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    //NOTE: example of persisting a new user:
    def "Saving our first user to the database"() {
        given: "A brand new user"
        def joe = new User(loginId: 'joe', password: 'secret')
        when: "the user is saved"
        joe.save()          //NOTE: like calling a JPA persist
        then: "it saved successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId     //NOTE: like a JPA 'find by ID'
    }

    //NOTE: example of updating an existing user:
    def "Updating a saved user changes its properties"() {

        given: "An existing user"

        def existingUser = new User(loginId: 'joe', password: 'secret')
        existingUser.save(failOnError: true)

        when: "A property is changed"

        def foundUser = User.get(existingUser.id)
        foundUser.password = 'sesame'      //NOTE changing the password here:
        foundUser.save(failOnError: true)  //NOTE the use of this parameter , it is a map literal.setting the params for the save
        //NOTE: fail on error means it will throw exception if any validation tests fail
        then: "The change is reflected in the database"
        User.get(existingUser.id).password == 'sesame'
    }

    //NOTE: example of deleting an existing user:
    def "Deleting an existing user removes it from the database"() {
        given: "An existing user"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)
        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true)      //NOTE the use of the delete here, note that we flush, as we subsequently do
        // a re-query to prove that it has been deleted.
        then: "The user is removed from the database"
        !User.exists(foundUser.id)
    }

    def "Saving a user with invalid properties causes an error"() {

        given: "A user which fails several field validations"
        def user = new User(loginId: 'joe',
                password: 'tiny')

        when: "The user is validated"

        user.validate()   //NOTE the way of validation, by calling this call. Doesn't throw an exception, rather it
        //adds the errors, which are then tested:

        then:

        user.hasErrors()    //NOTE the implicit assertions here in the then: section of the test:
        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny" == user.errors.getFieldError("password").rejectedValue
        !user.errors.getFieldError("loginId")
    }

    def "Recovering from a failed save by fixing invalid properties"() {
        given: "A user that has invalid properties"
        def chuck = new User(loginId: 'chuck',
                password: 'tiny')
        assert chuck.save() == null      //NOTE: looks like it returns null from this if there are errors saving
        //NOTE: calling save(failOnError: true) makes it throw a ValidationException, as we would expect
        assert chuck.hasErrors()     //NOTE: looks like you need the assert here, as can only do the implicit testing after
        //the then: block.

        when: "We fix the invalid properties"
        chuck.password = "fistfist"

        chuck.validate()
        then: "The user saves and validates fine"
        !chuck.hasErrors()
        chuck.save()
    }

    //NOTE: this tests the cross field validation example, see on the User class.
    def "Can't save a user with pwd same as loginId"() {
        given: "A user that has pwd same as loginId"
        def chuck = new User(loginId: 'fffffff',
                password: 'fffffff')

        when: "we try to save the user"

        chuck.save()

        then: "check has right errors"
        chuck.hasErrors()
        "validator.invalid" == chuck.errors.getFieldError("password").code


    }

    def "Ensure a user can follow other users"() {

        given: "A set of baseline users"
        def joe = new User(loginId: 'joe', password:'password').save()
        def jane = new User(loginId: 'jane', password:'password').save()
        def jill = new User(loginId: 'jill', password:'password').save()

        when: "Joe follows Jane & Jill, and Jill follows Jane"
        joe.addToFollowing(jane)
        joe.addToFollowing(jill)
        jill.addToFollowing(jane)

        then: "Follower counts should match following people"
        2 == joe.following.size()
        1 == jill.following.size()

    }



}
