package seekagift

class FavlistController {
	
    def favlist = {
    }
	
	def favstar = { String idGift ->
			
		 if (idGift) {
			
			 def create = false;
			 def favList = session['favList']
			 
			 println "favlist: " +  favList
			 
			 if (!favList){
				 favList = []
			 }
			 def giftsList = session['giftsList']
			 println "giftsList: " +  giftsList.id
			 println "idGift: " +  idGift
			 
			 def gift = giftsList.find { it.id as String == idGift }
			
	//		giftsList.sameCategoryGifts.each{giftCategory->
	//		
	//			def gift = giftCategory.findAll{it.id as Long == idGift}
	//			if (gift) {
	//				sameGift = gift[0]
	//			}
	//		 }
	
	//		 if (sameGift) {
			
			 println "gift: " +  gift
			if (gift) {
				
	
				 if (!favList.find { it.id as String == idGift } )	{			 
					 favList += gift
				 } else {		
					 	favList -= gift
				 }
			 }
			
			 println "favlist: " +  favList
			 session['favList'] = favList
		 }
	}
}
