import java.util.ArrayList;

//standard event class from which all others extend
//implements comparable to be sorted by time
public class Event  implements Comparable<Event>{
    private double time;

    //what type of event is being used
    private boolean isArrivalEvent;
    private boolean isShoppingEvent;
    private boolean isCheckoutEvent;
    private boolean isExitEvent;
    private Customer c;
    public Event(Customer c)
    {
        this.c=c;
    }
    public double getTime()
    {
        return time;
    }
    public void setTime(double time)
    {
        this.time=time;
    }
    public Customer getCustomer()
    {
        return c;
    }
    public boolean getArrivalEvent()
    {
        return isArrivalEvent;
    }
    public boolean getShoppingEvent()
    {
        return isShoppingEvent;
    }
    public boolean getCheckoutEvent()
    {
        return isCheckoutEvent;
    }
    public boolean getExitEvent()
    {
        return isExitEvent;
    }

    //sets the respective event to true 
    public void setArrivalEvent()
    {
        isArrivalEvent=true;
    }
    public void setShoppingEvent()
    {
        isShoppingEvent=true;
    }
    public void setCheckoutEvent()
    {
        isCheckoutEvent=true;
    }
    public void setExitEvent()
    {
        isExitEvent=true;
    }
    //to be implemented in other methods
    public int getLaneIndex()
    {
        return 0;
    }
    public int getExitIndex()
    {
        return 0;
    }
    public void customerExit(ArrayList<Lane> lanes, int index)
    {
        
    }
    public int compareTo(Event e)
    {
        if(this.getTime()<e.getTime())
        {
            return -1;
        }else if(this.getTime()>e.getTime())
        {
            return 1;
        }else 
        return 0;

    }
    
}
