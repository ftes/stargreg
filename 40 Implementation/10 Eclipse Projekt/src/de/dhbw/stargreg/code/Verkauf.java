package de.dhbw.stargreg.code;

public class Verkauf extends Transaktion {
	
	public Verkauf(Raumschifftyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public Raumschifftyp getRaumschifftyp() {
		return (Raumschifftyp) produkttyp;
	}
}