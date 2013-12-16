package ejb;

import product.Product;
import command.Cart;
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
    private Cart pan;
    @EJB
    private CartFacade cef;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        pan = new Cart();

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        
    }

    public Cart getCart() {
        return pan;
    }

    public void addToCart(Product p, Integer q) {
        if (pan == null) {
            pan = new Cart();
        }

        pan.setQuantity(p, q);
    }

    public void addToCart(Product p) {
        if (pan == null) {
            pan = new Cart();
        }
        pan.setQuantity(p, 1);


    }
}
