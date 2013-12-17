/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Cart;
import command.LineCommand;
import ejb.ProductFacade;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import product.type.Category;
import product.type.LineCharacteristic;
import user.Client;

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
    protected List<String> getArianeNames(HttpServletRequest request) {
        ArrayList<String> l = new ArrayList();
        product.Product p = pf.find(Long.parseLong(request.getParameter("id")));
        l.add("Catégories");
        if (p.getCategorie().getParent() != null)
            l.add(p.getCategorie().getParent().getCategorie());
        l.add(p.getCategorie().getCategorie());
        return l;
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        ArrayList<String> l = new ArrayList();
        product.Product p = pf.find(Long.parseLong(request.getParameter("id")));
        l.add("?page=Categories");
        if (p.getCategorie().getParent() != null)
            l.add("?page=Products&amp;category="+p.getCategorie().getParent().getId());
        l.add("?page=Products&amp;category="+p.getCategorie().getId());
        return l;
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        product.Product product = pf.find(Long.parseLong(request.getParameter("id")));
        
        if (product == null)
            throw new HTTPErrorException(404);
        
        Category category = product.getCategorie();
        out.println("<img id='photo' src='img/prod/"+product.getId()+".jpg' alt='' height='300px' width='300px'/>");
        out.println("<div class='prod_right'>");
        out.println(String.format("<div class='price'>%.2f &euro;</div>", product.getPrice()));
        if (product.getStock() == 0) {
            out.println("<div class='stock stock_no'>Rupture de stock</div>");
        } else {
            out.println("<div class='stock stock_yes'>En stock</div>");
        }   
        try {
            out.println("<a id='add2cart' href='AddToCart?&amp;product="+ product.getId() +"&amp;old="+URLEncoder.encode(request.getQueryString(), "UTF-8") + "' title='Ajouter au panier'><img src='img/cart.png' alt='Panier' height='60px' width='60px'/></a><br/>");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }             
                
        // recherche du produit si déjà dans le panier
        HttpSession session = request.getSession(true);
        Cart cart;
        if (session.getAttribute("client") == null) { //pas connecté
            cart = (Cart) session.getAttribute("cart");
        } else { //connecté
            cart = (Cart) ((Client) session.getAttribute("client")).getCart();
        }
        
        if(cart != null){ // si panier existe
            for (LineCommand lc : cart.getProducts()) { // recherche du produit dans le panier
                if ((lc.getProduct().getId()).equals(product.getId())) {
                    out.println("<div class=\"cart_contient\" >Quantité ajoutée : "+ cart.getQuantity(lc.getProduct()) +"</div>");
                }
            }
        }
        out.println("</div><h2>");
        out.println(HTMLEncode(product.getName()));
        out.println("</h2><table><tr><td>Caractéristiques</td><td><table>");
        for (LineCharacteristic carac : product.getProductCaracteristics()) {
            out.println("<tr><td>"+HTMLEncode(carac.getCharacteristic().getName())+"</td><td>"+HTMLEncode(carac.getName())+"</td><tr>");
        }
        out.print("</table></td></tr><tr><td>Catégorie</td><td>");
        if (category.getParent() != null)
            out.println("<a href='?page=Products&amp;category="+category.getParent().getId()+"'>"+HTMLEncode(category.getParent().getCategorie())+"</a> &gt; ");
        out.println("<a href='?page=Products&amp;category="+category.getId()+"'>"+HTMLEncode(category.getCategorie())+"</a>");
        out.print("</td></tr><tr><td>Marque</td><td>");
        out.println("<a href='?page=Products&amp;manufacturer="+product.getBrand().getId()+"'>"+HTMLEncode(product.getBrand().getName())+"</a>");
        out.println("</td></tr></table>");
    }
    
}
