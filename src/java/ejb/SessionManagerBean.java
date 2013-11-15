package ejb;

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
    private CommandEntity pan;
    
    @EJB
    private CommandEntityFacade cef;
    

    public void sessionCreated(HttpSessionEvent se) {
        pan = new CommandEntity();

    }

    public void sessionDestroyed(HttpSessionEvent se) {
        
        cef.create(pan);
//        pan = null;
    }

//    public int getActiveSessionsCount() {
//        return counter;
//    }
    public CommandEntity getCart() {
        return pan;
    }

    public void addToCart(ProductEntity p, Integer q) {
        if (pan == null) {
            pan = new CommandEntity();
        }

        pan.setQuantity(p, q);
    }

    public void addToCart(ProductEntity p) {
        if (pan == null) {
            pan = new CommandEntity();
        }
        pan.setQuantity(p, 1);


    }
}
