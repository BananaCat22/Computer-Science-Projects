import java.util.ArrayList;

public class ExitEvent extends Event {
    //exit event
    private int exitIndex;
    public ExitEvent(Customer c, int exitIndex)
    {
        super(c);
        this.exitIndex=exitIndex;
        setTime(c.getTotalTime());
        super.setExitEvent();

    }
    //returns the lane index where the customer is to be ejected
    @Override
    public int getExitIndex()
    {
        return exitIndex;
    }
    //removes the customer from the lane
    @Override
    public void customerExit(ArrayList<Lane> lanes, int index)
    {
        lanes.get(index).remove(0);
    }
    public String toString()
    {
        return "Customer "+getCustomer().getID()+" finished Checkout at "+ Math.round(getCustomer().getTotalTime()*100)/100.0+" in Lane "+getExitIndex()+" this customer experienced "+Math.round(getCustomer().getWaitTime()*100)/100.0+" wait time  this customer reached the front line at "+(Math.round((this.getCustomer().getArrivalTime()+this.getCustomer().getTotalShopTime()+this.getCustomer().getWaitTime())*100)/100.0);
    }
}
