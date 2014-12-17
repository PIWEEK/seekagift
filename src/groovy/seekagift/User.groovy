package seekagift

class User {
	
	def socialNetworkService
	String id
	String name
	String photo
	
    static constraints = {
    }
	
	public List<User> getFriends() {
		return socialNetworkService.getFriends(this)
	}
	
	
//	static transients = ['friends']
	

}
