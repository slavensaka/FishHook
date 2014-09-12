package com.fish.oprema;

public class Oprema {

	int id;
	String oprema;
	public Oprema() {
		
	}
	public Oprema(String oprema){
		this.oprema = oprema;
	}
	
	public Oprema(int id,String oprema){
		this.id = id;
		this.oprema = oprema;
	}
	 public void setId(int id){
		 this.id = id;	 
	 }
	 
	 public void setOprema(String oprema){
		 this.oprema = oprema;
	 }
	
	 public int getId(){
		 return this.id;
	 }
	 public String getOprema(){
		 return this.oprema;
	 }
}
