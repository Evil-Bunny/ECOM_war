/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.HomeAdFacade;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.HomeAd;

/**
 *
 * @author bousky
 */
public class Home extends AbstractPage {

    @EJB
    HomeAdFacade haf;
    
    @Override
    protected String getTitle(HttpServletRequest request){
        return "Accueil";
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<HomeAd> ads = haf.findAll();
        
        out.println("<div id=\"AllHome\">\n" +
            "    <img id=\"logo-home\" alt=\"Bienvenue !\" src=\"img/logo-home.png\" width=\"71px\" height=\"100px\"/>\n" +
            "    <img id=\"ballon\" alt=\"say\" src=\"img/ballon.png\" width=\"20px\" height=\"100px\"/>\n" +
            "    <div id=\"PresHome\">\n" +
            "        <h2>Evil Bunny</h2>\n" +
            "        <p id ='TextHome'>\n" +
            "            Votre site de E-commerce spécialisé dans la vente de matériel informatique rétro.</br>\n" +
            "            Du matériel d'occasion à votre disposition pour un prix incomparable.</br>\n" +
            "            Présent pour vous satisfaire depuis 2014.\n" +
            "        </p>\n" +
            "    </div>\n" +
            "</div>");
        
        out.println("<div id='pub'><table><tr>");
        printAd(out, ads, 0);
        printAd(out, ads, 1);
        printAd(out, ads, 2);
        out.println("</tr><tr>");
        printAd(out, ads, 3);
        printAd(out, ads, 4);
        printAd(out, ads, 5);
        out.println("</tr></table></div>");
    }

    private void printAd(PrintWriter out, List<HomeAd> ads, int id) {
        HomeAd ad;
        try {
            ad = ads.get(id);
        } catch (ArrayIndexOutOfBoundsException e) {
            out.println("<td></td>");
            return;
        }
        out.println("<td><a href='"+ad.getProduct().getId()+"'>");
        out.println("<img alt='' src='img/category.png' width='256px' height='150px' style='background: white'/>");
        out.println(String.format("<span class='price'>%.2f &euro;</span>", ad.getProduct().getPrice()));
        out.println("<div class='name'>"+ad.getName()+"</div>");
        out.println("</a></td>");
    }
    

}