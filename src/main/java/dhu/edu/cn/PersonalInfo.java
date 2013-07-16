package dhu.edu.cn;

public class PersonalInfo {
	public PersonalInfo(){
		
	}
	
	public PersonalInfo(int age, String mobile, String address){
		this.age = age;
		this.mobile = mobile;
		this.address = address;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	// age of a person
	private int age;
	// mobile number of a person
	private String mobile;
	// address of a person
	private String address;
	
}
