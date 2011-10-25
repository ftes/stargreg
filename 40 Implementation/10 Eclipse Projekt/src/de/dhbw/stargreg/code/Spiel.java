package de.dhbw.stargreg.code;
import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.util.Util;


/**
 * Verwaltet den gesamten Spielablauf, darunter alle Unternehmen
 * Aufgebaut nach Singleton-Muster
 * @author fredrik
 * 
 */
public enum Spiel {
	INSTANCE;
	
	/**
	 * Liste aller Unternehmen
	 */
	private final Vector<Unternehmen> unternehmen = new Vector<Unternehmen>();
	
	/**
	 * Liste aller Spielrunden
	 */
	private final Vector<SpielRunde> spielrunden = new Vector<SpielRunde>();
	
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
	 * Privater Konstruktor, um Singleton zu schützen
	 */
	private Spiel() {
		
	}
	
	/**
	 * Fügt die übergebene Spielrunde hinten an die Liste der Spielrunden an.
	 * Nur möglich in der Phase Spielen.
	 * @param spielRunde Die anzufügende Spielrunde
	 */
	public void fuegeSpielRundeHinzu(SpielRunde spielRunde) {
		if (status == Status.SPIELEN) {
			this.spielrunden.add(spielRunde);
			System.out.printf("Spielrunde %d hinzugefügt\n", this.spielrunden.size());
		} else {
			System.err.println("Spielrunde nicht hinzugefügt: nur in der Phase 'Spielen' möglich");
		}
	}
	
	public int getAnzahlUnternehmen() {
		return unternehmen.size();
	}
	
	/**
	 * Fügt das übergebene Unternehmen hinten an die Liste der Unternehmen an.
	 * Nur möglich, wenn das Spiel sich in der Einrichtungsphase befindet.
	 * @param unternehmen Das anzufügende Unternehmen
	 */
	public void fuegeUnternehmenHinzu(Unternehmen unternehmen) {
		if (status == Status.EINRICHTEN) {
			this.unternehmen.add(unternehmen);
			System.out.printf("Unternehmen %s hinzugefügt\n", unternehmen);
		} else {
			System.err.println("Unternehmen nicht hinzugefügt: nur in der Phase 'Einrichten' möglich");
		}
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
		aktuelleSpielRunde = spielrunden.firstElement();
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
		
		aktuelleSpielRunde = new SpielRunde();
		fuegeSpielRundeHinzu(aktuelleSpielRunde);
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
	
	/**
	 * Setzt das Spiel zurück. Es werden alle Unternehmen gelöscht und die Märkte
	 * zurückgesetzt.
	 */
	public void setzeZurueck() {
		raumschiffMarkt = new RaumschiffMarkt();
		bauteilMarkt = new BauteilMarkt();
		personalMarkt = new PersonalMarkt();
		kapitalMarkt = new KapitalMarkt();
		unternehmen.clear();
		spielrunden.clear();
	}
}
