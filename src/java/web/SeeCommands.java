/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import command.Command;
import command.LineCommand;
import ejb.CommandFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pages.HTTPRedirect;
import product.Product;
import user.Client;

/**
 *
 * @author Samy
 */
public class SeeCommands extends HttpServlet {

    @EJB
    CommandFacade cf;

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
            out.println("<title>Servlet VoirCommandes</title>");
            out.println("</head>");
            HttpSession session = request.getSession(true);
            if (session.getAttribute("client") == null) {
                throw new HTTPRedirect(".");
            } else {
                List<Command> commands = ((Client) session.getAttribute("client")).getCommands();
                for (Command command : commands) {
                    out.println("commande n° " + command.getId() + " :");

                    out.println("<table>");
                    out.println("<tr><th>Produit</th><th class='quant'>Quantité</th><th class='price'>Prix Unitaire</th><th>Prix</th><th>Supprimer</th></tr>");
                    for (LineCommand lc : command.getProducts()) {
                            out.print("<tr><td class='prems'><div class='info'><a href=\"?page=Product&id=" + lc.getProduct().getId()
                                    + "\">" + lc.getProduct().getName() + "</a>"
                                    + "</div></td><td class='quant'><p id='nb'>" + lc.getQuantity()
                                    + "</p>");
                            out.println("</td><td class='price'>"
                                    + String.format("%.2f &euro;", lc.getProduct().getPrice()) + "</td><td class='priceLine'>"
                                    + String.format("%.2f &euro;", lc.getPrice()) + "</td></tr>");
                    }
                    out.println("<tr><td class='lineTotal1'></td><td class='lineTotal2'></td><td class='tittleTotal'>Total : </td><td class='priceTotal'>"
                            + String.format("%.2f &euro;", command.getTotal()) + "</td><td class='lineTotal3'></td></tr> ");
                    out.println("</table>");

                }
            }








            out.println("<body>");
            out.println("<h1>Servlet VoirCommandes at " + request.getContextPath() + "</h1>");
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
