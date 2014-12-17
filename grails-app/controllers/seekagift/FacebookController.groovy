package seekagift

// import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class FacebookController {

    // def springSecurityService
    def facebookService
    // def userService
    def grailsApplication

    /**
     * Redirect to facebook login page
     */
    def login = {

        // Ask for a request token to facebook
        def authInfo = facebookService.getAuthDetailsLogin()

        if (authInfo) {
           // Save redirect uri to use it after the login with facebook
           session['FACEBOOK_NEXTURI'] = params.next

           // authInfo.authUrl is the facebook login page
           redirect(url: authInfo.authUrl)
        } else {
           flash.message = message(code:'login.fb.error')
           redirect(mapping:'loginAuth')
        }
    }

    /**
     * Callback called from facebook
     * This 'callback' action is called automatically after an ok/wrong facebook login   *
     */
    def callbackLogin = {
		def g = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')


        //If the user was logged correctly at facebook, 'getAccessToken' will return an access token
        def accessToken = facebookService.getAccessTokenLogin(params)

        // If the user could'n log in at fb, accessToken will be null
        if (!accessToken) {
            flash.message = message(code:'login.fb.denyPermissions')
            return redirect(mapping:'loginAuth')
        }

        //'getUserInfo' returns the info of the user logged in at fb (email/gender/name as user object)
        Map userInfo = facebookService.getUserInfoLogin(accessToken)

        // Find the user by facebookUserId (fbUid) instead of email
        // User user = User.findByFbUid(userInfo.fbUid)

        // Create the user if the user does not exist previously
        if (!user) 
	{

            // Check if the user exist by email. Update his fbUid and login the user
            user = User.findByEmail(userInfo.email?.toLowerCase())
            if (user) {
                user.fbUid = userInfo.fbUid
                user.save()

                springSecurityService.reauthenticate user.email?.toLowerCase()

            } else {

                def password = fakerService.bothify("????????????????")

                        def createdUser = userService.createUser(userInfo.name, userInfo.lastName, userInfo.email?.toLowerCase(), userInfo.birthday, password,
                                                userInfo.gender, RegistrationType.FACEBOOK, [fbUid:userInfo.fbUid, fbToken:accessToken.token, enabled:true, registerType:registerType, device: "WEB"])

                        if (createdUser.hasErrors()) {
                            if (createdUser.errors['email']) {
                                flash.error = message(code:'register.facebook.error.email', args:[
                                    g.createLink(mapping:"loginTwitter", absolute: true),
                                    g.createLink(mapping:"", absolute: true)]
                                )
                            } else {
                                flash.message = message(code:'register.facebook.error')
                            }
                            log.error createdUser.errors
                            return redirect(mapping:'loginAuth')
                        }

                        // Download the picture from facebook an use it as avatar
                        facebookService.downloadPicture(createdUser, userInfo.fbUid)

                    }

                    springSecurityService.reauthenticate userInfo.email?.toLowerCase()

                    session['FACEBOOK_NEXTURI'] = null
                }
            }
        } else {
            springSecurityService.reauthenticate user.email?.toLowerCase()
        }

        redirect (mapping:'loginIndex')
    }


    // /**
    //  * Redirect the user to login to facebook and when done call the action to get the friends of the user
    //  */
    // def searchFriendsInFacebook() {

    //     def authInfo = facebookService.getAuthDetailsFriends()

    //     if (authInfo) {
    //         redirect url:authInfo.authUrl
    //         return
    //     } else {
    //         flash.error = message(code:'login.fb.denyPermissions')
    //         redirect mapping:'home'
    //         return
    //     }
    // }

    // /**
    //  * Callback from Facebook to get the friends of the user
    //  */
    // def searchFriendsInFacebookCallback() {
    //     def user = springSecurityService.currentUser

    //     def accessToken = facebookService.getAccessTokenFriends(params)


    //     // If the user could not login in Facebook, accessToken will be null
    //     if (!accessToken) {
    //         flash.error = message(code:'login.fb.denyPermissions')

    //         if (session["TMP_FACEBOOK_WIZARD"]) {
    //             session["TMP_FACEBOOK_WIZARD"] = null
    //             redirect mapping:'wizardInviteMain'
    //         } else {
    //             redirect mapping:'home'
    //         }



    //         return
    //     }

    //     // Update the fbUid if needed
    //     Map userInfo = facebookService.getUserInfoFriends(accessToken)
    //     if (user.fbUid != userInfo.fbUid) {
    //         user.fbUid = userInfo.fbUid
    //         user.save()
    //     }
    //     def friends = facebookService.getFriends(user, accessToken)

    //     def sport = user.defaultSport
    //     def userSport = user.getUserSport(sport)

    //     // Find facebook friends in the system
    //     def decUsers = userSearchService.mergeFacebookFriendsWithSystemUsers(friends)


    //     //Get avatar of user dsm
    //     def picture = ""
    //     long dsmId = userService.getDSMUserId()
    //     def dsm = User.get(dsmId)
    //     if (dsm){
    //         String avatarDir = grailsApplication.config.avatar_final_dir
    //         picture = dsm.avatar
    //     }

    //     String fbText = message(code:'inviteFriends.facebook.text')
    //     String fbName = message(code:'inviteFriends.facebook.name')
    //     String link = g.createLink(absolute:true, mapping:'rootInvitation', params:[token:''])
    //     String fbAppId = grailsApplication.config.facebook.key as String

    //     def model= [friends:friends, sport:sport, user:user, userSport:userSport, fbText:fbText, fbName:fbName, link:link, fbAppId: fbAppId, picture: picture]

    //     if (session["TMP_FACEBOOK_WIZARD"]) {
    //         session["TMP_FACEBOOK_WIZARD"] = null
    //         model['template'] = "inviteFacebook"
    //         render view:'/wizard/wizardInvite', model:model
    //     } else {
    //         render view:'/user/facebookFriends', model:model
    //     }
    // }




}
