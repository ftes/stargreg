package de.dhbw.stargreg.code;

public abstract class Transaktion<T extends Typ> {
	protected final Unternehmen unternehmen;
	protected final int menge;
	protected final double preis;
	protected final T typ;
	
	
	public Transaktion(T typ, Unternehmen unternehmen, int menge, double preis) {
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
	
	public T getTyp() {
		return typ;
	}
	
	public double getKosten() {
		return menge * preis;
	}
}
