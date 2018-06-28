package com.bridgelabz.autowired;

public class Customer {
	//@Autowired
	private Product productBean;
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

	public Product getProductBean() {
		return productBean;
	}

	public void setProductBean(Product productBean) {
		this.productBean = productBean;
	}

	public void show() {
		System.out.println("customer name:" + customerName + " " + productBean.toString());
	}

}
