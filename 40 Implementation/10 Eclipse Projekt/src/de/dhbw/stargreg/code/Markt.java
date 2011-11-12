package de.dhbw.stargreg.code;

import java.util.Vector;


/**
 * Oberklasse für alle Märkte.
 * @author fredrik
 *
 */
public abstract class Markt<T extends Transaktion> {

	
	/**
	 * Verwaltung der Transaktionen dieser Runde dieses Marktes.
	 */
	protected final Vector<T> transaktionen = new Vector<T>();
	
	/**
	 * Fügt die TypTransaktion zur Liste der Transaktionen hinzu.
	 * @param transaktion Anzufügende TypTransaktion.
	 */
	public void fuegeTransaktionHinzu(T transaktion) {
		transaktionen.add(transaktion);
	}
	
	public Vector<T> getTransaktionen() {
		return transaktionen;
	}
	
	public Vector<T> getTransaktionen(Unternehmen unternehmen) {
		Vector<T> vector = new Vector<T>();
		for (T transaktion : transaktionen) {
			if (transaktion.getUnternehmen() == unternehmen) {
				vector.add(transaktion);
			}
		}
		return vector;
	}
	
	public void loescheTransaktionen() {
		transaktionen.clear();
	}
	
	/**
	 * Führt alle nötigen Simulationen auf dem Markt durch.
	 */
	public abstract void simuliere();
}
