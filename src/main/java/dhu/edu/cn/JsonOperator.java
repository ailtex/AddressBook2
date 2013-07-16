package dhu.edu.cn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * JsonOperator
 * <p> A base class of classes that operator the JSON file  
 * @author Hao Zhang
 *
 */
public class JsonOperator {
	public JsonOperator(){
		gson = new Gson();
	}
	/**
	 * Read JSON file to memory
	 * @param filePath the disk position of JSON file in String type
	 * @return the content of the JSON file in String type
	 */
	public String readJsonFile(String filePath){
		String jsonString = "";
		
		File file = new File(filePath);
		try{  
            BufferedReader input = new BufferedReader (new FileReader(file));  
            String text;
            while((text = input.readLine()) != null){  
               jsonString += text;
            }               
        }  
        catch(IOException e){  
            System.err.println("Read File Error!");  
        }  
		return jsonString;
	}
	/**
	 * Write the JSON file in memory to disk
	 * @param jsonString the content of the JSON file in String type
	 * @param filePath the disk position of JSON file in String type
	 */
	public void writeJsonFile(String jsonString, String filePath){
		try{
			File file = new File(filePath);
			FileWriter write = new FileWriter(file,false);
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(jsonString); 
			bufferedWriter.flush();
			write.flush();
			bufferedWriter.close();
			write.close();

        }  
        catch(IOException e){  
            System.err.println("Write File Error!");  
        }  
	}
	/**
	 * ls command to list the items in current position
	 * @param filePath the disk position of JSON file in String type
	 * @param mapType the map type of the JSON file
	 * @param directoryIndex the current position of directory in JSON file
	 * @param resultArray the ArrayList contains the files in this directory
	 */
	public void ls(String filePath, Type mapType, int directoryIndex,
			ArrayList resultArray){
		String jsonString = readJsonFile(filePath);
		Map jsonMap = jsonString2Map(jsonString, mapType);
		mapRecursive(directoryIndex,jsonMap,resultArray );
	}
	
	private void mapRecursive(int directoryIndex, Map jsonMap, ArrayList result){
		if(directoryIndex == 0){
			for(Object key : jsonMap.keySet()){
				result.add(key);
			}
			
		}else{
			for(Object key : jsonMap.keySet()){
				Map nextMap = (Map)jsonMap.get(key);
				mapRecursive(directoryIndex - 1, nextMap, result);
			}
		}
	}
	/**
	 * cd command to go to the entry like go to a directory
	 * @param directoryIndex the current position of directory in JSON file
	 * @param commands the commands content in String array
	 * @return -1 : error signal, execute fail
	 *         directoryIndex : new directory position
	 */
	public int cd(int directoryIndex, String[] commands) {
		if(commands.length == 2 && directoryIndex == 0 && commands[1].equals("entries")){
    		directoryIndex +=1;
    	}else if(commands.length == 2 && directoryIndex > 0 && commands[1].equals("..")){
			directoryIndex -= 1;
    	}else{
    		// error signal;
    		return -1;
    	}
		return directoryIndex;
		
	}
	/**
	 * cat command to display th item data
	 * @param filePath the disk position of JSON file in String type
	 * @param mapType the map type of the JSON file
	 * @param mapKey the key of the map of the JSON file
	 * @param nameKey the name of the item
	 * @return the content item data in string type, if no such item, return the error message
	 */
	public String cat(String filePath, Type mapType, String mapKey,
			String nameKey) {
		String result="";
		String jsonString = readJsonFile(filePath);
		Map jsonMap = jsonString2Map(jsonString, mapType);
		Map subMap = (Map)jsonMap.get(mapKey);
		
		if(subMap.containsKey(nameKey)){
			result = "\"" + nameKey + "\" : ";
			result += gson.toJson(subMap.get(nameKey));
		}else{    // no such item
			result = getErrorMessage();
		}
		return result;
	}
	/**
	 * add command to add new address entry to JSON
	 * @param filePath the disk position of JSON file in String type
	 * @param mapType the map type of the JSON file
	 * @param mapKey the key of the map of the JSON file
	 * @param name the name of the new address entry
	 * @param value the age, mobile, address information of the new address entry
	 * @param valueType the map type of the value information
	 * @return if trying to add duplicated key, return an error message, otherwise return "address entry added"
	 */
	public String add(String filePath, Type mapType, String mapKey,
			String name, String value, Type valueType) {
		String result="";
		String jsonString = readJsonFile(filePath);
		Map jsonMap = jsonString2Map(jsonString, mapType);
		Map subMap = (Map)jsonMap.get(mapKey);
		
		if(subMap.containsKey(name)){
			result = getErrorMessage();
		}else{
			PersonalInfo persInfo = gson.fromJson(value, valueType);
			subMap.put(name, persInfo);
			jsonString = gson.toJson(jsonMap);
			writeJsonFile(jsonString, filePath);
			result = "address entry added";
		}
		return result;
	}
	/**
	 * remove command to remove one or more address entries
	 * @param filePath the disk position of JSON file in String type
	 * @param mapType the map type of the JSON file
	 * @param mapKey the key of the map of the JSON file
	 * @param nameKey the name of the new address entry
	 * @return if no such item, return the error message, otherwise return the correct prompt
	 */
	public String remove(String filePath, Type mapType, String mapKey, String nameKey){
		String result="";
		String jsonString = readJsonFile(filePath);
		Map jsonMap = jsonString2Map(jsonString, mapType);
		Map subMap = (Map)jsonMap.get(mapKey);
		
		if(subMap.containsKey(nameKey)){
			subMap.remove(nameKey);
			jsonString = gson.toJson(jsonMap);
			writeJsonFile(jsonString, filePath);
			result = nameKey + " was deleted from JSON";
		}else{
			result = getErrorMessage();
		}
		return result;
	}
	/**
	 * Get the error message
	 * @return the error message in string type
	 */
	public String getErrorMessage(){
		return "Error!";
	}
	/**
	 * Get the help message
	 * @return the help message in string type
	 */
	public String getHelpMessage(){
		return (
				"AddressBook2 command list\n" + 
				"ls :      list the items in current position\n" +
				"cd :      go to a directory\n" +
				"cat :     display th item data\n"+
				"add :     add new address entry to JSON\n"+
				"remove :  remove one or more address entries\n"+
				"!help :   get help\n"+
				"!quit :   quit from the application\n");
	}
	
	private Map jsonString2Map(String jsonString, Type mapType){
		return gson.fromJson(jsonString, mapType);
	}
	
	// a json operator
	private  Gson gson;
	
}
