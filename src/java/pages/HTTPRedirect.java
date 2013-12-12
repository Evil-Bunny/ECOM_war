/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

/**
 *
 * @author bousky
 */
public class HTTPRedirect extends RuntimeException{
    
    public HTTPRedirect (String url) {
        super(url);
    }
    
}
