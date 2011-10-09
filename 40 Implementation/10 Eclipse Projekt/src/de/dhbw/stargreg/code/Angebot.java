package de.dhbw.stargreg.code;

public class Angebot extends Transaktion {
	private Raumschifftyp raumschifftyp;
	private double anteil;					//für spätere Berechnungen
	
	public Angebot(Raumschifftyp raumschifftyp, Unternehmen unternehmen, int menge, double preis) {
		super(unternehmen, menge, preis);
		this.raumschifftyp = raumschifftyp;
	}
	
	public Raumschifftyp getRaumschifftyp() {
		return raumschifftyp;
	}
	
	public Verkauf kloneVerkauf(int menge) {
		return new Verkauf(this.raumschifftyp, this.unternehmen, menge, this.preis);
	}

	public double getAnteil() {
		return anteil;
	}

	public void setAnteil(double anteil) {
		this.anteil = anteil;
	}
}
