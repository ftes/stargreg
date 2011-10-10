package de.dhbw.stargreg.code;

public class Einkauf extends Transaktion {
	
	public Einkauf(BauteilTyp bauteiltyp, Unternehmen unternehmen, int menge, double preis) {
		super(bauteiltyp, unternehmen, menge, preis);
	}
	
	public BauteilTyp getBauteiltyp() {
		return (BauteilTyp) produkttyp;
	}
}