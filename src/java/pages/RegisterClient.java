/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ClientFacade;
import command.Cart;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;
import user.data.Address;

/**
 *
 * @author Samy
 */
public class RegisterClient extends AbstractPage {

    @EJB
    private ClientFacade cif;

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Inscription";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);

        if (request.getParameter("address") != null && request.getParameter("name") != null && request.getParameter("surname") != null && request.getParameter("username") != null && request.getParameter("password") != null && !request.getParameter("address").isEmpty() && !request.getParameter("name").isEmpty() && !request.getParameter("surname").isEmpty() && !request.getParameter("username").isEmpty() && !request.getParameter("password").isEmpty()) {
            Address ai = new Address();
            ai.setName(request.getParameter("address"));
            Client ci = new Client();
            ci.setAddressDelivery(ai);
            ci.setAddressPayement(ai);
            ci.setFirstname(request.getParameter("name"));
            ci.setSurname(request.getParameter("surname"));
            ci.setUsername(request.getParameter("username"));

            ci.setPassword(request.getParameter("password"));
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new Cart());
            }
            ci.setCart((Cart) session.getAttribute("cart"));
            try {
                cif.edit(ci);
                session.setAttribute("client", ci);
                out.print("Inscription Réussie");
            } catch (EJBException e) {
                out.println(e.getMessage());
            }

        } else {
            if (request.getParameter("username") != null) {
                out.println("Veuillez renseigner tous les champs.<br />");
            }
            out.println("<form name=\"register\" action=\"?page=RegisterClient\" method=\"POST\">"
                    + "<label for=\"username\">Identifiant</label>\n"
                    + "            <input id=\"username\" type=\"text\" name=\"username\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"password\">Mot de passe</label>\n"
                    + "            <input id=\"password\" type=\"password\" name=\"password\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"name\">Prénom</label>\n"
                    + "            <input id=\"name\" type=\"text\" name=\"name\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surname\">Nom de famille</label>\n"
                    + "            <input id=\"surname\" type=\"text\" name=\"surname\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"address\">Addresse</label><br />\n"
                    + "            <textarea style=\"resize:none\" rows=\"4\" cols=\"50\" name=\"address\"></textarea><br /><br />"
                    + "            <input type=\"submit\" value=\"Submit\" />\n"
                    + "</form>\n");

        }
    }
}
