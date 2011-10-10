package de.dhbw.stargreg.code;

/**
 * Oberklasse für alle Märkte, definiert die abstrakte Methode {@code kloneFuernaechsteRunde()}
 * @author fredrik
 *
 */
public abstract class Markt {
	/**
	 * Erstellt das Marktobjekt für die nächste Spielrunde, was bei einigen Märkten Berechnungen
	 * für neue Preise auslöst
	 * @return Rückgabe des neuen Marktes
	 */
	public abstract Markt kloneFuerNaechsteRunde();
}
