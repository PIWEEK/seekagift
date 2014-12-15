package seekagift

class FacebookController {
	
	def facebookService

    def index() {
		
		def connection = facebookService.getUser()
//		myInfo.id
//		myInfo.first_name
//		myInfo.last_name
//		myInfo.gender
//		myInfo.timezone
//		myInfo.hometown
//		myInfo.link
		
		render "-->" + connection + "<--"
	}
}
