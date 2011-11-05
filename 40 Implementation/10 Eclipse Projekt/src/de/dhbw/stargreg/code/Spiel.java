package de.dhbw.stargreg.code;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.TableBuilder;
import de.dhbw.stargreg.util.Util;


/**
 * Verwaltet den gesamten Spielablauf, darunter alle Unternehmen.
 * @author fredrik
 * 
 */
public class Spiel {
	//INSTANCE;
	
	/**
	 * Liste aller Unternehmen
	 */
	private final Vector<Unternehmen> unternehmen = new Vector<Unternehmen>();
	
	/**
	 * Liste aller Spielrunden
	 */
	private final Vector<SpielRunde> spielRunden = new Vector<SpielRunde>();
	
	/**
	 * aktuelle Spielrunde
	 */
	private SpielRunde aktuelleSpielRunde;
	
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	private KapitalMarkt kapitalMarkt = new KapitalMarkt();
	
	/**
	 * aktueller Spielstatus
	 */
	private Status status = Status.EINRICHTEN;
	
	/**
	 * Spiel kann sich in drei Zuständen befinden: Einrichten, Spielen oder Auswerten.
	 * Dies wird durch diese Enumeration dargestellt.
	 * @author fredrik
	 *
	 */
	private enum Status { EINRICHTEN, SPIELEN, AUSWERTEN }
	
	/**
	 * Fügt die übergebene Spielrunde hinten an die Liste der Spielrunden an.
	 * Nur möglich in der Phase Spielen.
	 * @param spielRunde Die anzufügende Spielrunde
	 */
	public void fuegeSpielRundeHinzu(
			HashMap<RaumschiffTyp, Integer> nachfrage, 
			double personalKonjunkturFaktor,
			String nachricht) {
		if (status != Status.EINRICHTEN) {
			System.err.println("Spielrunde nicht hinzugefügt: nur in der Phase 'Spielen' möglich");
			return;
		}
		this.spielRunden.add(new SpielRunde(this, nachfrage, personalKonjunkturFaktor, nachricht, spielRunden.size() + 1));
//		System.out.printf("Spielrunde %d hinzugefügt\n", this.spielRunden.size());
	}
	
	public int getAnzahlUnternehmen() {
		return unternehmen.size();
	}
	
	/**
	 * Fügt das übergebene Unternehmen hinten an die Liste der Unternehmen an.
	 * Nur möglich, wenn das Spiel sich in der Einrichtungsphase befindet.
	 * @param unternehmen Das anzufügende Unternehmen
	 */
	public Unternehmen fuegeUnternehmenHinzu(String name, double startKapital) {
		if (status != Status.EINRICHTEN) {
			System.err.println("Unternehmen nicht hinzugefügt: nur in der Phase 'Einrichten' möglich");
			return null;
		}
		Unternehmen unternehmen = new Unternehmen(this, name, startKapital);
		this.unternehmen.add(unternehmen);
//		System.out.printf("Unternehmen %s hinzugefügt\n", unternehmen);
		return unternehmen;
	}
	
	/**
	 * Startet das Spiel, nur möglich in Einrichtungs-Phase.
	 */
	public void starteSpiel() {
		if (status != Status.EINRICHTEN) {
			System.err.println("Spiel nicht gestartet: wurde bereits gestartet");
			return;
		}
		if (unternehmen.size() < 2) {
			System.err.println("Spiel nicht gestartet: weniger als zwei Unternehmen registriert");
			return;
		}
		status = Status.SPIELEN;
		aktuelleSpielRunde = spielRunden.firstElement();
		Util.printHeading("Spiel gestartet");
		aktuelleSpielRunde.starteSpielRunde();
	}
	
	/**
	 * Beendet das Spiel, nur möglich wenn in Spielen-Phase.
	 */
	public void beendeSpiel() {
		if (status != Status.SPIELEN) {
			System.err.println("Spiel nicht beendet: wurde noch nicht gestartet oder schon beendet");
			return;
		}
		status = Status.AUSWERTEN;
		Util.printHeading("Spiel beendet");
	}
	
	/**
	 * Bewertet die Unternehmen
	 */
	public void bewerteUnternehmen() {
		if (status != Status.AUSWERTEN) {
			System.err.println("Keine Bewertung möglich, da Spiel noch nicht beendet wurde");
			return;
		}
		
		Util.printHeading("Bewertung der Unternehmen");
		Vector<Unternehmen> rangfolge = ermittleRangfolge();
		System.out.println("Ergebnis");
		TableBuilder tb = new TableBuilder("Rang", "Unternehmen", "Punkte");
		for (int i=0; i<rangfolge.size(); i++) {
			tb.addNewRow(i + 1 + ".",
					rangfolge.elementAt(i),
					String.format("%.0f", rangfolge.elementAt(i).getBewertung()));
		}
		tb.print();
	}

	/**
	 * Sobald alle Benutzer ihre Eingaben getätigt haben, kann die Spielrunde simuliert werden,
	 * dies setzt die aktuelle Spielrunde um eins weiter.
	 * Nur möglich im Status Spielen
	 */
	public void simuliere() {
		if (status != Status.SPIELEN) {
			System.out.println("Die nächste Runde kann nur im Modus Spielen erreicht werden");
			return;
		}
		
		// Prüfen, ob alle schon eingecheckt haben. Sonst abbrechen
		for (Unternehmen unternehmen : this.unternehmen) {
			if (! unternehmen.getRundeEingecheckt()) {
				System.err.printf("%s hat die Runde noch nicht eingecheckt\n", unternehmen);
				return;
			}
		}
		
		Util.printHeading("Simulation der Spielrunde");
		
		// Verkäufe nach Unternehmen gruppieren
		HashMap<Unternehmen, Vector<Verkauf>> verkaeufe = Util.gruppiereVerkaeufeNachUnternehmen(
				raumschiffMarkt.berechneGesamtAbsatz());
		
		for (Unternehmen unternehmen : this.unternehmen) {
			unternehmen.simuliere(verkaeufe.get(unternehmen));
		}
		
		aktuelleSpielRunde.fuegeTransaktionenHinzu(bauteilMarkt.simuliere());
		aktuelleSpielRunde.fuegeTransaktionenHinzu(personalMarkt.simuliere());
		aktuelleSpielRunde.fuegeTransaktionenHinzu(raumschiffMarkt.getAngebote());
		aktuelleSpielRunde.fuegeTransaktionenHinzu(raumschiffMarkt.simuliere());
		
		aktuelleSpielRunde = getNaechsteSpielRunde();
		if (aktuelleSpielRunde == null) {
			beendeSpiel();
		} else {
			aktuelleSpielRunde.starteSpielRunde();
		}
		// simulieren
	}

	public SpielRunde getAktuelleSpielRunde() {
		return aktuelleSpielRunde;
	}
	
	public RaumschiffMarkt getRaumschiffMarkt() {
		return raumschiffMarkt;
	}
	
	public BauteilMarkt getBauteilMarkt() {
		return bauteilMarkt;
	}
	
	public PersonalMarkt getPersonalMarkt() {
		return personalMarkt;
	}
	
	public KapitalMarkt getKapitalMarkt() {
		return kapitalMarkt;
	}
	
	private SpielRunde getNaechsteSpielRunde() {
		int naechste = spielRunden.indexOf(aktuelleSpielRunde) + 1;
		if (naechste == spielRunden.size()) return null;
		return spielRunden.elementAt(naechste);
	}
	
	public SpielRunde getVorherigeSpielRunde() {
		int vorherige = spielRunden.indexOf(aktuelleSpielRunde) - 1;
		if (vorherige == -1) return null;
		return spielRunden.elementAt(vorherige);
	}
	
	public RaumschiffTyp getStarDerLetztenRunde() {
		int vorherige = spielRunden.indexOf(aktuelleSpielRunde) - 1;
		if (vorherige == -1) return null;
		return spielRunden.elementAt(vorherige).getStar();
	}
	
	/**
	 * Ermittelt die Rangfolge der Unternehmen in der Endbewertung.
	 * Der ROI (Gewinn / Investition) wird zu 70% gewichtet, der
	 * Marktanteil gemessen am Absatzwert des Unternehmens wird zu 30%
	 * gewichtet.
	 * Zum Gewinn zählt dabei im vollen Umfang dsa Kapital, zu 67%
	 * fertige Raumschiffe im Lager und zu 33% Bauteile im Lager.
	 * @return
	 */
	private Vector<Unternehmen> ermittleRangfolge() {
		//Achtung: Problem bei negativen ROI -> wenn summeROI negativ ist, dann wird bei Division ein negativer ROI gut
		//Lösung: rechts-verschiebung bei Vorhandensein eines negativen ROI
		double minROI = Double.MAX_VALUE;
		for (Unternehmen unternehmen : this.unternehmen) {
			if (unternehmen.getROI() < minROI) minROI = unternehmen.getROI();
		}
		
		double rechtsVerschiebung = 0;
		if (minROI < 0) {
			rechtsVerschiebung = -minROI;
		}
		
		double summeROI = 0;
		double summeAbsatzWert = 0;
		for (Unternehmen unternehmen : this.unternehmen) {
			summeROI += unternehmen.getROI() + rechtsVerschiebung;
			summeAbsatzWert += unternehmen.getAbsatzWert();
		}
		
		System.out.println("Punkteverteilung");
		TableBuilder tb = new TableBuilder("Unternehmen", "ROI", "Marktanteil", "Bewertung");
		for (Unternehmen unternehmen : this.unternehmen) {
			double punkte = (unternehmen.getROI() + rechtsVerschiebung) / summeROI * 70;
			punkte += unternehmen.getAbsatzWert() / summeAbsatzWert * 30;
			unternehmen.setBewertung(punkte);
			tb.addNewRow(unternehmen, 
					String.format("%.1f", unternehmen.getROI() * 100) + " %",
					String.format("%.1f", unternehmen.getAbsatzWert() / summeAbsatzWert * 100) + " %",
					String.format("%.0f", punkte));
		}
		tb.print();
		
		// Bewertung sortieren um Rangfolge zu erhalten
		@SuppressWarnings("unchecked")
		Vector<Unternehmen> rangfolge = (Vector<Unternehmen>) unternehmen.clone();
		Collections.sort(rangfolge, new Comparator<Unternehmen>() {
			public int compare(Unternehmen u1, Unternehmen u2) {
				return ((Double) u2.getBewertung()).compareTo(u1.getBewertung());
			}
		});
		return rangfolge;
	}
	
	public Vector<SpielRunde> getSpielRunden() {
		return spielRunden;
	}
}
