/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.LineCharacteristicFacade;
import product.Product;
import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.Manufacturer;
import ejb.ManufacturerFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
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
public class EditProduct extends HttpServlet {

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

        Product prod = pef.find(Long.parseLong(request.getParameter("id")));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Édition de produit</title>");
            out.println("<script type='text/javascript' src='../scripts.js'></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Édition de produit</h1>");

            out.println("<script type='text/javascript' src='scripts.js'></script>");
            out.println("<form action='EditProduct?id="+request.getParameter("id")+"' enctype='multipart/form-data' method='POST'>");
            out.println("<label for='name'>Product name</label>");
            out.println("<input id='name' type='text' name='name' size='30' value='"+prod.getName()+"'/><br/>");
            out.println("<label for='brand'>Brand</label>");
            out.println("<input id='brand' type='text' name='brand' list='list_brand' value='"+prod.getBrand().getName()+"'/><br/> ");
            out.println("<datalist id='list_brand'>");
            List<Manufacturer> lm = mf.findAll();
            Collections.sort(lm);
            for (Manufacturer m : lm)
                out.println("<option>"+m.getName()+"</option>");
            out.println("</datalist>");
            out.println("<label for='Category'>Category</label>");
            out.println("<select id='category' name='category'> ");

            for (Category c : cf.findAll()) {
                out.print("<option value='" + c.getId() + "'");
                if (c.equals(prod.getCategorie()))
                    out.print(" selected='selected'");
                out.println(">" + c.getCategorie() + "</option>");
            }
            out.println("</select><br />");
            out.println("<label for='image'>Product picture</label>");
            out.println("<img width='300px' height='300px' src='../img/prod/"+prod.getId()+".jpg'/>");
            out.println("<input id='image' name='image' type='file' accept='image/jpeg'><br/>");
            out.println("<label for='price'>Price</label>");
            out.println(String.format((Locale)null, "<input id='price' type='text' name='price' size='10' value='%.2f'/>  <br/>", prod.getPrice()));
            out.println("<label>Stock</label>");
            out.println("<input id='stock' name='stock' type='text' value='"+prod.getStock()+"'/>");
            out.print("<table id = 'caracs'>");
            out.print("<tr>");
            out.println("<th>Nom du descripteur</th>");
            out.println("<th>Valeur</th><th></th>");
            out.println("</tr>");
            if (prod.getProductCaracteristics() == null || prod.getProductCaracteristics().isEmpty()) {
                out.print("<tr>");
                out.println("<td><input list='idDesc' name='idDes_1'/></td>");
                out.println("<td><input type='text' name='descr_1' size='30'/></td> ");
                out.println("<td><button type='button' onclick='delCarac(this)'>X</button></td> ");
                out.print("</tr>");
            } else {
                List<LineCharacteristic> llc = prod.getProductCaracteristics();
                for (int i = 0 ; i < llc.size() ; i++) {
                    out.print("<tr>");
                    out.println("<td><input type='text' list='idDesc' name='idDes_"+i+"' value='"+llc.get(i).getCharacteristic().getName()+"'/></td>");
                    out.println("<td><input type='text' name='descr_"+i+"' value='"+llc.get(i).getName()+"'/></td> ");
                    out.println("<td><button type='button' onclick='delCarac(this)'>X</button></td> ");
                    out.print("</tr>");
                }
            }

            out.println("<tr><td><button type='button' onclick='addCarac()'>+</button></td><td></td><td></td></tr>");
            out.print("</table>");
            out.println("<datalist id = 'idDesc'>");
            for (Characteristic c : charaf.findAll()) {
                out.println("<option>" + c.getName() + "</option>");
            }
            out.println("</datalist>");

            out.println("<input type='submit' value='Submit' />\n");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Product pe = pef.find(Long.parseLong(request.getParameter("id"))); 
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
            return;
        }
        try {
            pe.setStock(Integer.parseInt(getStringFromPart(request.getPart("stock"))));
        } catch (NumberFormatException e) {
            out.print("Veuillez rentrer un nombre dans le champ stock.");
            return;
        }
        pef.edit(pe);
        
        Enumeration<String> paramsNames = request.getParameterNames();
        String parName;
        String charName;
        String charVal;
        String parVal;
        ArrayList<LineCharacteristic> list = new ArrayList();
        
        while (paramsNames.hasMoreElements()) {

            parName = paramsNames.nextElement();
            if (parName.startsWith("idDes_") && !getStringFromPart(request.getPart(parName)).isEmpty()) {
                charName = getStringFromPart(request.getPart(parName));
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
        for (LineCharacteristic lc : pe.getProductCaracteristics()) {
            lcf.remove(lc);
        }
        pe.setProductCaracteristics(list);
        pef.edit(pe);
        
        InputStream i = request.getPart("image").getInputStream();
        if (i.available() != 0) {
            FileOutputStream s = new FileOutputStream(new File(getServletContext().getRealPath("img/prod/"+pe.getId()+".jpg")));
            byte[] bytes = new byte[1024];
            int read;
            while ((read = i.read(bytes)) != -1) {
                    s.write(bytes, 0, read);
            }
            s.close();
        }
        i.close();

        processRequest(request, response);

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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
