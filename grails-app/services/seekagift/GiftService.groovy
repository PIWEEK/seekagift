package seekagift

class GiftService {

	def amazonService
	
    /**
     * @param giftList
     * @return
     */
    public List<Gift> giftSorter(List<Gift> giftList) {
		
		def groupedGifts = []
		
		giftList.category.unique().each { catName ->
			def sameCategoryGifts = giftList.findAll{ it.category == catName}
			def tot = giftList.count {
				it.category == catName
			}	
			groupedGifts.add(tot:tot, sameCategoryGifts: sameCategoryGifts)
		}		
		
		return groupedGifts.sort{-it.tot}
		
    }


	/**
	 * @param likes
	 * @return
	 */
	public List<Gift> searchGifts(List<FriendLike> likes) {
		def gifts = []
		likes.category.unique().each { 
//			gifts += amazonService.searchByKeywords(it, "All")
			gifts += amazonService.searchByKeywords(it, "Books")
			gifts += amazonService.searchByKeywords(it, "Apparel")
			gifts += amazonService.searchByKeywords(it, "Electronics")
			
		}
		
		return gifts
	}
	
	
	public List<Gift> filteredGifts(String category, List<Gift> giftList) {
		
		def filteredGifts = giftList.findAll{it.category == category}
println "COOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOUNT: " + filteredGifts.size() 		
		return filteredGifts		
	}
		
}
