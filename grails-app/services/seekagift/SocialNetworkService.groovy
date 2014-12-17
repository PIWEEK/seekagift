package seekagift

import grails.converters.JSON

class SocialNetworkService {

    def facebookService

    public List getFriends(String accessToken) {
		return facebookService.getFriends(accessToken)
    }

    public User getUser() {
      return facebookService.getUser()
    }

    public List getLikes(String userId) {
      return facebookService.getLikes(userId)
    }

}
