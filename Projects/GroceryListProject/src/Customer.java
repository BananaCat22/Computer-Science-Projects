

public class Customer implements Comparable<Customer>{
    //customer class keeps track of ALL unique time instances
    private double arrivalTime;
    private int groceryNum;
    private double avgPerItemTime;
    private double totalShopTime;
    private double totalCheckoutTime;
    private double totalTime;
    private double waitTime;
    private String id;
    public Customer(String id, double arrivalTime, int groceryNum, double avgPerItemTime)
    {
        this.id=id;
        this.arrivalTime=arrivalTime;
        this.groceryNum=groceryNum;
        this.avgPerItemTime=avgPerItemTime;
        totalShopTime=this.avgPerItemTime*this.groceryNum;
        totalTime=this.arrivalTime;
        waitTime=0;
    }
    //standard getter and setter methods
    //addtotaltime increases total time based on input
    public int getGroceryNum()
    {
        return groceryNum;
    }
    public double getArrivalTime()
    {
        return arrivalTime;
    }
    public void setTotalCheckoutTime(double checkout)
    {
        totalCheckoutTime=checkout;
    }
    public double getTotalCheckoutTime()
    {
        return totalCheckoutTime;
    }
    public void addTotalTime(double times)
    {
        totalTime+=times;
    }
    public double getTotalTime()
    {
        return totalTime;
    }
    public double getTotalShopTime()
    {
        return totalShopTime;
    }
    public void setWaitTime(double wait)
    {
        waitTime=wait;
    }
    public double getWaitTime()
    {
        return waitTime;
    }
    public String getID()
    {
        return id;
    }
    public int compareTo(Customer c)
    {
        if(totalTime<c.getTotalTime())
        {
            return -1;
        }else if(totalTime>c.getTotalTime())
        {
            return 1;
        }else 
        return 0;
    }
}