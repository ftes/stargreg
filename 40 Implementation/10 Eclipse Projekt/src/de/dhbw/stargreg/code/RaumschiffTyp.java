package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Ein Raumschifftyp 
 * @author fredrik
 *
 */
public class RaumschiffTyp extends ProduktTyp {
	/**
	 * Mengen der Bauteile, die für die Produktion eines Raumschiffs diesen Typs benötigt werden
	 */
	private final HashMap<BauteilTyp, Integer> bauteile = new HashMap<BauteilTyp, Integer>();
	
	private final int benoetigtesPersonal;
	
	private int nachfrage;

	public RaumschiffTyp(String name, int benoetigtesPersonal) {
		super(name, 0);
		this.benoetigtesPersonal = benoetigtesPersonal;
	}
	
	/**
	 * Fügt ein Bauteil mit der zugehörigen Menge an die BauteilListe an
	 * Erhöht auch die benötigten LagerplatzEinheiten um den entsprechenden Wert
	 * @param bauteilTyp BauteilTyp
	 * @param menge Menge
	 */
	public void fuegeBauteilHinzu(BauteilTyp bauteilTyp, int menge) {
		bauteile.put(bauteilTyp, menge);
		System.out.printf("%d %s als Bauteil zu %s hinzugefügt\n", menge, bauteilTyp, this);
	}
	
	/**
	 * Berechnet die variablen Kosten für den Raumschifftyp, basierend auf den derzeitigen Marktpreisen der Bauteile
	 * @return variable Kosten für diesen Raumschifftyp
	 */
	public double getKosten() {
		double kosten = 0;
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			// Menge * Preis
			kosten += bauteile.get(bauteilTyp) * bauteilTyp.getPreis();
		}
		return kosten;
	}
	
	public int getBenoetigtesPersonal() {
		return benoetigtesPersonal;
	}
	
	public void setNachfrage(int nachfrage) {
		this.nachfrage = nachfrage;
	}
	
	public int getNachfrage() {
		return nachfrage;
	}
	
	public int getLagerplatzEinheiten(){
		int lagerplatzEinheiten = 0;
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			// Lagerplatzeinheiten * Menge
			lagerplatzEinheiten += bauteilTyp.getLagerplatzEinheiten() * bauteile.get(bauteilTyp);
		}
		return lagerplatzEinheiten;
	}
}
