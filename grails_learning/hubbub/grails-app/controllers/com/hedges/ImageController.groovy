package com.hedges

class PhotoUploadCommand {
    byte[] photo
    String loginId
}
class ImageController {

    def upload(PhotoUploadCommand puc) {
        def user = User.findByLoginId(puc.loginId)
        user.profile.photo = puc.photo       //NOTE: looks here like you implicitly get a byte[] out of the form,.. remember profile.photo is type byte[]
        redirect controller: "user", action: "profile", id: puc.loginId
    }
    def form() {
// pass through to upload form
        [ userList : User.list() ]
    }

    //NOTE: example of accepting
    def rawUpload() {
        // a Spring MultipartFile
        def mpf = request.getFile('photo')
        if (!mpf?.empty && mpf.size < 1024*200) {
            mpf.transferTo(new File(
                    "/hubbub/images/${params.loginId}/mugshot.gif"))
        }
    }

    //NOTE: how this uses output stream to write the image to the repsonse.
    def renderImage(String id) {
        def user = User.findByLoginId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())   //NOTE: presumably need to do this to allow it to work properly, i.e. so it knows when the
            //stream can be closed?
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }
}
