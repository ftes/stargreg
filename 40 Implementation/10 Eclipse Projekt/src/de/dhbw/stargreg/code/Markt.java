package de.dhbw.stargreg.code;

import java.util.Vector;


/**
 * Oberklasse für alle Märkte.
 * @author fredrik
 *
 */
public abstract class Markt<T extends Typ, A extends Transaktion> {
	/**
	 * Verwaltung der Typen dieses Marktes.
	 */
	protected final Vector<T> typen = new Vector<T>();
	
	/**
	 * Verwaltung der Transaktionen dieser Runde dieses Marktes.
	 */
	protected final Vector<A> transaktionen = new Vector<A>();
	
	/**
	 * Fügt den Typ zur Liste der Typen hinzu.
	 * @param typ Anzufügender Typ.
	 */
	public void fuegeTypHinzu (T typ) {
		typen.add(typ);
	}
	
	public Vector<T> getTypen() {
		return typen;
	}
	
	/**
	 * Fügt die Transaktion zur Liste der Transaktionen hinzu.
	 * @param transaktion Anzufügende Transaktion.
	 */
	public void fuegeTransaktionHinzu(A transaktion) {
		transaktionen.add(transaktion);
	}
	
	public Vector<A> getTransaktionen() {
		return transaktionen;
	}
	
	private void loescheTransaktionen() {
		transaktionen.clear();
	}
	
	/**
	 * Führt alle nötigen Simulationen auf dem Markt durch und liefert zur weiteren Verarbeitung
	 * den {@code Vector} mit den Transaktionen zurück.
	 * @return Transaktionen
	 */
	public Vector<A> simuliere() {
		//Transaktionen vielleicht nicht wie Instanzattribut nennen, da dies verwirrend ist
		Vector<A> transaktionen = new Vector<A>(); 
		transaktionen.addAll(this.transaktionen);
		loescheTransaktionen();
		return transaktionen;
	}
}