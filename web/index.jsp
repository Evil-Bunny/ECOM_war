<%@page import="pages.HTTPRedirect"%>
<%@page import="pages.HTTPErrorException"%>
<%@page import="user.Client"%>
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
        <link rel="stylesheet" type="text/css" href="<%=requestPage%>.css" />
        <script type="text/javascript" src="scripts.js"></script>
    </head>
    <body>
        <div id="central">
            <div id="header">
                <a href="."><img height="136px" width="640px" id="logo" src="img/logo.png" alt="Evil Bunny, fournisseur de matériel informatique rétro"/></a>
                <!--<span id="lang">
                    <a href="?fr"><img id="current_lang" src="img/fr.png" alt="Français / French" title="Français / French"/></a>
                    <a href="?en"><img src="img/en.png" alt="Anglais / English" title="Anglais / English"/></a>
                </span>-->
                <div id="band">
                    <a id="nav" href="<jsp:include page="<%=requestPage%>"><jsp:param name="get" value="PreviousLink"/></jsp:include>" title="Retour à la page <jsp:include page="<%=requestPage%>"><jsp:param name="get" value="PreviousName"/></jsp:include>"><img src="img/previous.png" width="30px" height="30px" alt=""/>Retour</a>
                    <h1><jsp:include page="<%=requestPage%>"><jsp:param name="get" value="Title"/></jsp:include></h1>
                    <span id="options"><a href="?page=ViewCart">Panier (<jsp:include page="ViewCart"><jsp:param name="total" value="true"/></jsp:include>)</a><%
                        if (request.getSession(true).getAttribute("client") == null) {
                            %><a href="?page=Login">Connexion</a><a href="?page=RegisterClient">Inscription</a><%
                        } else {
                            %><a href="Logout">Déconnexion</a><a href="?page=Account">Voir mon compte</a><%
                        }%></span>
                    </div><!--band-->
                </div><!--header-->
                <div id="left">
                    <ul id="menu">
                        <li>
                            <a href="?page=Categories">Catégories</a>
                            <jsp:include page="Categories"><jsp:param name="menu" value="true"/></jsp:include>
                        </li>
                        <li>
                            <a href="?page=Manufacturers">Marques &amp; Constructeurs</a>
                            <jsp:include page="Manufacturers"/>
                    </li>
                    <!--<li><a href="search">Recherche</a></li>-->
                    <li><a href="?page=Search">Recherche avancée</a></li>
                </ul><!--menu-->
                <div id="cart"><a href="?page=ViewCart">Panier (<jsp:include page="ViewCart"><jsp:param name="total" value="true"/></jsp:include> articles)</a>
                    <jsp:include page="ViewCart"><jsp:param name="menu" value="true"/></jsp:include>
                    </div><!--cart-->
                    <form id="search" action="." method='GET'>
                        <fieldset><legend>Recherche rapide</legend>
                            <input type='hidden' name='page' value='Products'/>
                            <input id="search_query" type="text" name="search" value="<%
                                if (request.getParameter("search") != null) {
                                    out.print(pages.AbstractPage.HTMLEncode(request.getParameter("search")));
                                }
                               %>"/>
                        <input id="search_button" type="submit" value="Rechercher"/>
                    </fieldset>
                </form>
                <% if (request.getSession(true).getAttribute("client") == null) { %>
                <form id="log" action="?page=Login" method="POST">
                    <fieldset><legend>Connexion rapide</legend>
                        <label>Identifiant : <input type="text" name="username"/></label>
                        <label>Mot de passe : <input type="password" name="pass"/></label>
                        <input type="submit" value="Se connecter"/>
                    </fieldset>
                </form>
                <% } else { %>
                <div id="log">
                    <span>Vous êtes connectés en tant que <%=((Client)request.getSession(true).getAttribute("client")).getUsername()%></span>
                    <ul>
                        <li><a href="?page=Account">Voir mon compte</a></li>
                        <li><a href="?page=SeeCommands">Voir mes commandes</a></li>
                        <li><a href="Logout">Déconnexion</a></li>
                    </ul>
                </div>
                <% } %>
            </div><!--left-->
            <div id="ariane">
                <jsp:include page="<%=requestPage%>"><jsp:param name="get" value="Ariane"/></jsp:include>
            </div>
            <div id="content">
                <% try { %>
                    <jsp:include page="<%=requestPage%>"/>
                <% } catch (HTTPErrorException e) {
                    response.sendError(e.getErrorCode());
                } catch (HTTPRedirect e) {
                    response.sendRedirect(e.getMessage());
                }%>
            </div><!--content-->
            <div class="clear_footer"></div>
        </div><!--central-->
        <div id="footer"><a href="?page=Contact">Nous contacter</a><a href="?page=Legal">Mentions légales</a><span>©EvilBunny</span></div>
    </body>
</html>