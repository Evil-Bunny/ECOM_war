/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

/**
 *
 * @author bousky
 */
public class HTTPErrorException extends RuntimeException {
    
    private int errorCode;
    
    public HTTPErrorException (int errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
}
