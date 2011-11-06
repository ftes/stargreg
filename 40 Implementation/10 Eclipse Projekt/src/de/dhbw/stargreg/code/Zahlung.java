package de.dhbw.stargreg.code;

public class Zahlung extends Transaktion {
	public static enum Art {ZINSEN, PERSONAL, LAGER, FEHLER};
	private final Art art;
	
	public Zahlung(double betrag, Art art, Unternehmen unternehmen) {
		super(unternehmen, betrag);
		this.art = art;
	}

	public Art getArt() {
		return art;
	}

	public double getBetrag() {
		return preis;
	}
}
