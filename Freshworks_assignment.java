package freshworks_assignment;
import data_operation.Data_Operation;                                           //package.classname
import java.io.*;
import java.util.*;
public class Freshworks_assignment {
   public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
    Data_Operation.initialize();                                            //Initially call the function. or else it will not get the previous data.
        System.out.println("Enter the option from the given below:::");
        for(;;){
            System.out.println("1.create  2.Read   3.Delete 4.exit");
            int option=scan.nextInt();
            switch(option){
                case 1:
                    Data_Operation.create();                                    //Calling the create method    
                    break; 
                case 2:
                    System.out.println("Enter the Key to search:");
                    String KeytoSearch=scan.next();
                    Data_Operation.read(KeytoSearch);                           //Calling the read method
                    break;
                case 3:
                    System.out.println("Enter the key to delete:");
                    String Key=scan.next();
                    Data_Operation.delete(Key);                                 //Calling the delete method
                    break;
                default:
                    System.exit(0);
            }       
        } 
    }   
}
