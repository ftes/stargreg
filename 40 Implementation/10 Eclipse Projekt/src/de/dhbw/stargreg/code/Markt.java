package de.dhbw.stargreg.code;

import java.util.Vector;


/**
 * Oberklasse f�r alle M�rkte.
 * @author fredrik
 *
 */
public abstract class Markt<T extends Typ, A extends Transaktion> {
	
	protected final Vector<T> typen = new Vector<T>();
	
	protected final Vector<A> transaktionen = new Vector<A>();
	
	public void fuegeTypHinzu (T typ) {
		typen.add(typ);
	}
	
	public Vector<T> getTypen() {
		return typen;
	}
	
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
	 * F�hrt alle n�tigen Simulationen auf dem Markt durch und liefert zur weiteren Verarbeitung
	 * den {@code Vector} mit den Transaktionen zur�ck.
	 * @return Transaktionen
	 */
	public Vector<A> simuliere() {
		Vector<A> transaktionen = new Vector<A>();
		transaktionen.addAll(this.transaktionen);
		loescheTransaktionen();
		return transaktionen;
	}
}