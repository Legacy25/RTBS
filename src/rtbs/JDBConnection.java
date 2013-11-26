/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Scythe
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBConnection {
    
    Connection conn;
   
    public JDBConnection()
    {}
    
    public void connect()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",RTBS.userName,RTBS.passWord);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public boolean checkConnection()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",RTBS.userName,RTBS.passWord);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }
    
    public void createUser(Profile user,String password)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("insert into profile values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, password);
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getMiddleName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getEmail());
            ps.setInt(9,0);
            ps.setString(10, user.getPrivileges());
            ps.setString(11, user.getMobile());
            int flag=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
    }
    
    public void updateUser(Profile user,String password)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("insert into profile(fname,mname,lname,address,phone,email,mobile) values(?,?,?,?,?,?,?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getEmail());
            ps.setString(11, user.getMobile());
            int flag=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
    }
    
    public boolean authenticateUsername(String uname)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("Select username from profile");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                if(uname.equals(rs.getString(1)))
                {
                    return true;
                }
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
        return false;
    }
    
    public boolean authenticatePassword(String pwd)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("Select password from profile");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                if(pwd.equals(rs.getString(1)))
                {
                    return true;
                }
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
        return false;
    }
    
    public void setProfile(String uname,String pwd)
    {
        Profile p=new Profile();
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select username,fname,mname,lname,address,phone,email,privilege,mobile from profile where username like '"+uname+"'");
            while(rs.next())
            {
                 p.setUsername(rs.getString(1));
                 p.setFirstName(rs.getString(2));
                 p.setMiddleName(rs.getString(3));
                 p.setLastName(rs.getString(4));
                 p.setAddress(rs.getString(5));
                 p.setPhone(rs.getString(6));
                 p.setMobile(rs.getString(9));
                 p.setEmail(rs.getString(7));
                 p.setPrivileges(rs.getString(8));
                
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
        RTBS.currentlyLoggedIn=p;
    }
    
    private int getDestinationCount()
    {
        int count=0;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select count(*) from destination");
            while(rs.next())
            {
              count=rs.getInt(1);
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        } 
        return count;
    }
    public Destination[] getDestination()
    {
        int i=0;
        Destination d[]=new Destination[this.getDestinationCount()];
        for(int j=0;j<this.getDestinationCount();j++)
        {
            d[j]=new Destination();
        }
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select destination_id,city,station from destination");
            while(rs.next())
            {
                d[i].setID(rs.getInt(1));
                d[i].setCity(rs.getString(2));
                d[i].setStation(rs.getString(3));
                i++;
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        }
        return d;
        
    }
    
    private int getTrainCount(int i1, int i2)
    {
        int count=0;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select count(*) from train where train.source="+i1+" and train.destination="+i2);
            while(rs.next())
            {
              count=rs.getInt(1);
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        } 
        return count;
    }
    
    public Train[] getTrain(int i1, int i2)
    {
        int i=0;
        i1++;
        i2++;
        Destination s[]=new Destination[this.getTrainCount(i1,i2)];
        Destination d[]=new Destination[this.getTrainCount(i1,i2)];
        Train t[]=new Train[this.getTrainCount(i1,i2)];
        for(int j=0;j<this.getTrainCount(i1,i2);j++)
        {
            t[j]=new Train();
            s[j]=new Destination();
            d[j]=new Destination();
        }
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select train.train_id,s.city,s.station,d.city,d.station,train.departure_time,train.arrival_time,train.total_time,train.first_rate,train.econ_rate,train.first_avail,train.econ_avail,train.first_booked,train.econ_booked,train.locotype from train,destination s,destination d where train.source="+i1+" and train.destination="+i2+" and s.destination_id=train.source and d.destination_id=train.destination");
            while(rs.next())
            {
                t[i].setTrainID(rs.getString(1));
                s[i].setID(i1);
                s[i].setCity(rs.getString(2));
                s[i].setStation(rs.getString(3));
                d[i].setID(i2);
                d[i].setCity(rs.getString(4));
                d[i].setStation(rs.getString(5));
                t[i].setSource(s[i]);
                t[i].setDestination(d[i]);
                t[i].setDepartureTime(rs.getString(6));
                t[i].setArrivalTime(rs.getString(7));
                t[i].setTotalTime(rs.getInt(8));
                t[i].setFirstClassRate(rs.getDouble(9));
                t[i].setEconomyClassRate(rs.getDouble(10));
                t[i].setFirstClassTotalAvailability(rs.getInt(11));
                t[i].setEconomyClassTotalAvailabilty(rs.getInt(12));
                t[i].setFirstClassBooked(rs.getInt(13));
                t[i].setEconomyClassBooked(rs.getInt(14));
                t[i].setLocomotiveType(rs.getString(15));
                i++;
            }
        }
        catch(SQLException sql)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, sql);
        }
        return t;
    }
    
    public int getAllTrainCount()
    {
        int count=0;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select count(*) from train");
            while(rs.next())
            {
              count=rs.getInt(1);
            }
        }
        catch(SQLException s)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, s);
        } 
        return count;
    }
    
    public Train[] getAllTrains()
    {
        int i=0;
        Destination s[]=new Destination[this.getAllTrainCount()];
        Destination d[]=new Destination[this.getAllTrainCount()];
        Train t[]=new Train[this.getAllTrainCount()];
        for(int j=0;j<this.getAllTrainCount();j++)
        {
            t[j]=new Train();
            s[j]=new Destination();
            d[j]=new Destination();
        }
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select train.train_id,s.city,s.station,d.city,d.station,train.departure_time,train.arrival_time,train.total_time,train.first_rate,train.econ_rate,train.first_avail,train.econ_avail,train.first_booked,train.econ_booked,train.locotype, s.destination_id,d.destination_id from train,destination s,destination d where s.destination_id=train.source and d.destination_id=train.destination");
            while(rs.next())
            {
                t[i].setTrainID(rs.getString(1));
                s[i].setID(rs.getInt(16));
                s[i].setCity(rs.getString(2));
                s[i].setStation(rs.getString(3));
                d[i].setID(rs.getInt(17));
                d[i].setCity(rs.getString(4));
                d[i].setStation(rs.getString(5));
                t[i].setSource(s[i]);
                t[i].setDestination(d[i]);
                t[i].setDepartureTime(rs.getString(6));
                t[i].setArrivalTime(rs.getString(7));
                t[i].setTotalTime(rs.getInt(8));
                t[i].setFirstClassRate(rs.getDouble(9));
                t[i].setEconomyClassRate(rs.getDouble(10));
                t[i].setFirstClassTotalAvailability(rs.getInt(11));
                t[i].setEconomyClassTotalAvailabilty(rs.getInt(12));
                t[i].setFirstClassBooked(rs.getInt(13));
                t[i].setEconomyClassBooked(rs.getInt(14));
                t[i].setLocomotiveType(rs.getString(15));
                i++;
            }
        }
        catch(SQLException sql)
        {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, sql);
        }
        return t;
    }
    
    public void updateAccount(Profile p)
    {
        PreparedStatement ps;
        int i;
        try
        {
            ps=conn.prepareStatement("Update profile set fname=? where username=?");
            ps.setString(1, p.getFirstName());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setFirstName( p.getFirstName());
            
            
            ps=conn.prepareStatement("Update profile set mname=? where username=?");
            ps.setString(1, p.getMiddleName());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setMiddleName(p.getMiddleName());
            
            
            ps=conn.prepareStatement("Update profile set lname=? where username=?");
            ps.setString(1, p.getLastName());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setLastName(p.getLastName());
            
            
            ps=conn.prepareStatement("Update profile set address=? where username=?");
            ps.setString(1, p.getAddress());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setAddress(p.getAddress());
            
            
            ps=conn.prepareStatement("Update profile set phone=? where username=?");
            ps.setString(1, p.getPhone());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setPhone(p.getPhone());
            
            
            ps=conn.prepareStatement("Update profile set mobile=? where username=?");
            ps.setString(1, p.getMobile());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setMobile(p.getMobile());
            
            
            ps=conn.prepareStatement("Update profile set email=? where username=?");
            ps.setString(1, p.getEmail());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            i=ps.executeUpdate();
            RTBS.currentlyLoggedIn.setEmail(p.getEmail());
            
           
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public boolean checkPassword(String pwd){
        try
        {
            PreparedStatement ps=conn.prepareStatement("Select password from profile where username=?");
            ps.setString(1, RTBS.currentlyLoggedIn.getUsername());
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                if(pwd.equals(rs.getString(1)))
                {
                    return true;
                }
            }
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
        return false;
    }
    
    public void changeUsername(String uname)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("Update profile set username=? where username=?");
            ps.setString(1, uname);
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            int i=ps.executeUpdate();
            
            RTBS.currentlyLoggedIn.setUsername(uname);
            
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public void changePassword(String pwd)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("Update profile set password=? where username=?");
            ps.setString(1, pwd);
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            int i=ps.executeUpdate();
           
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public String randomIDGenerator()
    {
        String id="";
        id=id+System.currentTimeMillis();
        return id;
    }
    public void updateBooking(double amount,int fc,int sc,String pType,Train tr)
    {
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select first_avail,econ_avail,first_booked,econ_booked from train where train_id='"+tr.getTrainID()+"'");
            PreparedStatement ps=conn.prepareStatement("insert into booking values(?,?,?,?,?,?,?,?)");
            ps.setString(1, this.randomIDGenerator());
            ps.setString(2, RTBS.currentlyLoggedIn.getUsername());
            ps.setString(3, tr.getTrainID());
            ps.setInt(4, fc);
            ps.setInt(5, sc);
            ps.setDouble(6, amount);
            ps.setString(7, pType);
            ps.setString(8, "Yes");
            int i=ps.executeUpdate();
            while(rs.next())
            {
                ps=conn.prepareStatement("Update train set first_avail=? where train_id='"+tr.getTrainID()+"'");
                ps.setInt(1, rs.getInt(1)-fc);
                i=ps.executeUpdate();

                ps=conn.prepareStatement("Update train set econ_avail=? where train_id='"+tr.getTrainID()+"'");
                ps.setInt(1, rs.getInt(2)-sc);
                i=ps.executeUpdate();

                ps=conn.prepareStatement("Update train set first_booked=? where train_id='"+tr.getTrainID()+"'");
                ps.setInt(1, rs.getInt(3)+fc);
                i=ps.executeUpdate();

                ps=conn.prepareStatement("Update train set first_booked=? where train_id='"+tr.getTrainID()+"'");
                ps.setInt(1, rs.getInt(4)+fc);
                i=ps.executeUpdate();
            }
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public int getBookingCount()
    {
        int count=0;
        try
        {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("Select count(*) from booking where username='"+RTBS.currentlyLoggedIn.getUsername()+"'");
            while(rs.next())
            {
                count=rs.getInt(1);
            }
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
        return count;
    }
    
    public String toString(int n)
    {
        String s="";
        s=s+n;
        return s;
    }
    
    public String toString(double n)
    {
        String s="";
        s=s+n;
        return s;
    }
    
    public String[][] getBookingHistory()
    {
        int i=0;
        String history[][]=new String[this.getBookingCount()][6];
        
        try
        {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("Select train_id,first_seats,econ_seats,total_amount,payment_method,payment_approved from booking where username='"+RTBS.currentlyLoggedIn.getUsername()+"'");
            while(rs.next())
            {
                history[i][0]=rs.getString(1);
                history[i][1]=toString(rs.getInt(2));
                history[i][2]=toString(rs.getInt(3));
                history[i][3]=toString(rs.getDouble(4));
                history[i][4]=rs.getString(5);
                history[i][5]=rs.getString(6);
            }
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
        
        return history;
    }
    
    public void addTrains(Train tr)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("insert into train values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, tr.getTrainID());
            ps.setInt(2, tr.getDestination().getID());
            ps.setInt(3, tr.getSource().getID());
            ps.setString(4, tr.getArrivalTime());
            ps.setString(5, tr.getDepartureTime());
            ps.setInt(6, tr.getTotalTime());
            ps.setDouble(7, tr.getFirstClassRate());
            ps.setDouble(8, tr.getEconomyClassRate());
            ps.setInt(9, tr.getFirstClassTotalAvailability());
            ps.setInt(10, tr.getEconomyClassTotalAvailabilty());
            ps.setInt(11, tr.getFirstClassBooked());
            ps.setInt(12, tr.getEconomyClassBooked());
            ps.setString(13, tr.getLocomotiveType());
            int i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public void addDestination(Destination d)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("insert into destination values(?,?,?)");
            ps.setInt(1, d.getID());
            ps.setString(2, d.getCity());
            ps.setString(3, d.getStation());
            int i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public void updateDestination(Destination d)
    {
        PreparedStatement ps;
        int i;
        try
        {
            ps=conn.prepareStatement("update destination set city=? where destination_id=?");
            ps.setString(1, d.getCity());
            ps.setInt(2, d.getID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update Destination set station=? where destination_id=?");
            ps.setString(1, d.getStation());
            ps.setInt(2, d.getID());
            i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public void deleteDestination(int d)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("delete from destination where destination_id=?");
            ps.setInt(1, d);
            int i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public int getUserCount()
    {
        int count=0;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("select count(*) from profile");
            while(rs.next())
            {
                count=rs.getInt(1);
            }
        }
        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return count;
    }
    public String[][] getUsers()
    {
        String str[][]=new String[this.getUserCount()][2];
        int i=0;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("select username,fname,lname from profile where privilege not like 'ADMIN'");
            while(rs.next())
            {
                str[i][0]=rs.getString(1);
                str[i][1]=rs.getString(2)+" "+rs.getString(3);
                i++;
            }
        }
        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
        return str;
    }
    
    public void addAdministrator(String str){
        try
        {
            PreparedStatement ps=conn.prepareStatement("update profile set privilege='ADMIN' where username=?");
            ps.setString(1, str);
            int i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public void deleteTrain(String s)
    {
        try
        {
            PreparedStatement ps=conn.prepareStatement("delete from train where train_id=?");
            ps.setString(1, s);
            int i=ps.executeUpdate();
        }
        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
    }
    
    public void updateTrain(Train tr)
    {
        PreparedStatement ps;
        int i;
        try
        {
            ps=conn.prepareStatement("update train set source=? where train_id=?");
            ps.setInt(1,tr.getSource().getID());
            ps.setString(2, tr.getTrainID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set destination=? where train_id=?");
            ps.setInt(1,tr.getDestination().getID());
            ps.setString(2, tr.getTrainID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set arrival_time=? where train_id=?");
            ps.setString(1,tr.getArrivalTime());
            ps.setString(2, tr.getTrainID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set departure_time=? where train_id=?");
            ps.setString(1,tr.getDepartureTime());
            ps.setString(2, tr.getTrainID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set locotype=? where train_id=?");
            ps.setString(1,tr.getLocomotiveType());
            ps.setString(2, tr.getTrainID());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set first_rate=? where train_id=?");
            ps.setInt(1,tr.getSource().getID());
            ps.setDouble(2, tr.getFirstClassRate());
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("update train set econ_rate=? where train_id=?");
            ps.setInt(1,tr.getSource().getID());
            ps.setDouble(2, tr.getEconomyClassRate());
            i=ps.executeUpdate();
        }
        catch(SQLException sql)
        {
            sql.printStackTrace();
        }
    }
    
    public boolean databaseExists()
    {
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select train_id from train");
            while(rs.next())
            {
                continue;
            }
        }
        catch(SQLException s)
        {
            return false;
        }
        return true;
    }
    
    public boolean initiateDatabase()
    {
        PreparedStatement ps;
        int i;
        String destinationQueries[]={"insert into destination values(1,'London','Kings Cross Station')",
                                        "insert into destination values(2,'Los Angeles','Union Station')",
                                        "insert into destination values(3,'Porto','Sao Bento Station')",
                                        "insert into destination values(4,'Istanbul','Sirkeci Staion')",
                                        "insert into destination values(5,'Paris','Gare Du Nord')"};
        String trainQueries[]={"insert into train values('1',1,2,'13:30','15:30',120,40.00,20.00,50,100,0,0,'AC')",
                                "insert into train values('2',1,3,'13:30','16:30',180,140.00,120.00,50,100,0,0,'AC')",
                                "insert into train values('3',1,4,'13:30','14:30',60,45.00,25.00,50,100,0,0,'AC')",
                                "insert into train values('4',1,5,'13:30','15:00',90,50.00,30.00,50,100,0,0,'AC')",
                                "insert into train values('5',2,1,'13:30','15:30',120,40.00,20.00,50,100,0,0,'AC')",
                                "insert into train values('6',2,3,'13:30','18:30',300,540.00,520.00,50,100,0,0,'AC')",
                                "insert into train values('7',2,4,'13:30','19:30',360,410.00,390.00,50,100,0,0,'AC')",
                                "insert into train values('8',2,5,'13:30','14:00',30,30.00,20.00,50,100,0,0,'AC')",
                                "insert into train values('9',3,1,'13:30','16:30',180,140.00,120.00,50,100,0,0,'AC')",
                                "insert into train values('10',3,2,'13:30','18:30',300,540.00,520.00,50,100,0,0,'AC')",
                                "insert into train values('11',3,4,'13:30','23:30',600,100.00,80.00,50,100,0,0,'AC')",
                                "insert into train values('12',3,5,'13:30','14:45',75,150.00,130.00,50,100,0,0,'AC')",
                                "insert into train values('13',4,1,'13:30','14:30',60,45.00,25.00,50,100,0,0,'AC')",
                                "insert into train values('14',4,2,'13:30','19:30',360,410.00,390.00,50,100,0,0,'AC')",
                                "insert into train values('15',4,3,'13:30','23:30',600,100.00,80.00,50,100,0,0,'AC')",
                                "insert into train values('16',4,5,'13:30','17:15',225,190.00,170.00,50,100,0,0,'AC')",
                                "insert into train values('17',5,1,'13:30','15:00',90,50.00,30.00,50,100,0,0,'AC')",
                                "insert into train values('18',5,2,'13:30','14:00',30,30.00,20.00,50,100,0,0,'AC')",
                                "insert into train values('19',5,3,'13:30','14:45',75,150.00,130.00,50,100,0,0,'AC')",
                                "insert into train values('20',5,4,'13:30','17:15',225,190.00,170.00,50,100,0,0,'AC')"};
        
        try
        {
           
            for(int j=0;j<destinationQueries.length;j++)
            {
                ps=conn.prepareStatement(destinationQueries[j]);
                i=ps.executeUpdate();
            }
            
            for(int j=0;j<trainQueries.length;j++)
            {
                ps=conn.prepareStatement(trainQueries[j]);
                i=ps.executeUpdate();
            }
            
        }
        catch(SQLException s)
        {
            s.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean adminExists()
    {
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select username from profile");
            while(rs.next())
            {
                continue;
            }
        }
        catch(SQLException s)
        {
            return false;
        }
        
        return true;
        
    }
    
    public void initiateAdministrator()
    {
        PreparedStatement ps;
        int i;
        try
        {
            ps=conn.prepareStatement("create table profile(username varchar2(255) not null primary key,password varchar2(255) not null,fname varchar2(255) not null,mname varchar2(255),lname varchar2(255) not null,address varchar2(255),phone varchar2(255) not null,email varchar2(255),raflag int,privilege varchar2(255),mobile varchar2(255) not null)");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("insert into profile values('admin','admin','Admin','is','trator','Kensington Road','76959858','scythe@gmail.com',0,'ADMIN','9696057875')");
            i=ps.executeUpdate();
            
            ps=conn.prepareStatement("create table destination(destination_id int not null primary key,city varchar2(255),station varchar2(255))");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table creditcard(cc_id varchar2(255) not null primary key,cc_number varchar2(255) not null,cc_code varchar2(255) not null)");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table train(train_id varchar2(255) not null primary key,source int not null,destination int not null,departure_time varchar2(255) not null,arrival_time varchar2(255) not null,total_time int not null,first_rate number(10,2),econ_rate number(10,2),first_avail int,econ_avail int,first_booked int,econ_booked int,locotype varchar2(255),constraint fk_source foreign key(source) references destination(destination_id),constraint fk_destination foreign key(destination) references destination(destination_id))");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table booking(booking_id varchar2(255) not null primary key,username varchar2(255) not null,train_id varchar2(255) not null,first_seats int,econ_seats int,total_amount number(10,2) not null,payment_method varchar2(255) not null,payment_approved varchar2(255) not null,constraint fk_username foreign key(username) references profile(username))");        
            i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
        }
    }
    
    public boolean resetDatabase()
    {
        PreparedStatement ps;
        int i;
        try
        {
            ps=conn.prepareStatement("drop table creditcard");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("drop table booking");
            i=ps.executeUpdate(); 
            ps=conn.prepareStatement("drop table train");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("drop table destination");
            i=ps.executeUpdate(); 
            ps=conn.prepareStatement("create table destination(destination_id int not null primary key,city varchar2(255),station varchar2(255))");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table creditcard(cc_id varchar2(255) not null primary key,cc_number varchar2(255) not null,cc_code varchar2(255) not null)");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table train(train_id varchar2(255) not null primary key,source int not null,destination int not null,departure_time varchar2(255) not null,arrival_time varchar2(255) not null,total_time int not null,first_rate number(10,2),econ_rate number(10,2),first_avail int,econ_avail int,first_booked int,econ_booked int,locotype varchar2(255),constraint fk_source foreign key(source) references destination(destination_id),constraint fk_destination foreign key(destination) references destination(destination_id))");
            i=ps.executeUpdate();
            ps=conn.prepareStatement("create table booking(booking_id varchar2(255) not null primary key,username varchar2(255) not null,train_id varchar2(255) not null,first_seats int,econ_seats int,total_amount number(10,2) not null,payment_method varchar2(255) not null,payment_approved varchar2(255) not null,constraint fk_username foreign key(username) references profile(username))");        
            i=ps.executeUpdate();
        }
        catch(SQLException s)
        {
            s.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean trainExists(int d)
    {
        
        boolean flag=false;
        try
        {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select train_id,source,destination from train where source="+d+" or destination="+d+"");
            while(rs.next())
            {
                flag=true;
            }
        }
        catch(SQLException s)
        {
           
        }
        
        return flag;
    }
    
    public String getEmailID(String username) {

        connect();
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs=st.executeQuery("Select email from profile where username='"+username+"'");
            while(rs.next())
            {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            
        }
        return "Username doesn't exist";
    }
    
    public String getPassword(String username) {

        connect();
        
        String password = randomPasswordGenerator();
        try
        {
            PreparedStatement ps=conn.prepareStatement("update profile set password=? where username=?");
            ps.setString(1, password);
            ps.setString(2, username);
            int i=ps.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger(JDBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return password;
    }
    
    private String randomPasswordGenerator() {
        char[] arr = new char[8];
        
        for(int i=0; i<8; i++) {
            Number rand = (Math.random()*10);
            int c = rand.intValue();
            c%=5;
            switch(c) {
                case 0:
                    arr[i] = 'a';
                    break;
                case 1:
                    arr[i] = 'B';
                    break;
                case 2:
                    arr[i] = 'H';
                    break;
                case 3:
                    arr[i] = '9';
                    break;
                case 4:
                    arr[i] = '5';
                    break;
            }
        }
        
        return new String(arr);
    }
}