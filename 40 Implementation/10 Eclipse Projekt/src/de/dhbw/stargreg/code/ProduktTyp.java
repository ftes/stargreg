package de.dhbw.stargreg.code;

/**
 * Abstrakte Klasse für Produkttypen, die den Namen und die benötigten Lagerplatzeinheiten speichert
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