/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.ManufacturerFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static pages.AbstractPage.HTMLEncode;
import product.Manufacturer;
import product.type.Category;
import product.type.Characteristic;

/**
 *
 * @author Samy
 */
public class AdminSearch extends HttpServlet {
    @EJB
    CategoryFacade cf;
    
    @EJB
    ManufacturerFacade mf;
    
    @EJB
    CharacteristicFacade chf;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminSearch</title>");            
            out.println("</head>");
            out.println("<body>");
        List<Manufacturer> manufacturers = mf.findAll();
        List<Characteristic> caracteristics = chf.findAll();
        out.println("<form action='AdminProducts' method='POST'>");
        out.println("<input type='hidden' name='page' value='Products'/>");
        out.println("<label>Nom du produit<input name='name' type='text'/></label>");
        out.println("<label>Marque & Constructeur<select name='manufacturer'>");
        out.println("<option value=''>Choisir une marque</option>");
        Collections.sort(manufacturers);
        for (Manufacturer m : manufacturers) {
            out.println("<option value='"+m.getId()+"'>"+HTMLEncode(m.getName())+"</option>");
        }
        out.println("</select></label>");
        out.println("<label>Catégorie<select name='category'>");
        out.println("<option value=''>Choisir une catégorie</option>");
        for (Category c : cf.findAll()) {
            out.println("<option value='"+c.getId()+"'>"+HTMLEncode(c.getCategorie())+"</option>");
        }
        out.println("</select></label>");
        out.println("<label>En stock<input type='checkbox' name='stock' checked='checked'/></label>");
        out.println("<fieldset><legend>Prix</legend><label>supérieur à<input id='minPrice' type='text' name='minPrice'/></label><label>inférieur à<input id='maxPrice' type='text' name='maxPrice'/></label></fieldset>");
        out.println("<fieldset id='carac'><legend>Caractéristiques</legend><div><table id='caracs'>");
        out.println("<tr><td>Nom</td><td>Valeur</td><td></td></tr>");
        out.println("<tr><td><select name='caracName_1'>");
        Collections.sort(caracteristics);
        for (Characteristic c : caracteristics) {
            out.println("<option value='"+c.getId()+"'>"+HTMLEncode(c.getName())+"</option>");
        }
        out.println("</select></td><td><input type='text' name='caracVal_1'/></td><td><button type='button' onclick='delCarac(this)'>X</button></td></tr>");
        out.println("<tr><td><button type='button' onclick='addCarac()'>+</button></td><td></td></tr>");
        out.println("</table></div></fieldset>");
        out.println("<input type='submit' value='Rechercher'/>");
        out.println("</form>");            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
