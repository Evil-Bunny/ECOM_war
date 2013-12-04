/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.CategoryFacade;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.type.Category;

/**
 *
 * @author bousky
 */
public class Categories extends AbstractPage {
    
    @EJB
    CategoryFacade cf;

    @Override
    protected String getTitle(HttpServletRequest request){
        return "Catégories";
    }
    
    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<Category> topCategories = new ArrayList();
        List<Category> subCategories;
        for (Category c : cf.findAll()) {
            if (c.getParent() == null)
                topCategories.add(c);
        }
        Collections.sort(topCategories);
        out.println("<ul>");
        for (Category c : topCategories) {
            out.println("<li><a href='?page=Products&amp;category="+c.getId()+"'>");
            if (request.getParameter("menu") == null)
                out.println("<img height='200px' width='200px' src='img/category.png' alt=''/>");
            out.println(c.getCategorie()+"</a><ul>");
            subCategories = c.getSubCategories();
            Collections.sort(subCategories);
            for (Category s : subCategories) {
                out.println("<li><a href='?page=Products&amp;category="+s.getId()+"'>"+s.getCategorie()+"</a></li>");
            }
            out.println("</ul></li>");
        }
        out.println("</ul>");
    }
    
}