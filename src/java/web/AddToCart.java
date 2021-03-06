/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import command.Cart;
import ejb.ClientFacade;
import ejb.CartFacade;
import ejb.ProductFacade;
import java.io.IOException;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import product.Product;
import user.Client;

/**
 *
 * @author Samy
 */
public class AddToCart extends HttpServlet {

    @EJB
    ClientFacade cif;
    @EJB
    ProductFacade pf;
    @EJB
    CartFacade cf;
    private Cart cart;

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
        Enumeration paramNames = request.getParameterNames();
        if (paramNames.hasMoreElements()) {
            HttpSession session = request.getSession(true);
            session.removeAttribute("command");
            if (session.getAttribute("client") == null) {
                if (session.getAttribute("cart") == null) {
                    session.setAttribute("cart", new Cart());
                }
                cart = (Cart) session.getAttribute("cart");

            } else {
                cart = cif.find(((Client) session.getAttribute("client")).getId()).getCart();
            }

            Product p = pf.find(new Long(request.getParameter("product")));
            if (p.getStock() > 0) {
                if (cart.getQuantity(p) != null) {
                    cart.setQuantity(p, cart.getQuantity(p) + 1);
                } else {
                    cart.setQuantity(p, 1);
                }

                if (session.getAttribute("client") == null) {
                    session.setAttribute("cart", cart);
                } else {
                    ((Client) session.getAttribute("client")).setCart(cart);
                    cf.edit(cart);
                }
                pf.edit(p);
            }
        }
        response.sendRedirect(response.encodeRedirectURL("?" + request.getParameter("old")));
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
