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
	private final SpielWelt spielWelt;
	/**
	 * Achtung: zu multiplizieren mit der Anzahl der Unternehmen!
	 */
	private final HashMap<RaumschiffTyp, Integer> nachfrage;
	private final double personalKonjunkturFaktor;
	private final String nachricht;
	private final int nummer;
	
	public String getNachricht() {
		return nachricht;
	}
	
	public SpielRunde(
			SpielWelt spielWelt,
			HashMap<RaumschiffTyp, Integer> nachfrage,
			double personalKonjunkturFaktor,
			String nachricht,
			int nummer) {
		this.spielWelt = spielWelt;
		this.nachfrage = nachfrage;
		this.personalKonjunkturFaktor = personalKonjunkturFaktor;
		this.nachricht = nachricht;
		this.nummer = nummer;
	}
	
	public void starteSpielRunde() {
		Util.printHeading(String.format("Spielrunde %d einrichten", nummer));
		System.out.println("Nachfrage auf Raumschiffmarkt");
		TableBuilder tb = new TableBuilder("RaumschiffTyp", "Nachfrage");
		for (RaumschiffTyp raumschiffTyp : nachfrage.keySet()) {
			tb.addNewRow(raumschiffTyp,
					nachfrage.get(raumschiffTyp) * spielWelt.getAnzahlUnternehmen());
			raumschiffTyp.setNachfrage(nachfrage.get(raumschiffTyp) * spielWelt.getAnzahlUnternehmen());
		}
		tb.print();
		System.out.println("Personalkosten");
		tb = new TableBuilder("PersonalTyp", "Laufende Kosten", "Werbungskosten");
		for (PersonalTyp personalTyp : spielWelt.getPersonalMarkt().getTypen()) {
			personalTyp.setKonjunkturFaktor(personalKonjunkturFaktor);
			tb.addNewRow(personalTyp,
					String.format("%.2f", personalTyp.getLaufendeKosten()),
					String.format("%.2f", personalTyp.getWerbungsKosten()));
		}
		tb.print();
		Util.pause();
	}
	
	private final Vector<Transaktion> transaktionen = new Vector<Transaktion>();
	
	public void fuegeTransaktionHinzu(Transaktion transaktion) {
		transaktionen.add(transaktion);
	}
	
	public void fuegeTransaktionenHinzu(Vector<? extends Transaktion> transaktionen) {
		this.transaktionen.addAll(transaktionen);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Vector<T> getTransaktionen(Class<T> clazz) {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion transaktion : this.transaktionen) {
			if (transaktion.getClass() == clazz) {
				transaktionen.add((T) transaktion);
			}
		}
		return transaktionen;
	}
	
	public <T extends TypTransaktion<? extends Typ>> double getSummeTypTransaktionen(Class<T> clazz, Unternehmen unternehmen) {
		return Util.summiereTypTransaktionen(getTransaktionen(clazz, unternehmen));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Transaktion> Vector<T> getTransaktionen(Class<T> clazz, Unternehmen unternehmen) {
		Vector<T> transaktionen = new Vector<T>();
		for (Transaktion transaktion : this.transaktionen) {
			if (transaktion.getClass() == clazz && transaktion.getUnternehmen() == unternehmen) {
				transaktionen.add((T) transaktion);
			}
		}
		return transaktionen;
	}
	
	public Zahlung getZahlung(Zahlung.Art art, Unternehmen unternehmen) {
		for (Zahlung zahlung : getTransaktionen(Zahlung.class, unternehmen)) {
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
