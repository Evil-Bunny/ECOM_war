/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.ClientFacade;
import command.Cart;
import command.LineCommand;
import ejb.CommandFacade;
import product.Product;
import ejb.ProductFacade;
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
import product.Manufacturer;
import product.type.Category;
import user.Client;

/**
 *
 * @author Samy
 */
@WebServlet(name = "testCart", urlPatterns = {"/testCart"})
public class testCart extends HttpServlet {

    @EJB
    private ProductFacade pef;
    private Cart cart;
    @EJB
    private CommandFacade cef;
    @EJB
    private ClientFacade cif;

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
            if (session.getAttribute("client") == null) {
                if (session.getAttribute("cart") != null) {
                    cart = (Cart) session.getAttribute("cart");
                } else {
                    session.setAttribute("cart", new Cart());
                    cart = (Cart) session.getAttribute("cart");
                }
            } else {
                cart = (Cart) ((Client) session.getAttribute("client")).getCommand();
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testCart</title>");
            out.println("</head>");
            out.println("<body>");
            Enumeration paramNames = request.getParameterNames();
            if (!paramNames.hasMoreElements()) {
                out.println("<h1>Voici le Cart</h1>");
                for (LineCommand p : cart.getProducts()) {
                    out.println(p.toString());
                }

            } else {
                out.println("<h1>Merci de l'achat pigeon</h1>");
                Manufacturer m = new Manufacturer();
                Category c = new Category();
                Product pe = new Product();

                m.setName(request.getParameter("brand"));
                c.setCategorie(request.getParameter("category"));
                pe.setBrand(m);
                pe.setCategorie(c);
                pe.setName(request.getParameter("name"));
                pe.setPrice(Float.parseFloat(request.getParameter("price")));
                pef.create(pe);
                cart.setQuantity(pe, 1);

                if (session.getAttribute("client") == null) {
                    session.setAttribute("cart", cart);
                    cef.create(cart);
                } else {
                    ((Client) session.getAttribute("client")).setCommand(cart);
                    cif.create((Client) session.getAttribute("client"));
                }

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
