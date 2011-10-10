package de.dhbw.stargreg.code;

/**
 * Abstrakte Klasse f�r Produkttypen, die den Namen und die ben�tigten Lagerplatzeinheiten speichert
 * @author fredrik
 *
 */
public abstract class ProduktTyp {
	protected String name;
	protected int lagerplatzEinheiten;
	
	public ProduktTyp(String name, int lagerplatzEinheiten){
		this.name = name;
		this.lagerplatzEinheiten = lagerplatzEinheiten;
	}
	
	public String toString(){
		return this.name;
	}
	
	public int getLagerplatzEinheiten(){
		return this.lagerplatzEinheiten;
	}
}
