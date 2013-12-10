/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Client;
import user.data.Address;

/**
 *
 * @author bousky
 */
public class Account extends AbstractPage {

    @Override
    protected String getTitle(HttpServletRequest request){
        return "Compte client";
    }
    
    @Override
    protected void printPagePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        Client c = (Client)request.getSession(true).getAttribute("client");
        boolean formOK = true;
        
        out.println("<noscript>");
        if (request.getParameter("username") == null || request.getParameter("username").length() < 3) {
            out.println("Le nom d'utilisateur doit faire plus de 3 charactères.<br />");
            formOK = false;
        }
        if (request.getParameter("mail") == null || ! request.getParameter("mail").contains("@")) {
            out.println("L'email n'est pas valide<br />");
            formOK = false;
        }
        if (request.getParameter("addressPayement") == null || request.getParameter("addressPayement").isEmpty()) {
            out.println("L'adresse de facturation est requise<br/>");
            formOK = false;
        }
        if (request.getParameter("addressDelivery") == null || request.getParameter("addressDelivery").isEmpty()) {
            out.println("L'adresse de livraison est requise<br/>");
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
        if (formOK) {
            Address tmp;
            c.setUsername(request.getParameter("username"));
            c.setMail(request.getParameter("mail"));
            c.setFirstname(request.getParameter("name"));
            c.setSurname(request.getParameter("surname"));
            // mise à jour des adresses
            if (c.getAddressDelivery().equals(c.getAddressPayement())) {
                if (request.getParameter("addressPayement").equals(request.getParameter("addressDelivery"))) {
                    c.getAddressPayement().setName(request.getParameter("addressPayement"));
                } else {
                    c.getAddressPayement().setName(request.getParameter("addressPayement"));
                    tmp = new Address();
                    tmp.setName(request.getParameter("addressDelivery"));
                    c.setAddressDelivery(tmp);
                }
            } else {
                if (request.getParameter("addressPayement").equals(request.getParameter("addressDelivery"))) {
                    c.getAddressPayement().setName(request.getParameter("addressPayement"));
                    c.setAddressDelivery(c.getAddressPayement());
                } else {
                    c.getAddressPayement().setName(request.getParameter("addressPayement"));
                    c.getAddressDelivery().setName(request.getParameter("addressDelivery"));
                    
                }
            }
            printPage(out, request, response);
        } else {
            out.println("<form action='?page=Account' method='POST' onsubmit='return checkAccount();'>");

            out.print("<label>Identifiant :<input name='username' id='username' type='text' value='");
            if (request.getParameter("username") != null)
                out.print(HTMLEncode(request.getParameter("username")));
            out.println("'/></label>");

            out.print("<label>Prénom :<input name='name' id='name' type='text' value='");
            if (request.getParameter("name") != null)
                out.print(HTMLEncode(request.getParameter("name")));
            out.println("'/></label>");

            out.print("<label>Nom de famille :<input name='surname' id='surname' type='text' value='");
            if (request.getParameter("surname") != null)
                out.print(HTMLEncode(request.getParameter("surname")));
            out.println("'></label>");

            out.print("<label>Mail :<input name='mail' id='mail' type='text' value='");
            if (request.getParameter("mail") != null)
                out.print(HTMLEncode(request.getParameter("mail")));
            out.println("'/></label>");

            out.print("<label>Addresse de facturation :<textarea id='addressPayement' name='addressPayement'>");
            if (request.getParameter("addressPayement") != null)
                out.print(HTMLEncode(request.getParameter("addressPayement")));
            out.println("</textarea></label>");

            out.print("<label>Addresse de livraison :<textarea id='addressDelivery' name='addressDelivery'>");
            if (request.getParameter("addressDelivery") != null)
                out.print(HTMLEncode(request.getParameter("addressDelivery")));
            out.println("</textarea></label>");

            out.println("<input type='submit' value='Enregistrer les modifications'/></form>");
            out.println("<script type='text/javascript'>checkAccount();</script>");
        }

    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        Client c = (Client)request.getSession(true).getAttribute("client");
        
        out.println("<form action='?page=Account' method='POST' onsubmit='return checkAccount();'>");
        out.println("<label>Identifiant :<input name='username' id='username' type='text' value='"+HTMLEncode(c.getUsername())+"'/></label>");
        out.println("<label>Prénom :<input name='name' id='name' type='text' value='"+HTMLEncode(c.getFirstname())+"'/></label>");
        out.println("<label>Nom de famille :<input name='surname' id='surname' type='text' value='"+HTMLEncode(c.getSurname())+"'></label>");
        out.println("<label>Mail :<input name='mail' id='mail' type='text' value='"+HTMLEncode(c.getMail())+"'/></label>");
        out.print("<label>Addresse de facturation :<textarea id='addressPayement' name='addressPayement'>");
        out.print(HTMLEncode(c.getAddressPayement().getName()));
        out.println("</textarea></label>");
        out.print("<label>Addresse de livraison :<textarea id='addressDelivery' name='addressDelivery'>");
        out.print(HTMLEncode(c.getAddressDelivery().getName()));
        out.println("</textarea></label>");
        out.println("<input type='submit' value='Enregistrer les modifications'/></form>");
        
    }
    
}