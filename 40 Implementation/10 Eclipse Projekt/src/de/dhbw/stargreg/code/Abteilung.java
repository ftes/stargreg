package de.dhbw.stargreg.code;

/**
 * Abstrakte Oberklasse für alle Abteilungen.
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
	
	/**
	 * Gibt Informationen zu dieser oder der letzten SpielRunde aus.
	 * @param aktuelleSpielRunde {@code true} wenn Informationen zu dieser SpielRunde erwünscht sind.
	 */
	public abstract void gebeInformationenAus(boolean aktuelleSpielRunde);

}
