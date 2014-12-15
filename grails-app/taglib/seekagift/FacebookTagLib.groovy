package seekagift

import grails.converters.JSON

class FacebookTagLib {
	static namespace = 'fcb'
	def facebookGraphService
	def fbInfo = { attrs ->
		if (session.facebook) {
			def myInfo = JSON.parse (facebookGraphService.getFacebookProfile().toString() )

			out << "<br/>id" << myInfo.id
			out << "<br/>first_name:" << myInfo.first_name
			out << "<br/>Last Name:" << myInfo.last_name
			out << "<br/>Gender:" << myInfo.gender
			out << "<br/>Timezone:" << myInfo.timezone
			out << "<br/>Home Town:" << myInfo.hometown
			out << "<br/>Link:" << myInfo.link
			out << "<br/>Photo:" << "<img src='${facebookGraphService.getProfilePhotoSrc(myInfo.id);}'/>"
		} else {
			out << "Not logged in to Facebook"
		}
	}
}
