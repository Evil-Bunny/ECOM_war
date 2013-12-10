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

        if (request.getParameter("address") != null && request.getParameter("confirmPass") != null && request.getParameter("mail") != null && request.getParameter("name") != null && request.getParameter("surname") != null && request.getParameter("username") != null && request.getParameter("password") != null && !request.getParameter("address").isEmpty() && !request.getParameter("name").isEmpty() && !request.getParameter("surname").isEmpty() && !request.getParameter("username").isEmpty() && !request.getParameter("password").isEmpty() && !request.getParameter("confirmPass").isEmpty() && !request.getParameter("mail").isEmpty() && request.getParameter("password").equals(request.getParameter("confirmPass")) && request.getParameter("password").length() >= 3 && request.getParameter("username").length() >= 3 && request.getParameter("mail").contains("@")) {
            Address ai = new Address();
            ai.setName(request.getParameter("address"));
            Client ci = new Client();
            ci.setAddressDelivery(ai);
            ci.setAddressPayement(ai);
            ci.setFirstname(request.getParameter("name"));
            ci.setSurname(request.getParameter("surname"));
            ci.setMail(request.getParameter("mail"));
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
            if (request.getParameterNames().hasMoreElements()) {
                if (request.getParameter("address") == null || request.getParameter("confirmPass") == null || request.getParameter("mail") == null || request.getParameter("name") == null || request.getParameter("surname") == null || request.getParameter("username") == null || request.getParameter("password") == null || request.getParameter("address").isEmpty() || request.getParameter("name").isEmpty() || request.getParameter("surname").isEmpty() || request.getParameter("username").isEmpty() || request.getParameter("password").isEmpty() || request.getParameter("confirmPass").isEmpty() || request.getParameter("mail").isEmpty()) {
                    out.println("Veuillez renseigner tous les champs.<br />");
                }
                if (request.getParameter("username") != null && request.getParameter("username").length() < 3) {
                    out.println("Le nom d'utilisateur doit faire plus de 3 charactères.<br />");
                }
                if (request.getParameter("password") != null && request.getParameter("password").length() < 3) {
                    out.println("Le mot de passe doit faire plus de 3 charactères.<br />");
                }
                if (request.getParameter("password") != null && request.getParameter("confirmPass") != null && !request.getParameter("password").equals(request.getParameter("confirmPass"))) {
                    out.println("Les mots de passe sont différents.<br />");
                }
                if (request.getParameter("mail") != null && !request.getParameter("mail").contains("@")) {
                    out.println("L'email n'est pas valide<br />");
                }
            }
            out.print("<form action=\"?page=RegisterClient\" method=\"POST\" onsubmit='return checkRegister();'>\n");
            
            out.print("<label>Identifiant :<input name='username' id='username' type='text'");
            if (request.getParameter("username") != null) {
                out.print(" value='" + request.getParameter("username") + "'");
            }
            out.println("/></label>");
            out.print("            <label for=\"password\">Mot de passe :\n"
                    + "            <input name=\"password\" id=\"password\" type=\"password\" value=\"\" size=\"30\" /></label>\n"
                    + "            <label for=\"confirmPass\">Confirmation Mot de passe :\n"
                    + "            <input name=\"confirmPass\" id=\"confirmPass\" type=\"password\" value=\"\" size=\"30\" /></label>\n");


            out.print("<label>Prénom :<input name='name' id='name' type='text'");
            if (request.getParameter("name") != null) {
                out.print(" value='" + request.getParameter("name") + "'");
            }
            out.println("/></label>");

            out.print("<label>Nom de famille :<input name='surname' id='surname' type='text'");
            if (request.getParameter("surname") != null) {
                out.print(" value='" + request.getParameter("surname") + "'");
            }
            out.println("/></label>");


            out.print("<label>Mail :<input name='mail' id='mail' type='text'");
            if (request.getParameter("mail") != null) {
                out.print(" value='" + request.getParameter("mail") + "'");
            }
            out.println("/></label>");

            out.print("<label>Addresse :<textarea id='address' style='resize:none' rows='4' cols='50' name='address'>");
            if (request.getParameter("address") != null) {
                out.print(request.getParameter("address"));
            }
            out.println("</textarea></label>");

            out.println("            <input type=\"submit\" value=\"Submit\" />\n"
                    + "</form>\n");

        }
    }
}
