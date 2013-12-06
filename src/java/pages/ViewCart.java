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
            out.println("<h2>Panier</h2>");
            for (LineCommand p : cart.getProducts()) {
                out.println(p.getQuantity() + "x " + p.getProduct().getName() + ": " + String.format("<div class='price'>%.2f &euro;</div>", p.storePrice()) + "<br/>");
            }
            out.println("Total: " + String.format("<div class='price'>%.2f &euro;</div>", cart.getTotal()));
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
