package de.dhbw.stargreg.code;

/**
 * Unternehmen tätigen auf dem Bauteilmarkt Einkäufe. Alle relevanten Informationen wie
 * Bauteiltyp, Preis etc. werden hier festgehalten
 * @author fredrik
 *
 */
public class Einkauf extends Transaktion {
	
	public Einkauf(BauteilTyp bauteiltyp, Unternehmen unternehmen, int menge, double preis) {
		super(bauteiltyp, unternehmen, menge, preis);
	}
	
	public BauteilTyp getBauteiltyp() {
		return (BauteilTyp) typ;
	}
}