package de.dhbw.stargreg.code;

/**
 * Oberklasse f�r alle M�rkte, definiert die abstrakte Methode {@code kloneFuernaechsteRunde()}
 * @author fredrik
 *
 */
public abstract class Markt {
	/**
	 * Erstellt das Marktobjekt f�r die n�chste Spielrunde, was bei einigen M�rkten Berechnungen
	 * f�r neue Preise ausl�st
	 * @return R�ckgabe des neuen Marktes
	 */
	public abstract Markt kloneFuerNaechsteRunde();
}
