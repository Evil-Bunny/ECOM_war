/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.ClientFacade;
import command.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.Client;
import user.data.Address;

/**
 *
 * @author Samy
 */
public class RegisterClient extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterClient</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterClient at " + request.getContextPath() + "</h1>");

            Enumeration paramNames = request.getParameterNames();
            if (paramNames.hasMoreElements()) {
                Address ai = new Address();
                ai.setName(request.getParameter("address"));
                Client ci = new Client();
                ci.setAddressDelivery(ai);
                ci.setAddressPayement(ai);
                ci.setFirstname(request.getParameter("name"));
                ci.setSurname(request.getParameter("surname"));
                ci.setUsername(request.getParameter("username"));
                ci.setPassword(request.getParameter("password"));
                ci.setCommand(new Cart());
                try {
                    cif.edit(ci);
                } catch (EJBException e) {
                    out.println(e.getMessage());
                }
            } else {
                out.println("<form name=\"register\" action=\"RegisterClient\" method=\"POST\">"
                        + "<label for=\"username\">username</label>\n"
                        + "            <input id=\"username\" type=\"text\" name=\"username\" value=\"\" size=\"30\" /><br/>\n"
                        + "            <label for=\"password\">password</label>\n"
                        + "            <input id=\"password\" type=\"password\" name=\"password\" value=\"\" size=\"30\" /><br/>\n"
                        + "            <label for=\"name\">first name</label>\n"
                        + "            <input id=\"name\" type=\"text\" name=\"name\" value=\"\" size=\"30\" /><br/>\n"
                        + "            <label for=\"surname\">surname</label>\n"
                        + "            <input id=\"surname\" type=\"text\" name=\"surname\" value=\"\" size=\"30\" /><br/>\n"
                        + "            <label for=\"addressnb\">address number</label>\n"
                        + "            <input id=\"addressnb\" type=\"text\" name=\"addressnb\" value=\"\" size=\"10\" /><br/>\n"
                        + "            <label for=\"address\">address</label>\n"
                        + "            <input id=\"address\" type=\"text\" name=\"address\" value=\"\" size=\"100\" /><br/>\n"
                        + "            <input type=\"submit\" value=\"Submit\" />\n"
                        + "</form>\n");

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
