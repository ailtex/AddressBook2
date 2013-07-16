package dhu.edu.cn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;

/**
 * AddressBook2.java
 * <p>AddressBook2.java function to browse and modify a JSON data structure with ls,cd,cat, add, remove
 * @author Hao Zhang
 * @version 1.0
 * @lastupdate 2013-7-15
 */

public class AddressBook2 implements Runnable{
// Interrupt signal
		volatile boolean stop = false;
		// command starting words in String type 
		public final static String commandWelcome	= "AddressBook2> ";
		// file path of the json file
		public final static String filePath = "addressbook.json";
		// for Junit test, should test result
		public static String testResult;
		
		public AddressBook2(){
			//0 is in the first entries directory
			//1 is in the second directory
			directoryIndex = 0;
			jsonOperator = new JsonOperator();
			mapType = new TypeToken<Map<String, Map<String,PersonalInfo> >>(){}.getType();
			
			testResult = "";
		}
		
		@Override
		public void run() {
			while(!stop){
				// read the command
				System.out.print(commandWelcome);
				Scanner in = new Scanner(System.in);
				String command = in.nextLine();
				
				// format the command string
				String[] commands = command.trim().split("\\s+");
				
				commmandDistribute(commands);
			}
		}
		/**
		 * distribute the commands
		 * @param commands commands read from the console
		 */
		public void commmandDistribute(String[] commands){
			if(commands[0].equals("ls")){
				operatorLS(commands);
            }else if(commands[0].equals("cd")){
				operatorCD(commands);
			}else if(commands[0].equals("cat")){
				operatorCAT(commands);
			}else if(commands[0].equals("add")){
				// read key
				System.out.print("Key: ");
				Scanner in = new Scanner(System.in);
				String name = in.nextLine();
				// read value
				System.out.print("Value: ");
				String value = in.nextLine();
				
				operatorADD(commands, name, value);
			}else if(commands[0].equals("remove")){
				// read the key
				System.out.print("please give the key: ");
				Scanner in = new Scanner(System.in);
				String name = in.nextLine();
				
				operatorREMOVE(commands, name);
			}else if(commands[0].equals("!help")){
				operatorHELP();
			}else if(commands[0].equals("!quit")){
				stop = true;
			}else{
				operatorHELP();
			}
		}
		
		
		private void operatorHELP() {
			resultOutput(jsonOperator.getHelpMessage());
			
			// Used for Junit test
			testResult = jsonOperator.getHelpMessage();
		}

		private void operatorREMOVE(String[] commands, String name) {
			if(directoryIndex == 1){	// should be in the entries directory
				String result = jsonOperator.remove(filePath, mapType, "entries", name);
				resultOutput(result);
				testResult = result;
			}else{
				resultOutput(jsonOperator.getErrorMessage());
				testResult = jsonOperator.getErrorMessage();
			}
		}

		private void operatorADD(String[] commands, String name, String value) {
			if(directoryIndex == 1){
				Type valueType = new TypeToken<PersonalInfo>(){}.getType();
				String result = jsonOperator.add(filePath, mapType, "entries", name, value, valueType);
				System.out.println(result);
				// Used for Junit test
				testResult = result;
			}else{
				resultOutput(jsonOperator.getErrorMessage());
				// Used for Junit test
				testResult = jsonOperator.getErrorMessage();
			}
		}

		private void operatorCAT(String[] commands) {
			// should be in the entries directory and one parameter
			if(commands.length == 2 && directoryIndex == 1 ){
				String name = commands[1];
				String result = jsonOperator.cat(filePath, mapType, "entries", name);
				
				resultOutput(result);
				// Used for Junit test
				testResult = result;
			}else{
				resultOutput(jsonOperator.getErrorMessage());
				// Used for Junit test
				testResult = jsonOperator.getErrorMessage();
			}
			
		}

		private void operatorCD(String[] commands) {
			int result = jsonOperator.cd(directoryIndex, commands);
			if(result == -1){    // -1 means CD command execute fail
				resultOutput(jsonOperator.getErrorMessage());
				// Used for Junit test
				testResult = jsonOperator.getErrorMessage();
			}else{
				directoryIndex = result;
				// Used for Junit test
				testResult = String.valueOf(directoryIndex);
			}
			
		}

		private void operatorLS(String[] commands) {
			if(commands.length > 1){    // no parameters after LS command 
				resultOutput(jsonOperator.getErrorMessage());
				testResult = jsonOperator.getErrorMessage();
			}else{
				ArrayList resultArray = new ArrayList();
				jsonOperator.ls(filePath, mapType, directoryIndex, resultArray);
			
				resultOutput(resultArray);
				
				// for Junit test
				testResult="";
				Iterator it = resultArray.iterator();
				testResult = (String)it.next();
				while(it.hasNext()){
					testResult+=" "+it.next();
				}
			}
			
		}

		private void resultOutput(ArrayList result) {
			System.out.print(commandWelcome);
			Iterator it = result.iterator();
			System.out.print(it.next());
			while(it.hasNext()){
				System.out.print(" "+it.next());
			}
			System.out.println();
		}
		
		private void resultOutput(String result){
			System.out.print(commandWelcome);
			System.out.println(result);
		}
		
		// json file in string type
		private String jsonString;
		// the current position of directory in JSON file
		private int directoryIndex;
		// a class of JSON file operator
		private JsonOperator jsonOperator;
		// the map type of the JSON file
		private Type mapType;
}
