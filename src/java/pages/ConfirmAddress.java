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
        HttpSession session = request.getSession(true);
        if (session.getAttribute("client") == null) {
            return "Entrer les informations";
        }else{
            return "Confirmer les informations";
        }
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("client") == null) {//pas connecté
            out.print("<form name=\"adress\" action=\"?page=ConfirmPanier\" method=\"POST\" onsubmit='return checkConfirmAddress();'>"
                    +" <fieldset><legend>Addresse de facturation</legend>"
                    + "            <label for=\"nameFact\">Prénom : </label>\n"
                    + "            <input id=\"nameFact\"type=\"text\" name=\"nameFact\" value=\"\" /><br/>\n"
                    + "            <label for=\"surnameFact\">Nom de famille : </label>\n"
                    + "            <input id=\"surnameFact\" type=\"text\" name=\"surnameFact\" value=\"\" /><br/>\n"
                    + "<label for=\"addressFact\">Adresse de facturation : "
                    + "<textarea style=\"resize:none\" id='addressFact' rows=\"4\" cols=\"50\" name=\"addressFact\"></textarea></label></fieldset><br />"
                    
                    +" <fieldset><legend>Addresse de livraison</legend>"
                    + "            <label for=\"nameLivr\">Prénom : </label>\n"
                    + "            <input id=\"nameLivr\" type=\"text\" name=\"nameLivr\" value=\"\" /><br/>\n"
                    + "            <label for=\"surnameLivr\">Nom de famille : </label>\n"
                    + "            <input id=\"surnameLivr\" type=\"text\" name=\"surnameLivr\" value=\"\" /><br/>\n"
                    + "<label for=\"addressLivr\">Adresse de livraison : "
                    + "<textarea style=\"resize:none\" id='addressLivr' rows=\"4\" cols=\"50\" name=\"addressLivr\"></textarea></label></fieldset><br />"
                    + "<fieldset><legend>Contact</legend>"
                    + "<label for=\"mail\">E-mail : </label>"
                    + "            <input id=\"input_mail\" type=\"text\" name=\"mail\" value=\"\" /><br/>\n"
                    + "<label for=\"tel\">Téléphone : </label>"
                    + "            <input id=\"input_tel\" type=\"text\" name=\"tel\" value=\"\" /></fieldset>\n"
                    + "<input id=\"confirm\" type=\"submit\" value=\"Confirmer les informations\" />"
                    + "</form>");
        } else {//connecté

            Client c = (Client) session.getAttribute("client");

            out.print("<form name=\"adress\" action=\"?page=ConfirmPanier\" method=\"POST\" onsubmit='return checkConfirmAddress();'>"
                    + " <fieldset><legend>Addresse de facturation</legend>"
                    + "            <label for=\"nameFact\">Prénom : </label>\n"
                    + "            <input id=\"nameFact\" type=\"text\" name=\"nameFact\" value=\"" + c.getFirstname() + "\" /><br/>\n"
                    + "            <label for=\"surnameFact\">Nom de famille : </label>\n"
                    + "            <input id=\"surnameFact\" type=\"text\" name=\"surnameFact\" value=\"" + c.getSurname() + "\" /><br/>\n"
                    + "<label for=\"addressFact\">Adresse de facturation : "
                    + "<textarea id='addressFact' style=\"resize:none\" rows=\"4\" cols=\"50\" name=\"addressFact\">" + c.getAddressPayement().getName() + "</textarea></label></fieldset><br />"
                    
                    + " <fieldset><legend>Addresse de livraison</legend>"
                    + "            <label for=\"nameLivr\">Prénom : </label>\n"
                    + "            <input id=\"nameLivr\" type=\"text\" name=\"nameLivr\" value=\"" + c.getFirstname() + "\" /><br/>\n"
                    + "            <label for=\"surnameLivr\">Nom de famille : </label>\n"
                    + "            <input id=\"surnameLivr\" type=\"text\" name=\"surnameLivr\" value=\"" + c.getSurname() + "\" /><br/>\n"
                    + "<label for=\"addressLivr\">Adresse de livraison : "
                    + "<textarea style=\"resize:none\" rows=\"4\" cols=\"50\" id='addressLivr' name=\"addressLivr\">" + c.getAddressDelivery().getName() + "</textarea></label></fieldset><br />"
                    
                    + "<fieldset><legend>Contact</legend>"
                    + "<label for=\"mail\">E-mail : </label>"
                    + "            <input id=\"input_mail\" type=\"text\" name=\"mail\" value=\"" + c.getMail() + "\" /><br/>\n"
                    + "<label for=\"tel\">Téléphone : </label>"
                    + "            <input id=\"input_tel\" type=\"text\" name=\"tel\" value=\"\" /></fieldset>\n"
                    
                    + "<input id=\"confirm\" type=\"submit\" value=\"Confirmer les informations\" />"
                    + "</form>");

        }
    }
}
