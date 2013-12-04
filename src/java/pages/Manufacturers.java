/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.ManufacturerFacade;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.Manufacturer;

/**
 *
 * @author bousky
 */
public class Manufacturers extends AbstractPage {
    
    @EJB
    ManufacturerFacade mf;

    @Override
    protected String getTitle(HttpServletRequest request){
        return "Marques &amp; Constructeurs";
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<Manufacturer> manufacturers = mf.findAll();
        Collections.sort(manufacturers);
        out.println("<ul>");
        for (Manufacturer m : manufacturers) {
            out.println("<li><a href='?page=Products&amp;manufacturer="+m.getId()+"'>"+m.getName()+"</a></li>");
        }
        out.println("</ul>");

    }
}