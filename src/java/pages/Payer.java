/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Cart;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import command.Command;
import command.LineCommand;
import ejb.CommandFacade;
import user.Client;
import ejb.CartFacade;
import ejb.LineCommandFacade;
import java.util.ArrayList;
import javax.ejb.EJB;

/**
 *
 * @author msi
 */
public class Payer extends AbstractPage {

    @EJB
    CommandFacade cf;
    
    @EJB
    CartFacade cartf;
    
    @EJB
    LineCommandFacade lcf;
    
    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Payement de la commande";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        Command c = (Command) session.getAttribute("command");
        if (c != null) {
            //if
            out.println("Payement accepté");
            cf.edit(c);
            Client cl = (Client) session.getAttribute("client");
            if (cl != null) {
                Cart cartClient = cl.getCart();
                for (LineCommand lc : cartClient.getProducts())
                {
                    out.println(lc.getProduct().getName()+"<br/>");
                    lcf.remove(lc);
                }
                cartClient.setProducts(new ArrayList<LineCommand>());
                cartf.edit(cartClient);
            }
            else
            {
                out.println("pas de client");
            }
            session.removeAttribute("command");
            session.removeAttribute("cart");
            //else
//            out.print("Payement refusé");
        } else {
            out.println("Vous n'avez aucune commande à payer, peut être avez vous modifié votre panier.");
        }
    }
}
