<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html lang="fr" xml:lang="fr" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Evil Bunny - Catégories</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<meta name      ="author"       content="Nicolas Bouscarle" />
		<!--<link rel="shortcut icon" href="favicon.ico" />-->
		<link rel="stylesheet" type="text/css" href="common.css" />
		<link rel="stylesheet" type="text/css" href="small.css" media="screen and (max-width:1134px)" /><!--max-device-width-->
		<link rel="stylesheet" type="text/css" href="list.css" />
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
					if (left.offsetWidth != 260) {
						left.style.position = "fixed";
						left.style.width = document.body.clientWidth-4 + "px";
						left.style.top = "32px";
						header.style.marginBottom = left.clientHeight+2 + "px";
						if (document.documentElement.clientWidth > document.body.clientWidth) 
							left.style.left = (document.documentElement.clientWidth - document.body.clientWidth)/2 + "px";
						else
							left.style.left = -(document.documentElement.scrollLeft+document.body.scrollLeft)+2 + "px";
					} else {
						left.style.position = "";
						left.style.width = "";
						left.style.top = "";
						header.style.marginBottom = "";					
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
					<a id="nav" href="categories.jsp" title="Retour aux catégories"><img src="img/previous.png" width="30px" height="30px" alt=""/>Retour</a>
					<h1>Câbles &amp; Connectique</h1>
					<span id="options"><a href="cart">Panier (10)</a><a href="log">Connexion</a><a href="register">Inscription</a></span>
				</div><!--band-->
			</div><!--header-->
			<div id="left">
				<ul id="menu">
					<li><a href="categories.jsp">Catégories</a>
						<ul>
							<li>
								<a href="perif">Périphériques</a>
								<ul>
									<li><a href="">Claviers</a></li>
									<li><a href="">Souris</a></li>
									<li><a href="">Écrans</a></li>
									<li><a href="">Imprimantes</a></li>
									<li><a href="">Scanners</a></li>
									<li><a href="list.jsp">Câbles &amp; Connectique</a></li>
									<li><a href="">Lecteurs</a></li>
								</ul>
							</li><!--Périphériques-->
							<li>
								<a href="conso">Consommables</a>
								<ul>
									<li><a href="">Papier</a></li>
									<li><a href="">Encre</a></li>
								</ul>
							</li><!--Consommables-->
							<li>
								<a href="compo">Composants</a>
								<ul>
									<li><a href="">Cartes mères</a></li>
									<li><a href="">Cartes réseau</a></li>
									<li><a href="">Cartes son</a></li>
									<li><a href="">Cartes graphiques</a></li>
									<li><a href="">Disques durs</a></li>
									<li><a href="">Connectique interne</a></li>
									<li><a href="">Lecteurs</a></li>			
								</ul>
							</li><!--Composants-->
						</ul>
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
			<div id="ariane"><a href=""><img src="img/home.png" alt="EvilBunny" width="20px" height="20px"/></a> &gt; <a href="">Catégories</a> &gt; Câbles &amp; Connectique</div>
			<div id="content">
				<ul id="list_products">
					<li>
						<a href=""><img src="img/category.png" alt="" height="100px" width="100px" style="background:white;"/></a>
						<div class="prod_right">
							<div class="price">99.99 &euro;</div>
							<div class="stock stock_yes">En stock</div>
							<a href="">Ajouter au panier</a><br/>
							<a href="">Plus d'info</a>
						</div>
						<h2><a href="nom produit">Nom produit</a></h2>
						<table>
							<tr><td>Caractéristiques</td><td>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.</td></tr>
							<tr><td>Catégorie</td><td><a href="">Périphériques</a> &gt; <a href="">Câbles & Connectique</a></td></tr>
							<tr><td>Marque</td><td><a href="">Tatatititutu</a></td></tr>
						</table>
						<div class="clear_footer"></div>
					</li>
					<li>
						<a href=""><img src="img/category.png" alt="" height="100px" width="100px" style="background:white;"/></a>
						<div class="prod_right">
							<div class="price">99.99 &euro;</div>
							<div class="stock stock_no">Rupture de stock</div>
							<a href="">Plus d'info</a>
						</div>
						<h2><a href="">Nom produit</a></h2>
						<table>
							<tr><td>Caractéristiques</td><td>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.</td></tr>
							<tr><td>Catégorie</td><td><a href="">Périphériques</a> &gt; <a href="">Câbles & Connectique</a></td></tr>
							<tr><td>Marque</td><td><a href="">Tatatititutu</a></td></tr>
						</table>
						<div class="clear_footer"></div>
					</li>
				</ul>
				<div class="pages"><a href="">Début</a> <a href="">Précédent</a> <a href="">1</a> <span>2</span> <a href="">3</a> <a href="">4</a> <a href="">5</a> <a href="">6</a> <a href="">7</a> <a title="Aller à  la page..." href="">...</a> <a href="">42</a> <a href="">Suivant</a> <a href="">Fin</a></div>
				<script  type="text/javascript">
					list = document.getElementById('list_products');
					str = "";
					for (i=0 ; i<5 ; i++)
						str = str + list.innerHTML;
					list.innerHTML = str;
				</script>
			</div><!--content-->
			<div class="clear_footer"></div>
		</div><!--central-->
		<div id="footer"><a href="contact">Nous contacter</a><a href="legal">Mentions légales</a><span>©EvilBunny</span></div>
	</body>
</html>
