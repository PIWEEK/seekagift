package seekagift

import java.util.List;

import grails.converters.JSON

class FacebookService {
	
	def facebookGraphService

    public User getUser() {
		def connection
		
		def sessionFacebook = facebookGraphService.getFacebookData()
		def user = null
		
		if (sessionFacebook) {
			connection = JSON.parse (facebookGraphService.getFacebookProfile().toString() )
			connection.imageURL=facebookGraphService.getProfilePhotoSrc(connection.id)			
			user = new User(name: connection.first_name,photo:connection.imageURL)
		} else {
			connection = null
		}		
		
		return user
		
    }

	public User getFriend(String idFriend) {			
		def friend = facebookGraphService.getDetails('id':idFriend)		
		def user = new User()
		if (friend) {			
	
				user.id = friend.id
				user.name = friend.name
				user.photo = facebookGraphService.getProfilePhotoSrc(friend.id);
				
			}		
		return user
	}
		
    public List getFriends() {
		def friendsB = facebookGraphService.getFriends()
		def friends = []
		def user 
		if (friendsB) {
			friendsB.data.each {
				user = new User()				
				user.id = it.id
				user.name =it.name
				user.photo = facebookGraphService.getProfilePhotoSrc(it.id);
				friends.add(user);
			}
		}		
		
		return friends
		
    }
	
	public List getLikes(String idFriend) {
		
		def result = facebookGraphService.api("/${idFriend}/likes",facebookGraphService.getFacebookData(),['idFriend':idFriend])
		
		def likes = []
		def likesUser
		if (result){
			result.data.each{
				likesUser = new FriendLike()
				likesUser.id = it.id
				likesUser.category = it.category
				likesUser.name = it.name
				likes.add(likesUser)
			}
		}
		return likes
		
	}	

	/**
	 * Order a list of friendLikes by category
	 * @param likesList List of friendLikes
	 * @return friendLikes ordered by category
	 */
	public List<String> orderLikes (List<FriendLike> likesList) {
		
		def likesOcurrences = []
		likesList.category.unique().each { catName ->
			def friendLikeList = likesList.findAll{ it.category == catName}
			
			def tot = likesList.count {
				it.category == catName				 
			}

			likesOcurrences.add(tot:tot, friendLikeList: friendLikeList)		
		}

		return (likesOcurrences.sort{-it.tot}).friendLikeList.flatten()
	}
		
}
