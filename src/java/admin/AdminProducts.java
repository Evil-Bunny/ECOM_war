/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.LineCharacteristicFacade;
import ejb.ManufacturerFacade;
import ejb.ProductFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static pages.AbstractPage.HTMLEncode;
import pages.HTTPErrorException;
import pages.Products;
import static pages.Products.PAGESAROUND;
import static pages.Products.PRODUCTSBYPAGE;
import product.Manufacturer;
import product.Product;
import product.type.Category;
import product.type.LineCharacteristic;

/**
 *
 * @author Samy
 */
public class AdminProducts extends HttpServlet {

    @EJB
    CategoryFacade cf;
    @EJB
    ManufacturerFacade mf;
    @EJB
    ProductFacade pf;
    @EJB
    CharacteristicFacade chf;
    @EJB
    LineCharacteristicFacade lcf;
    public static final int PRODUCTSBYPAGE = 10;
    public static final int PAGESAROUND = 5;

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
            out.println("<title>Servlet AdminProducts</title>");
            out.println("</head>");
            out.println("<body>");
            List<Product> products;
            int start, nbPages;
            String tmp;

            // récupération de la liste des produits

            if (request.getParameter("search") != null) {
                String search = request.getParameter("search");
                Set<Product> p = new HashSet(pf.search(search));
                for (Manufacturer m : mf.search(search)) {
                    p.addAll(m.getProducts());
                }
                for (Category c : cf.search(search)) {
                    p.addAll(c.getProducts());
                    if (c.getSubCategories() != null) {
                        for (Category sc : c.getSubCategories()) {
                            p.addAll(sc.getProducts());
                        }
                    }
                }
                for (LineCharacteristic lc : lcf.search(search)) {
                    p.add(lc.getProduct());
                }
                products = new ArrayList(p);
            } else if (request.getParameter("category") != null && request.getParameter("manufacturer") != null) {
                // Advanced search
                String param;
                Category c = null;
                Manufacturer m = null;
                boolean stock = false;
                float minPrice = 0, maxPrice = 0;
                param = request.getParameter("category");
                if (param != null && !param.equals("")) {
                    c = cf.find(Long.parseLong(param));
                }
                param = request.getParameter("manufacturer");
                if (param != null && !param.equals("")) {
                    m = mf.find(Long.parseLong(param));
                }
                param = request.getParameter("stock");
                if (param != null && !param.equals("")) {
                    stock = true;
                }
                param = request.getParameter("minPrice");
                if (param != null && !param.equals("")) {
                    minPrice = Long.parseLong(param);
                }
                param = request.getParameter("maxPrice");
                if (param != null && !param.equals("")) {
                    maxPrice = Long.parseLong(param);
                }
                // LineCharacteristic
                Enumeration<String> params = request.getParameterNames();
                HashMap<Integer, Long> caracName = new HashMap();
                HashMap<Integer, String> caracVal = new HashMap();
                while (params.hasMoreElements()) {
                    param = params.nextElement();
                    if (param.startsWith("caracName_")) {
                        caracName.put(new Integer(param.substring("caracName_".length())), new Long(request.getParameter(param)));
                    } else if (param.startsWith("caracVal_")) {
                        caracVal.put(new Integer(param.substring("caracVal_".length())), request.getParameter(param));
                    }
                }
                List<LineCharacteristic> characs = new ArrayList();
                for (Integer i : caracName.keySet()) {
                    String val = caracVal.get(i);
                    if (val == null || val.equals("")) {
                        continue;
                    }
                    LineCharacteristic lc = new LineCharacteristic();
                    lc.setCharacteristic(chf.find(caracName.get(i)));
                    lc.setName(caracVal.get(i));
                    characs.add(lc);
                }
                products = pf.findAdvanced(c, m, request.getParameter("name"), stock, minPrice, maxPrice, characs);
            } else if (request.getParameter("category") != null) {
                Category category = cf.find(Long.parseLong(request.getParameter("category")));
                if (category == null) {
                    throw new HTTPErrorException(404);
                }
                products = category.getProducts();
                for (Category c : category.getSubCategories()) {
                    products.addAll(c.getProducts());
                }
            } else if (request.getParameter("manufacturer") != null) {
                try {
                    products = mf.find(Long.parseLong(request.getParameter("manufacturer"))).getProducts();
                } catch (NullPointerException e) {
                    throw new HTTPErrorException(404);
                }
            } else {
                products = pf.findAll();
            }
            if (request.getParameter("start") != null) {
                start = Integer.parseInt(request.getParameter("start"));
            } else {
                start = 0;
            }

            // affichage de la liste des produits

            nbPages = (products.size() - 1) / PRODUCTSBYPAGE + 1;
            products = products.subList(start, Math.min(products.size(), start + PRODUCTSBYPAGE));
            out.println("<a href='../admin'>Retour à l'accueil</a>&nbsp;&nbsp;&nbsp;");
            out.println("<a href='AdminSearch'>Retour à la recherche</a>");
            if (products.isEmpty()) {
                out.println("<br/><br/>Aucun produit ne correspond à ces critères.");
                return;
            }
            out.println("<ul id='list_products'>");
            for (Product p : products) {
                Category category = p.getCategorie();
                out.println("<li><a href='EditProduct?id=" + p.getId() + "'>");
                out.println("<img src='img/prod/" + p.getId() + ".jpg' alt='' height='100px' width='100px'/>");
                out.println("</a><div class='prod_right'>");

                out.println("<h2><a href='EditProduct?id=" + p.getId() + "'>" + HTMLEncode(p.getName()) + "</a></h2>");
                out.print("<table><tr><td>Caractéristiques</td><td>");
                tmp = "";
                for (LineCharacteristic carac : p.getProductCaracteristics()) {
                    tmp += " - " + HTMLEncode(carac.getCharacteristic().getName()) + " : " + HTMLEncode(carac.getName());
                }
                if (tmp.length() != 0) {
                    out.println(tmp.substring(3));
                }
                out.print("</td></tr><tr><td>Catégorie</td><td>");
                if (category.getParent() != null) {
                    out.println("<a href='AdminProducts?category=" + category.getParent().getId() + "'>" + HTMLEncode(category.getParent().getCategorie()) + "</a> &gt; ");
                }
                out.println("<a href='AdminProducts?category=" + category.getId() + "'>" + HTMLEncode(category.getCategorie()) + "</a>");
                out.print("</td></tr><tr><td>Marque</td><td>");
                out.println("<a href='AdminProducts?manufacturer=" + p.getBrand().getId() + "'>" + HTMLEncode(p.getBrand().getName()) + "</a></td></tr></table>");
                out.println(String.format("<div class='price'>%.2f &euro;</div>", p.getPrice()));
                out.println("<a href='EditProduct?id=" + p.getId() + "'>Modifier</a></div>");
                out.println("<div class='clear_footer'></div></li>");
            }
            out.println("</ul>");

            // pagination

            if (nbPages != 1) {
                int currentPage = start / PRODUCTSBYPAGE + 1;
                String baseURL = "AdminProducts?";
                if (request.getParameter("category") != null) {
                    baseURL += "&amp;category=" + request.getParameter("category");
                }
                if (request.getParameter("manufacturer") != null) {
                    baseURL += "&amp;manufacturer=" + request.getParameter("manufacturer");
                }
                if (request.getParameter("search") != null) {
                    baseURL += "&amp;search=" + request.getParameter("search");
                }
                baseURL += "&amp;start=";
                out.println("<div class='pages'><a href='" + baseURL + "0'>Début</a>");
                if (currentPage != 1) {
                    out.println("<a href='" + baseURL + (start - PRODUCTSBYPAGE) + "'>Précédent</a>");
                }
                for (int i = Math.max(1, currentPage - PAGESAROUND); i < currentPage; i++) {
                    out.println("<a href='" + baseURL + ((i - 1) * PRODUCTSBYPAGE) + "'>" + i + "</a>");
                }
                out.println("<span>" + currentPage + "</span>");
                for (int i = currentPage + 1; i <= Math.min(nbPages, currentPage + PAGESAROUND); i++) {
                    out.println("<a href='" + baseURL + ((i - 1) * PRODUCTSBYPAGE) + "'>" + i + "</a>");
                }
                if (currentPage != nbPages) {
                    out.println("<a href='" + baseURL + (start + PRODUCTSBYPAGE) + "'>Suivant</a>");
                }
                out.println("<a href='" + baseURL + ((nbPages - 1) * PRODUCTSBYPAGE) + "'>Fin</a></div>");
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
