package de.dhbw.stargreg.code;

public class Angebot extends Transaktion {
	private double anteil;					//für spätere Berechnungen
	
	public Angebot(RaumschiffTyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public RaumschiffTyp getRaumschifftyp() {
		return (RaumschiffTyp) produkttyp;
	}
	
	public Verkauf kloneVerkauf(int menge) {
		return new Verkauf(getRaumschifftyp(), this.unternehmen, menge, this.preis);
	}

	public double getAnteil() {
		return anteil;
	}

	public void setAnteil(double anteil) {
		this.anteil = anteil;
	}
}
