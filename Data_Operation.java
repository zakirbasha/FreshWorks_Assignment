package data_operation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser; 
public class Data_Operation {
    static Map<String,JSONObject> datastore=new LinkedHashMap<>();      //It's map data structure to store data temporarily.
    static File file=new File("output.txt");                            //Creating a text file in project location as output.txt 
    static int flag=1;
    static final long fileSize=(1024*1024*1024);                        //To set a file size of 1GB
    static public void initialize(){                                    //After importing the jar file use run method to collect its previous data.
        BufferedReader br = null;
        try{
            if(file.exists()){                                          //To check whether the file exits or not.
               if(file.length()>0&&file.length()<=fileSize){            //To check the file size and if greater than 1GB it does not do create function
                    br = new BufferedReader( new FileReader(file) );
                    String line = null;
                    while ( (line = br.readLine()) != null ){
                    String[] parts = line.split("=>");                  //While reading the file it is used to split file using "=>" the left side will be the key as string and right side will be the value as JSONobject.
                    String name = parts[0].trim();                      //It's the key part
                    if( !name.equals("") && !parts[1].equals("") ){
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(parts[1].trim()); //It's json part, parser used to convert the string to json format, where the value written in the file will be considered as String to convert into json it will be used.
                    datastore.put(name,json);                                    //It will store the data in map, it is used to read and delete using the key which is easy to manipulate.
                        }
                    }
                    br.close();
                }
               else if(file.length()==0){                                          //To check wheather the file has data or not.
                   System.out.println("File is empty");
               }
               else{                                                               //if the file is greater than 1GB it does not do create function.                 
                   System.out.println("File should be less than 1 GB");
                  flag=0;                                                           //file is greater than 1Gb it will make the flag as 0
               }
               
            }
        else{
            file.createNewFile();                                                   //if the file does not exist here,create a new file.
            System.out.println("New File is created");
            }
     }
     catch(Exception ex){
            ex.printStackTrace();
      }
    }
        
    static public void create(){                                                    //Create Method.
            if(flag==1){                                                            //If the flag is 0, it means the file size is greater than 1Gb. 
                Scanner scan=new Scanner(System.in);
                System.out.println("Enter the key");
                String key=scan.next();                                             //getting the Key.
                if(key.length()<=32){                                               //checking the Key length
                    if(datastore.containsKey(key)==false){                          //Checking whether the key is present or not to give unique key.
                    System.out.println("Enter the value as json");
                    int num=1;
                    JSONObject obj = new JSONObject();                               //creating a JSONobject
                    while(num==1){
                        System.out.print("Enter the json key:");                        
                        String jkey=scan.next();                                    // Getting a key and Value for JSONobject
                        System.out.println("Enter the json value:");
                        String jvalue=scan.next();
                        obj.put(jkey,jvalue);                                       //entering data into JSONobject
                        String objsize=obj.toString();
                        if(objsize.getBytes().length<=8*16*1024){                   //To check the JSONobject is less than 16Kb or not
                            System.out.println("Do you want to continue press 1 to discontinue press 0");
                            num=scan.nextInt();                                     //If it is less than 16Kb getting a suggestion for next json key value
                        }
                        else{
                          System.out.println("value exceed the size \n want to continue press 1 or to discontinue press 0");
                         num=scan.nextInt();                                        //if size exceeds getting a suggestion
                        }
                    }
                    datastore.put(key, obj);                                       //Put into map
                    try{       
                    String value=obj.toString();
                    if((file.length()+value.getBytes().length)<=fileSize){          //Checking exceeds the size or not
                        FileWriter fw=new FileWriter(file,true);                    //usage of true does not overwrite the file.
                        fw.write(key+"=>"+obj);                                     //Writing the data to file separated by =>.
                        fw.append("\n");                                    
                        fw.close();
                        System.out.println("Data created successfully");
                    }
                    else{
                        System.out.println("file exceeds the size");
                    }
                    }
                    catch(Exception ex){
                         ex.printStackTrace();
                    }
                   }
                  else{
                System.out.println("Key Alreasy exist");
            }
            }
            else{
                System.out.println("Key size is greater do you want to continue press 1 or end press 0");
                int decision=scan.nextInt();
                if(decision==1)
                    create();
                else 
                    System.out.println("You terminated the create function");
            }
        }
        else{
                System.out.println("you cannot create because the size exceeds the limit");
        }
    }
    static public void delete(String Key){              //delete method
        if(datastore.containsKey(Key)==true){            //whether the key is present or not
                datastore.remove(Key);
                System.out.println("data deleted successfully");
                try{
                    FileWriter f=new FileWriter(file);
                    for(Map.Entry data:datastore.entrySet()){
                    f.write(data.getKey()+"=>"+data.getValue());        //After deleting overwrite file as new.
                    f.append("\n");
                    }
                    f.close();
                }
                catch(Exception ex){
                   ex.printStackTrace();
                }
        }
        else{
            System.out.println("Key does not exist");
        }
    }
    static public void read(String Key){
        if(datastore.containsKey(Key)==true)
                        System.out.println(datastore.get(Key));         //To read the value map is efficiently used.
                    else
                        System.out.println("Key does not exist");
    }
    
}

    
    

