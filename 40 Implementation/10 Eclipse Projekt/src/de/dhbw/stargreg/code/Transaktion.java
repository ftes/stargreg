package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected Unternehmen unternehmen;
	protected int menge;
	protected double preis;
	protected Typ typ;
	
	
	public Transaktion(Typ typ, Unternehmen unternehmen, int menge, double preis) {
		this.unternehmen = unternehmen;
		this.menge = menge;
		this.preis = preis;
		this.typ = typ;
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
		return String.format("%d %s zu %f Credits", menge, typ, preis);
	}
	
	public Typ getTyp() {
		return typ;
	}
	
	public double getKosten() {
		return menge * preis;
	}
}
