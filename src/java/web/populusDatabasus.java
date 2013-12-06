/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.CategoryFacade;
import ejb.ClientFacade;
import ejb.ManufacturerFacade;
import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import product.Manufacturer;
import product.Product;
import product.type.Category;

/**
 *
 * @author Samy
 */
public class populusDatabasus extends HttpServlet {

    @EJB
    ClientFacade cif;
    @EJB
    ProductFacade pf;
    @EJB
    ManufacturerFacade mf;
    @EJB
    CategoryFacade cf;

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
            ArrayList<String> categories = new ArrayList();
            categories.add("Périphériques");
            categories.add("Composants");
            categories.add("Consommables");
            ArrayList<ArrayList> subCategories = new ArrayList();
            subCategories.add(new ArrayList(Arrays.asList("Clavier", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Souris", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Ecran", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Imprimantes", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Lecteurs", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Scanner", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Cables et Connectique", 0)));
            subCategories.add(new ArrayList(Arrays.asList("Cartes mères", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Processeur", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Cartes son", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Cartes graphiques", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Disques durs", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Connectique interne", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Mémoire RAM", 1)));
            subCategories.add(new ArrayList(Arrays.asList("Papier", 2)));
            subCategories.add(new ArrayList(Arrays.asList("Encre", 2)));
            ArrayList<String> manufacturers = new ArrayList();
            manufacturers.add("Nvidia");
            manufacturers.add("ATI");
            manufacturers.add("Intel");
            manufacturers.add("Corsair");
            manufacturers.add("Commodore");
            manufacturers.add("Dell");
            manufacturers.add("Amiga");
            manufacturers.add("IBM");
            manufacturers.add("Macintosh");
            manufacturers.add("Hewlett-Packard");
            manufacturers.add("IBM");





            Category c = new Category();
            Manufacturer m = new Manufacturer();
            Product p = new Product();

            for (String s : categories) {
                c.setCategorie(s);
                cf.edit(c);
            }
            for (ArrayList arrayList : subCategories) {
                c.setCategorie((String) arrayList.get(0));
                c.setParent(cf.findByName(categories.get((Integer) arrayList.get(1))));
                cf.edit(c);
            }
            for (String s : manufacturers) {
                m.setName(s);
                mf.edit(m);
            }


            for (int i = 0; i < subCategories.size(); i++) {
                c = cf.findByName((String) ((ArrayList) subCategories.get(i)).get(0));
                for (int j = 1; j < 4; j++) {
                    m = mf.findByName((String) manufacturers.get(new Random().nextInt(manufacturers.size())));
                    p.setCategorie(c);
                    p.setBrand(m);
                    p.setName(c.getCategorie()+j);
                    p.setPrice(10.0f+new Random().nextFloat()*((1000.0f-10.0f)+1.0f));
                    p.setStock(10);
                    out.println(m.toString() + " lol");
                    pf.edit(p);
                }

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
