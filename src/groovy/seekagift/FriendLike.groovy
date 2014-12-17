package seekagift

class FriendLike {
	
	def socialNetworkService
	String id
	String category
	String name
	
    static constraints = {
    }
	
	public List<FriendLike> getLikes() {
		return socialNetworkService.getLikes(this)
	}
	
	

}
