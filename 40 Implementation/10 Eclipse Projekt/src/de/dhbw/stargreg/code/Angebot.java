package de.dhbw.stargreg.code;

public class Angebot extends Transaktion {
	private double anteil;					//für spätere Berechnungen
	
	public Angebot(Raumschifftyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(raumschifftyp, unternehmen, menge, preis);
	}
	
	public Raumschifftyp getRaumschifftyp() {
		return (Raumschifftyp) produkttyp;
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
