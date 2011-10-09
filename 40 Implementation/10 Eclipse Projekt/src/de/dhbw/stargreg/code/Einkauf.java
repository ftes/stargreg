package de.dhbw.stargreg.code;

public class Einkauf extends Transaktion {
	private Bauteiltyp bauteiltyp;
	
	public Einkauf(Bauteiltyp bauteiltyp, Unternehmen unternehmen, int menge, double preis) {
		super(unternehmen, menge, preis);
		this.bauteiltyp = bauteiltyp;
	}
	
	public Bauteiltyp getBauteiltyp() {
		return bauteiltyp;
	}
}