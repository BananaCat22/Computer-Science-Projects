import java.util.*;

//Chromosome class extemds ArrayList<Item> meaning that Chromosome can itself be treated as an ArrayList
//for Item objects
public  class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{

    //random class random numbers generator
    private static Random rng= new Random();

    //empty default constructor, called during crossover method
    public Chromosome()
    {

    }

    //Chromosome constructor that takes in Item ArrayList, has a 50/50 chance to set included 
    //of instantiated item to true or false
    public Chromosome(ArrayList<Item> items)
    {
  
        for(Item x:items)
        {
            add(new Item(x));
        }
        for(int x=0;x<size();x++)
        {
            int randomizer=rng.nextInt(10);
            if(randomizer<5)
            {
                get(x).setIncluded(true);
            }else if(randomizer>=5)
            {
            get(x).setIncluded(false);
            }
        }
    }

    //crossover item looks through the lists of both the current chromosome and another chromosome
    //50/50 chance to implement either parent's element to the returned child Chromosome
    //uses the Item copy constructor as well as default chromosome constructor
    public Chromosome crossover(Chromosome other)
    {
        Chromosome child= new Chromosome();
        for(int x=0;x<this.size();x++)
        {
            int cross= rng.nextInt(10);
            if(cross<5)
            {
                child.add(new Item(this.get(x)));
            }else if(cross>=5)
            {
            child.add(new Item(other.get(x)));
            }
           
        }
        return child;
    }

    //mutate function will have a 1 in 10 chance to setInclude to the opposite parity 
    //a random instance in the list
    public void mutate()
    {
        for(int x=0;x<size();x++)
        {
            if(rng.nextInt(10)==1)
            {
                if(get(x).isIncluded())
                {
                    get(x).setIncluded(false);
                }else
                get(x).setIncluded(true);
            }
        }
    }

    //returns the Fitness of the chromosome, if the weight exceeds 10
    //the fitness default to 0, the total value is given otherwise
    public int getFitness()
    {
        double totalWeight=0;
        int totalValue=0;
        for(Item x: this)
        {
            if(x.isIncluded())
            {
            totalWeight+=x.getWeight();
            totalValue+=x.getValue();
            }
        }
        if(totalWeight>10)
        {
        return 0;
        }
        else
        return totalValue;
        
    }

    // override for the comporable compareTo method, compares the chromosomes by fitness
    public int compareTo(Chromosome other)
    {
        if(this.getFitness()>other.getFitness())
        {
        return 1;
        }
        else if(this.getFitness()<other.getFitness())
        {
        return -1;
        }
        else 
        return 0;
    }

    //override of the toString method, the item list is printed out along with the total value at the end
    public String toString()
    {
        String output="";
        for(Item x: this)
        {
            if(x.isIncluded())
            {
            output+=x+"\n";
            }
        }
        output+="total value: $"+getFitness();
        return output;
    }

}
