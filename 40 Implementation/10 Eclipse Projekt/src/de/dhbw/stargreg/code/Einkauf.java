package de.dhbw.stargreg.code;

public class Einkauf extends Transaktion {
	
	public Einkauf(Bauteiltyp bauteiltyp, Unternehmen unternehmen, int menge, double preis) {
		super(bauteiltyp, unternehmen, menge, preis);
	}
	
	public Bauteiltyp getBauteiltyp() {
		return (Bauteiltyp) produkttyp;
	}
}