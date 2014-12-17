class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/" (controller:'login', action:'home')
        "500"(view:'/error')

        // Login
        name loginFacebook: "/login/facebook-login" {controller = 'facebook'; action = 'login'}
        name loginFacebookCallback: "/login/facebook/callback" {controller = 'facebook'; action = 'callbackLogin'}
	}
}
