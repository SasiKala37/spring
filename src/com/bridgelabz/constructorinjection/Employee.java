package com.bridgelabz.constructorinjection;

public class Employee {
private int id;
private String name;
private String designation;
private Address address;
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}


public Employee(int id, String name, Address address) {
	
	this.id = id;
	this.name = name;
	this.address = address;
}
@Deprecated
public void run() {
	
}
public void show(){  
    System.out.println(id+" "+name);  
    System.out.println(address.toString());  
}  
}
