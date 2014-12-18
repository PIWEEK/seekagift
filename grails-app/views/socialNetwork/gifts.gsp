
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
 <g:javascript src="libs/jquery-1.7.2.js" />
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
<%--                                <li><img src='${user}'/></li><br/> --%>
                                <li>${user}</li><br/>                              
                        </g:if>         
                </ul>   
                                
                <div class="relative">          
                <div class="line line2"></div>
                <div id="home_pitch">
                        <div style="float:left; width:80px;">
                                <p align='left'><img src='../images/step3.jpg' width='70px' height='70px'/></p>
                        </div>
                        <div style="float:right; width:850px;"">                        
                                <h1>Selecciona ahora uno de tus regalos de la lista</h1>
                                <g:if test="${friend}">
                                        <div class="selectfriend">
<%--                                        <img src='${friend.photo}'/><span>${friend.name}</span> --%>
                                        <span>${friend}</span>
                                        </div>          
                                </g:if>                                 
                                
                                <a  class="btn1 withstar" href="${createLink(controller:'favlist', action:'favlist')}"><span></span>Lista de Favoritos</a>
                        </div>                                                  
                </div>
                </div>
                
                
                <div style="width: 120px; float: right; padding-left: 30px;">
                        <h4>Categorías</h4>
                        <table>
                        <g:each var="category" in="${categories}">
                                <tr><td>        
                                        <div class="category">
                                                <a href="${createLink(action:'filteredGifts', params: [selectedCategory:category, idFriend:friend])}">${category}</a></br>
                                        </div>
                                </td></tr>      
                        </g:each>
                                
                        </table>
                </div>
                                
                <div style="width: 820px; float: left;">        
                        <g:if test="${giftList}">
                        <input type="hidden" id="urlStar" value="/seekagift/favlist/favstar"/>
<%--                            <g:each var="giftGroupedList" in="${giftList.sameCategoryGifts}">--%>
                                        <g:each var="gift" in="${giftList}">
                                                <div class="gift">
                                                        <p><a href="${gift.giftURL}">
                                                                <img src='${gift.photoURL}' width="260px" height="300px"/>
                                                        </a>
                                                        </p>
                                                        <p class="title">${gift.name}</p>
                                                        <p class="title">${gift.price}</p>
                                                        
                                                        <g:if test="${session.favList.find() { it.id == gift.id }}">
                                                                <div class="active_favorite" rel="${gift.id}"></div>
                                                        </g:if>
                                                        <g:else>
                                                                <div class="inactive_favorite" rel="${gift.id}"></div>
                                                        </g:else>
                                                </div>  
                                                
                                        </g:each>                                       
<%--                            </g:each>--%>
                        </g:if>
                </div>  
                <div id="footer">
                        <p>&copy; Copyright 2009 Distinctive &minus; Design: Luka Cvrk, <a href="http://www.solucija.com" title="Free CSS Templates">Solucija</a></p>
                </div>  
        </div>
</body>
</html>







<script type="text/javascript">
  $('.inactive_favorite').live('click', function(e){     
        var id = $(this).attr('rel');
        $(this).removeClass('inactive_favorite');       
        $(this).addClass('active_favorite');
        var urlBase = $('#urlStar').val();      
        var url = urlBase ;
        $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        data: {
                idGift:id,
        },
        success: function (data) {
            if (data.success) {
                //window.location.reload(true);
                //window.location.href = window.location.pathname + window.location.search + "&timeStamp=" + new Date().getTime()
            }
        }       
        });
        
  });

  $('.active_favorite').live('click', function(e){        
        var id = $(this).attr('rel');
        $(this).removeClass('active_favorite');         
        $(this).addClass('inactive_favorite');
        var urlBase = $('#urlStar').val();
        var url = urlBase;              
                
        $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        data: {
                idGift:id,
        },
        success: function (data) {
            if (data.success) {
                //window.location.reload(true);
                //window.location.href = window.location.pathname + window.location.search + "&timeStamp=" + new Date().getTime()
            }
        }       
        });
                
  });
          
</script>




