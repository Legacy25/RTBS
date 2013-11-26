/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Ary
 */
public class Train {
    
    private String trainID;
    private Destination source;
    private Destination destination;
    
    private String departureTime;
    private String arrivalTime;
    private int totalTime;
    
    private double firstClassRate;
    private double economyClassRate;
    
    private int firstClassTotalAvailability;
    private int economyClassTotalAvailabilty;
    
    private int firstClassBooked;
    private int economyClassBooked;
    
    private String locomotiveType;
    
    public void setTrainID(String s) {
        trainID = s;
    }
    
    public String getTrainID() {
        return trainID;
    }
    
    public void setSource(Destination s) {
        source = s;
    }
    
    public Destination getSource() {
        return source;
    }
    
    public void setDestination(Destination s) {
        destination = s;
    }
    
    public Destination getDestination() {
        return destination;
    }
    
    public void setDepartureTime(String s) {
        departureTime = s;
    }
    
    public String getDepartureTime() {
        return departureTime;
    }
    
    public void setArrivalTime(String s) {
        arrivalTime = s;
    }
    
    public String getArrivalTime() {
        return arrivalTime;
    }
    
    public void setTotalTime(int s)
    {
        totalTime=s;
    }
    
    public int getTotalTime()
    {
        return totalTime;
    }
    public void setFirstClassRate(double s) {
        firstClassRate = s;
    }
    
    public double getFirstClassRate() {
        return firstClassRate;
    }
    
    public void setEconomyClassRate(double s) {
        economyClassRate = s;
    }
    
    public double getEconomyClassRate() {
        return economyClassRate;
    }
    
    public void setFirstClassTotalAvailability(int s) {
        firstClassTotalAvailability = s;
    }
    
    public int getFirstClassTotalAvailability() {
        return firstClassTotalAvailability;
    }
    
    public void setEconomyClassTotalAvailabilty(int s) {
        economyClassTotalAvailabilty = s;
    }
    
    public int getEconomyClassTotalAvailabilty() {
        return economyClassTotalAvailabilty;
    }
    
    public void setFirstClassBooked(int s) {
        firstClassBooked = s;
    }
    
    public int getFirstClassBooked() {
        return firstClassBooked;
    }
    
    public void setEconomyClassBooked(int s) {
        economyClassBooked = s;
    }
    
    public int getEconomyClassBooked() {
        return economyClassBooked;
    }
    
    public void setLocomotiveType(String s) {
        locomotiveType = s;
    }
    
    public String getLocomotiveType() {
        return locomotiveType;
    }
    
}
