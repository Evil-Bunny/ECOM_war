/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ClientFacade;
import command.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
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
    protected void printPagePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Address ai = new Address();
        Client ci = new Client();
        boolean formOK = true;        
        
        if (request.getParameter("username") == null || request.getParameter("username").length() < 3) {
            out.println("Le nom d'utilisateur doit faire plus de 3 charactères.<br />");
            formOK = false;
        }
        if (request.getParameter("password") == null || request.getParameter("password").length() < 3) {
            out.println("Le mot de passe doit faire plus de 3 charactères.<br />");
            formOK = false;
        }
        if (request.getParameter("password") == null || request.getParameter("confirmPass") == null || ! request.getParameter("password").equals(request.getParameter("confirmPass"))) {
            out.println("Les mots de passe sont différents.<br />");
            formOK = false;
        }
        if (request.getParameter("mail") == null || ! request.getParameter("mail").contains("@")) {
            out.println("L'email n'est pas valide<br />");
            formOK = false;
        }
        if (request.getParameter("address") == null || request.getParameter("address").isEmpty()) {
            out.println("L'adresse est requise<br/>");
            formOK = false;
        }
        if (request.getParameter("name") == null || request.getParameter("name").isEmpty()) {
            out.println("Le prénom est requise<br/>");
            formOK = false;
        }
        if (request.getParameter("surname") == null || request.getParameter("surname").isEmpty()) {
            out.println("Le nom de famille est requise<br/>");
            formOK = false;
        }
        if (! formOK) {
            printPage(out, request, response);
            return;
        }
        
        ai.setName(request.getParameter("address"));
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
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        out.println("<form action='?page=RegisterClient' method='POST' onsubmit='return checkRegister();'>");

        out.print("<label>Identifiant :<input name='username' id='username' type='text'");
        if (request.getParameter("username") != null) {
            out.print(" value='" + request.getParameter("username") + "'");
        }
        out.println("/></label>");
        
        out.println("<label for='password'>Mot de passe :<input name='password' id='password' type='password'/></label>");
        out.println("<label for='confirmPass'>Confirmation Mot de passe :<input name='confirmPass' id='confirmPass' type='password' /></label>");

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

        out.println("<input type='submit' value=\"S'inscrire\" /></form>");

    }
}
