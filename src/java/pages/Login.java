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
                out.println("Connection Reussie");
                session.setAttribute("client", ci);
            } else {
                out.println("Erreur de login/mdp");
            }

        } else {
            out.println("        <form name=\"register\" action=\"?page=Login\" method=\"POST\">\n"
                    + "            <label for=\"username\">Username</label>\n"
                    + "            <input id=\"username\" type=\"text\" name=\"username\" value=\"\" size=\"30\" /><br/>\n"
                    + "            <label for=\"pass\">Password</label>\n"
                    + "            <input id=\"pass\" type=\"password\" name=\"pass\" value=\"\" size=\"30\" />   <br/>     \n"
                    + "            <input type=\"submit\" value=\"Log in\" />\n"
                    + "        </form>");

        }
    }
}
