package dhu.edu.cn;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Unit test for AddressBook2 App.
 */
public class AddressBook2Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AddressBook2Test( String testName )
    {
        super( testName );
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AddressBook2Test.class );
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
	private AddressBook2 addressBook2 = new AddressBook2();
	private JsonOperator jsonOperator = new JsonOperator();
	
	/**
	 * help command testcase
	 * should output the  help message
	 */
	
	public void testOperatorHELP() {
		String command = "    !help   ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
        assertEquals(jsonOperator.getHelpMessage(), addressBook2.testResult);
	}
	
	/**
	 * LS command testcase1
	 * show the entries directory
	 */
	
	public void testOperatorLS1() {
		String command = "    ls   ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
        assertEquals("entries", addressBook2.testResult);
	}
	
	/**
	 * LS command testcase2
	 * show the items in the sub directory
	 */
	
	public void testOperatorLS2() {
		String command1 = "  cd     entries  ";
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		
		String command = "    ls   ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
        assertEquals("lilei hanmeimei", addressBook2.testResult);
	}
	
	/**
	 *  CD command testcase1
	 *  no parameter, should get error message
	 */
	
	public void testOperatorCD1(){
		String command = "    cd   ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * CD command testcase2
	 * correct input, return 1 which means that it has been to the sub directory
	 */
	
	public void testOperatorCD2(){
		String command = "  cd     entries  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
		assertEquals("1", addressBook2.testResult);
	}
	
	/**
	 * CD command testcase3
	 * no such sub-directory, get error message
	 */
	
	public void testOperatorCD3(){
		String command = "  cd     lili  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		addressBook2.commmandDistribute(commands);
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * CAT command testcase1
	 * should first go into the entries
	 * cat the exist item, get item information
	 */
	
	public void testOperatorCAT1(){
		String command1 = "  cd     entries  ";
		String command2 = "  cat    lilei  ";
		// format the command string
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		String[] commands2 = command2.trim().split("\\s+");
		addressBook2.commmandDistribute(commands2);
		
		String actualResult ="\"lilei\" : {\"age\":27,\"mobile\":\"13700000000\",\"address\":\"Earth somewhere\"}";
		assertEquals(actualResult, addressBook2.testResult);
	}
	/**
	 * CAT command testcase2
	 * should first go into the entries
	 * cat the non-exist, get error message
	 */
	
	public void testOperatorCAT2(){
		String command1 = "  cd     entries  ";
		String command2 = "  cat    lala  ";
		// format the command string
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		String[] commands2 = command2.trim().split("\\s+");
		addressBook2.commmandDistribute(commands2);
		
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * CAT command testcase3
	 * should first go into the entries
	 * CAT multiple item, get error message
	 */

	public void testOperatorCAT3(){
		String command1 = "  cd     entries  ";
		String command2 = "  cat    lilei hanmeimei  ";
		// format the command string
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		String[] commands2 = command2.trim().split("\\s+");
		addressBook2.commmandDistribute(commands2);
		
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * ADD command testcase1
	 * not in the sub directory, get error message
	 * @throws Exception
	 */
	
	public void testOperatorADD1() throws Exception{
		String command = "  add  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		String name = "lilei";
		String value = "{ \"age\": 28, \"mobile\" : \"13700090002\", \"address\" : \"Earth somewhere too\" }";
		Method m = addressBook2 .getClass().getDeclaredMethod("operatorADD",new Class[]{String[].class, String.class, String.class});
		m.setAccessible(true);
		m.invoke(addressBook2 ,new Object[] {commands, name, value });
		m.setAccessible(false);
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * ADD command testcase1
	 * add duplicated key, get error message
	 * @throws Exception
	 */
	
	public void testOperatorADD2() throws Exception{
		String command1 = "  cd     entries  ";
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		
		String command = "  add  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		String name = "lilei";
		String value = "{ \"age\": 28, \"mobile\" : \"13700090002\", \"address\" : \"Earth somewhere too\" }";
		Method m = addressBook2 .getClass().getDeclaredMethod("operatorADD",new Class[]{String[].class, String.class, String.class});
		m.setAccessible(true);
		m.invoke(addressBook2 ,new Object[] {commands, name, value });
		m.setAccessible(false);
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
	
	/**
	 * Add command testcase2
	 * add the new item, get the correct prompt
	 * @throws Exception
	 */
	
	public void testOperatorADD3() throws Exception{
		// should first enter into entries directory
		String command1 = "  cd     entries  ";
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		
		String command = "  add  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		String name = "tata";
		String value = "{ \"age\": 28, \"mobile\" : \"13700090002\", \"address\" : \"Earth somewhere too\" }";
		Method m = addressBook2 .getClass().getDeclaredMethod("operatorADD",new Class[]{String[].class, String.class, String.class});
		m.setAccessible(true);
		m.invoke(addressBook2 ,new Object[] {commands, name, value });
		m.setAccessible(false);
		assertEquals("address entry added", addressBook2.testResult);
	}
	
	/**
	 * Remove command testcase1
	 * remove the exist item, get correct prompt
	 * @throws Exception
	 */
	
	public void testOperatorREMOVE1() throws Exception{
		// should first enter into entries directory
		String command1 = "  cd     entries  ";
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		
		String command = "  remove  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		String name = "tata";
		
		Method m = addressBook2 .getClass().getDeclaredMethod("operatorREMOVE",new Class[]{String[].class, String.class});
		m.setAccessible(true);
		m.invoke(addressBook2 ,new Object[] {commands, name});
		m.setAccessible(false);
		assertEquals("tata was deleted from JSON", addressBook2.testResult);
	}
	
	/**
	 * Remove command testcase2
	 * remove an non-exist key, get error message
	 * @throws Exception
	 */
	
	public void testOperatorREMOVE2() throws Exception{
		// should first enter into entries directory
		String command1 = "  cd     entries  ";
		String[] commands1 = command1.trim().split("\\s+");
		addressBook2.commmandDistribute(commands1);
		
		String command = "  remove  ";
		// format the command string
		String[] commands = command.trim().split("\\s+");
		String name = "lileiJieJie";
		
		Method m = addressBook2 .getClass().getDeclaredMethod("operatorREMOVE",new Class[]{String[].class, String.class});
		m.setAccessible(true);
		m.invoke(addressBook2 ,new Object[] {commands, name});
		m.setAccessible(false);
		assertEquals(jsonOperator.getErrorMessage(), addressBook2.testResult);
	}
}
