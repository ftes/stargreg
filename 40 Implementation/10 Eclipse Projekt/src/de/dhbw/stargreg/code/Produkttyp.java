package de.dhbw.stargreg.code;

public abstract class Produkttyp {
	protected String name;
	protected int lagerplatzeinheiten;
	
	public Produkttyp(String name_c, int lagerplatzeinheiten_c){
		this.name = name_c;
		this.lagerplatzeinheiten = lagerplatzeinheiten_c;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getLagerplatzeinheiten(){
		return this.lagerplatzeinheiten;
	}
	
	
	

}
