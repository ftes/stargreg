package de.dhbw.stargreg.code;
import java.util.Collections;
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
	}
	
	/**
	 * Beendet das Spiel, nur möglich wenn in Spielen-Phase.
	 */
	public void beendeSpiel() {
		if (status == Status.SPIELEN) {
			status = Status.AUSWERTEN;
			System.out.println("Spiel beendet");
		} else {
			System.out.println("Spiel nicht beendet: wurde noch nicht gestartet oder schon beendet");
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
		
		aktuelleSpielRunde = getNaechsteSpielRunde();
		if (aktuelleSpielRunde == null) {
			beendeSpiel();
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
	
	public Vector<Unternehmen> ermittleRangfolge() {
		HashMap <Unternehmen, Double> rOIs = new HashMap <Unternehmen, Double>();
		for (Unternehmen unternehmen : this.unternehmen) {
			double rOI = 0;
			rOI += unternehmen.getFinanzen().getKontostand();
			for (BauteilTyp bauteilTyp : bauteilMarkt.getTypen()) {
				rOI += unternehmen.getLager().getAnzahl(bauteilTyp) * bauteilTyp.getPreis() / 3;
			}
			for (RaumschiffTyp raumschiffTyp : raumschiffMarkt.getTypen()) {
				rOI += unternehmen.getLager().getAnzahl(raumschiffTyp) * raumschiffTyp.getKosten() * 2 / 3;
			}
			rOI = rOI / unternehmen.getFinanzen().getStartKapital();
			rOIs.put(unternehmen, rOI);
		}
		
		HashMap <Unternehmen, Double> marktAnteile = new HashMap <Unternehmen, Double>();
		for (Unternehmen unternehmen : this.unternehmen) {
			marktAnteile.put(unternehmen, 0.0);
		}
		for (SpielRunde spielRunde : spielRunden) {
			HashMap <Unternehmen, Vector<Verkauf>> verkaeufe = Util.gruppiereVerkaeufeNachUnternehmen(spielRunde.getVerkaeufe());
			for (Unternehmen unternehmen : verkaeufe.keySet()) {
				double umsatz = 0;
				for (Verkauf verkauf : verkaeufe.get(unternehmen)) {
					umsatz += verkauf.getKosten();
				}
				double alt = marktAnteile.get(unternehmen);
				marktAnteile.put(unternehmen, alt + umsatz);
			}
		}
		
		HashMap <Unternehmen, Double> bewertung = new HashMap<Unternehmen, Double>();
		double summeROI = 0;
		double summeMarktAnteile = 0;
		for (double rOI : rOIs.values()) {
			summeROI += rOI;
		}
		for (double marktAnteil : marktAnteile.values()) {
			summeMarktAnteile += marktAnteil;
		}
		for (Unternehmen unternehmen : rOIs.keySet()) {
			double punkte = 0;
			punkte = rOIs.get(unternehmen) / summeROI * 70;
			bewertung.put(unternehmen, punkte);
		}
		for (Unternehmen unternehmen : marktAnteile.keySet()) {
			double punkte = 0;
			punkte = marktAnteile.get(unternehmen) / summeMarktAnteile * 30;
			double alt = bewertung.get(unternehmen);
			bewertung.put(unternehmen, alt + punkte);
		}
		// Bewertung sortieren um Rangfolge zu erhalten
	return null;
	}
}
