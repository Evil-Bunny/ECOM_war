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
public class DelToCart extends HttpServlet {

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
                cart = (Cart) session.getAttribute("cart");
            } else {
                cart = cif.find(((Client) session.getAttribute("client")).getId()).getCart();
            }

            if (cart != null) {
//                LineCommand p = pf.find(new Long(request.getParameter("product")));
//                if (p != null) {
                Product p;
                int indexProd;
                for (LineCommand lc : cart.getProducts()) {
//                    System.out.println(lc.getProduct().getId() + ":  " + request.getParameter("product"));
                    if (lc.getProduct().getId().equals(new Long(request.getParameter("product")))) {
                        p = lc.getProduct();
                        p.setStock(p.getStock() + cart.getQuantity(p));
                        indexProd = cart.IndexProduit(p);
                        if(indexProd != -1) {
                            cart.getProducts().remove(indexProd);
                        }
                        pf.edit(p);
                        break;
                    }
                }

                /*if (p != null) {
                    System.out.println("trouvé : "+p );
                    p.setStock(p.getStock() + cart.getQuantity(p));
                    cart.getProducts().remove(lineCommand);
                    pf.edit(p);
                }*/

                if (session.getAttribute("client") == null) {
                    session.setAttribute("cart", cart);
                } else {
                    ((Client) session.getAttribute("client")).setCart(cart);
                    cf.edit(cart);
                }
                cf.edit(cart);
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
