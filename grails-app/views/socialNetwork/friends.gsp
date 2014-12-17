
<!doctype html>
<head>
    <title>Seekagift</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <meta name="description" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1">

	<meta name="Robots" content="index,follow" />
	<meta name="author" content="Luka Cvrk (www.solucija.com)" />
	<meta name="keywords" content="shop, crawler" />
	<%--<meta name="layout" content="main" /> --%>
	<%--	<link rel="stylesheet" type="text/css" href="/css/screen.css" media="screen" />--%>
    <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}" /><%-- basic boilerplate styles  --%>

<%--    <g:layoutHead />--%>
</head>
<body>
	<fbg:resources/>

	<div id="content">
		<p id="top">Confucio pensó alguna vez: "¿Qué le regalaré a ese tío que me cae tan mal si no se ni cómo se llama?"</p>
		<div id="logo">
			<img src='/seekagift/images/mini-gift.png'/>
			<h1><a href="/seekagift">Seekagift</a></h1>
		</div>

		<ul id="menu">
			<g:if test="${user}">
			    LOGADO
			</g:if>

		</ul>

		<div class="line"></div>
		<div id="home_pitch">
			<div style="float:left; width:80px;">
				<p align='left'><img src='../images/step2.jpg' width='70px' height='70px'/></p>
			</div>
			<div style="float:right; width:850px;"">
				<h1>Selecciona ahora uno de tus amigos de la lista, y deja que Seekagift te muestre sugerencias de regalos	</h1>
			</div>
		</div>
			<g:if test="${user}">

					<g:each var="friend" in="${friends}">
					<div class="friend-item">
<%--						<br/><a href="${createLink(controller:'socialNetwork', action:'likes', params:[idFriend:friend.id])}"><img src='${friend.photo}'/></a> ${friend.name}<br/>--%>
						<a href="${createLink(controller:'socialNetwork', action:'likes', params:[idFriend:friend.id])}"><img src='${friend.photo}'/></a> <span>${friend.name}</span>
					</div>
					</g:each>
			</g:if>
<%--		<div class="right">--%>
<%--			<h3>12 / 2009</h3>--%>
<%--			<p>Duis rutrum dapibus diam. Sed turpis sem, suscipit et, ullamcorper vel, aliquam in, tellus. Phasellus interdum. Sed pede. Fusce semper tellus quis sapien.</p>--%>
<%--			<br />--%>
<%--			<h3>12 / 2009</h3>--%>
<%--			<p>Duis rutrum dapibus diam. Sed turpis sem, suscipit et, ullamcorper vel, aliquam in, tellus. Phasellus interdum. Sed pede. Fusce semper tellus quis sapien.</p>--%>
<%--		</div>--%>
		<div id="footer">
			<p>&copy; Copyright 2009 Distinctive &minus; Design: Luka Cvrk, <a href="http://www.solucija.com" title="Free CSS Templates">Solucija</a></p>
		</div>
	</div>
</body>
</html>











