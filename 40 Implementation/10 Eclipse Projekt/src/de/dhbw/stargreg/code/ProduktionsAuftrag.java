package de.dhbw.stargreg.code;

public class ProduktionsAuftrag extends Transaktion<RaumschiffTyp> {

	public ProduktionsAuftrag(RaumschiffTyp raumschiffTyp, Unternehmen unternehmen, int menge) {
		super(raumschiffTyp, unternehmen, menge, 0);
	}

}
