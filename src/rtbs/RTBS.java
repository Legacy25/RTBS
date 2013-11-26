/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

import javax.swing.*;
/**
 *
 * @author Ary
 */
public class RTBS {
    
    public static Profile currentlyLoggedIn;
    public static String userName;
    public static String passWord;
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        try {            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        currentlyLoggedIn = new Profile();
        currentlyLoggedIn.setUsername("Anonymous");
        
        BufferFrame b =new BufferFrame();
        b.setLocationRelativeTo(null);
        b.setVisible(true);
        b.waitAction();
        b.setVisible(false);
        
        JDBConnection jdbc=new JDBConnection();
        jdbc.connect();
        
        if(!jdbc.adminExists()){
            jdbc.initiateAdministrator();
        }
      
        Welcome w = new Welcome();
        w.setLocationRelativeTo(null);
        w.setVisible(true);
    }
}
