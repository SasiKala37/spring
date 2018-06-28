package com.bridgelabz.qualifier;

public class Product {
	private int id;
	private String name;
	private double price;
	public Product() {
	}
	
	public Product(int id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	public String  toString() {
		return "product id:"+id+" "+" product name:"+name+" product price:"+price;
	}
}
