/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ProductFacade;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.type.Category;
import product.type.LineCharacteristic;

/**
 *
 * @author bousky
 */
public class Product extends AbstractPage {
    
    @EJB
    ProductFacade pf;

    @Override
    protected String getTitle(HttpServletRequest request){
        return pf.find(new Long(request.getParameter("id"))).getName();
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        product.Product product = pf.find(new Long(request.getParameter("id")));
        Category category = product.getCategorie();
        out.println("<img id='photo' src='img/category.png' alt='' height='300px' width='300px' style='background:white;'/>");
        out.println("<div class='prod_right'>");
        out.println(String.format("<div class='price'>%.2f &euro;</div>", product.getPrice()));
        if (product.getStock() == 0) {
            out.println("<div class='stock stock_no'>Rupture de stock</div>");
        } else {
            out.println("<div class='stock stock_yes'>En stock</div>");
        } 
        try {
            out.println("<a id='add2cart' href='AddToCart?&amp;product="+ product.getId() +"&amp;old="+URLEncoder.encode(request.getQueryString(), "UTF-8") + "' title='Ajouter au panier'><img src='img/cart.png' alt='Panier' height='60px' width='60px'/></a></div><h2>");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.println(product.getName());
        out.println("</h2><table><tr><td>Caractéristiques</td><td><table>");
        for (LineCharacteristic carac : product.getProductCaracteristics()) {
            out.println("<tr><td>"+carac.getCharacteristic().getName()+"</td><td>"+carac.getName()+"</td><tr>");
        }
        out.print("</table></td></tr><tr><td>Catégorie</td><td>");
        if (category.getParent() != null)
            out.println("<a href='?page=Products&amp;category="+category.getParent().getId()+"'>"+category.getParent().getCategorie()+"</a> &gt; ");
        out.println("<a href='?page=Products&amp;category="+category.getId()+"'>"+category.getCategorie()+"</a>");
        out.print("</td></tr><tr><td>Marque</td><td>");
        out.println("<a href='?page=Products&amp;manufacturer="+product.getBrand().getId()+"'>"+product.getBrand().getName()+"</a>");
        out.println("</td></tr></table>");
    }
    
}
