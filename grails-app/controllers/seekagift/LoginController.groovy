package seekagift

class LoginController {

	def socialNetworkService

    def home() {
		def user = session['FACEBOOK_FBUID']

		render(view: "/home", model: [user: user])
	}
}
