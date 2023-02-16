public class ArrivalEvent extends Event {
    //arrival event
    public ArrivalEvent(Customer c)
    {
        super(c);
        setTime(c.getArrivalTime());
        super.setArrivalEvent();
    }
    public String toString()
    {
        return "Customer "+getCustomer().getID()+" Arrived at "+getTime();
    }
}
