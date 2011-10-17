package de.dhbw.stargreg.code;

import java.util.Vector;


/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	private final Vector<Transaktion> transaktionen = new Vector<Transaktion>();
	
	public void fuegeTransaktionHinzu(Transaktion transaktion) {
		transaktionen.add(transaktion);
	}
	
	public void fuegeTransaktionenHinzu(Vector<? extends Transaktion> transaktionen) {
		this.transaktionen.addAll(transaktionen);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Vector<T> get(Class<T> clazz) {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion transaktion : this.transaktionen) {
			if (transaktion.getClass() == clazz) {
				transaktionen.add((T) transaktion);
			}
		}
		return transaktionen;
	}
	
	public Vector<Verkauf> getVerkaeufe() {
		return get(Verkauf.class);
	}
	
	public Vector<Angebot> getAngebote() {
		return get(Angebot.class);
	}
	
	public Vector<Einkauf> getEinkaeufe() {
		return get(Einkauf.class);
	}
	
	public Vector<Einstellung> getEinstellungen() {
		return get(Einstellung.class);
	}
	
	public Vector<Entlassung> getEntlassungen() {
		return get(Entlassung.class);
	}
	
	public Vector<Schulung> getSchulungen() {
		return get(Schulung.class);
	}
}
