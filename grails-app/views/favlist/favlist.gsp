
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
<%--	<link rel="stylesheet" type="text/css" href="/css/screen.css" media="screen" />--%>
    <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}" /><%-- basic boilerplate styles  --%>
	
<%--    <g:layoutHead />--%>
 <g:javascript src="libs/jquery-1.7.2.js" />

</head>
<body>
	<fbg:resources/>
	
	<div id="content">
		<p id="top">Confucio pensó alguna vez: "¿Qué le regalaré a ese tío que me cae tan mal si no se ni cómo se llama?"</p>
		<div id="logo">
			<img src='/seekagift/images/mini-gift.png'/>
			<h1><a href="index.html">Seekagift</a></h1>
		</div>

		<div id="home_pitch" class="gift-list">
			<p align='left'><img src='../images/step3.jpg'  width='70px' height='70px'/></p>
			<div class="gift-list-head">
			<h1>Lista de Regalos favoritos</h1>
			<a class="btn1" href="javascript:window.history.back();">&lt; Volver a Lista de Regalos</a>
			</div>						
		</div>
			<g:if test="${session.favList}">
					
					<g:each var="gift" in="${session.favList}">
						<div class="gift">							
							<p><a href="${gift.giftURL}"><img src='${gift.photoURL}' width="260px" height="300px"/></a></p>
							<p class="title">${gift.name.truncate(20,true)}</p>
							<p class="title">${gift.price}</p>
						</div>	
						
					</g:each>					
			</g:if>
		<div id="footer">
			<p>&copy; Copyright 2009 Distinctive &minus; Design: Luka Cvrk, <a href="http://www.solucija.com" title="Free CSS Templates">Solucija</a></p>
		</div>	
	</div>
</body>
</html>
