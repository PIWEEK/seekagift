class UrlMappings {

        static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        name home: "/" {controller='login'; action='home'}
        "500"(view:'/error')

        // Login
        name loginFacebook: "/login/facebook-login" {controller = 'facebook'; action = 'login'}
        name loginFacebookCallback: "/login/facebook/callback" {controller = 'facebook'; action = 'callbackLogin'}

        // Search friends in Facebook
        name searchFriendsFacebook: "/user/search/facebook/friends" { controller = 'facebook'; action = [GET:'searchFriendsInFacebook'] }
        // name searchFriendsFacebookCallback: "/user/search/facebook/friends/callback" { controller = 'facebook'; action = [GET:'searchFriendsInFacebookCallback'] }

        name giftForMe: "/gift/me/" { controller = 'socialNetwork'; action="giftForMe" }

        }
}
