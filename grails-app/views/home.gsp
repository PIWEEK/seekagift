
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
<%--    <link rel="stylesheet" type="text/css" href="/css/screen.css" media="screen" />--%>
    <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}" /><%-- basic boilerplate styles  --%>

<%--    <g:layoutHead />--%>
</head>
<body>
        <div id="content">
                <p id="top">Confucio pensó alguna vez: "¿Qué le regalaré a ese tío que me cae tan mal si no se ni cómo se llama?"</p>
                <div id="logo">
                        <img src='images/mini-gift.png'/>
                        <h1><a href="/seekagift">Seekagift</a></h1>
                </div>

                <ul id="menu">
                        <g:if test="${user}">
                                LOGADO user: ${user}
                        </g:if>
                </ul>


                <div class="line"></div>
                <div id="pitch">
                        <h1>¿Cómo que qué le vas a regalar?<br />¡Si tu amigo te lo está diciendo continuamente!</h1>
                        <h2>Aprovecha las redes sociales para hacer el regalo perfecto.</h2>
                </div>
                <div class="third">
                        <p align='left'><img src='images/step1.jpg'/></p>
                        <h3>Conéctate a Facebook</h3>
                        <p>Contectandote a facebook accederás a un montón de información que ya tienes sobre tus amigos.</p>
                        <p><g:link mapping='loginFacebook'>Login Facebook</g:link></p>
                </div>
                <div class="third">
                        <p align='left'><img src='images/step2.jpg'/></p>
                        <h3 align='center'>Elige a uno de tus amigos</h3>
                        <p>Ahora llegó el momento de elegir al agraciado.</p>
                                <g:if test="${user}">
                                <a href="${createLink(mapping:'searchFriendsFacebook')}">Tu lista de amigos...</a>
                                <g:link mapping="giftForMe">O un regalo para ti</g:link>
                                </g:if>

                </div>
                <div class="third last">
                        <p align='left'><img src='images/step3.jpg'/></p>
                        <h3>Elige el regalo ¡Así de fácil!</h3>
                        <p>Te ofreceremos una lista de regalos basado en la información que tu amigo a compartido en las redes sociales.</p>
                </div>
                <div class="line"></div>
                <div class="left">
                        <h4>¿Qué es Seekagift?</h4>
                        <p><em>Seekagift es un proyecto nacido en la <a href="http://www.piweek.com">PiWeek</a>. Seekagift se conecta a las redes sociales para obtener de ellas la información más fidedigna posible con la que obtener ideas para poder regalar a tus amigos.</em></p>
                </div>

                <div class="right">
                        <h4>¿Algunas ideas?</h4>
                        <p><a href="http://www.amazon.com/Aqua-Sphere-Seal-Goggle-Clear/dp/B001Q3LTJQ/ref=sr_1_37?s=sporting-goods&ie=UTF8&qid=1342174409&sr=1-37">
                                <img src='http://ecx.images-amazon.com/images/I/51CuNrF2alL._AA1000_.jpg' width="260px" height="300px"/>
                        </a>
                        <a href="http://www.amazon.es/gp/product/B0030SV25Q/ref=s9_simh_gw_p23_d0_g23_i1?pf_rd_m=A1AT7YVPFBWXBL&pf_rd_s=center-2&pf_rd_r=1RF62S8THP2JQEB4EPBG&pf_rd_t=101&pf_rd_p=312235527&pf_rd_i=602357031">
                                <img src='http://ecx.images-amazon.com/images/I/415kwMq1BeL._SL500_AA300_.jpg' width="260px" height="300px"/>
                        </a>
                        </p>
                </div>

<%--            <div class="right">--%>
<%--            </div>--%>
<%--            <div class="right">--%>
<%--                    <h3>12 / 2009</h3>--%>
<%--                    <p>Duis rutrum dapibus diam. Sed turpis sem, suscipit et, ullamcorper vel, aliquam in, tellus. Phasellus interdum. Sed pede. Fusce semper tellus quis sapien.</p>--%>
<%--                    <br />--%>
<%--                    <h3>12 / 2009</h3>--%>
<%--                    <p>Duis rutrum dapibus diam. Sed turpis sem, suscipit et, ullamcorper vel, aliquam in, tellus. Phasellus interdum. Sed pede. Fusce semper tellus quis sapien.</p>--%>
<%--            </div>--%>


                <div id="footer">
                        <p>&copy; Copyright 2009 Distinctive &minus; Design: Luka Cvrk, <a href="http://www.solucija.com" title="Free CSS Templates">Solucija</a></p>
                </div>
        </div>
</body>
</html>
