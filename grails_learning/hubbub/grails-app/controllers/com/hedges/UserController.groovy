package com.hedges



class UserController {

    static scaffold = true

    //ACTION for the search jsp, i.e. nothing to do, but needed here to allow it to work?     Yes, if the action is not here for a given view, you get a 404 not found.
    def search() {}

    def results(String loginId) {
        def users = User.where {       ///NOTE: doing some data SQL QUERYING HERE
            ldef users = User.where { loginId =~ "%${loginId}%" }.list()  //NOTE the use of %...%. same as in normal sql
        }.list()                          //NOTE *** in the above, on the right is always the argument of the method
                                            //on the left is always the thing checked on the db object, so be CAREFUL!!

        return [ users: users,
                term: params.loginId,
                totalUsers: User.count() ]
    }

    def advSearch(){ }//NOTE: again blank method needed for advSearch view to get shown.

    //****NOTE NOTE: this method and the assocuated advSearh.gsp are a very nice generic method for doing an advanced search
    //totally reusable for any search idea.
    def advResults() {
        def profileProps = Profile.metaClass.properties*.name

        def profiles = Profile.withCriteria {
            "${params.queryType}" {
                params.each { field, value ->
                    if (profileProps.contains(field) && value) {
                        ilike field, "%${value}%"
                    }
                }
            }
        }
        return [ profiles : profiles ]
    }

    def register() {
        if (request.method == "POST") {

            log.error params //

            def user = new User(params)  //note how the names of the params in the register gsp can be fed in here as they
            //refer to either stuff on the user direct, or stuff on the nested profile.
            if (user.validate() ) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [ user: user ]
            }
        }
    }

    def register2(UserRegistrationCommand urc) {
        log.error(" in register 2")
        if (urc.hasErrors()) {
            log.error(" has errors")
            render view: "register", model: [user: urc]
        } else {
            def user = new User(urc.properties)
            user.profile = new Profile(urc.properties)
            log.error(" saving user")
            if ( user.save()) {
                log.error(" saved user")
                flash.message =
                    "Welcome aboard, ${urc.fullName ?: urc.loginId}"
                log.error(" user has been saved, re directing")
                redirect(uri: '/')
            } else {
                log.error("user not saved")

                // maybe not unique loginId?
                return [user: urc]
            }
        }
    }

    def mailService

    def welcomeEmail(String email) {
        if (email) {
            mailService.sendMail {
                to email
                subject "Welcome to Hubbub!"
                html view: "/user/welcomeEmail", model: [ email: email ]    //NOTE: how we use the welcomeEmail.gsp view to generate the html.
            }
            flash.message = "Welcome aboard"
        }
        redirect(uri: "/")
    }

    def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }
}

class UserRegistrationCommand {

    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    //NOTE: this injected, same as anything else:
    def cryptoService

    static constraints = {
        importFrom Profile
        importFrom User
        password(size: 6..8, blank: false,
                validator: { passwd, urc ->
                    return passwd != urc.loginId
                })

        passwordRepeat(nullable: false,
                validator: { passwd2, urc ->
                    return passwd2 == urc.password
                })          //NOTE this constraint on here, the password repeat must match.
    }

    String getEncryptedPassword() {
        return cryptoService.getEncryptedPassword(password)
    }
}