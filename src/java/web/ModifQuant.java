/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import command.Cart;
import command.LineCommand;
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
 * @author Pierrick
 */
public class ModifQuant extends HttpServlet {

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

            if (session.getAttribute("client") == null) {
                cart = (Cart) session.getAttribute("cart");
            } else {
                cart = (Cart) ((Client) session.getAttribute("client")).getCart();
            }

            if (cart != null) {
                Product p;
                String todo;
                for (LineCommand lc : cart.getProducts()) {
                    if (lc.getProduct().getId().equals(new Long(request.getParameter("id")))) {
                        p = lc.getProduct();
                        todo = request.getParameter("do");
                        if (todo!=null && todo.equals("add")){
                            if(p.getStock()>=1) {
                                p.setStock(p.getStock() - 1);
                                lc.setQuantity(lc.getQuantity()+1);
                            }
                        } else if (todo!=null && todo.equals("minus")) {
                            if (lc.getQuantity() > 0) {
                                p.setStock(p.getStock() + 1);
                                lc.setQuantity(lc.getQuantity()-1);
                                if(lc.getQuantity() == 0) {
                                    int indexProd = cart.IndexProduit(p);
                                    if(indexProd != -1) {
                                        cart.getProducts().remove(indexProd);
                                    }
                                }
                            }
                        }
                        pf.edit(p);
                        break;
                    }
                }

                if (session.getAttribute("client") == null) {
                    session.setAttribute("cart", cart);
                } else {
                    ((Client) session.getAttribute("client")).setCart(cart);
                }
                cf.edit(cart);
            }
        }
        response.sendRedirect(response.encodeRedirectURL("?page=ViewCart"));
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
