package com.hedges



class LameSecurityFilters {


    //NOTE that these filters provide security around adding posts

    //****NOTE && false added to checks, to effectively disable this filter, as it was in the way  ,
    //NOTE: added it so that it always sets session user as chuck norris.

    def filters = {
        secureActions(controller:'post',
                action:'(addPost|deletePost)') { //NOTE: how we specify what controller and actions it applies to:
            before = {             //executed before controller is reached
                if (params.impersonateId && false) {       //NOTE how filters can get at the usual controller varialbes, i.e: also can ((request, response, params, session, flash)
                    session.user = User.findByLoginId(params.impersonateId)
                }
                if (!session.user && false) {
                    redirect(controller: 'login', action: 'form')
                    return false     //RETURN false, the filter has blocked the request.
                }


            }
            after = { model->     //executed after controller is returned but before view is rendered, possibly allows you to modify the model?
            }
            afterView = {        //done after the view has been rendered.....
                log.debug "Finished running ${controllerName} –${actionName}"
            }
        }

        defaultUserToChuckNorris(controller:'*',
                action:'*') {
            before = {
                session.user = User.findByLoginId("chuck_norris")
                }

            after = { model->     //executed after controller is returned but before view is rendered, possibly allows you to modify the model?
            }
            afterView = {        //done after the view has been rendered.....
                log.debug "Finished running ${controllerName} –${actionName}"
            }
        }
    }
}

