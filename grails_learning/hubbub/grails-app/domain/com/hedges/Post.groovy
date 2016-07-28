package com.hedges

class Post {
    String content
    Date dateCreated
    static constraints = {
        content blank: false
    }

    //NOTE: theis belongs to, shows that one of these is owned by the User obj, see the reverse of the rel on the User obj, the hasMany:
    static belongsTo = [ user : User ]      //NOTE: see book p.83, the belongs to means that a delete of a user will cascade the delete to
    //these Post objects.

    static hasMany = [tags: Tag]
}
