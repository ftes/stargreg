package de.dhbw.stargreg.code;

public abstract class Typ {
	protected final String name;
	
	public Typ(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
