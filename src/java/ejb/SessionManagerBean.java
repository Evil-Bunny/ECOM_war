package ejb;

import product.Product;
import command.Command;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author nb
 */
@Singleton
@LocalBean
@WebListener
public class SessionManagerBean implements HttpSessionListener {

//    @EJB
    private Command pan;
    
    @EJB
    private CommandFacade cef;
    

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        pan = new Command();

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
        cef.create(pan);
    }

    public Command getCart() {
        return pan;
    }

    public void addToCart(Product p, Integer q) {
        if (pan == null) {
            pan = new Command();
        }

        pan.setQuantity(p, q);
    }

    public void addToCart(Product p) {
        if (pan == null) {
            pan = new Command();
        }
        pan.setQuantity(p, 1);


    }
}
