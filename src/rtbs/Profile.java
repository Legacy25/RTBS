/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Ary
 */
public class Profile {
    
    private String username;    
    private String firstName;    
    private String middleName;    
    private String lastName;    
    private String address;    
    private String phone; 
    private String mobile;
    private String email;
    private String privileges;
    
    public void setUsername(String s) {
        username = s;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setFirstName(String s) {
        firstName = s;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setMiddleName(String s) {
        middleName = s;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setLastName(String s) {
        lastName = s;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setAddress(String s) {
        address = s;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setPhone(String s) {
        phone = s;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setMobile(String s) {
        mobile = s;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setEmail(String s) {
        email = s;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setPrivileges(String s) {
        privileges = s;
    }
    
    public String getPrivileges() {
        return privileges;
    }
}
