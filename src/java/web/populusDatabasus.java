/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.common.io.Files;
import command.Cart;
import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import ejb.ClientFacade;
import ejb.HomeAdFacade;
import ejb.ManufacturerFacade;
import ejb.ProductFacade;
import java.io.File;
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
import product.HomeAd;
import product.Manufacturer;
import product.Product;
import product.type.Category;
import product.type.Characteristic;
import product.type.LineCharacteristic;
import user.Client;
import user.data.Address;

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
    @EJB
    CharacteristicFacade chf;
    @EJB   
    ClientFacade clf;
    @EJB
    HomeAdFacade haf;

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
            ArrayList<String> charac = new ArrayList();
            charac.add("Résolution");
            charac.add("Fréquence");
            charac.add("Capacité");
            charac.add("Tours/Minute");
            charac.add("Ports");
            charac.add("Alimentation");
            charac.add("Type");
            charac.add("DPI");
            charac.add("PPP");
            charac.add("Format");
            charac.add("Socket");
            charac.add("Taille");
            charac.add("Poids");
            ArrayList<ArrayList> names = new ArrayList();
            names.add(new ArrayList(Arrays.asList("Hyde", "Megan")));
            names.add(new ArrayList(Arrays.asList("Reilly", "Abbot")));
            names.add(new ArrayList(Arrays.asList("Lynn", "Armando")));
            names.add(new ArrayList(Arrays.asList("Murphy", "Thor")));
            names.add(new ArrayList(Arrays.asList("Dillon", "Leila")));
            names.add(new ArrayList(Arrays.asList("Cherry", "Carly")));
            names.add(new ArrayList(Arrays.asList("Best", "Lars")));
            names.add(new ArrayList(Arrays.asList("Kirk", "Dale")));
            names.add(new ArrayList(Arrays.asList("Moran", "Zenaida")));
            names.add(new ArrayList(Arrays.asList("Gregory", "Dieter")));
            names.add(new ArrayList(Arrays.asList("Gentry", "Joshua")));
            names.add(new ArrayList(Arrays.asList("Herrera", "Kiayada")));
            names.add(new ArrayList(Arrays.asList("Haynes", "Sloane")));
            names.add(new ArrayList(Arrays.asList("Mooney", "Wendy")));
            names.add(new ArrayList(Arrays.asList("Dillon", "Yetta")));
            names.add(new ArrayList(Arrays.asList("Ball", "Nigel")));
            names.add(new ArrayList(Arrays.asList("Mooney", "Reed")));
            names.add(new ArrayList(Arrays.asList("Pate", "Chase")));
            names.add(new ArrayList(Arrays.asList("Solomon", "Gray")));
            names.add(new ArrayList(Arrays.asList("Brady", "Bert")));
            names.add(new ArrayList(Arrays.asList("Watson", "Diana")));
            names.add(new ArrayList(Arrays.asList("Elliott", "Akeem")));
            names.add(new ArrayList(Arrays.asList("Shields", "Quinlan")));
            names.add(new ArrayList(Arrays.asList("Doyle", "Jillian")));
            names.add(new ArrayList(Arrays.asList("Reyes", "Lysandra")));
            names.add(new ArrayList(Arrays.asList("Randall", "Beau")));
            names.add(new ArrayList(Arrays.asList("Buck", "Carol")));
            ArrayList<String> addresses = new ArrayList();

            addresses.add("P.O. Box 281, 7217 Magna Ave" + " Lawrenceville");
            addresses.add("938-7401 Non, Street" + " Salisbury");
            addresses.add("294-5933 Vivamus Av." + " Covington");
            addresses.add("Ap #893-7214 Luctus, Av." + " Santa Clarita");
            addresses.add("466-4175 Molestie Rd." + " Keene");
            addresses.add("P.O. Box 530, 2920 Porttitor St." + " Somerville");
            addresses.add("P.O. Box 433, 7618 Aliquam Av." + " Spokane Valley");
            addresses.add("Ap #477-801 Aliquam Av." + " Fresno");
            addresses.add("P.O. Box 235, 1092 Elit St." + " Sunbury");
            addresses.add("369-7963 Ac, Road" + " Saint Joseph");
            addresses.add("8698 Egestas Street" + " Provo");
            addresses.add("Ap #973-8286 Cursus Ave" + " Bay St. Louis");
            addresses.add("P.O. Box 626, 4107 Et Ave" + " Normal");
            addresses.add("887-8747 Bibendum Ave" + " Indianapolis");
            addresses.add("426-1183 Nec, Ave" + " Orlando");
            addresses.add("463-7448 Vitae Av." + " Evanston");
            addresses.add("8254 Odio Av." + " Springfield");
            addresses.add("Ap #547-1125 Sem. Av." + " Citrus Heights");
            addresses.add("6254 Vulputate, Ave" + " Aberdeen");
            addresses.add("708-2489 Nisi Rd." + " Salem");
            addresses.add("275-2426 Quam, Road" + " Springfield");
            addresses.add("P.O. Box 756, 8639 Facilisis Avenue" + " Cairo");
            addresses.add("4469 Mattis. Ave" + " Fort Dodge");
            addresses.add("409-2914 Tempor Rd." + " Forest Lake");
            addresses.add("P.O. Box 514, 6990 Et Street" + " Paramount");
            addresses.add("Ap #147-9202 Nibh Rd." + " Urbana");
            addresses.add("Ap #338-9110 Pharetra Ave" + " Dubuque");
            Category c = new Category();
            Manufacturer m = new Manufacturer();
            Product p = new Product();
            Characteristic ch = new Characteristic();
            Client cl = new Client();

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
            for (String s : charac) {
                ch.setName(s);
                chf.edit(ch);
            }

            Long prodid;
            Random rand = new Random();
            for (int i = 0; i < subCategories.size(); i++) {
                c = cf.findByName((String) ((ArrayList) subCategories.get(i)).get(0));
                for (int j = 1; j < 4; j++) {
                    m = mf.findByName((String) manufacturers.get(new Random().nextInt(manufacturers.size())));
                    p.setCategorie(c);
                    p.setBrand(m);
                    p.setName(c.getCategorie() + j);
                    p.setPrice(10.0f + new Random().nextFloat() * ((1000.0f - 10.0f) + 1.0f));
                    p.setStock(10);
                    ArrayList<LineCharacteristic> lcs = new ArrayList<>();
                    for (int k = 0; k < 5; k++) {
                        k += new Random().nextInt(5);
                        ch = chf.findByName((String) charac.get(new Random().nextInt(charac.size())));
                        LineCharacteristic lc = new LineCharacteristic();
                        lc.setProduct(p);
                        lc.setCharacteristic(ch);
                        lc.setName(c.getCategorie() + ch.getName() + k);
                        lcs.add(lc);
                    }
                    p.setProductCaracteristics(lcs);
                    pf.edit(p);
                    prodid = pf.findAdvanced(null, null, p.getName(), false, 0, 0, new ArrayList()).get(0).getId();
                    Files.copy(
                            new File(getServletContext().getRealPath("img/prod/pDb_"+rand.nextInt(3)+".jpg")),
                            new File(getServletContext().getRealPath("img/prod/"+prodid+".jpg")));
                }
            }

            for (int i = 0; i < names.size(); i++) {
                String fn = (String) names.get(i).get(1);
                String ln = (String) names.get(i).get(0);
                cl.setFirstname(fn);
                cl.setSurname(ln);
                cl.setMail(fn + "." + ln + "@gmail.com");
                cl.setUsername(fn + ln);
                cl.setPassword(ln + fn);
                Address ai = new Address(addresses.get(i));
                cl.setAddressDelivery(ai);
                cl.setAddressPayement(ai);
                cl.setCart(new Cart());
                clf.edit(cl);
            }
            
            int[] range = {0, 6};
            HomeAd ad = new HomeAd();
            for (Product pa : pf.findRange(range)) {
                ad.setName(pa.getName());
                ad.setProduct(pa);
                haf.edit(ad);
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
