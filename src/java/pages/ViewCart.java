/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Cart;
import command.LineCommand;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;

/**
 *
 * @author Samy
 */
public class ViewCart extends AbstractPage {

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Panier";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        Cart cart;
        HttpSession session = request.getSession(true);
        if (session.getAttribute("client") == null) {
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new Cart());
            }
            cart = (Cart) session.getAttribute("cart");
        } else {
            cart = (Cart) ((Client) session.getAttribute("client")).getCart();
        }

        if (request.getParameter("total") == null && request.getParameter("menu") == null) {
            int Prod = 0;
             for (LineCommand p : cart.getProducts()) {
                 Prod = Prod + p.getQuantity();
             }
             if(Prod <= 0){
                 out.println("<p>Votre panier ne contient aucun élément</p>");
             }
             else if(Prod == 1){
                 out.println("<p>Votre panier contient "+ Prod  +" élément</p>");
             }
             else if(Prod > 1){
                 out.println("<p>Votre panier contient "+ Prod  +" éléments</p>");
             }
            
             if (Prod > 0) {
                out.println("<table>");
                out.println("<tr><th>Produit</th><th class='quant'>Quantité</th><th class='price'>Prix Unitaire</th><th>Prix</th><th>Supprimer</th></tr>");
                for (LineCommand p : cart.getProducts()) {
                    try{
                        out.print("<tr><td class='prems'><div class='info'><a href=\"?page=Product&id="+ p.getProduct().getId()
                                + "\">"+p.getProduct().getName()+"</a>"
                                + "</div></td><td class='quant'><p id='nb'>"+p.getQuantity()
                                
                                + "</p><div id=\"buttom_mod\"><form id=\"modif_quant_add\" method=\"POST\" action=\"ModifQuant\">"
                                + "<input type=\"hidden\" name=\"id\" value=\""+ p.getProduct().getId() +"\" />"
                                + "<input type=\"hidden\" name=\"do\" value=\"add\" />"
                                + "<input id=\"add\" type=\"submit\" value=\"+\"");
                        
                        if(p.getProduct().getStock() <= 0){
                            out.print(" disabled=\"disabled\"");
                        }
                                
                        out.print("/></form>"
                                + "<form id=\"modif_quant_minus\" method=\"POST\" action=\"ModifQuant\">"
                                + "<input type=\"hidden\" name=\"id\" value=\""+ p.getProduct().getId() +"\"/>"
                                + "<input type=\"hidden\" name=\"do\" value=\"minus\" />"
                                + "<input id=\"minus\" type=\"submit\" value=\"-\"");
                        
                        if(cart.getQuantity(p.getProduct()) <= 1){
                            out.print(" disabled=\"disabled\"");
                        }
                                
                        out.println("/></form></div></td><td class='price'>"
                                + String.format("%.2f &euro;", p.getProduct().getPrice()) +"</td><td class='priceLine'>" 
                                + String.format("%.2f &euro;", p.storePrice()) + "</td><td class='suppr'>"
                                + "<a href='DelToCart?&amp;product=" + p.getProduct().getId() + "&amp;old="
                                + URLEncoder.encode(request.getQueryString(), "UTF-8") + "'>supprimer</a></td></tr>");
                        } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    //out.println( + "x " +  + " : " + String.format("<div class='price'>%.2f &euro;</div>", p.storePrice()) + "<br/>");
                }
                out.println("<tr><td class='lineTotal1'></td><td class='lineTotal2'></td><td class='tittleTotal'>Total : </td><td class='priceTotal'>"
                        + String.format("%.2f &euro;", cart.getTotal()) +"</td><td class='lineTotal3'></td></tr> ");
                out.println("</table>");
                out.println("<button type=\"button\" id=\"valid\">Acheter le Panier (TODO)</button>");
             }
        } else if (request.getParameter("menu") == null) {
            Integer total = 0;
            for (LineCommand p : cart.getProducts()) {
                total += p.getQuantity();
            }
            out.print(total);
        } else if (request.getParameter("total") == null) {
            out.println("<ul>");
            for (LineCommand p : cart.getProducts().subList(0, Math.min(cart.getProducts().size(), 3))) {
                out.println("<li><a href='?page=Product&amp;id="+p.getProduct().getId()+"'>" + p.getProduct().getName() + "</a></li>");
            }
            if (cart.getProducts().size() > 3)
                out.println("<a href='?page=ViewCart' id='cart_more'>voir la suite ...</a>");
            out.println("</ul>");
        }
    }
}
