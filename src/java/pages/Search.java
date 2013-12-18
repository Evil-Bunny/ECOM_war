/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import ejb.CategoryFacade;
import ejb.CharacteristicFacade;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ejb.ManufacturerFacade;
import java.util.Collections;
import product.Manufacturer;
import product.type.Category;
import product.type.Characteristic;

/**
 *
 * @author bousky
 */
public class Search extends AbstractPage {

    @EJB
    CategoryFacade cf;
    
    @EJB
    ManufacturerFacade mf;
    
    @EJB
    CharacteristicFacade chf;
    
    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Recherche avancée";
    }
    
    @Override
    protected List<String> getArianeNames(HttpServletRequest request) {
        return null;
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        return null;
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        List<Manufacturer> manufacturers = mf.findAll();
        List<Characteristic> caracteristics = chf.findAll();
        out.println("<form action='?page=Products' method='POST' onsubmit='return checkSearch();'>");
        out.println("<input type='hidden' name='page' value='Products'/>");
        out.println("<label>Nom du produit<input name='name' type='text'/></label>");
        out.println("<label>Marque & Constructeur<select name='manufacturer'>");
        out.println("<option value=''>Choisir une marque</option>");
        Collections.sort(manufacturers);
        for (Manufacturer m : manufacturers) {
            out.println("<option value='"+m.getId()+"'>"+HTMLEncode(m.getName())+"</option>");
        }
        out.println("</select></label>");
        out.println("<label>Catégorie<select name='category'>");
        out.println("<option value=''>Choisir une catégorie</option>");
        for (Category c : cf.findAll()) {
            out.println("<option value='"+c.getId()+"'>"+HTMLEncode(c.getCategorie())+"</option>");
        }
        out.println("</select></label>");
        out.println("<label>En stock<input type='checkbox' name='stock' checked='checked'/></label>");
        out.println("<fieldset><legend>Prix</legend><label>supérieur à<input id='minPrice' type='text' maxlength='10' name='minPrice'/></label><label>inférieur à<input id='maxPrice' maxlength='10' type='text' name='maxPrice'/></label></fieldset>");
        out.println("<fieldset id='carac'><legend>Caractéristiques</legend><div><table id='caracs'>");
        out.println("<tr><td>Nom</td><td>Valeur</td><td></td></tr>");
        out.println("<tr><td><select name='caracName_1'>");
        Collections.sort(caracteristics);
        for (Characteristic c : caracteristics) {
            out.println("<option value='"+c.getId()+"'>"+HTMLEncode(c.getName())+"</option>");
        }
        out.println("</select></td><td><input type='text' name='caracVal_1'/></td><td><button type='button' onclick='delCarac(this)'>X</button></td></tr>");
        out.println("<tr><td><button type='button' onclick='addCarac()'>Ajouter un critère</button></td><td></td></tr>");
        out.println("</table></div></fieldset>");
        out.println("<input type='submit' value='Rechercher'/>");
        out.println("</form>");
    }
    
}
