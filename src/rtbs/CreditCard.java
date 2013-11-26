/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Ary
 */
public class CreditCard {
    
    private String CCID;
    private String CCNumber;
    private String CCCode;
    
    public void setCCID(String s) {
        CCID= s;
    }
    
    public String getCCID() {
        return CCID;
    }
    
    public void setCCNumber(String s) {
        CCNumber = s;
    }
    
    public String getCCNumber() {
        return CCNumber;
    }
    
    public void setCCCode(String s) {
        CCCode = s;
    }
    
    public String getAddress() {
        return CCCode;
    }
}
