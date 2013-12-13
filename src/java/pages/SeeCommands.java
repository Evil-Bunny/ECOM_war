/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import command.Command;
import command.LineCommand;
import ejb.CommandFacade;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;

/**
 *
 * @author Samy
 */
public class SeeCommands extends AbstractPage {

    @EJB
    CommandFacade cf;

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Récapitulatif des commandes";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
            HttpSession session = request.getSession(true);
            if (session.getAttribute("client") == null) {
                throw new HTTPRedirect(".");
            } else {
                List<Command> commands = ((Client) session.getAttribute("client")).getCommands();
                if (commands.isEmpty()){
                    out.println("<p>Aucune commande n'est enregistrée pour votre profil.<p/>");
                } else {
                    out.println("toto");
                    for (Command command : commands) {
                        out.println("commande n° " + command.getId() + " :");

                        out.println("<table>");
                        out.println("<tr><th>Produit</th><th class='quant'>Quantité</th><th class='price'>Prix Unitaire</th><th>Prix</th><th>Supprimer</th></tr>");
                        for (LineCommand lc : command.getProducts()) {
                                out.print("<tr><td class='prems'><div class='info'><a href=\"?page=Product&id=" + lc.getProduct().getId()
                                        + "\">" + lc.getProduct().getName() + "</a>"
                                        + "</div></td><td class='quant'><p id='nb'>" + lc.getQuantity()
                                        + "</p>");
                                out.println("</td><td class='price'>"
                                        + String.format("%.2f &euro;", lc.getProduct().getPrice()) + "</td><td class='priceLine'>"
                                        + String.format("%.2f &euro;", lc.getPrice()) + "</td></tr>");
                        }
                        out.println("<tr><td class='lineTotal1'></td><td class='lineTotal2'></td><td class='tittleTotal'>Total : </td><td class='priceTotal'>"
                                + String.format("%.2f &euro;", command.getTotal()) + "</td><td class='lineTotal3'></td></tr> ");
                        out.println("</table>");

                    }
                }
            }
    }
}
