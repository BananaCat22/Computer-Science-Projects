import java.util.ArrayList;
public class Lane extends ArrayList<Customer>
{
    //Lane Superclass
    //the lane subclasses only change the constructor fields but are otherwise identical
    private  double avgItemTime;
    private  double waitTime=0;
    private  double processTime;
    private  boolean isExpress;
    public Lane(double avgItemTime, double processTime, boolean isExpress)
    {
        this.avgItemTime=avgItemTime;
        this.processTime=processTime;
        this.isExpress=isExpress;
    }
    public boolean getIsExpress()
    {
        return isExpress;
    }
    //calculates the total checkout time
    public void setTotalCheckoutTime(int index)
    {
        setWaitTime();
        double checkoutTime=(this.get(index).getGroceryNum()*avgItemTime)+processTime;
        this.get(index).setTotalCheckoutTime(checkoutTime);
        this.get(index).addTotalTime(checkoutTime+this.get(index).getWaitTime());

    }
    //sets the wait time based on lane position
    public void setWaitTime()
    {
        if(this.size()>1)
        {
            for(int x=1;x<this.size();x++)
            {
            if(x>=2&&(this.get(x)).getWaitTime()==0)
                {
                    waitTime=this.get(x-1).getTotalTime()-(this.get(x).getTotalTime());
                    this.get(x).setWaitTime(waitTime);
                    waitTime=0;
                }else if(x==1&&this.get(x).getWaitTime()==0)
                {
                   waitTime=this.get(0).getTotalTime()-this.get(x).getTotalTime();
                   this.get(x).setWaitTime(waitTime);
                   waitTime=0;
                }
            }
        }
    }

}