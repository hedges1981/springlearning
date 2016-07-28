package com.hedges

class Profile {

    User user
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
}