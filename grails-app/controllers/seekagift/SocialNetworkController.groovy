package seekagift

class SocialNetworkController {

        // def socialNetworkService
        def facebookService
        def giftService
        
                
        def likes(String idFriend) {
                def accessToken = session['FACEBOOK_FBACCESSTOKEN']
                def user = session['FACEBOOK_FBUID']

                def friendLikes = []
                def orderedLikes = [:]
                if (idFriend) {
                        friendLikes = facebookService.getLikes(idFriend, accessToken)

                        println friendLikes

                        orderedLikes = facebookService.orderLikes(friendLikes)

                }
                //render "Likes: " + orderedLikes.friendLikeList.name + "<br/> Categories: " + orderedLikes.friendLikeList.category  + "<br/> Ocurrences: " + orderedLikes.tot
println "====================> Likes: " + orderedLikes.name + " Categories: " + orderedLikes.category 
//              redirect(action: "gifts", params: [orderedLikes: orderedLikes, idFriend:idFriend])
                
                def List<Gift> giftList = giftService.searchGifts(orderedLikes)
                
                def categories = giftList.category.unique()
                
                
                //println "==> groupedGifts: " +        sortedGifts.sameCategoryGifts.name
                session['giftsList'] = giftList
                
                render(view: "gifts", model: [user: user, giftList: giftList, categories: categories, friend:idFriend])
        }
        
        def filteredGifts(String selectedCategory, String idFriend) {   
                
                def user = session['FACEBOOK_FBUID']
                def friend = facebookService.getFriend(idFriend)                
                
                def giftList = session['giftsList']
                def categories = giftList.category.unique()
                def filteredGiftList = giftService.filteredGifts(selectedCategory, giftList)            
                println "_-----------------------------------> lista filtrada: "+filteredGiftList.size()
                
def lista = session['giftsList']
println "_-----------------------------------> lista filtrada sesión: "+lista.size()
println "#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ categories/category " +     categories + "-" + selectedCategory
                render(view: "filteredGifts", model: [user: user, giftList: filteredGiftList, categories: categories, friend:friend, selectedCategory: selectedCategory])                               
        }

        def giftForMe() {
                def accessToken = session['FACEBOOK_FBACCESSTOKEN']
                def user = session['FACEBOOK_FBUID']
                def idFriend = "me"

                def friendLikes = []
                def orderedLikes = [:]
                if (user) {
                        friendLikes = facebookService.getLikes(idFriend, accessToken)

                        println friendLikes

                        orderedLikes = facebookService.orderLikes(friendLikes)

                }
                //render "Likes: " + orderedLikes.friendLikeList.name + "<br/> Categories: " + orderedLikes.friendLikeList.category  + "<br/> Ocurrences: " + orderedLikes.tot
println "====================> Likes: " + orderedLikes.name + " Categories: " + orderedLikes.category 
//              redirect(action: "gifts", params: [orderedLikes: orderedLikes, idFriend:idFriend])
                
                def List<Gift> giftList = giftService.searchGifts(orderedLikes)
                
                def categories = giftList.category.unique()
                
                
                //println "==> groupedGifts: " +        sortedGifts.sameCategoryGifts.name
                session['giftsList'] = giftList
                
                render(view: "gifts", model: [user: user, giftList: giftList, categories: categories, friend:idFriend])
        }
}
