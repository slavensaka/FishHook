package com.fish.oprema;

public class Name {

	int id;
	String name;
	String created_at;
	String oprema;
	public   Name(){
		
	}
	
	public Name(String name,String oprema){
		this.name = name;
		this.oprema = oprema;
	}
	public Name(int id, String name,String oprema){
		this.id = id;
		this.name =name;
		this.oprema = oprema;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}
	
	public String getCreatedAt(){
		return this.created_at;
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	 public void setOprema(String oprema){
		 this.oprema = oprema;
	 }
	
	 
	 public String getOprema(){
		 return this.oprema;
	 }
}
