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
import user.Client;
import ejb.CartFacade;
import ejb.ClientFacade;
import ejb.LineCommandFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author msi
 */
public class Payer extends AbstractPage {

    @EJB
    ClientFacade cf;
    
    @EJB
    CartFacade cartf;
    
    @EJB
    LineCommandFacade lcf;
    
    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Payement de la commande";
    }

    @Override
    protected List<String> getArianeNames(HttpServletRequest request) {
        return Arrays.asList("Panier", "Informations client", "Vérification avant paiment");
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        return Arrays.asList("?page=ViewCart", "?page=ConfirmAddress", "?page=ConfirmPanier");
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        Command c = (Command) session.getAttribute("command");
        if (c != null) {
            //if
            out.println("Payement accepté");
            c.setDateCommand(new Date());
            c.setClient((Client)session.getAttribute("client"));
            cf.edit((Client)session.getAttribute("client"));
            Client cl = (Client) session.getAttribute("client");
            if (cl != null) {
                Cart cartClient = cl.getCart();
                for (LineCommand lc : cartClient.getProducts())
                {
                    lcf.remove(lc);
                }
                cartClient.setProducts(new ArrayList<LineCommand>());
                cartf.edit(cartClient);
            }
            session.removeAttribute("command");
            session.removeAttribute("cart");
        } else {
            out.println("Vous n'avez aucune commande à payer, peut être avez vous modifié votre panier.");
        }
    }
}
