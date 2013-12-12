package pages;

import ejb.ClientFacade;
import ejb.CommandFacade;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.Client;

/**
 *
 * @author Samy
 */
public class Login extends AbstractPage {

    @EJB
    ClientFacade cif;
    @EJB
    private CommandFacade cef;

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Connection";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        if (request.getParameter("pass") != null && request.getParameter("username") != null) {
            Client ci = cif.find(request.getParameter("username"), request.getParameter("pass"));
            if (ci != null) {
                out.println("<h2>Connection Réussie</h2>");
                session.setAttribute("client", ci);
                out.println("Connection Reussie");
                throw new HTTPRedirect(".");
            } else {
                out.println("<h2>Veuillez vous identifier de nouveau</h2>");
                out.println("<h3>Identifiant ou de mot de passe inconnu</h3>");
                out.println("        <form name=\"register\" action=\"?page=Login\" method=\"POST\">\n"
                        + "            <label for=\"username\">Identifiant : "
                        + "            <input id=\"username\" type=\"text\" name=\"username\" value=\""+request.getParameter("username")+"\" /></label><br/>\n"
                        + "            <label for=\"pass\">Mot de passe : "
                        + "            <input id=\"pass\" type=\"password\" name=\"pass\" value=\"\" /> </label>  <br/>     \n"
                        + "            <input id=\"button\" type=\"submit\" value=\"Se connecter\" />\n"
                        + "        </form>");
            }

        } else {
            out.println("<h2>Veuillez vous identifier</h2>");
            out.println("        <form name=\"register\" action=\"?page=Login\" method=\"POST\">\n"
                    + "            <label for=\"username\">Identifiant : "
                    + "            <input id=\"username\" type=\"text\" name=\"username\" value=\"\" /></label><br/>\n"
                    + "            <label for=\"pass\">Mot de passe : "
                    + "            <input id=\"pass\" type=\"password\" name=\"pass\" value=\"\" />  </label> <br/>     \n"
                    + "            <input id=\"button\" type=\"submit\" value=\"Se connecter\" />\n"
                    + "        </form>");

        }
    }
}
