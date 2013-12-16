/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bousky
 */
public abstract class AbstractPage extends HttpServlet {

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
        if (request.getParameter("get") != null) {
            switch (request.getParameter("get")) {
                case "Title":
                    out.print(HTMLEncode(getTitle(request)));
                break;
                case "Ariane":
                    out.print(getAriane(request));
                break;
                case "PreviousLink":
                    out.print(getPreviousLink(request));
                break;
                case "PreviousName":
                    out.print(getPreviousName(request));
                break;
                default:
                    out.print("&lt;" + HTMLEncode(request.getParameter("get")) + "&gt");
            }
        } else {
            if (request.getMethod().equals("GET"))
                printPage(out, request, response);
            else if (request.getMethod().equals("POST"))
                printPagePost(out, request, response);
        }
    }

    abstract protected String getTitle(HttpServletRequest request);
    
    private String getAriane(HttpServletRequest request) {
        String ret = "<div id='ariane'><a href='.'><img src='img/home.png' alt='EvilBunny' width='20px' height='20px'/></a> &gt; ";
        List<String> names = getArianeNames(request);
        List<String> links = getArianeLinks(request);
        if (names == null || links == null)
            return "";
        Iterator<String> namesIter = names.iterator();
        Iterator<String> linksIter = links.iterator();
        while (namesIter.hasNext() && linksIter.hasNext()) {
            String link = linksIter.next();
            if (link != null) {
                ret += "<a href='"+link+"'>"+HTMLEncode(namesIter.next())+"</a> &gt; ";
            } else {
                ret += HTMLEncode(namesIter.next())+" &gt; ";
            }
        }
        return ret + HTMLEncode(getTitle(request))+"</div>";
    }
    
    abstract protected List<String> getArianeNames(HttpServletRequest request);

    abstract protected List<String> getArianeLinks(HttpServletRequest request);

    private String getPreviousLink(HttpServletRequest request) {
        List<String> l = getArianeLinks(request);
        if (l == null)
            return ".";
        String s = l.get(l.size() - 1);
        if (s != null)
            return s;
        else
            return "";
    }
    
    private String getPreviousName(HttpServletRequest request) {
        List<String> l = getArianeNames(request);
        if (l == null)
            return "d'accueil";
        String s = l.get(l.size() - 1);
        if (s != null)
            return s;
        else
            return "d'accueil";        
    }
    
    abstract protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response);
    
    protected void printPagePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        printPage(out, request, response);
    }
    
    public static String HTMLEncode(String s) {
        return s.replace(">", "&gt;").replace("<", "&lt;").replace("&", "&amp;");
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
