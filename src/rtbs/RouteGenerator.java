/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtbs;

/**
 *
 * @author Scythe
 */
public class RouteGenerator {

    public Train tr[];
    public Destination ds[];
    public int time[][];
    public double firstRate[][];
    public double econRate[][];
    public int timeCopy[][];
    public double firstRateCopy[][];
    public double econRateCopy[][];
    public int route[][];
    public int generatedRoute[];
    public int trace;
    
    
    public RouteGenerator(){
        JDBConnection jdbc=new JDBConnection();
        jdbc.connect();
        tr=jdbc.getAllTrains();
        ds=jdbc.getDestination();
        this.setArray();
        trace=0;
    }   
    
    public int traceLength()
    {
        return trace;
    }
    
    public void initRoute()
    {
        route=new int[ds.length][ds.length];
        generatedRoute=new int[ds.length];
        trace=0;
        
        for(int i=0;i<ds.length;i++)
        {
            for(int j=0;j<ds.length;j++)
            {
                if(i!=j)
                {
                    route[i][j]=i;
                }
            }
        }
    }
    public int[] generateRoute(int s,int d,String travelClass){
        this.copyArray();
        this.initRoute();
        
        if(travelClass.equals("ECONOMY"))
        {
            floydWarshallEconomy();
        }
        else{
            if(travelClass.equals("FIRST")){
                floydWarshallFirst();
            }
            else{
                floydWarshallTime();
            }
            
        }
        traceRoute(s,d);
       
        return generatedRoute;
    }
    
    public void traceRoute(int i,int j)
    {
        if(i==route[i][j])
        {
            return;
        }
        else
        {
            traceRoute(i,route[i][j]);
            generatedRoute[trace]=route[i][j];
            System.out.println(route[i][j]);
            trace++;
            traceRoute(route[i][j],j);
        }
        
    }
    
    public void floydWarshallEconomy()
    {
        for(int i=0;i<ds.length;i++)
        {
            for(int j=0;j<ds.length;j++)
            {
                for(int k=0;k<ds.length;k++)
                {
                    if(econRateCopy[j][k]!=0)
                    {
                        if((econRateCopy[j][i]+econRateCopy[i][k])<econRateCopy[j][k])
                        {
                            econRateCopy[j][k]=econRateCopy[j][i]+econRateCopy[i][k];
                         
                            route[j][k]=i;
                          
                        }
                    }
                }
            }
        }
    }
    
    public void floydWarshallFirst()
    {
        for(int i=0;i<ds.length;i++)
        {
            for(int j=0;j<ds.length;j++)
            {
                for(int k=0;k<ds.length;k++)
                {
                    if(firstRateCopy[j][k]!=0)
                    {
                        if((firstRateCopy[j][i]+firstRateCopy[i][k])<firstRateCopy[j][k])
                        {
                            firstRateCopy[j][k]=firstRateCopy[j][i]+firstRateCopy[i][k];
                         
                            route[j][k]=i;
                          
                        }
                    }
                }
            }
        }
    }
    
    public void floydWarshallTime()
    {
        for(int i=0;i<ds.length;i++)
        {
            for(int j=0;j<ds.length;j++)
            {
                for(int k=0;k<ds.length;k++)
                {
                    if(timeCopy[j][k]!=0)
                    {
                        if((timeCopy[j][i]+timeCopy[i][k])<timeCopy[j][k])
                        {
                            timeCopy[j][k]=timeCopy[j][i]+timeCopy[i][k];
                         
                            route[j][k]=i;
                          
                        }
                    }
                }
            }
        }
    }
        
    public void setArray(){
        time=new int[ds.length][ds.length];
        firstRate=new double[ds.length][ds.length];
        econRate=new double[ds.length][ds.length];
        for(int i=0;i<tr.length;i++){
            int s=this.getIndex(tr[i].getSource().getID());
            int d=this.getIndex(tr[i].getDestination().getID());
            if(true){
                if(time[s][d]==0){
                    time[s][d]=tr[i].getTotalTime();
                }
                if(firstRate[s][d]==0){
                    firstRate[s][d]=tr[i].getFirstClassRate();
                }
                if(econRate[s][d]==0){
                    econRate[s][d]=tr[i].getEconomyClassRate();
                }
            }
            else
            {
                if(tr[i].getTotalTime()<time[s][d]){
                    time[s][d]=tr[i].getTotalTime();
                }
                if(tr[i].getFirstClassRate()<firstRate[s][d]){
                    firstRate[s][d]=tr[i].getFirstClassRate();
                }
                if(tr[i].getEconomyClassRate()<econRate[s][d]){
                    econRate[s][d]=tr[i].getEconomyClassRate();
                }
            }
            
        }
    }
    
    public void copyArray(){
        timeCopy=new int[ds.length][ds.length];
        firstRateCopy=new double[ds.length][ds.length];
        econRateCopy=new double[ds.length][ds.length];
        for(int i=0;i<ds.length;i++){
            for(int j=0;j<ds.length;j++){
                timeCopy[i][j]=time[i][j];
                firstRateCopy[i][j]=firstRate[i][j];
                econRateCopy[i][j]=econRate[i][j];
            }
        }
    }
    
    public int getIndex(int i){
    
        int ret;
        for(ret=0;ret<ds.length;ret++){
            if(ds[ret].getID()==i)
            {
                return ret;
            }
        }
        return ret;   
    }
    
    
}
