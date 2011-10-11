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
	private HashMap<BauteilTyp, Integer> bauteile = new HashMap<BauteilTyp, Integer>();

	public RaumschiffTyp(String name) {
		super(name, 0);
	}
	
	/**
	 * F�gt ein Bauteil mit der zugeh�rigen Menge an die BauteilListe an
	 * Erh�ht auch die ben�tigten LagerplatzEinheiten um den entsprechenden Wert
	 * @param bauteilTyp BauteilTyp
	 * @param menge Menge
	 */
	public void fuegeBauteilHinzu(BauteilTyp bauteilTyp, int menge) {
		bauteile.put(bauteilTyp, menge);
		lagerplatzEinheiten += bauteilTyp.getLagerplatzEinheiten() * menge;
		System.out.printf("%d %s als Bauteil zu %s hinzugef�gt\n", menge, bauteilTyp, this);
	}
	
	/**
	 * Berechnet die variablen Kosten f�r den Raumschifftyp, basierend auf den derzeitigen Marktpreisen der Bauteile
	 * @return
	 */
	public double getKosten() {
		double kosten = 0;
		for (BauteilTyp bauteilTyp : bauteile.keySet()) {
			// Menge * Preis
			kosten += bauteile.get(bauteilTyp) * Spiel.getSpiel().getAktuelleSpielRunde().getBauteilMarkt().getPreis(bauteilTyp);
		}
		return kosten;
	}
}
