package com.bridgelabz.customizedannotation;
@Phone(os="Android",version=5.1)
public class Redmi {
	 String model;
	    int size;
	    /**
	     * @param model
	     * @param size
	     */
	  public Redmi() {
		  
	  }
	    public Redmi(String model, int size) {
	        
	        this.model = model;
	        this.size = size;
	    }
	   @Phone(os="Kitkat")
	    public void show() {
	    	
	    }
}
