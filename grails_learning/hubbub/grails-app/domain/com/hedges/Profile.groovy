package com.hedges

class Profile {

    static belongsTo = [user:User]
    //NOTE: this belongsTo is needed so that validation cascades to this when saving a user object.
    //the relational association on the USer class ensures saves cascade, but this is needed to ensure that a profile is validated when it is
    //saved with a user
    byte[] photo    //NOTE that this is a byte[], to hold the binary data of the photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static constraints = {
        fullName blank: false
        bio nullable: true, maxSize: 1000
        homepage url: true, nullable: true
        email email: true, blank: false
        photo nullable: true, maxSize: 2 * 1024 * 1024        //NOTE the max size on this one, as photo is a byte[]
        country nullable: true
        timezone nullable: true
        jabberAddress email: true, nullable: true
    }

    //NOTE the to String used for logging, etc.
    String toString()
    {
        "Profile of "+fullName +" id:$id"
    }

    //NOTE this one is the one that is default called by the grails scaffolding stuff to decide what to show for it in the UI.
    String getDisplayString()
    {
        fullName
    }
}