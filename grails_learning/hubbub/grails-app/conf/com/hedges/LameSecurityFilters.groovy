package com.hedges



class LameSecurityFilters {

    def filters = {
        secureActions(controller:'post',
                action:'(addPost|deletePost)') { //NOTE: how we specify what controller and actions it applies to:
            before = {             //executed before controller is reached
                if (params.impersonateId) {
                    session.user = User.findByLoginId(params.impersonateId)
                }
                if (!session.user) {
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
    }
}

