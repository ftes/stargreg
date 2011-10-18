package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Ein Raumschifftyp 
 * @author fredrik
 *
 */
public class RaumschiffTyp extends ProduktTyp {
	/**
	 * Mengen der Bauteile, die f�r die Produktion eines Raumschiffs diesen Typs ben�tigt werden
	 */
	private final HashMap<BauteilTyp, Integer> bauteile = new HashMap<BauteilTyp, Integer>();
	
	/**
	 * Gibt das ben�tigte Personal f�r diesen Raumschifftyp zur�ck.
	 */
	private final int benoetigtesPersonal;
	
	private int nachfrage;

	public RaumschiffTyp(String name, int benoetigtesPersonal) {
		super(name, 0);
		this.benoetigtesPersonal = benoetigtesPersonal;
	}
	
	/**
	 * F�gt ein Bauteil mit der zugeh�rigen Menge an die BauteilListe an
	 * Erh�ht auch die ben�tigten LagerplatzEinheiten um den entsprechenden Wert
	 * @param bauteilTyp BauteilTyp
	 * @param menge Menge
	 */
	public void fuegeBauteilHinzu(BauteilTyp bauteilTyp, int menge) {
		bauteile.put(bauteilTyp, menge);
		System.out.printf("%d %s als Bauteil zu %s hinzugef�gt\n", menge, bauteilTyp, this);
	}
	
	/**
	 * Berechnet die variablen Kosten f�r den Raumschifftyp, basierend auf den derzeitigen Marktpreisen der Bauteile
	 * @return variable Kosten f�r diesen Raumschifftyp
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
	
	public HashMap<BauteilTyp, Integer> getBauteile() {
		return bauteile;
	}
}
