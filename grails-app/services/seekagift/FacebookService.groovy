package seekagift

import grails.converters.JSON

import org.springframework.transaction.annotation.*
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.FacebookApi
import org.scribe.model.OAuthRequest
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.model.Token


/**
 * Based on https://github.com/tomaslin/grails-inviter
 */
class FacebookService {
    def grailsApplication
    def authServiceLogin
    def authServiceFriends
    def grailsLinkGenerator

    static transactional = false

    /**
     * Singleton for authServiceLogin
     */
    private getAuthServiceLogin(){
        if (! authServiceLogin) {
            def callbackUrl = grailsLinkGenerator.link(absolute: true,mapping: 'loginFacebookCallback')
            def key = grailsApplication.config.facebook.key as String

            def secret = grailsApplication.config.facebook.secret as String

            authServiceLogin = new ServiceBuilder().provider(FacebookApi.class)
                    .apiKey(key)
                    .apiSecret(secret)
                    .scope('user_about_me,email,user_birthday,publish_stream')
                    .callback(callbackUrl)
                    .build()
        }
        return authServiceLogin
    }


    /**
     * Singleton for authServiceFriends
     */
    private getAuthServiceFriends(){
        if (! authServiceFriends) {
            def callbackUrl = grailsLinkGenerator.link(absolute: true,mapping: 'searchFriendsFacebookCallback')
            def key = grailsApplication.config.facebook.key as String

            def secret = grailsApplication.config.facebook.secret as String

            authServiceFriends = new ServiceBuilder().provider(FacebookApi.class)
                    .apiKey(key)
                    .apiSecret(secret)
                    .scope('user_about_me,email,user_birthday,publish_stream')
                    .callback(callbackUrl).build()
        }

        return authServiceFriends
    }


    /**
     * Returns the authentication data for login
     *
     * @param callbackUrl
     * @return
     */
    public getAuthDetailsLogin() {
        [authUrl:getAuthServiceLogin().getAuthorizationUrl(null)]
    }

    /**
     * Returns the authentication data for search friends
     *
     * @param callbackUrl
     * @return
     */
    public getAuthDetailsFriends() {
        [authUrl:getAuthServiceFriends().getAuthorizationUrl(null)]
    }


    /**
     * Returns the access token for login
     * @param params
     *
     * @return access token
     */
    public getAccessTokenLogin(params) {
        try {
            def code = params.code as String
            Verifier verifier = new Verifier(code)
            def token = getAuthServiceLogin().getAccessToken(null, verifier)
            return token
        } catch (Exception e) {
            log.error  "FacebookService.getAccessTokenLogin Exception" + e
            return null
        }
    }

    /**
     * Returns the access token for friends
     * @param params
     *
     * @return access token
     */
    public getAccessTokenFriends(params) {
        try {
            Verifier verifier = new Verifier(params.code as String)
            getAuthServiceFriends().getAccessToken(null, verifier)
        } catch (Exception e) {
            return null
        }
    }

    /**
     * Retrieves user information from facebook for login
     *
     * @param accessToken user token
     * @return a map with the user info
     */
    public Map getUserInfoLogin(accessToken) {

        OAuthRequest request = new OAuthRequest(Verb.GET, 'https://graph.facebook.com/me/')
        getAuthServiceLogin().signRequest(accessToken, request)
        def response = request.send()

        def jsonInfo = JSON.parse(response.body)

        /* Example
            [birthday:05/04/1988,
            location:[id:106504859386230, name:Madrid, Spain],
            link:http://www.facebook.com/profile.php?id=100000740668025,
            locale:en_US,
            updated_time:2012-08-16T12:07:16+0000,
            id:100000740668025,
            first_name:Cuenta,
            timezone:2,
            email:master.manager.test@gmail.com,
            verified:true,
            name:Cuenta de Prueba,
            last_name:de Prueba,
            gender:male]
        */

        def userInfo = [:]
        userInfo.name = jsonInfo.first_name
        userInfo.lastName = jsonInfo.last_name
        userInfo.email = jsonInfo.email
        userInfo.birthday = Utils.parseDate(jsonInfo.birthday, "MM/dd/yyyy")
        userInfo.gender = jsonInfo.gender == "male" ? GenderType.MALE : GenderType.FEMALE
        userInfo.fbUid = jsonInfo.id

        return userInfo
    }


    /**
     * Retrieves user information from facebook for friends
     *
     * @param accessToken user token
     * @return a map with the user info
     */
    public Map getUserInfoFriends(accessToken) {

        OAuthRequest request = new OAuthRequest(Verb.GET, 'https://graph.facebook.com/me/')
        getAuthServiceFriends().signRequest(accessToken, request)
        def response = request.send()

        def jsonInfo = JSON.parse(response.body)

        /* Example
            [birthday:05/04/1988,
            location:[id:106504859386230, name:Madrid, Spain],
            link:http://www.facebook.com/profile.php?id=100000740668025,
            locale:en_US,
            updated_time:2012-08-16T12:07:16+0000,
            id:100000740668025,
            first_name:Cuenta,
            timezone:2,
            email:master.manager.test@gmail.com,
            verified:true,
            name:Cuenta de Prueba,
            last_name:de Prueba,
            gender:male]
        */

        def userInfo = [:]
        userInfo.name = jsonInfo.first_name
        userInfo.lastName = jsonInfo.last_name
        userInfo.email = jsonInfo.email
        userInfo.birthday = Utils.parseDate(jsonInfo.birthday, "MM/dd/yyyy")
        userInfo.gender = jsonInfo.gender == "male" ? GenderType.MALE : GenderType.FEMALE
        userInfo.fbUid = jsonInfo.id

        return userInfo
    }


    /**
     * Get the friends from facebook
     *
     * @param user user logged from facebook
     * @param accessToken user access token
     * @return a list with the friends
     */
    def getFriends(user, accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, 'https://graph.facebook.com/me/friends/')
        getAuthServiceFriends().signRequest(accessToken, request)
        def response = request.send()

        def friends = []

        def jsonInfo = JSON.parse(response.body)
        def result
        jsonInfo.data.each {
            def contact = [:]
            contact.name = it.name
            contact.fbUid = it.id
            contact.photo = "https://graph.facebook.com/${it.id}/picture"
            result = FriendInvitationHistory.findAllByFbUidAndUser(it.id, user)
            if (result){
                contact.hasBeenInvited = true
                contact.dateInvited = Utils.stringDate(result.dateCreated[result.dateCreated.size-1],"dd/MM/yyyy")
            }else{
                contact.hasBeenInvited = false
            }
            friends << contact
        }

        friends.sort { it.name.toLowerCase() }

        return friends
    }



    /**
     * Download the avatar file from facebook and use it as avatar for the user
     *
     * @param user The user to update his avatar
     * @param fbUid The fbUid of the facebook user to get hist avatar
     */
    public void downloadPicture(User user, String fbUid) {
        def now = new Date().time

        File tmpFile = File.createTempFile('tmp_', 'imageService')
        use (FileBinaryCategory) {
            tmpFile << "https://graph.facebook.com/${fbUid}/picture?type=large".toURL()
        }

	log.error tmpFile

    }
}
