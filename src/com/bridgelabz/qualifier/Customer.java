package com.bridgelabz.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Customer {
  private  String customerName;
  @Autowired
  @Qualifier("productBean")
  private Product productBean;

public String getCustomerName() {
	return customerName+" "+" "+productBean.toString();
}

public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
  
}
