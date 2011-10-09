package de.dhbw.stargreg.code;

public class Verkauf extends Transaktion {
	private Raumschifftyp raumschifftyp;
	
	public Verkauf(Raumschifftyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(unternehmen, menge, preis);
		this.raumschifftyp = raumschifftyp;
	}
	
	public Raumschifftyp getRaumschifftyp() {
		return raumschifftyp;
	}
}