/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.LineCharacteristicFacade;
import ejb.ProductFacade;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ejb.ManufacturerFacade;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import product.Manufacturer;
import product.Product;
import product.type.Category;
import product.type.LineCharacteristic;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bousky
 */
public class Products extends AbstractPage {

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
    
    @Override
    protected String getTitle(HttpServletRequest request) {
        if (request.getParameter("category") != null && request.getParameter("manufacturer") != null) {
            return "Produits - Recherche";
        }
        if (request.getParameter("search") != null) {
            return "Produits - Recherche";
        }
        if (request.getParameter("category") != null && ! request.getParameter("category").equals("")) {
            return cf.find(new Long(request.getParameter("category"))).getCategorie();
        }
        if (request.getParameter("manufacturer") != null && ! request.getParameter("manufacturer").equals("")) {
            return mf.find(new Long(request.getParameter("manufacturer"))).getName();
        }
        return "Produits";
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<Product> products;
        int start, nbPages;
        String tmp;
        
        if (request.getParameter("search") != null) {
            String search = request.getParameter("search");
            Set<Product> p = new HashSet(pf.search(search));
            for (Manufacturer m : mf.search(search)) {
                p.addAll(m.getProducts());
            }
            for (Category c : cf.search(search)) {
                p.addAll(c.getProducts());
                if (c.getSubCategories() != null) {
                    for (Category sc : c.getSubCategories())
                        p.addAll(sc.getProducts());
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
            if (param != null && ! param.equals(""))
                c = cf.find(new Long(param));
            param = request.getParameter("manufacturer");
            if (param != null && ! param.equals(""))
                m = mf.find(new Long(param));
            param = request.getParameter("stock");
            if (param != null && ! param.equals(""))
                stock = true;
            param = request.getParameter("minPrice");
            if (param != null && ! param.equals(""))
                minPrice = new Float(param);
            param = request.getParameter("maxPrice");
            if (param != null && ! param.equals(""))
                maxPrice = new Float(param);
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
                if (val == null || val.equals(""))
                    continue;
                LineCharacteristic lc = new LineCharacteristic();
                lc.setCharacteristic(chf.find(caracName.get(i)));
                lc.setName(caracVal.get(i));
                characs.add(lc);
            }
            products = pf.findAdvanced(c, m, request.getParameter("name"), stock, minPrice, maxPrice, characs);
        } else if (request.getParameter("category") != null) {
            Category category = cf.find(new Long(request.getParameter("category")));
            products = category.getProducts();
            for (Category c : category.getSubCategories()) {
                products.addAll(c.getProducts());
            }
        } else if (request.getParameter("manufacturer") != null) {
            products = mf.find(new Long(request.getParameter("manufacturer"))).getProducts();
        } else {
            products = pf.findAll();
        }
        if (request.getParameter("start") != null) {
            start = new Integer(request.getParameter("start"));
        } else {
            start = 0;
        }
        nbPages = (products.size()-1)/PRODUCTSBYPAGE +1;
        products = products.subList(start, Math.min(products.size(),start+PRODUCTSBYPAGE));
        
        out.println("<ul id='list_products'>");
        for (Product p : products) {
            Category category = p.getCategorie();
            out.println("<li><a href='?page=Product&amp;id="+p.getId()+"'>");
            out.println("<img src='img/category.png' alt='' height='100px' width='100px' style='background:white;'/>");
            out.println("</a><div class='prod_right'>");
            out.println(String.format("<div class='price'>%.2f &euro;</div>", p.getPrice()));
            if (p.getStock() == 0) {
                out.println("<div class='stock stock_no'>Rupture de stock</div>");
            } else {
                out.println("<div class='stock stock_yes'>En stock</div>");
                try {
                    out.println("<a href='AddToCart?&amp;product="+p.getId()+"&amp;old="+URLEncoder.encode(request.getQueryString(), "UTF-8") + "'>Ajouter au panier</a><br/>");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.println("<a href='?page=Product&amp;id="+p.getId()+"'>Plus d'info</a></div>");
            out.println("<h2><a href='?page=Product&amp;id="+p.getId()+"'>"+HTMLEncode(p.getName())+"</a></h2>");
            out.print("<table><tr><td>Caractéristiques</td><td>");
            tmp = "";
            for (LineCharacteristic carac : p.getProductCaracteristics()) {
                tmp += " - "+carac.getCharacteristic().getName()+" : "+carac.getName();
            }
            if (tmp.length() != 0)
                out.println(tmp.substring(3));
            out.print("</td></tr><tr><td>Catégorie</td><td>");
            if (category.getParent() != null)
                out.println("<a href='?page=Products&amp;category="+category.getParent().getId()+"'>"+HTMLEncode(category.getParent().getCategorie())+"</a> &gt; ");
            out.println("<a href='?page=Products&amp;category="+category.getId()+"'>"+HTMLEncode(category.getCategorie())+"</a>");
            out.print("</td></tr><tr><td>Marque</td><td>");
            out.println("<a href='?page=Products&amp;manufacturer="+p.getBrand().getId()+"'>"+HTMLEncode(p.getBrand().getName())+"</a>");
            out.println("</td></tr></table><div class='clear_footer'></div></li>");
        }
        out.println("</ul>");
        if (nbPages != 1) {
            int currentPage = start/PRODUCTSBYPAGE +1;
            String baseURL = "?page=Products";
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
            out.println("<div class='pages'><a href='"+baseURL+"0'>Début</a>");
            if (currentPage != 1)
                out.println("<a href='"+baseURL+(start-PRODUCTSBYPAGE)+"'>Précédent</a>");
            for (int i = Math.max(1, currentPage-PAGESAROUND) ; i < currentPage ; i++)
                out.println("<a href='"+baseURL+i+"'>"+i+"</a>");
            out.println("<span>"+currentPage+"</span>");
            for (int i = currentPage+1; i<= Math.min(nbPages, currentPage+PAGESAROUND) ; i++)
                out.println("<a href='"+baseURL+i+"'>"+i+"</a>");
            if (currentPage != nbPages)
                out.println("<a href='"+baseURL+(start+PRODUCTSBYPAGE)+"'>Suivant</a>");
            out.println("<a href='"+baseURL+((nbPages-1)*PRODUCTSBYPAGE)+"'>Fin</a></div>");
        }
    }
    
}
