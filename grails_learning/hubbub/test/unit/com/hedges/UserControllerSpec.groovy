package com.hedges

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, Profile])
class UserControllerSpec extends Specification {


    //NOTE: some good stuff in here, e.g. the use of the implicit params object, with(), further testing short cuts.
    def "Registering a user with known good parameters"() {
        given: "a set of user parameters"
        params.with {
            loginId = "glen_a_smith"
            password = "winnning"
            homepage = "http://blogs.bytecode.com.au/glen"
        }
        and: "a set of profile parameters"
        params['profile.fullName'] = "Glen Smith"
        params['profile.email'] = "glen@bytecode.com.au"
        params['profile.homepage'] = "http://blogs.bytecode.com.au/glen"
        when: "the user is registered"
        request.method = "POST"
        controller.register()
        then: "the user is created, and browser redirected"
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }


    @Unroll
    def "Registration command object for #loginId validate correctly"() {
        given: "a mocked command object"
        def urc = mockCommandObject(UserRegistrationCommand)        //NOTE: this is important, tells it that the object is a mock command object
        //and that therefore it is decorated with all the stuff to validate it
        and: "a set of initial values from the spock test"
        urc.loginId = loginId
        urc.password = password
        urc.passwordRepeat = passwordRepeat
        urc.fullName = "Your Name Here"
        urc.email = "someone@nowhere.net"

        when: "the validator is invoked"
        def isValidRegistration = urc.validate()

        then: "the appropriate fields are flagged as errors"
        isValidRegistration == anticipatedValid
        urc.errors.getFieldError(fieldInError)?.code == errorCode

        //NOTE again the use of the where, in combination with the @Unroll
        where:
        loginId | password | passwordRepeat | anticipatedValid| fieldInError | errorCode
        "glen" | "password" | "no-match" | false | "passwordRepeat" | "validator.invalid"
        "peter" | "password" | "password" | true | null | null
        "a" | "password" | "password" | false | "loginId" | "size.toosmall"
    }


    //TEST that tests the register2 method, that uses the command object:
    def "Invoking the new register action via a command object"() {
        given: "A configured command object"
        def urc = mockCommandObject(UserRegistrationCommand)
        urc.with {
            loginId = "glen_a_smith"
            fullName = "Glen Smith"
            email = "glen@bytecode.com.au"
            password = "password"
            passwordRepeat = "password"
        }
        and: "which has been validated"
        urc.validate()           //NOTE : that we validate the command object before it is passed into the controller, as that is what
        //happens in real life.
        when: "the register action is invoked"
        controller.register2(urc)
        then: "the user is registered and browser redirected"
        !urc.hasErrors()
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1
    }
}
