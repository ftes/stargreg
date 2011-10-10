package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected Unternehmen unternehmen;
	protected int menge;
	protected double preis;
	protected ProduktTyp produkttyp;
	
	
	public Transaktion(ProduktTyp produkttyp, Unternehmen unternehmen, int menge, double preis) {
		this.unternehmen = unternehmen;
		this.menge = menge;
		this.preis = preis;
		this.produkttyp = produkttyp;
	}
	
	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public int getMenge() {
		return menge;
	}

	public double getPreis() {
		return preis;
	}
	
	public String toString() {
		return String.format("%d Stück zu %f Credits", menge, preis);
	}
	
	public ProduktTyp getProdukttyp() {
		return produkttyp;
	}
}
