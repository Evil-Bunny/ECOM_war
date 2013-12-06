/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.LineCharacteristicFacade;
import product.Product;
import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.Manufacturer;
import ejb.ManufacturerFacade;
import java.util.ArrayList;
import java.util.Enumeration;
import product.type.Category;
import product.type.Characteristic;
import product.type.LineCharacteristic;

/**
 *
 * @author msi
 */
public class addProd extends HttpServlet {

    @EJB
    private ProductFacade pef;
    @EJB
    private CategoryFacade cf;
    @EJB
    private ManufacturerFacade mf;
    @EJB
    private CharacteristicFacade charaf;
    @EJB
    private LineCharacteristicFacade lcf;

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
            out.println("<title>Ajout de produit</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addProd at " + request.getContextPath() + "</h1>");

            if (!request.getParameterNames().hasMoreElements()) {
                out.println("<script type=\"text/javascript\" src=\"scripts.js\"></script>");
                out.println("<form name=\"addProduct\" action=\"addProd\" method=\"POST\">");
                out.println("<label for=\"name\">Product name</label>");
                out.println("<input id=\"name\" type=\"text\" name=\"name\" value=\"\" size=\"30\" /><br/>");
                out.println("<label for=\"brand\">Brand</label>");
                out.println("<input id=\"brand\" type=\"text\" name=\"brand\" value=\"\" size=\"30\" /><br/> ");
                out.println("<label for=\"Category\">Category</label>");

                out.println("<select id=\"category\" name=\"category\"> ");

                for (Category c : cf.findAll()) {
                    out.println("<option value=\"" + c.getId() + "\">" + c.getCategorie() + "</option>");
                }

                out.println("</select><br />");
                out.println("<label for=\"price\">Price</label>");
                out.println("<input id=\"price\" type=\"text\" name=\"price\" value=\"\" size=\"10\" />  <br/>");
                out.print("<table id = \"caracs\">");
                out.print("<tr>");
                out.println("<th>Nom du descripteur</th>");
                out.println("<th>Valeur</th><th></th>");
                out.println("</tr>");
                out.print("<tr>");
                out.println("<td><input list=\"idDesc\" name=\"idDes_1\"> ");
                out.println("<datalist id = \"idDesc\">");
                for (Characteristic c : charaf.findAll()) {
                    out.println("<option value=\"" + c.getName() + "\">");
                }
                out.println("</datalist></td>");
                out.println("<td><input type=\"text\" name=\"descr_1\" value=\"\" size=\"30\"/></td> ");
                out.println("<td><button type='button' onclick='delCarac(this)'>X</button></td> ");
                out.print("</tr>");
                out.println("<tr><td><button type='button' onclick='addCarac()'>+</button></td><td></td><td></td></tr>");
                out.print("</table>");

                out.println("<input type=\"submit\" value=\"Submit\" />\n");
                out.println("</form>");

            } else {
/////////////////RAJOUTER LES CARACTS DANS LIGNE CARAC AVEC l'ID recup avec getCharacByName
                Product pe = new Product();

                Manufacturer m;
                m = mf.findByName(request.getParameter("brand"));
                if (m == null) {
                    m = new Manufacturer();
                    m.setName(request.getParameter("brand"));
                }
                pe.setBrand(m);
                pe.setName(request.getParameter("name"));

                pe.setCategorie(cf.find(Long.parseLong(request.getParameter("category"))));
                try {
                    pe.setPrice(Float.parseFloat(request.getParameter("price")));
                } catch (NumberFormatException e) {
                    out.print("Veuillez rentrer un nombre dans le champ prix.");

                }
                Enumeration<String> paramsNames = request.getParameterNames();
                String parName = new String();
                String charName = new String();
                String charVal = new String();
                String parVal = new String();
                ArrayList<LineCharacteristic> list = new ArrayList<LineCharacteristic>();
                while (paramsNames.hasMoreElements()) {

                    parName = paramsNames.nextElement();
                    if (parName.startsWith("idDes_") && !request.getParameter(parName).isEmpty()) {
                        charName = request.getParameter(parName);
                        //}
                        parVal = paramsNames.nextElement();
                        if (parVal.startsWith("descr_") && !request.getParameter(parVal).isEmpty()) {
                            charVal = request.getParameter(parVal);
                            LineCharacteristic line = new LineCharacteristic();
                            Characteristic charac;
                            if ((charac = charaf.findByName(charName)) != null) {//si ca existe
                            } else { //sinon
                                charac = new Characteristic();
                                charac.setName(charName);

                            }
                            line.setCharacteristic(charac);
                            line.setName(charVal);
                            list.add(line);
                        }
                    }
                }
                if (!list.isEmpty()) {
                    pe.setProductCaracteristics(list);
                }
                try {
                    pef.edit(pe);
                    out.println("Produit " + request.getParameter("name") + " ajouté.");
                } catch (EJBException e) {
                    out.print(e.getMessage());
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
