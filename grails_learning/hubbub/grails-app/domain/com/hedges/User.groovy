package com.hedges

class User {

    String loginId
    String password

    static hasOne = [profile:Profile] //  NOTE: this models a 1-1 relationship, the User has a field called profile, that
    //points to an object of type Profile
    //NOTE that it defaults to eager loading for a hasOne relationship

    //NOTE: TODO: look into how you might have >1 has one relationship,..idiot, you would just have > 1 in the map!!!

    static hasMany = [posts: Post,  tags: Tag, following: User]  //NOTE: this way of doing the 1-many rels, again add more to the map to have > 1 rel.
    //NOTE: the circular relationship, a user can follow many other users.

    Date dateCreated


    static mapping = {      //NOTE: here we specify the sort order of the posts object, like you would with an @annotation in JPA:
        posts sort:'dateCreated'
    }

    static constraints = {
        loginId size: 3..20, unique: true, nullable: false

        //NOTE: default is for fields to be non-optional, so you need
        //to specify nullable: true to get that.

        //**NOTE: the cross-field 'custom validation done on password, good pattern, don't want password to be = userId
        password size: 6..8, nullable: false, validator: {passwd, user -> passwd !=user.loginId}

        profile (nullable:true )  //NOTE: makes no diff if the constaint info is in ( ), but earlier versions
        //of grails might enforce it

        //NOTE: lots of different validators available, see book page 77. Get the impression that you can create a custom
        //validator also.

        //NOTE: see page 79, use of importFrom to share constraints between objects, save repeating code.
        //TODO: look into a way that they might be held in a completely separate 'Constraints' class, book example seems
        //a bit wierd, having ApplicationUser class reference the User class.
    }
}

