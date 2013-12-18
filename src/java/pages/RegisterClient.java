/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ClientFacade;
import command.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
    protected List<String> getArianeNames(HttpServletRequest request) {
        return null;
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        return null;
    }

    @Override
    protected void printPagePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Address ai = new Address();
        Client ci = new Client();
        boolean formOK = true;        
        
        out.println("<noscript>");
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
            out.println("Le prénom est requis<br/>");
            formOK = false;
        }
        if (request.getParameter("surname") == null || request.getParameter("surname").isEmpty()) {
            out.println("Le nom de famille est requis<br/>");
            formOK = false;
        }
        out.println("</noscript>");
        if (! formOK) {
            printPage(out, request, response);
            out.println("<script type='text/javascript'>checkRegister();</script>");
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
            ci = cif.find(ci.getUsername(),request.getParameter("password"));
            session.setAttribute("client", ci);
            out.print("Inscription Réussie");
            String referer = request.getParameter("referer");
            if (referer != null && referer.startsWith(request.getRequestURL().toString()))
                throw new HTTPRedirect(referer);
            else
                throw new HTTPRedirect(".");
        } catch (EJBException e) {
            out.println(e.getMessage());
        }        
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        out.println("<form action='?page=RegisterClient' method='POST' onsubmit='return checkRegister();'>");

        out.print("<label>Identifiant :<input name='username' id='username' type='text'");
        if (request.getParameter("username") != null) {
            out.print(" value='" + HTMLEncode(request.getParameter("username")) + "'");
        }
        out.println("/></label>");
        
        out.println("<label>Mot de passe :<input name='password' id='password' type='password'/></label>");
        out.println("<label>Confirmation du mot de passe :<input name='confirmPass' id='confirmPass' type='password' /></label>");

        out.print("<label>Prénom :<input name='name' id='name' type='text'");
        if (request.getParameter("name") != null) {
            out.print(" value='" + HTMLEncode(request.getParameter("name")) + "'");
        }
        out.println("/></label>");

        out.print("<label>Nom de famille :<input name='surname' id='surname' type='text'");
        if (request.getParameter("surname") != null) {
            out.print(" value='" + HTMLEncode(request.getParameter("surname")) + "'");
        }
        out.println("/></label>");

        out.print("<label>Mail :<input name='mail' id='mail' type='text'");
        if (request.getParameter("mail") != null) {
            out.print(" value='" + HTMLEncode(request.getParameter("mail")) + "'");
        }
        out.println("/></label>");

        out.print("<label>Addresse :<textarea id='address' name='address'>");
        if (request.getParameter("address") != null) {
            out.print(HTMLEncode(request.getParameter("address")));
        }
        out.println("</textarea></label>");
        
        if (request.getParameter("referer") != null)
            out.println("<input type='hidden' name='referer' value='"+request.getParameter("referer")+"'/>");
        else
            out.println("<input type='hidden' name='referer' value='"+request.getHeader("referer")+"'/>");

        out.println("<input type='submit' value=\"S'inscrire\" /></form>");
    }
}
