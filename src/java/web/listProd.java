package web;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import product.Product;
import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author msi
 */
public class listProd extends HttpServlet {

    @EJB
    private ProductFacade pef;

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


        out.print("<form name=\"buy\" action=\"buyProd\" method=\"POST\">");
        out.print("<label for=\"name\">Product name</label><br />");

        for (Product pe : pef.findAll()) {

            out.print("<input type=\"checkbox\" name=\"id\" value=" + pe.getId() + ">Name : " + pe.getName() + " nBrand : " + pe.getBrand().getName() + " nPrice : " + pe.getPrice() + "<br />");
            //out.print("Name : "+pe.getName() + "\nBrand : "+pe.getBrand()+ "\nPrice : "+pe.getPrice() + "\n<br />");

        }
        out.print("<input id=\"number\" type=\"text\" name=\"number\" value=\"\" size=\"10\" /><br />");
        out.print("<input type=\"submit\" value=\"Submit\" />");
        out.print("</form>");

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
