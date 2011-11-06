package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected final Unternehmen unternehmen;
	protected final double preis;
	
	public Transaktion(Unternehmen unternehmen, double preis) {
		this.unternehmen = unternehmen;
		this.preis = preis;
	}
	
	public Unternehmen getUnternehmen() {
		return unternehmen;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public String toString() {
		return String.format("%s: %.2f", unternehmen, preis);
	}

}
