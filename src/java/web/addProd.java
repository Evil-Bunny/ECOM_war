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
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import product.type.Category;
import product.type.Characteristic;
import product.type.LineCharacteristic;

/**
 *
 * @author msi
 */
@MultipartConfig
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

    
    
    String getStringFromPart(Part is) throws IOException {
        java.util.Scanner s = new java.util.Scanner(is.getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
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






                out.println("<script type=\"text/javascript\" src=\"scripts.js\"></script>");
                out.println("<form name=\"addProduct\" action=\"addProd\" enctype=\"multipart/form-data\" method=\"POST\">");
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
                out.println("<label for=\"image\">Product picture</label>");
                out.println("<input id=\"image\" name=\"image\" type=\"file\" accept=\"image/jpeg\"><br/>");
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
        //    processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Product pe = new Product();
        out.print(getStringFromPart(request.getPart("brand")));
        Manufacturer m;
        m = mf.findByName(getStringFromPart(request.getPart("brand")));
        if (m == null) {
            m = new Manufacturer();
            m.setName(getStringFromPart(request.getPart("brand")));
        }
        pe.setBrand(m);
        pe.setName(getStringFromPart(request.getPart("name")));

        pe.setCategorie(cf.find(Long.parseLong(getStringFromPart(request.getPart("category")))));
        try {
            pe.setPrice(Float.parseFloat(getStringFromPart(request.getPart("price"))));
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
            if (parName.startsWith("idDes_") && !getStringFromPart(request.getPart(parName)).isEmpty()) {
                charName = getStringFromPart(request.getPart(parName));
                //}
                parVal = paramsNames.nextElement();
                if (parVal.startsWith("descr_") && !getStringFromPart(request.getPart(parVal)).isEmpty()) {
                    charVal = getStringFromPart(request.getPart(parVal));
                    LineCharacteristic line = new LineCharacteristic();
                    Characteristic charac;
                    if ((charac = charaf.findByName(charName)) != null) {//si ca existe
                    } else { //sinon
                        charac = new Characteristic();
                        charac.setName(charName);

                    }
                    line.setCharacteristic(charac);
                    line.setName(charVal);
                    line.setProduct(pe);
                    list.add(line);
                }
            }
        }
        if (!list.isEmpty()) {
            pe.setProductCaracteristics(list);
        }
        try {
            pef.edit(pe);
            out.println("Produit " + getStringFromPart(request.getPart("name")) + " ajouté.");
            out.println(request.getPart("image"));
            //out.println(getServletContext().getRealPath("img/prod/"+prodid+".jpg"));return;
            Long prodid = pef.findAdvanced(null, null, pe.getName(), false, 0, 0, new ArrayList()).get(0).getId();
            InputStream i = request.getPart("image").getInputStream();
            FileOutputStream s = new FileOutputStream(new File(getServletContext().getRealPath("img/prod/"+prodid+".jpg")));
            		byte[] bytes = new byte[1024];
                int read = 0;
		while ((read = i.read(bytes)) != -1) {
			s.write(bytes, 0, read);
		}
               s.close();
               i.close();
            
        } catch (EJBException e) {
            out.print(e.getMessage());
        }


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
