/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Ary
 */
public class Destination {
    
    private int id;
    private String city;
    private String station;
    
    public void setID(int s) {
        id = s;
    }
    
    public int getID() {
        return id;
    }
    
    public void setCity(String s) {
        city = s;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setStation(String s) {
        station = s;
    }
    
    public String getStation() {
        return station;
    }
    
}
