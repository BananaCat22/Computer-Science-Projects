public class ShoppingEvent extends Event {
    //Shopping event
    //adds the shopping time to the total time
    public ShoppingEvent(Customer c)
    {
        super(c);
        c.addTotalTime(c.getTotalShopTime());
        setTime(c.getTotalTime());
        super.setShoppingEvent();
    }
    public String toString()
    {
        return "Customer "+getCustomer().getID()+" Finished shopping at "+Math.round(getTime()*100)/100.0;
    }
}
