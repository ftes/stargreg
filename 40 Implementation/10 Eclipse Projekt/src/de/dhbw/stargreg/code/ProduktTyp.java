package de.dhbw.stargreg.code;

public abstract class ProduktTyp {
	protected String name;
	protected int lagerplatzeinheiten;
	
	public ProduktTyp(String name_c, int lagerplatzeinheiten_c){
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
