package seekagift

class SocialNetworkController {

	def socialNetworkService
	def facebookService
	def giftService
	
	def friends() {
		def user = socialNetworkService.getUser()
		def friends
		if (user) {
			friends = socialNetworkService.getFriends(user)
		}
	
		render(view: "friends", model: [user: user, friends: friends])
	}
		
	def likes(String idFriend) {
		def user = socialNetworkService.getUser()
		def friendLikes = []
		def orderedLikes = [:]
		if (user) {
			friendLikes = socialNetworkService.getLikes(idFriend)
			orderedLikes = facebookService.orderLikes(friendLikes)

		}
		//render "Likes: " + orderedLikes.friendLikeList.name + "<br/> Categories: " + orderedLikes.friendLikeList.category  + "<br/> Ocurrences: " + orderedLikes.tot
println "====================> Likes: " + orderedLikes.name + " Categories: " + orderedLikes.category 
//		redirect(action: "gifts", params: [orderedLikes: orderedLikes, idFriend:idFriend])
		
		def friend = facebookService.getFriend(idFriend)
//		def user = socialNetworkService.getUser()
		
		def List<Gift> giftList = giftService.searchGifts(orderedLikes)
		
		// Se comenta el sorted por no necesario
//		def sortedGifts = giftService.giftSorter(giftList)
		
		def categories = giftList.category.unique()
		
		
		//println "==> groupedGifts: " + 	sortedGifts.sameCategoryGifts.name
		session['giftsList'] = giftList
		
		render(view: "gifts", model: [user: user, giftList: giftList, categories: categories, friend:friend])
	}
	
	def filteredGifts(String selectedCategory, String idFriend) {	
		
		def user = socialNetworkService.getUser()
		def friend = facebookService.getFriend(idFriend)		
		
		def giftList = session['giftsList']
		def categories = giftList.category.unique()
		def filteredGiftList = giftService.filteredGifts(selectedCategory, giftList)		
		println "_-----------------------------------> lista filtrada: "+filteredGiftList.size()
		
def lista = session['giftsList']
println "_-----------------------------------> lista filtrada sesión: "+lista.size()
println "#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ categories/category " +  	categories + "-" + selectedCategory
		render(view: "filteredGifts", model: [user: user, giftList: filteredGiftList, categories: categories, friend:friend, selectedCategory: selectedCategory])				
	}

}
