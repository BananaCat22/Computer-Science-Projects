import java.util.ArrayList;
public class CheckoutEvent extends Event {
    private int LaneIndex;
    private ArrayList<Lane> lanes;

    //Checkout event also busiest event
    public CheckoutEvent(Customer c, ArrayList<Lane> lanes)
    {
        super(c);
        this.setTime(c.getTotalTime());
        laneSelect(lanes);
        super.setCheckoutEvent();
        this.lanes=lanes;
    }
    public ArrayList<Lane> getLanes()
    {
        return lanes;
    }
    @Override
    public int getLaneIndex()
    {
        return LaneIndex;
    }
    //selects the lane which the customer will be placed in
    //also implements the setting of the wait time
    //as well as the checkout time
    //that will be used to queue the exit event
    public void laneSelect(ArrayList<Lane> lanes)
    {
        if(this.getCustomer().getGroceryNum()<=12)
        {
            int index=0;
            for(int x=0;x<lanes.size();x++)
            {
                if(lanes.get(x).size()<lanes.get(index).size())
                {
                    index=x;
                }
            }
            for(int x=0;x<lanes.size();x++)
            {
                if((lanes.get(index).size()==lanes.get(x).size())&&!(lanes.get(index).getIsExpress())&&(lanes.get(x).getIsExpress()))
                {
                    index=x;
                }
            }
            lanes.get(index).add(this.getCustomer());
            lanes.get(index).setTotalCheckoutTime(lanes.get(index).size()-1);
            LaneIndex=index;
        }else if(this.getCustomer().getGroceryNum()>12)
        {
            int index=0;
            for(int x=0;x<lanes.size();x++)
            {
                if(lanes.get(index).size()>lanes.get(x).size()&&!(lanes.get(x).getIsExpress()))
                {
                    index=x;
                }
            }
            lanes.get(index).add(this.getCustomer());
            lanes.get(index).setTotalCheckoutTime(lanes.get(index).size()-1);
            LaneIndex=index;
        }
    }
    public String toString()
    {
        if(getCustomer().getGroceryNum()>12)
        {
            return "Customer "+getCustomer().getID()+" has more than 12 items choose Lane "+getLaneIndex()+" Number of customers already in line: "+(lanes.get(this.getLaneIndex()).size()-1);
        }else
        return "Customer "+getCustomer().getID()+" has 12 or less items choose Lane "+getLaneIndex()+" Number of customers already in line: "+(lanes.get(this.getLaneIndex()).size()-1);
    }
}
