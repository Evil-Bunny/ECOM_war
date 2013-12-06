/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Cart;
import command.LineCommand;
import java.io.PrintWriter;
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
            out.println("<h2>Contenu du panier</h2>");
            out.println("<table>");
            out.println("<tr><th>Produit</th><th class='quant'>Quantité</th><th class='price'>Prix Unitaire</th><th>Prix</th></tr>");
            for (LineCommand p : cart.getProducts()) {
                out.println("<tr><td class='prems'><div class='info'>"+p.getProduct().getName()+"</div></td><td class='quant'>"+p.getQuantity()+"</td><td class='price'>"+ String.format("%.2f &euro;", p.getProduct().getPrice()) +"</td><td class='priceLine'>" + String.format("%.2f &euro;", p.storePrice()) + "</td></tr>");
                //out.println( + "x " +  + " : " + String.format("<div class='price'>%.2f &euro;</div>", p.storePrice()) + "<br/>");
            }
            out.println("<tr><td class='lineTotal1'></td><td class='lineTotal2'></td><td class='tittleTotal'>Total : </td><td class='priceTotal'>"+ String.format("%.2f &euro;", cart.getTotal()) +"</td></tr> ");
            out.println("</table>");
            out.println("<input type='submit' value='Acheter le Panier (TODO)' id='valid'></input>");
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
