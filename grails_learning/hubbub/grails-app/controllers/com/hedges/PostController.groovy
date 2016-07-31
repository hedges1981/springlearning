package com.hedges

class PostController {
    static scaffold = true

    def postService //injects an instance of postService

    //NOTE: this index () gives the default action, if. you browse to e.g. ..../post
    //defaults to showin the timeline view for ChucjNorris.
    def index() {
        if (!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline() {

        def user = User.findByLoginId(params.id)  //NOTE the way this works, a url of e.g. ...post/timeline/chuck_norris
        //passes chuck_norris as the val of params.id. this is like a grails convention.
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }


    //NOTE: this was refactored to make it use a service,
    def addPost_Old() {
        def user = User.findByLoginId(params.id)
        if (user) {
            def post = new Post(params)   //NOTE; params has a .content field, this binds it to the .content of the post
            user.addToPosts(post)
            if (user.save()) {
                flash.message = "Successfully created Post"    //NOTE: 'flash scope, way of flashing a message to the user.
            } else {
                flash.message = "Invalid or empty post"
            }
        } else {
            flash.message = "Invalid User Id"
        }
        redirect(action: 'timeline', id: params.id)       //NOTE: here the redirect to show a different view at the end...how to control application flowo.
    }

    //NOTE: the use of data binding here, binds variables straight to form values, remember id is the defaulted one from the URL, e.g. chuck_norris
    def addPost(String id, String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        log.error "post done ok, re directing"
        redirect(action: 'timeline', id: id)
    }
}
