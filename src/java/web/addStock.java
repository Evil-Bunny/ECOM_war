/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.Product;

/**
 *
 * @author msi
 */
public class addStock extends HttpServlet {
    
    @EJB
    private ProductFacade pf;

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet addStock</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addStock at " + request.getContextPath() + "</h1>");
            
            if (request.getParameterNames().hasMoreElements()) {
                if (request.getParameter("number") != null) {
                    try {
                        Product p = pf.find(Long.parseLong(request.getParameter("id")));
                        int stock = Integer.parseInt(request.getParameter("number"));
                        p.setStock(p.getStock() + stock);
                        pf.edit(p);
                    } catch (Exception e) {
                        out.print(e.getMessage());
                    }
                } else {
                    out.print("<form name=\"buy\" action=\"addStock\" method=\"POST\">");
                    out.print("<label for=\"name\">Ajout de stock</label><br />");

//                for (Product pe : pf.findAll()) {
//                    out.print("<input type=\"checkbox\" name=\"id\" value=" + pe.getId() + ">Name : " + pe.getName() + "\tBrand : " + pe.getBrand().getName() + "\tPrice : " + pe.getPrice() + "<br />");
//                }
                    out.print("<input type=\"hidden\" name=\"id\" value=\"" + request.getParameter("id") + "\"> ");
                    out.print("<input id=\"number\" type=\"text\" name=\"number\" value=\"\" size=\"10\" /><br />");
                    out.print("<input type=\"submit\" value=\"Submit\" />");
                    out.print("</form>");
                }
            } else {
                out.print("Vous ne devez pas venir sur cette page sans suivre un lien");
//            response.sendRedirect("");
            }
            out.println("</body>");
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
