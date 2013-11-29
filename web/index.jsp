<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="fr" xml:lang="fr" xmlns="http://www.w3.org/1999/xhtml">
	<head>
                <%
                    String requestPage = request.getParameter("page");
                    if (requestPage == null) {
                        requestPage = "Home";
                    }
                %>
                <title>Evil Bunny - <jsp:include page="<%=requestPage%>"><jsp:param name="get" value="Title"/></jsp:include></title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta name      ="author"       content="Nicolas Bouscarle" />
		<!--<link rel="shortcut icon" href="favicon.ico" />-->
		<link rel="stylesheet" type="text/css" href="common.css" />
		<link rel="stylesheet" type="text/css" href="small.css" media="screen and (max-width:1134px)" /><!--max-device-width-->
		<link id="page_css" rel="stylesheet" type="text/css" href="<%=requestPage%>.css" />
		<script type="text/javascript">
			function positionBand() {
				band = document.getElementById('band');
				header = document.getElementById('header');
				left = document.getElementById('left');
				if (window.pageYOffset > header.clientHeight - band.clientHeight -2) {
					band.style.position = "fixed";
					header.style.paddingBottom = band.clientHeight+2 + "px";
					band.style.width = document.body.clientWidth-4 + "px";
					if (document.documentElement.clientWidth > document.body.clientWidth) 
						band.style.left = (document.documentElement.clientWidth - document.body.clientWidth)/2 + "px";
					else
						band.style.left = -(document.documentElement.scrollLeft+document.body.scrollLeft) + "px";
					band.style.marginLeft = "2px";
					if (left.offsetWidth !== 260) {
						left.style.position = "fixed";
						left.style.width = document.body.clientWidth-4 + "px";
						left.style.top = "32px";
						header.style.marginBottom = left.clientHeight+2 + "px";
						if (document.documentElement.clientWidth > document.body.clientWidth) 
							left.style.left = (document.documentElement.clientWidth - document.body.clientWidth)/2 + "px";
						else
							left.style.left = -(document.documentElement.scrollLeft+document.body.scrollLeft)+2 + "px";
					}
				} else {
					band.style.position = "";
					header.style.paddingBottom = "";
					band.style.width = "";
					band.style.left = "";
					band.style.marginLeft = "";
					left.style.position = "";
					left.style.width = "";
					left.style.top = "";
					header.style.marginBottom = "";
				}
			}
			window.onscroll = positionBand;
			window.onresize = positionBand;
                </script>
	</head>
	<body>
		<div id="central">
			<div id="header">
				<a href=""><img height="136px" width="640px" id="logo" src="img/logo.png" alt="Evil Bunny, fournisseur de matériel informatique rétro"/></a>
				<span id="lang">
					<a href="?fr"><img id="current_lang" src="img/fr.png" alt="Français / French" title="Français / French"/></a>
					<a href="?en"><img src="img/en.png" alt="Anglais / English" title="Anglais / English"/></a>
				</span>
				<div id="band">
					<a id="nav" href="" title="Retour à  ..."><img src="img/previous.png" width="30px" height="30px" alt=""/>Retour</a>
					<h1><jsp:include page="<%=requestPage%>"><jsp:param name="get" value="Title"/></jsp:include></h1>
					<span id="options"><a href="cart">Panier (10)</a><a href="log">Connexion</a><a href="register">Inscription</a></span>
				</div><!--band-->
			</div><!--header-->
			<div id="left">
				<ul id="menu">
					<li><a href="?page=Categories">Catégories</a>
                                        <jsp:include page="Categories"><jsp:param name="menu" value="true"/></jsp:include>
					</li>
					<li><a href="brand">Marques &amp; Constructeurs</a>
						<ul>
                                                    <jsp:include page="ManufacturerList"/>
						</ul>
					</li>
					<!--<li><a href="search">Recherche</a></li>-->
					<li><a href="advanced_search">Recherche avancée</a></li>
				</ul><!--menu-->
				<div id="cart"><a href="cart">Panier (10 articles)</a>
					<ul>
						<li><a href="toto" title="Toto">Toto</a></li>
						<li><a href="tata" title="Tata">Tata</a></li>
						<li><a href="titi" title="Titi et Gros Minet sont sur un bateau">Titi et Gros Minet sont sur un bateau</a></li>
					</ul>
					<a href="cart" id="cart_more">voir la suite ...</a>
				</div><!--cart-->
				<form id="search" action="search">
					<fieldset><legend>Recherche rapide</legend>
						<input id="search_query" type="text" name="query"/>
						<input id="search_button" type="submit" value="Rechercher"/>
					</fieldset>
				</form>
				<form id="log" action="log">
					<fieldset><legend>Connexion rapide</legend>
						<label>Identifiant : <input type="text" name="login"/></label>
						<label>Mot de passe : <input type="password" name="pass"/></label>
						<input type="submit" value="Se connecter"/>
					</fieldset>
				</form>
			</div><!--left-->
			<div id="ariane"><a href=""><img src="img/home.png" alt="EvilBunny" width="20px" height="20px"/></a> &gt; Catégories</div>
			<div id="content">
                            <jsp:include page="<%=requestPage%>"/>
			</div><!--content-->
			<div class="clear_footer"></div>
		</div><!--central-->
		<div id="footer"><a href="contact">Nous contacter</a><a href="legal">Mentions légales</a><span>©EvilBunny</span></div>
	</body>
</html>
