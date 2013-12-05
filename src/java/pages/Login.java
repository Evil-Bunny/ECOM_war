package pages;

import ejb.ClientFacade;
import ejb.CommandFacade;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(request.getParameter("pass").getBytes());
                byte byteData[] = md.digest();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < byteData.length; i++) {
                    String hex = Integer.toHexString(0xff & byteData[i]);
                    if (hex.length() == 1) {
                        sb.append('0');
                    }
                    sb.append(hex);
                }
                Client ci = cif.find(request.getParameter("username"), sb.toString());
                if (ci != null) {
                    out.println("Connection Reussie");
                    session.setAttribute("client", ci);
                } else {
                    out.println("Erreur de login/mdp");
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
