package dhu.edu.cn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * AddressBook2Main.java 
 * <p> AddressBook2Main.java is a main class to call the AddressBook2 to browse and modify a JSON data structure
 * @author Hao Zhang
 * @version 1.0
 * @lastupdate 2013-7-15
 * 
 */
public class AddressBook2Main 
{
    public static void main( String[] args )
    {
        try{
		      // Create a single thread to execute the program
		      ExecutorService executorService = Executors.newSingleThreadExecutor();
		      executorService.execute(new AddressBook2());
		      executorService.shutdown();
		 }
		catch(Exception e){
			 System.out.println(e.toString());
		}
    }
}

