/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.CommandEntity;
import ejb.CommandEntityFacade;
import ejb.ProductEntity;
import ejb.ProductEntityFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samy
 */
@WebServlet(name = "testCart", urlPatterns = {"/testCart"})
public class testCart extends HttpServlet {

    @EJB
    private ProductEntityFacade pef;
    private CommandEntity cart;
    @EJB
    private CommandEntityFacade cef;

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
            HttpSession session = request.getSession(true);
            if (session.getAttribute("cart") != null) {
                cart = (CommandEntity) session.getAttribute("cart");
            } else {
                session.setAttribute("cart", new CommandEntity());
                cart = (CommandEntity) session.getAttribute("cart");
            }



            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testCart</title>");
            out.println("</head>");
            out.println("<body>");
            Enumeration paramNames = request.getParameterNames();
            if (!paramNames.hasMoreElements()) {
                out.println("<h1>Voici le Cart</h1>");
                for (ProductEntity p : cart.getProducts().keySet()) {
                    out.println(p.toString() + " : " + cart.getProducts().get(p).toString());
                }

            } else {
                out.println("<h1>Merci de l'achat pigeon</h1>");

                ProductEntity pe = new ProductEntity();
                pe.setBrand(request.getParameter("brand"));
                pe.setName(request.getParameter("name"));
                pe.setPrice(Float.parseFloat(request.getParameter("price")));
                pef.create(pe);
                cart.setQuantity(pe, 1);
                session.setAttribute("cart", cart);
                cef.create(cart);

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
