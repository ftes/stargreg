package de.dhbw.stargreg.code;

public abstract class Transaktion {
	protected final Unternehmen unternehmen;
	protected final double einzelBetrag;
	
	public Transaktion(Unternehmen unternehmen, double preis) {
		this.unternehmen = unternehmen;
		this.einzelBetrag = preis;
	}
	
	public Unternehmen getUnternehmen() {
		return unternehmen;
	}
	
	public double getEinzelBetrag() {
		return einzelBetrag;
	}
	
	public String toString() {
		return String.format("%s: %.2f", unternehmen, einzelBetrag);
	}

}
