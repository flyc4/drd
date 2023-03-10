package com.multi.drd.gym;

public class GymDTO {
	private int gymSEQ;
	private String name;
	private String address;
	private String telephoneNumber;
	
	
	public GymDTO() {
		super();
	}


	public GymDTO(String name, String address, String telephoneNumber) {
		super();
		this.name = name;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
	}



	public int getGymSEQ() {
		return gymSEQ;
	}


	public void setGymSEQ(int gymSEQ) {
		this.gymSEQ = gymSEQ;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}



	@Override
	public String toString() {
		return "GymDTO [gymSEQ=" + gymSEQ + ", name=" + name + ", address=" + address + ", telephoneNumber="
				+ telephoneNumber + "]";
	}
	
	
	
	

}
