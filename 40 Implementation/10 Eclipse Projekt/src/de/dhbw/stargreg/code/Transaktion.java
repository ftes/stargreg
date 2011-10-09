package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected Unternehmen unternehmen;
	protected int menge;
	protected double preis;
	
	
	public Transaktion(Unternehmen unternehmen, int menge, double preis) {
		this.unternehmen = unternehmen;
		this.menge = menge;
		this.preis = preis;
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
}
