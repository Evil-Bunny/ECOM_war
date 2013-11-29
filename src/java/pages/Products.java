/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.CategoryFacade;
import ejb.ProductFacade;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.ManufacturerFacade;
import product.Product;
import product.type.Category;

/**
 *
 * @author bousky
 */
public class Products extends AbstractPage {

    @EJB
    CategoryFacade cf;
    
    @EJB
    ManufacturerFacade mf;
    
    @EJB
    ProductFacade pf;
    
    @Override
    protected String getTitle(HttpServletRequest request) {
        if (request.getParameter("category") != null) {
            return cf.find(new Long(request.getParameter("category"))).getCategorie();
        }
        if (request.getParameter("manufacturer") != null) {
            return mf.find(new Long(request.getParameter("manufacturer"))).getName();
        }
        if (request.getParameter("search") != null) {
            return "Produits - Recherche";
        }
        return "Produits";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<Product> products;
        int start, nbPages;
        if (request.getParameter("category") != null) {
            Category category = cf.find(new Long(request.getParameter("category")));
            products = category.getProducts();
            for (Category c : category.getSubCategories()) {
                products.addAll(c.getProducts());
            }
        } else if (request.getParameter("manufacturer") != null) {
            products = mf.find(new Long(request.getParameter("manufacturer"))).getProducts();
        } else {
            products = pf.findAll();
        }
        if (request.getParameter("start") != null) {
            start = new Integer(request.getParameter("start"));
        } else {
            start = 0;
        }
        nbPages = products.size()/10;
        products = products.subList(start, Math.min(products.size(),start+10));
        
        out.println("<ul id='list_products'>");
        for (Product p : products) {
            Category category = p.getCategorie();
            out.println("<li><a href='?page=Product&amp;id="+p.getId()+"'>");
            out.println("<img src='img/category.png' alt='' height='100px' width='100px' style='background:white;'/>");
            out.println("</a><div class='prod_right'>");
            out.println("<div class='price'>"+p.getPrice()+" &euro;</div>");
            out.println("<div class='stock stock_yes'>En stock</div>");
            out.println("<a href='?page=cart&amp;add="+p.getId()+"'>Ajouter au panier</a><br/>");
            out.println("<a href='?page=Product&amp;id="+p.getId()+"'>Plus d'info</a></div>");
            out.println("<h2><a href='?page=Product&amp;id="+p.getId()+"'>"+p.getName()+"</a></h2>");
            out.println("<table><tr><td>Caractéristiques</td><td>");
            out.println("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.");
            out.println("</td></tr><tr><td>Catégorie</td><td>");
            if (category.getParent() != null)
                out.println("<a href='?page=Products&amp;category="+category.getParent().getId()+"'>"+category.getParent().getCategorie()+"</a> &gt; ");
            out.println("<a href='?page=Products&amp;category="+category.getId()+"'>"+category.getCategorie()+"</a>");
            out.println("</td></tr><tr><td>Marque</td><td>");
            out.println("<a href='?page=Products&amp;manufacturer="+p.getBrand().getId()+"'>"+p.getBrand().getName()+"</a>");
            out.println("</td></tr></table><div class='clear_footer'></div></li>");
        }
        out.println("</ul>");
        
        /*for (int i=0 ; i<5 ; i++) {
            out.println("        <li>\n" +
"                <a href=\"\"><img src=\"img/category.png\" alt=\"\" height=\"100px\" width=\"100px\" style=\"background:white;\"/></a>\n" +
"                <div class=\"prod_right\">\n" +
"                        <div class=\"price\">99.99 &euro;</div>\n" +
"                        <div class=\"stock stock_yes\">En stock</div>\n" +
"                        <a href=\"\">Ajouter au panier</a><br/>\n" +
"                        <a href=\"\">Plus d'info</a>\n" +
"                </div>\n" +
"                <h2><a href=\"nom produit\">Nom produit</a></h2>\n" +
"                <table>\n" +
"                        <tr><td>Caractéristiques</td><td>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.</td></tr>\n" +
"                        <tr><td>Catégorie</td><td><a href=\"\">Périphériques</a> &gt; <a href=\"\">Câbles & Connectique</a></td></tr>\n" +
"                        <tr><td>Marque</td><td><a href=\"\">Tatatititutu</a></td></tr>\n" +
"                </table>\n" +
"                <div class=\"clear_footer\"></div>\n" +
"        </li>\n" +
"        <li>\n" +
"                <a href=\"\"><img src=\"img/category.png\" alt=\"\" height=\"100px\" width=\"100px\" style=\"background:white;\"/></a>\n" +
"                <div class=\"prod_right\">\n" +
"                        <div class=\"price\">99.99 &euro;</div>\n" +
"                        <div class=\"stock stock_no\">Rupture de stock</div>\n" +
"                        <a href=\"\">Plus d'info</a>\n" +
"                </div>\n" +
"                <h2><a href=\"\">Nom produit</a></h2>\n" +
"                <table>\n" +
"                        <tr><td>Caractéristiques</td><td>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris.</td></tr>\n" +
"                        <tr><td>Catégorie</td><td><a href=\"\">Périphériques</a> &gt; <a href=\"\">Câbles & Connectique</a></td></tr>\n" +
"                        <tr><td>Marque</td><td><a href=\"\">Tatatititutu</a></td></tr>\n" +
"                </table>\n" +
"                <div class=\"clear_footer\"></div>\n" +
"        </li>\n");
        }
        out.println("</ul>");
        out.println("<div class=\"pages\"><a href=\"\">Début</a> <a href=\"\">Précédent</a> <a href=\"\">1</a> <span>2</span> <a href=\"\">3</a> <a href=\"\">4</a> <a href=\"\">5</a> <a href=\"\">6</a> <a href=\"\">7</a> <a title=\"Aller à  la page...\" href=\"\">...</a> <a href=\"\">42</a> <a href=\"\">Suivant</a> <a href=\"\">Fin</a></div>\n");*/
    }
    
}