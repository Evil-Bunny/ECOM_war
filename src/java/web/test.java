/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.ClientFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
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
public class test extends HttpServlet {

    @EJB
    private ClientFacade clientImplFacade;
    @Resource(mappedName = "jms/NewMessageFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/NewMessage")
    private Queue queue;

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
            out.println("<title>Servlet test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet test at " + request.getContextPath() + "</h1>");
            Enumeration paramNames = request.getParameterNames();
            if (paramNames.hasMoreElements()) {
                try {
                    Connection connection = connectionFactory.createConnection();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer messageProducer = session.createProducer(queue);

                    ObjectMessage message = session.createObjectMessage();
                    Address ai = new Address();
                    ai.setNumber(Integer.parseInt(request.getParameter("addressnb")));
                    ai.setName(request.getParameter("address"));

                    message.setObject(ai);
                    messageProducer.send(message);
                    messageProducer.close();
                    System.out.println("ai.getId()");

//                    messageProducer = session.createProducer(queue);
                    Client ci = new Client();

                    ci.setAddress(ai);
                    ci.setFirstname(request.getParameter("name"));
                    ci.setSurname(request.getParameter("surname"));
//
//                    message.setObject(ci);
//                    messageProducer.send(message);
//                    messageProducer.close();
                    connection.close();
                    // clientImplFacade.create(ci);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            } else {



                response.sendRedirect("register.jsp");


//                List l = clientImplFacade.findAll();
//                for (Iterator it = l.iterator(); it.hasNext();) {
//                    Client elem = (Client) it.next();
//                    out.println(" <b>" + elem.getFirstname() + " </b><br />");
//                    out.println(elem.getSurname() + "<br /> ");
//                }
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
