package com.hedges

class Tag {
    String name
    User user

    static belongsTo = [ User, Post ] //NOTE: see the reverse mappings on the User and Post objects
    static hasMany = [ posts: Post ]   //NOTE: also reverse has Many on the Post, we have a bi-directional many to many rel.

    static constraints = {
        name blank: false
    }
}