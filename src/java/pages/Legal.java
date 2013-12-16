
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
public class Legal extends AbstractPage {
    
    @Override
    protected String getTitle(HttpServletRequest request){
        return "Legal";
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
        
    out.println("<div id=\"AllLegal\">\n" +
        "    <img id=\"logo-home\" alt=\"Legal !\" src=\"img/logo-home.png\" width=\"71px\" height=\"100px\"/>\n" +
        "    <img id=\"ballon\" alt=\"say\" src=\"img/ballon.png\" width=\"20px\" height=\"100px\"/>\n" +
        "    <div id=\"PresLegal\">\n" +
        "        <h2>Evil Bunny</h2>\n" +
        "        <ul id ='TextLegal'>\n" +
        "             <h3>Engagement Evil bunny</h3>\n" +
        "             <p id ='ad'> Evil Bunny s'engage auprès de vous, visiteurs et clients de notre site, afin de vous garantir la confidentialité des informations personnelles que vous nous fournissez.\n</p>"+
        "             <h3>Respect des réglementations française et européenne</h3>\n" +
        "             <p id ='ad'> Conformément à la loi Informatique et Libertés en date du 6 janvier 1978, vous disposez d'un droit d'accès, de rectification, de modification et de suppression concernant les données qui vous concernent. </p>" +
        "        </ul>\n" +
        "    </div>\n" +
        "</div>");
    }
    
}
