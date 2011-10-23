package de.dhbw.stargreg.code;

public class ProduktionsAuftrag extends Transaktion {

	public ProduktionsAuftrag(RaumschiffTyp raumschiffTyp, Unternehmen unternehmen, int menge) {
		super(raumschiffTyp, unternehmen, menge, 0);
	}
	
	public RaumschiffTyp getRaumschiffTyp() {
		return (RaumschiffTyp) typ;
	}

}
