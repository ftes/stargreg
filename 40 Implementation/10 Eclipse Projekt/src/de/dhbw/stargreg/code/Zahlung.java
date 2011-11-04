package de.dhbw.stargreg.code;

public class Zahlung {
	public static enum Art {ZINSEN, PERSONAL, LAGER, FEHLER};
	private Art art;
	private double betrag;
	private Unternehmen unternehmen;
	
	public Zahlung(double betrag, Art art, Unternehmen unternehmen) {
		this.betrag = betrag;
		this.art = art;
		this.unternehmen = unternehmen;
	}

	public Art getArt() {
		return art;
	}

	public double getBetrag() {
		return betrag;
	}
	
	public Unternehmen getUnternehmen() {
		return unternehmen;
	}
}
