package de.dhbw.stargreg.code;

public abstract class Typ {
	protected String name;
	
	public Typ(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
