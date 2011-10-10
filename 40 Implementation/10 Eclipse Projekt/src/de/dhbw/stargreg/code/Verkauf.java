package de.dhbw.stargreg.code;

public class Verkauf extends Transaktion {
	
	public Verkauf(RaumschiffTyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public RaumschiffTyp getRaumschifftyp() {
		return (RaumschiffTyp) produkttyp;
	}
}