package com.hedges

class PostController {
    static scaffold = true

    static layout= 'post'

    static navigation = [
            [group:'tabs', action: 'personal', title: 'My Timeline', order: 0],
            [action: 'global', title: 'Global Timeline', order: 1]
    ]

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

    def personal()
    {
        log.error "in Persoinal"
        flash.message = "this would be the personal time line if I create it, but have taken you back to the golbal instead"
        redirect(action: 'global', params: params)
    }

    def getSomeJson(String fullUrl) {
        def origUrl = fullUrl?.encodeAsURL()
        def tinyUrl =
            new URL("http://tinyurl.com/api-create.php?url=${origUrl}").text
        render(contentType:"application/json") {
            urls(small: tinyUrl, full:fullUrl)
        }
    }

    def addPostAjax(String content) {
        try {
            log.error session.user
            def newPost = postService.createPost(session.user.loginId, content)
            def recentPosts = Post.findAllByUser(
                    session.user,
                    [sort: 'dateCreated', order: 'desc', max: 20])
            render template: 'postEntry',
                    collection: recentPosts,
                    var: 'post'
        } catch (PostException pe) {
            render {
                div(class:"errors", pe.message)
            }
        }
    }

    def global() {
        [ posts : Post.list(params), postCount : Post.count() ]
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
