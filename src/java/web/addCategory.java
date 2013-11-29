/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.CategoryFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.type.Category;

/**
 *
 * @author msi
 */
public class addCategory extends HttpServlet {

    @EJB
    CategoryFacade cf;

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
            out.println("<title>Servlet addCategory</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addCategory at " + request.getContextPath() + "</h1>");
            if (!request.getParameterNames().hasMoreElements()) {

                out.println("<form name=\"addCategory\" action=\"addCategory\" method=\"POST\">");
                out.println("<label for=\"name\">Category name</label>");
                out.println("<input id=\"name\" type=\"text\" name=\"name\" value=\"\" size=\"30\" /><br/>");
                out.println("<label for=\"parent\">Parent</label>");
                out.println("<select id=\"parent\" type=\"text\" name=\"parent\" value=\"\" size=\"1\">");
                out.println("<option value=\"null\">Aucun</option>");
                
                for (Category c : cf.findAll()) {
                    out.println("<option value=\"" + c.getId() + "\">" + c.getCategorie() + "</option>");
                }
                
                out.println("</select><br />");
                out.println("<input type=\"submit\" value=\"Submit\" />");
                out.println("</form>");
            } else {
                Category c = new Category();
                c.setCategorie(request.getParameter("name"));

                if (!request.getParameter("parent").equals(new String("null"))) {
                    c.setParent(cf.find(Long.parseLong(request.getParameter("parent"))));
                }
                cf.edit(c);
                
                out.println("Catégorie "+request.getParameter("name")+ " ajoutée");
            }
                out.println("</body>");
                out.println("</html>");
            }  finally {
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
