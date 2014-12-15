<html>
    <head>
        <title>Seekagift</title>
    </head>
    <body>
    <fbg:resources/>

	<fb:login-button scope="email,publish_stream" onlogin="facebookLogin();" size="large">
		Login Facebook
	</fb:login-button>
	<br/><br/>

<%--
	<fcb:fbInfo/>
--%>

	<g:if test="${user}">
		<g:each var="friend" in="${friends}">
			<br/><a href="${createLink(controller:'socialNetwork', action:'likes', params:[idFriend:friend.id])}"><img src='${friend.photo}'/></a> ${friend.name}<br/> 
		</g:each>
	</g:if>
    </body>    
</html>
