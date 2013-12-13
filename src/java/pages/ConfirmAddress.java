/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;

/**
 *
 * @author msi
 */
public class ConfirmAddress extends AbstractPage {

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Confirmation d'adresse";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("client") == null) {//pas connecté
            out.print("<form name=\"adress\" action=\"?page=ConfirmPanier\" method=\"POST\" onsubmit='return checkConfirmAddress();'>"
                    +" <fieldset><legend>Addresse de facturation</legend>"
                    + "            <label for=\"nameFact\">Prénom</label>\n"
                    + "            <input type=\"text\" id='nameFact' name=\"nameFact\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surnameFact\">Nom de famille</label>\n"
                    + "            <input type=\"text\" id='surnameFact' name=\"surnameFact\" value=\"\" size=\"30\" /><br/>\n"
                    + "<label for=\"addressFact\">Adresse de facturation</label><br />"
                    + "<textarea style=\"resize:none\" id='addressFact' rows=\"4\" cols=\"50\" name=\"addressFact\"></textarea></fieldset><br />"
                    
                    +" <fieldset><legend>Addresse de livraison</legend>"
                    + "            <label for=\"nameLivr\">Prénom</label>\n"
                    + "            <input type=\"text\" id='nameLivr' name=\"nameLivr\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surnameLivr\">Nom de famille</label>\n"
                    + "            <input type=\"text\" id='surnameLivr' name=\"surnameLivr\" value=\"\" size=\"30\" /><br/>\n"
                    + "<label for=\"addressLivr\">Adresse de livraison</label><br />"
                    + "<textarea style=\"resize:none\" id='addressLivr' rows=\"4\" cols=\"50\" name=\"addressLivr\"></textarea></fieldset><br />"
                    + "<label for=\"mail\">e-mail de contact</label><br />"
                    + "            <input type=\"text\" id='mail' name=\"mail\" value=\"\" size=\"30\" /><br/>\n"
                    + "<label for=\"tel\">Téléphone de contact</label><br />"
                    + "            <input type=\"text\"  id='tel' name=\"tel\" value=\"\" size=\"30\" /><br/>\n"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "</form>");
        } else {//connecté

            Client c = (Client) session.getAttribute("client");

            out.print("<form name=\"adress\" action=\"?page=ConfirmPanier\" method=\"POST\" onsubmit='return checkConfirmAddress();'>"
                    +" <fieldset><legend>Addresse de facturation</legend>"
                    + "            <label for=\"nameFact\">Prénom</label>\n"
                    + "            <input type=\"text\" id='nameFact' name=\"nameFact\" value=\"" + c.getFirstname() + "\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surnameFact\">Nom de famille</label>\n"
                    + "            <input type=\"text\" id='surnameFact' name=\"surnameFact\" value=\"" + c.getSurname() + "\" size=\"30\" /><br/>\n"
                    + "<label for=\"addressFact\">Adresse de facturation</label><br />"
                    + "<textarea style=\"resize:none\" id='addressFact' rows=\"4\" cols=\"50\" name=\"addressFact\">" + c.getAddressPayement().getName() + "</textarea></fieldset><br />"
                    
                    +" <fieldset><legend>Addresse de livraison</legend>"
                    + "            <label for=\"nameLivr\">Prénom</label>\n"
                    + "            <input type=\"text\" id='nameLivr' name=\"nameLivr\" value=\"" + c.getFirstname() + "\" size=\"30\" /><br/>\n"
                    + "            <label for=\"surnameLivr\">Nom de famille</label>\n"
                    + "            <input type=\"text\" id='surnameLivr'  name=\"surnameLivr\" value=\"" + c.getSurname() + "\" size=\"30\" /><br/>\n"
                    + "<label for=\"addressLivr\">Adresse de livraison</label><br />"
                    + "<textarea id='addressLivr' style=\"resize:none\" rows=\"4\" cols=\"50\" name=\"addressLivr\">" + c.getAddressDelivery().getName() + "</textarea></fieldset><br />"
                    + "<label for=\"mail\">e-mail de contact</label><br />"
                    + "            <input type=\"text\" id='mail' name=\"mail\" value=\""+c.getMail()+"\" size=\"30\" /><br/>\n"
                    + "<label for=\"tel\">Téléphone de contact</label><br />"
                    + "            <input type=\"text\" id='tel' name=\"tel\" value=\"\" size=\"30\" /><br/>\n"
                    + "<input type=\"submit\" value=\"Submit\" />"
                    + "</form>");

        }
    }
}
