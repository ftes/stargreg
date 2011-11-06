package de.dhbw.stargreg.code;

/**
 * Unternehmen tätigen auf dem Bauteilmarkt Einkäufe.
 * @author fredrik
 *
 */
public class Einkauf extends ProduktTransaktion<BauteilTyp> {
	
	public Einkauf(BauteilTyp bauteilTyp, Unternehmen unternehmen, int menge, double preis) {
		super(bauteilTyp, unternehmen, menge, preis);
	}
}