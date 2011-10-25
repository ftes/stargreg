package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;


/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	private final Spiel spiel;
	/**
	 * Achtung: zu multiplizieren mit der Anzahl der Unternehmen!
	 */
	private final HashMap<RaumschiffTyp, Integer> nachfrage;
	private final HashMap<PersonalTyp, Double> laufendeKosten;
	private final HashMap<PersonalTyp, Double> werbungsKosten;
	private final String nachricht;
	private final int nummer;
	
	public SpielRunde(
			Spiel spiel,
			HashMap<RaumschiffTyp, Integer> nachfrage,
			HashMap<PersonalTyp, Double> laufendeKosten,
			HashMap<PersonalTyp, Double> werbungsKosten,
			String nachricht,
			int nummer) {
		this.spiel = spiel;
		this.nachfrage = nachfrage;
		this.laufendeKosten = laufendeKosten;
		this.werbungsKosten = werbungsKosten;
		this.nachricht = nachricht;
		this.nummer = nummer;
	}
	
	public void starteSpielRunde() {
		for (RaumschiffTyp raumschiffTyp : nachfrage.keySet()) {
			raumschiffTyp.setNachfrage(nachfrage.get(raumschiffTyp) * spiel.getAnzahlUnternehmen());
		}
		for (PersonalTyp personalTyp : laufendeKosten.keySet()) {
			personalTyp.setLaufendeKosten(laufendeKosten.get(personalTyp));
			personalTyp.setWerbungsKosten(werbungsKosten.get(personalTyp));
		}
		System.out.printf("Spielrunde %d gestartet\n", nummer);
		System.out.println(nachricht);
	}
	
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
