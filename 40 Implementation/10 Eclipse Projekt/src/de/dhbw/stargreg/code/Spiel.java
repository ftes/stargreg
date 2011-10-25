package de.dhbw.stargreg.code;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

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
			HashMap<PersonalTyp, Double> laufendeKosten,
			HashMap<PersonalTyp, Double> werbungsKosten,
			String nachricht) {
		if (status != Status.EINRICHTEN) {
			System.err.println("Spielrunde nicht hinzugefügt: nur in der Phase 'Spielen' möglich");
			return;
		}
		this.spielRunden.add(new SpielRunde(this, nachfrage, laufendeKosten, werbungsKosten, nachricht, spielRunden.size()));
		System.out.printf("Spielrunde %d hinzugefügt\n", this.spielRunden.size());
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
		System.out.printf("Unternehmen %s hinzugefügt\n", unternehmen);
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
		System.out.println("Spiel gestartet");
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
		System.out.println("Spiel beendet");
	}
	
	/**
	 * Bewertet die Unternehmen
	 */
	public void bewerteUnternehmen() {
		if (status != Status.AUSWERTEN) {
			System.err.println("Keine Bewertung möglich, da Spiel noch nicht beendet wurde");
			return;
		}
		Vector<Unternehmen> rangfolge = ermittleRangfolge();
		System.out.println("Spielergebnis:");
		for (int i=0; i<rangfolge.size(); i++) {
			System.out.printf("%d. %s", i, rangfolge.elementAt(i));
		}
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
				System.out.printf("%s hat die Runde noch nicht eingecheckt\n", unternehmen);
				return;
			}
		}
		
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
		
		System.out.printf("Star dieser Runde war der Raumschifftyp %s\n", aktuelleSpielRunde.getStar());
		
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
	
	public RaumschiffTyp getStarDerLetztenRunde() {
		int vorherige = spielRunden.indexOf(aktuelleSpielRunde) - 1;
		if (vorherige == -1) return null;
		return spielRunden.elementAt(vorherige).getStar();
	}
	
	/**
	 * Ermittelt die Rangfolge der Unternehmen in der Endbewertung.
	 * Der ROI (Gewinn / Investition) wird zu 70% gewichtet, der
	 * Marktanteil gemessen am Umsatz des Unternehmens wird zu 30%
	 * gewichtet.
	 * Zum Gewinn zählt dabei im vollen Umfang dsa Kapital, zu 67%
	 * fertige Raumschiffe im Lager und zu 33% Bauteile im Lager.
	 * @return
	 */
	public Vector<Unternehmen> ermittleRangfolge() {
		double summeROI = 0;
		double summeUmsatz = 0;
		for (Unternehmen unternehmen : this.unternehmen) {
			summeROI += unternehmen.getROI();
			summeUmsatz += unternehmen.getUmsatz();
		}
		
		System.out.printf("Unternehmen | ROI         | Marktanteil | Bewertung\n");
		for (Unternehmen unternehmen : this.unternehmen) {
			double punkte = unternehmen.getROI() / summeROI * 70;
			punkte += unternehmen.getUmsatz() / summeUmsatz * 30;
			unternehmen.setBewertung(punkte);
			System.out.printf("%11-s | %11.1f | 11.1f | 11.0f\n", unternehmen, unternehmen.getROI(), unternehmen.getUmsatz() / summeUmsatz, punkte);
		}
		
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
