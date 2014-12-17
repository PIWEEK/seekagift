package seekagift

class LoginController {

	def socialNetworkService
	
    def home() { 		
		def user = socialNetworkService.getUser()
		def friends
		if (user) {
			friends = socialNetworkService.getFriends(user)
		}

		render(view: "/home", model: [user: user, friends: friends])
	}
}
