<%-- 
    Document   : products
    Created on : 22 nov. 2013, 09:47:47
    Author     : bousky
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">setTitle("Produits");</script> 
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
<script type="text/javascript">
        list = document.getElementById('list_products');
        str = "";
        for (i=0 ; i<5 ; i++)
                str = str + list.innerHTML;
        list.innerHTML = str;
</script>