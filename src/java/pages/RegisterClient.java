/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ClientFacade;
import command.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        
        if (request.getParameter("address")!=null && request.getParameter("name")!=null && request.getParameter("surname")!=null && request.getParameter("username")!=null && request.getParameter("password")!=null){
            Address ai = new Address();
            ai.setName(request.getParameter("address"));
            Client ci = new Client();
            ci.setAddressDelivery(ai);
            ci.setAddressPayement(ai);
            ci.setFirstname(request.getParameter("name"));
            ci.setSurname(request.getParameter("surname"));
            ci.setUsername(request.getParameter("username"));
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(request.getParameter("password").getBytes());
                byte byteData[] = md.digest();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < byteData.length; i++) {
                    String hex = Integer.toHexString(0xff & byteData[i]);
                    if (hex.length() == 1) {
                        sb.append('0');
                    }
                    sb.append(hex);
                }
                ci.setPassword(sb.toString());
                ci.setCart(new Cart());
                try {
                    cif.edit(ci);
                    out.print("Inscription Réussie");
                } catch (EJBException e) {
                    out.println(e.getMessage());
                }
            } catch (NoSuchAlgorithmException e) {
                out.println("Impossible de hasher le password");
            }

        } else {
            out.println("<form name=\"register\" action=\"?page=RegisterClient\" method=\"POST\">"
                    + "<label for=\"username\">username</label>\n"
                    + "            <input id=\"username\" type=\"text\" name=\"username\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"password\">password</label>\n"
                    + "            <input id=\"password\" type=\"password\" name=\"password\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"name\">first name</label>\n"
                    + "            <input id=\"name\" type=\"text\" name=\"name\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surname\">surname</label>\n"
                    + "            <input id=\"surname\" type=\"text\" name=\"surname\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"address\">address</label>\n"
                    + "            <input id=\"address\" type=\"text\" name=\"address\" value=\"\" size=\"100\" /><br/>\n"
                    + "            <input type=\"submit\" value=\"Submit\" />\n"
                    + "</form>\n");

        }
    }
}
