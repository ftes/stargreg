package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.TableBuilder;
import de.dhbw.stargreg.util.Util;


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
	
	public String getNachricht() {
		return nachricht;
	}
	
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
		Util.printHeading(String.format("Spielrunde %d einrichten", nummer));
		System.out.println("Nachfrage auf Raumschiffmarkt");
		TableBuilder tb = new TableBuilder("RaumschiffTyp", "Nachfrage");
		for (RaumschiffTyp raumschiffTyp : nachfrage.keySet()) {
			tb.addNewRow(raumschiffTyp,
					nachfrage.get(raumschiffTyp));
			raumschiffTyp.setNachfrage(nachfrage.get(raumschiffTyp) * spiel.getAnzahlUnternehmen());
		}
		tb.print();
		System.out.println("Personalkosten");
		tb = new TableBuilder("PersonalTyp", "Laufende Kosten", "Werbungskosten");
		for (PersonalTyp personalTyp : laufendeKosten.keySet()) {
			tb.addNewRow(personalTyp,
					String.format("%.2f", laufendeKosten.get(personalTyp)),
					String.format("%.2f", werbungsKosten.get(personalTyp)));
			personalTyp.setLaufendeKosten(laufendeKosten.get(personalTyp));
			personalTyp.setWerbungsKosten(werbungsKosten.get(personalTyp));
		}
		tb.print();
	}
	
	private final Vector<Transaktion<?>> transaktionen = new Vector<Transaktion<?>>();
	private final Vector<Zahlung> zahlungen = new Vector<Zahlung>();
	
	public void fuegeTransaktionHinzu(Transaktion<?> transaktion) {
		transaktionen.add(transaktion);
	}
	
	public void fuegeTransaktionenHinzu(Vector<? extends Transaktion<?>> transaktionen) {
		this.transaktionen.addAll(transaktionen);
	}
	
	public void fuegeZahlungHinzu(Zahlung zahlung) {
		zahlungen.add(zahlung);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Vector<T> getTransaktionen(Class<T> clazz) {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion<?> transaktion : this.transaktionen) {
			if (transaktion.getClass() == clazz) {
				transaktionen.add((T) transaktion);
			}
		}
		return transaktionen;
	}
	
	public <T extends Transaktion<?>> double getSummeTransaktionen(Class<T> clazz, Unternehmen unternehmen) {
		return Util.summiereTransaktionen(getTransaktionen(clazz, unternehmen));
	}
	
	@SuppressWarnings("unchecked")
	public <T> Vector<T> getTransaktionen(Class<T> clazz, Unternehmen unternehmen) {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion<?> transaktion : this.transaktionen) {
			if (transaktion.getClass() == clazz && transaktion.getUnternehmen() == unternehmen) {
				transaktionen.add((T) transaktion);
			}
		}
		return transaktionen;
	}
	
	public Zahlung getZahlung(Zahlung.Art art, Unternehmen unternehmen) {
		for (Zahlung zahlung : zahlungen) {
			if (zahlung.getArt() == art && zahlung.getUnternehmen() == unternehmen) return zahlung;
		}
		return null;
	}
	
	/**
	 * Bestimmt den Star der RaumschiffTypen dieser Runde anhand des erzielten
	 * Umsatzes über die Verkäufe aller Unternehmen.
	 * @return Star
	 */
	public RaumschiffTyp getStar() {
		HashMap<RaumschiffTyp, Double> umsaetze = Util.gruppiereUndSummiereVerkaeufeNachRaumschiffTyp(getTransaktionen(Verkauf.class));
		Vector<RaumschiffTyp> rangfolge = Util.sortiere(umsaetze, false);
		
		if (rangfolge.isEmpty()) return null;
		return rangfolge.firstElement();
	}
}
