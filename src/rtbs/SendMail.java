/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Ary
 */
public class SendMail {
    
    public void sendPassword(String username, String email) {
        
        JDBConnection jdbc = new JDBConnection();
        String password = jdbc.getPassword(username);
        
        if(password == null) {
            JOptionPane.showMessageDialog(null, "Something went wrong. Try again.", "Error retrieving password", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("easytrainrtbs","rolande09");
				}
			});
        
        try {
            MimeMessage m = new MimeMessage(session);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            m.setFrom(new InternetAddress("easytrainrtbs@gmail.com"));
            m.setSubject("Your password has been reset");
            m.setText("Your EasyTrain password has been reset. Your new password is "+password+"\n\n"
                    + "We look forward to your continued patronage. Thank you.");
            Transport.send(m);
        } catch (Exception ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
