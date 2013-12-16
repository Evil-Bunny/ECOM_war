/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
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
        return "Informations client";
    }
    
    @Override
    protected List<String> getArianeNames(HttpServletRequest request) {
        return Arrays.asList("Panier");
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        return Arrays.asList("?page=ViewCart");
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Client c = (Client)session.getAttribute("client");
        String firstname = "";
        String surname = "";
        String addressPayment = "";
        String addressDelivery = "";
        String mail = "";
        
        if (c != null) { //connecté
            firstname = c.getFirstname();
            surname = c.getSurname();
            addressPayment = c.getAddressPayement().getName();
            addressDelivery = c.getAddressDelivery().getName();
            mail = c.getMail();
        }
        
        out.println("<form action='?page=ConfirmPanier' method='POST' onsubmit='return checkConfirmAddress();'>");
        out.println("<fieldset><legend>Adresse de facturation</legend>");
        out.println("<label>Prénom :<input id='nameFact' type='text' name='nameFact' value='"+firstname+"'/></label>");
        out.println("<label>Nom de famille :<input id='surnameFact' type='text' name='surnameFact' value='"+surname+"'/></label>");
        out.println("<label>Adresse :<textarea id='addressFact' name='addressFact'>"+addressPayment+"</textarea></label></fieldset>");

        out.println("<label>Utiliser la même adresse pour la facturation et la livraison : <input id='sameAddr' name='sameAddr' type='checkbox' onclick='sameAddrOnClick()'/></label>");
        out.println("<fieldset><legend>Adresse de livraison</legend>");
        out.println("<label>Prénom :<input id='nameLivr' type='text' name='nameLivr' value='"+firstname+"'/></label>");
        out.println("<label>Nom de famille :<input id='surnameLivr' type='text' name='surnameLivr' value='"+surname+"'/></label>");
        out.println("<label>Adresse :<textarea id='addressLivr' name='addressLivr'>"+addressDelivery+"</textarea></label></fieldset>");

        out.println("<fieldset><legend>Contact</legend>");
        out.println("<label>E-mail :<input id='input_mail' type='text' name='mail' value='"+mail+"'/></label>");
        out.println("<label>Téléphone :<input id='input_tel' type='text' name='tel'/></label></fieldset>");

        out.println("<input id='confirm' type='submit' value='Confirmer les informations'/></form>");
    }
}
