/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author msi
 */
public class PayementOk extends AbstractPage {

    @Override
    protected String getTitle(HttpServletRequest request) {
        return "Payement Accepté";
    }

    @Override
    protected List<String> getArianeNames(HttpServletRequest request) {
        return Arrays.asList("Panier", "Informations client", "Vérification avant payement", "Payement");
    }

    @Override
    protected List<String> getArianeLinks(HttpServletRequest request) {
        return Arrays.asList(null, null, null, null);
    }

    @Override
    protected void printPage(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        out.println("Payement accepté");
    }

}
