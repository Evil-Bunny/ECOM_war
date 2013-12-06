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

        if (request.getParameter("menu") == null) {
            out.println("<h2>Panier</h2>");
            for (LineCommand p : cart.getProducts()) {
                out.println(p.getQuantity() + "x " + p.getProduct().getName() + ": " +String.format("<div class='price'>%.2f &euro;</div>", p.storePrice()) + "<br/>");
            }
            out.println("Total: " + String.format("<div class='price'>%.2f &euro;</div>", cart.getTotal()) );
        } else {
            Integer total = 0;
            for (LineCommand p : cart.getProducts()) {
                total += p.getQuantity();
            }
            out.print(total);
        }
    }
}
