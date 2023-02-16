import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;

//Time taken with 4 thread in milliseconds: 6230821
//Time taken with 3 thread in milliseconds: 882999
public class Driver {
    public static void main(String[] args) throws IOException
    {
        final int letterNum=26;
        final int numThreads=13;
        char alphabet='a';

        //Defines the number of letters minimum per thread
        int cutLength=letterNum/numThreads;

        //Defines the remaining number of letters that remain after getting the cuts
        //To be added later on
        int excess=letterNum-(cutLength*numThreads);

        //Templates for the duplicate .zip and contents directories
        String copyFile="copy";
        String contentsCopy="contents";

        //Contains the list of threads
        ArrayList<Passworder> passwordThreads= new ArrayList<Passworder>();


        for(int x=0;x<numThreads;x++)
        {
            int instanceMaker=cutLength;
            String letterSet="";
            
            //adds an additional letter in the instance for every 1 in the excess variable
            //Only occurs once per instance
            if(excess>0)
            {
                instanceMaker++;
                excess--;
            }

            //Creates the letter string for password generation 
            for(int y=0;y<instanceMaker;y++)
            {
                letterSet=letterSet+Character.toString(alphabet);
                alphabet+=1;
            } 

            //Creates the duplicate files for use by each thread
            String copy=copyFile+Integer.toString(x)+".zip";
            Files.copy(Path.of("protected5.zip"), Path.of(copy));
            String contentCopy=contentsCopy+Integer.toString(x);

            //Adds threads to the list
            passwordThreads.add(new Passworder(5,copy,contentCopy,letterSet,"protected5.zip"));   
                 
        }
      for(Passworder x: passwordThreads)
      {
        x.start();
      }   
    }
    
}
