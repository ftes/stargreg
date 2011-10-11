package de.dhbw.stargreg.code;

/**
 * Unternehmen verkaufen auf dem Raumschiffmarkt Raumschiffe. Alle relevanten Informationen wie
 * Raumschifftyp, Preis etc. werden hier festgehalten
 * @author fredrik
 *
 */
public class Verkauf extends Transaktion {
	
	public Verkauf(RaumschiffTyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public RaumschiffTyp getRaumschifftyp() {
		return (RaumschiffTyp) typ;
	}
}