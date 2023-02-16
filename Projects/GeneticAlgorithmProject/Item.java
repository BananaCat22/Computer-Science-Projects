public class Item{
    private final String name;
    private final double weight;
    private final int value;
    private boolean included;

    //item class constructor included is set to false by default
    public Item(String name, double weight, int value)
    {
        this.name=name;
        this.weight=weight;
        this.value=value;
        included=false;
    }
    
    //copy constructor for item, included field is set to copied items included field, 
    //important to note for crossover method in chromosome
    public Item(Item other)
    {
        this.name=other.name;
        this.weight=other.weight;
        this.value=other.value;
        included=other.included;
    }

    //getter methods
    public double getWeight()
    {
        return weight;
    }
    public int getValue()
    {
        return value;
    }
    public boolean isIncluded()
    {
        return included;
    }

    //setter method, only included can be changed as it is the only non final variable
    public void setIncluded(boolean included)
    {
        this.included=included;
    }

    //toString overriade formats the Item object as name (weight ,$value)
    public String toString()
    {
        return name+" ("+weight+"lbs, $"+value+")";
    }
}
