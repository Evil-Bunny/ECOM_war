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
import ejb.ManufacturerFacade;
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
    
    public static final int PRODUCTSBYPAGE = 10;
    public static final int PAGESAROUND = 5;
    
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
        nbPages = (products.size()-1)/PRODUCTSBYPAGE +1;
        products = products.subList(start, Math.min(products.size(),start+PRODUCTSBYPAGE));
        
        out.println("<ul id='list_products'>");
        for (Product p : products) {
            Category category = p.getCategorie();
            out.println("<li><a href='?page=Product&amp;id="+p.getId()+"'>");
            out.println("<img src='img/category.png' alt='' height='100px' width='100px' style='background:white;'/>");
            out.println("</a><div class='prod_right'>");
            out.println(String.format("<div class='price'>%.2f &euro;</div>", p.getPrice()));
            if (p.getStock() == 0) {
                out.println("<div class='stock stock_no'>Rupture de stock</div>");
            } else {
                out.println("<div class='stock stock_yes'>En stock</div>");
                out.println("<a href='?page=cart&amp;add="+p.getId()+"'>Ajouter au panier</a><br/>");
            }
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
        if (nbPages != 1) {
            int currentPage = start/PRODUCTSBYPAGE +1;
            String baseURL = "?page=Products";
            if (request.getParameter("category") != null) {
                baseURL += "&amp;category=" + request.getParameter("category");
            }
            if (request.getParameter("manufacturer") != null) {
                baseURL += "&amp;manufacturer=" + request.getParameter("manufacturer");
            }
            if (request.getParameter("search") != null) {
                baseURL += "&amp;search=" + request.getParameter("search");
            }
            baseURL += "&amp;start=";
            out.println("<div class='pages'><a href='"+baseURL+"0'>Début</a>");
            if (currentPage != 1)
                out.println("<a href='"+baseURL+(start-PRODUCTSBYPAGE)+"'>Précédent</a>");
            for (int i = Math.max(1, currentPage-PAGESAROUND) ; i < currentPage ; i++)
                out.println("<a href='"+baseURL+i+"'>"+i+"</a>");
            out.println("<span>"+currentPage+"</span>");
            for (int i = currentPage+1; i<= Math.min(nbPages, currentPage+PAGESAROUND) ; i++)
                out.println("<a href='"+baseURL+i+"'>"+i+"</a>");
            if (currentPage != nbPages)
                out.println("<a href='"+baseURL+(start+PRODUCTSBYPAGE)+"'>Suivant</a>");
            out.println("<a href='"+baseURL+((nbPages-1)*PRODUCTSBYPAGE)+"'>Fin</a></div>");
        }
    }
    
}