package de.dhbw.stargreg.code;

/**
 * Abstrakte Klasse f�r Produkttypen, die den Namen und die ben�tigten Lagerplatzeinheiten speichert
 * @author fredrik
 *
 */
public abstract class ProduktTyp extends Typ {
	protected int lagerplatzEinheiten;
	
	public ProduktTyp(String name, int lagerplatzEinheiten){
		super(name);
		this.lagerplatzEinheiten = lagerplatzEinheiten;
	}
	
	public int getLagerplatzEinheiten(){
		return this.lagerplatzEinheiten;
	}
}