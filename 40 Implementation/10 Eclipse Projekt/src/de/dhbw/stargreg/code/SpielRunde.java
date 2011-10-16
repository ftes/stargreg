package de.dhbw.stargreg.code;

import java.util.Vector;


/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	private Vector<Transaktion> transaktionen = new Vector<Transaktion>();
	
	public void fuegeTransaktionHinzu(Transaktion transaktion) {
		transaktionen.add(transaktion);
	}
	
	public void fuegeTransaktionenHinzu(Vector<Transaktion> transaktionen) {
		this.transaktionen.addAll(transaktionen);
	}
	
	public <T extends Transaktion> Vector<T> getTransaktionen() {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion transaktion : this.transaktionen) {
			if (transaktion instanceof ) {
				transaktionen.add((T) transaktion);
			}
		}
	}
}
