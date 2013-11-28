/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.ClientFacade;
import command.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.ejb.EJB;
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
            out.println("<title>Servlet test2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet test2 at " + request.getContextPath() + "</h1>");

            Enumeration paramNames = request.getParameterNames();
            if (paramNames.hasMoreElements()) {
                Address ai = new Address();
                ai.setNumber(Integer.parseInt(request.getParameter("addressnb")));
                ai.setName(request.getParameter("address"));
                Client ci = new Client();
                ci.setAddressDelivery(ai);
                ci.setAddressPayement(ai);
                ci.setFirstname(request.getParameter("name"));
                ci.setSurname(request.getParameter("surname"));
                ci.setUsername(request.getParameter("username"));
                ci.setPassword(request.getParameter("password"));
                ci.setCommand(new Cart());
                cif.create(ci);

                out.println("</body>");
                out.println("</html>");
            }

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
