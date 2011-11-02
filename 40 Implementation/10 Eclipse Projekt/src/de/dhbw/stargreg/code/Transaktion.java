package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected final Unternehmen unternehmen;
	protected final int menge;
	protected final double preis;
	protected final Typ typ;
	
	
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
		return String.format("%d %s zu %.2f Credits", menge, typ, preis);
	}
	
	public Typ getTyp() {
		return typ;
	}
	
	public double getKosten() {
		return menge * preis;
	}
}
