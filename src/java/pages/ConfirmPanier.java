/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Cart;
import command.Command;
import command.LineCommand;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;

/**
 *
 * @author msi
 */
public class ConfirmPanier extends AbstractPage {

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Vérification avant paiment";
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
            if (Prod <= 0) {
                out.println("<p>Votre panier ne contient aucun élément</p>");
            } else if (Prod == 1) {
                out.println("<p>Votre panier contient " + Prod + " élément</p>");
            } else if (Prod > 1) {
                out.println("<p>Votre panier contient " + Prod + " éléments</p>");
            }

            if (Prod > 0) {
                
                
                Command c = new Command();
                c.setProducts(cart.getProducts());
                c.storePrices();
                c.setClient((Client)session.getAttribute("client"));
                session.setAttribute("command", c);

                out.println("<table>");
                out.println("<tr><th>Produit</th><th class='quant'>Quantité</th><th class='price'>Prix Unitaire</th><th>Prix</th></tr>");
                for (LineCommand p : cart.getProducts()) {
                    out.print("<tr><td class='prems'><div class='info'><a href=\"?page=Product&id=" + p.getProduct().getId()
                            + "\">" + p.getProduct().getName() + "</a>"
                            + "</div></td><td class='quant'><p id='nb'>" + p.getQuantity()
                            + "</p></td><td class='price'>"
                            + String.format("%.2f &euro;", p.getProduct().getPrice()) + "</td><td class='priceLine'>"
                            + String.format("%.2f &euro;", p.storePrice()) + "</tr>");

                    //out.println( + "x " +  + " : " + String.format("<div class='price'>%.2f &euro;</div>", p.storePrice()) + "<br/>");
                }
                out.println("<tr><td class='lineTotal1'></td><td class='lineTotal2'></td><td class='tittleTotal'>Total : </td><td class='priceTotal'>"
                        + String.format("%.2f &euro;", cart.getTotal()) + "</td></tr> ");
                out.println("</table>");
                
                
                out.println("<form name='adress' action='?page=Payer' method='POST'>"
                        + "<input type='radio' name='payment' id='paypal'>Paypal</input>"
                        + "<input checked='true' type='radio' name='payment' id='carte'>Carte Bancaire</input>"
                        + "<input type=\"submit\" value=\"Submit\"/>"
                        + "</form>");
            }
        }

    }
}
