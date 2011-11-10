package de.dhbw.stargreg.code;

/**
 * Unternehmen verkaufen auf dem Raumschiffmarkt Raumschiffe. Alle relevanten Informationen wie
 * Raumschifftyp, Preis etc. werden hier festgehalten
 * @author fredrik
 *
 */
public class Verkauf extends ProduktTransaktion<RaumschiffTyp> {
	/**
	 * Wert eines Raumschiffs gemessen an Bauteilpreisen zum Zeitpunkt des Verkaufs
	 **/
	private final double einzelWert;
	
	public Verkauf(RaumschiffTyp raumschiffTyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschiffTyp, unternehmen, menge, preis);
		einzelWert = raumschiffTyp.getKosten();
	}
	
	public double getGesamtWert() {
		return einzelWert * menge;
	}
}