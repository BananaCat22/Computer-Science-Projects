import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Passworder extends Thread {

/*
 * Variables will be defined as follows:
 * instances= number of characters in password set
 * fileName= name of duplicate zip file
 * passwordLetters= letter set to generate passwords for 
 * contentsFileName= name of duplicate contents directory
 * ifFound= if password has been found or not yet if yes will terminate all threads
 * originFile= the original zip folder
 */
private final int instances;
private final String fileName;
private String passwordLetters;
private final String contentsFileName;
private static volatile boolean isFound;
private final String originFile;
private ArrayList<String> passwordKey=new ArrayList<String>();

//This generates passwords and places them into the passwordKey ArrayList
public Passworder( int instances,String fileName, String contentsFileName, String passwordLetters, String originFile)
{
    this.instances=instances;
    this.fileName=fileName;
    this.contentsFileName=contentsFileName;
    this.passwordLetters=passwordLetters;
    this.originFile=originFile;
    isFound=true;
}

//This generates passwords and places them into the passwordKey ArrayList
public  ArrayList<String> passwordGen(int length, String alphabet)
{

    //base case is letterset is emptied out, this returns the key
    if(alphabet.isEmpty())
    {
        return passwordKey;
    }else
    {
        
        String start="";
        String end="";

        //generates first and last instances in the list
        for(int x=0;x<length;x++)
        {
            if(x==0)
            {
            start+=Character.toString(alphabet.charAt(0));
            end+=Character.toString(alphabet.charAt(0));
            }
            if(x>=1)
            {
                start+="a";
                end+="z";
            }
        }    

            //will close the gap between the beginning and ending of the list
            //with the permutations in between
            while(!(start.equals(end)))
            {
                int letterPointer=length-1;
                String replacements="";

                for(int x=0;x<25;x++)
                {
                    passwordKey.add(start);
                    start=start.substring(0,letterPointer)+Character.toString((start.charAt(letterPointer))+1);
                }

                passwordKey.add(start);

                for(int x=length-1;x>=1;x--)
                {
                    if(start.charAt(x)=='z')
                    {
                        letterPointer--;
                        replacements+="a";
                    }else
                    break;
                }

                if(letterPointer<length-1&&letterPointer>=1)
                {
                start=start.substring(0,letterPointer)+Character.toString(start.charAt(letterPointer)+1)+replacements;
                }
            }
        //recursive call reduces the letterset by 1 instance to start the next set
        return passwordGen(length, alphabet.substring(1));
    }
    
  
}

public void run()
{
    long startTime= System.currentTimeMillis();
    ArrayList<String> passwordKey=passwordGen(instances,passwordLetters);

    //will iterate until either a password has been found or the thread runs out of 
    //passwords
    while(!passwordKey.isEmpty())
    {
        try
        {
            if(isFound==false)
            {
            break;
            }

            //Attempts to extract file contents using password
            ZipFile zipFile= new ZipFile(fileName);
            zipFile.setPassword(passwordKey.get(passwordKey.size()-1));
            zipFile.extractAll(contentsFileName);

            //if successful the following occurs
            System.out.println("Successfully Cracked!");
            System.out.println("The password is: "+ passwordKey.get(passwordKey.size()-1));
            long endTime= System.currentTimeMillis();
            System.out.println("It took "+(endTime-startTime)+" milliseconds to find this password");

            //sets isFound to false to begin termination of other instances
            isFound=false;

            //extracts the contents of the original zip into the contents directort
            ZipFile zipFileOrigin=new ZipFile(originFile);
            zipFileOrigin.setPassword(passwordKey.get(passwordKey.size()-1));
            zipFileOrigin.extractAll("contents");

            break;
        }catch(ZipException ze)
        {
            //will terminate loop if the password has been found
            //other remove the instance at the end of the list
            if(isFound==false)
            {
            break;
            }
            passwordKey.remove(passwordKey.size()-1);    
        }  
        catch(Exception e)
        {
            e.printStackTrace();
        }  
    }
    try
        {
            //Deletes the duplicate files and their contents
            Files.delete(Path.of(contentsFileName+"\\message.txt"));
            Files.delete(Path.of(fileName));
            Files.delete(Path.of(contentsFileName));
            
        }catch(IOException e)
        {
        System.out.println("File could not be deleted");
        }
}
}

