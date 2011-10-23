package de.dhbw.stargreg.code;

/**
 * Abstrakte Oberklasse für alle Abteilungen
 * @author fredrik
 *
 */
public abstract class Abteilung {
	protected Unternehmen unternehmen;
	
	public Abteilung(Unternehmen unternehmen) {
		this.unternehmen = unternehmen;
	}
	
	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

}
