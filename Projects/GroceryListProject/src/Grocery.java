import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;

/*
Chris Elim Nodel
CS-1181-01
CS-1181L-06
11/6/2022
*/
public class Grocery {

    //reads through the file to create a list of customers
    public static PriorityQueue<Customer> readFile(String fileName) throws IOException
    {
        PriorityQueue<Customer> customerQueue= new PriorityQueue<Customer>();
        Scanner fileScanner= new Scanner(new File(fileName));
        int counterID=1;
        while(fileScanner.hasNextLine())
        {
            Customer newCustomer= new Customer(Integer.toString(counterID),fileScanner.nextDouble(), fileScanner.nextInt(), fileScanner.nextDouble());
            customerQueue.offer(newCustomer);
            counterID++;

        }
        return customerQueue;
    }
    public static void main(String [] args) throws IOException
    {
        //holds all events that will occur
        PriorityQueue<Event> eventList= new PriorityQueue<Event>();

        //list of times of each customer after checkout listed in order
        PriorityQueue<Double> totalTimeList= new PriorityQueue<Double>();

        //holds lanes
        ArrayList<Lane> laneList= new ArrayList<Lane>();

        //is used to calculate the average waiting time of the data set
        ArrayList<Double> times= new ArrayList<>();
        double largestTime=0;

        //initializing lanes
        for(int x=0;x<5;x++)
        {
        laneList.add(new NormalLane());
        }
        for(int x=0;x<2;x++)
        {
        laneList.add(new ExpressLane());
        }

        //calls on filereader method
        PriorityQueue<Customer> customers= readFile("medium.txt");

        //initializes the arrival and shopping events 
        int size= customers.size();
        for(int x=0;x<size;x++)
        {
            Customer c=customers.poll();
            eventList.offer(new ArrivalEvent(c));
            eventList.offer(new ShoppingEvent(c));
        }
        
        //used to write into log file
        PrintWriter pw= new PrintWriter("Results.txt");

        //goes though the event queue performing different actions based on each event
        while(!eventList.isEmpty())
        {
            Event e=eventList.poll();
            Event placer;
            if(e.getArrivalEvent())
            {
                pw.println(e);
            }else if(e.getShoppingEvent())
            {
                //initializes the checkout event
                pw.println(e);
                placer= new CheckoutEvent(e.getCustomer(), laneList);
                eventList.offer(placer);
            }else if(e.getCheckoutEvent())
            {
                //initializes the exit event 
                pw.println(e);
                int index=e.getLaneIndex();
                placer= new ExitEvent(e.getCustomer(), index);
                eventList.offer(placer);
            }else if(e.getExitEvent())
            {
                //ejects the customer from the lane, while also providing the customers statistics for calculation
                totalTimeList.offer(e.getCustomer().getWaitTime());
                times.add(e.getCustomer().getWaitTime());
                pw.println(e);
                e.customerExit(laneList, e.getExitIndex());
            }
        }
        double sum=0;

        //calculares for the largest waiting instance
        while(!totalTimeList.isEmpty())
        {
            largestTime=totalTimeList.poll();
        }

        //calculates for the average waiting time
        for(int x=0;x<times.size();x++)
        {
            sum+=times.get(x);
        }
        pw.println("The average waiting time was: "+ (Math.round((sum/times.size())*100)/100.0)+" The largest waiting time was: " +(Math.round(largestTime*100)/100.0));
        pw.close();

    }

    
}
