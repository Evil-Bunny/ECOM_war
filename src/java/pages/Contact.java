/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

/**
 *
 * @author Pierrick
 */

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pierrick
 */
public class Contact extends AbstractPage {
    
    @Override
    protected String getTitle(HttpServletRequest request){
        return "Contact";
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
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        
    out.println("<div id=\"AllContact\">\n" +
        "    <img id=\"logo-home\" alt=\"Contact !\" src=\"img/logo-home.png\" width=\"71px\" height=\"100px\"/>\n" +
        "    <img id=\"ballon\" alt=\"say\" src=\"img/ballon.png\" width=\"20px\" height=\"100px\"/>\n" +
        "    <div id=\"PresContact\">\n" +
        "        <h2>Evil Bunny</h2>\n" +
        "        <ul id ='TextContact'>\n" +
        "             <li><h3>Adresse :</h3><p>42 rue de la Réponse </br> 42042 Chambles (France)</br></p></li>" +
        "             <li><h3>N° tel :</h3><p>+337 13 37 13 37</br></p></li>" +
        "             <li><h3>Email :</h3><p><a href='mailto:ecom.lapin@laposte.net'>ecom.lapin@laposte.net</a></br></p></li>" +
        "        </ul>\n" +
        "    </div>\n" +
        "</div>");
    }
    
}
