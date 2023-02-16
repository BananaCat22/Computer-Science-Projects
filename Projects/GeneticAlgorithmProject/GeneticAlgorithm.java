import java.util.*;
import java.io.*;
import java.util.Collections;
/*
 * Chris Nodel
 * 9/18/2022
 * CS 1181 Project 1: Genetic Algorithm 
 * Instructor: Michelle Cheatham 
 * Lab Section: 06
 */

public class GeneticAlgorithm {


    private static Random rng= new Random();

    //readData method reads in file data of type string, double, int and parses them into variables to be read into an item arraylist
    public static ArrayList<Item> readData(String fileName) throws FileNotFoundException
    {
        Scanner fileReader= new Scanner(new File(fileName));
        ArrayList<Item> listedItems= new ArrayList<Item>();

        //seperates a line of a file into substrings to parse the double and int and then adds into an item arrayList
        while(fileReader.hasNextLine())
        {
            String item= fileReader.nextLine();
            String name= item.substring(0, item.indexOf(","));
            double weight= Double.parseDouble(item.substring(name.length()+2, item.indexOf(",", name.length()+2)));
            int value=Integer.parseInt(item.substring(item.indexOf(",",name.length()+2)+2));

            listedItems.add(new Item(name,weight, value));
        }
        fileReader.close();
        return listedItems;
    }

    //initializePopulation method takes in item arraylist and int 
    //creates an arraylist of chromosomes of size int 
    //and then using the item arraylist as the component for the chromosome constructor
    //returns chromosome list
    public static ArrayList<Chromosome> initializePopulation( ArrayList<Item> items, int populationSize)
    {
        ArrayList<Chromosome> ChromosomeList=new ArrayList<Chromosome>();
        for(int x=0;x<populationSize;x++)
        {
            ChromosomeList.add(new Chromosome(items));
        }
        return ChromosomeList;
    }
    
    //main method
    public static void main(String[] args) throws FileNotFoundException
    {
        //initializes initial population of chromosomes
        ArrayList<Item> items= readData("items.txt");
        ArrayList<Chromosome> chromosomes= initializePopulation(items, 10);

        //algorithm will be ran 20 instances, varies based on sample size
        for (int z=0;z<20;z++)
        {

            //initializes origin, origin will be used to determine
            //the original size
            //top x of chromosomes
            //as well as additional chromosome pairings for child instances
            int origin=chromosomes.size();

            //pairing will contain the set of pairs that will be used to pair
            //off parents for crossover
            int [] pairing= new int[chromosomes.size()];

            //this set of loops initializes pairing so that all instances in the array are unique
            //y references initialized variable in the array
            //if y=x at any point then the variable at x is reinitialized and loop repeats
            //this continues until the reference variables equal each other
            //this ensures instances will be unique
            for(int x=0;x<origin;x++)
            {
                pairing[x]=rng.nextInt(origin);
                while(true)
                {
                    int y=0;
                    for(y=0;y<x;y++)
                    {
                    if(pairing[x]==pairing[y])
                    {
                        pairing[x]=rng.nextInt(origin);
                        break;                    
                    } 
                    }
                    if(x!=y)
                    {
                        continue;
                    }
                    else
                    break;
                }
            }

            //using pairings given add new children equal to 50% of the paresnts population size using
            //crossover
            for(int x=0;x<origin;x+=2)
            {
                chromosomes.add((chromosomes.get(pairing[x]).crossover(chromosomes.get(pairing[x+1]))));
            }

            //goes through the arraylist has a 1/origin chance to mutate an instance
            for(Chromosome x:chromosomes)
            {
                if(rng.nextInt(origin)==1)
                {
                    x.mutate();
                }
            }
            
            //sorts chromosome arraylist by fitness
            Collections.sort(chromosomes);

            //while chromosomes is not equal to its origin size the instance at chromosome(0) is removed
            while(chromosomes.size()!=origin)
            chromosomes.remove(0);
        }
        //prints out most fit instance 
        System.out.println("The most optimal value of items is given by the following combination:\n"+chromosomes.get(chromosomes.size()-1));

    }
    
}
