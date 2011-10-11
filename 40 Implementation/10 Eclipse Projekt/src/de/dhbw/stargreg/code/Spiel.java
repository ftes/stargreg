package de.dhbw.stargreg.code;
import java.util.Vector;


/**
 * Verwaltet den gesamten Spielablauf, darunter alle Unternehmen
 * Aufgebaut nach Singleton-Muster
 * @author fredrik
 * 
 */
public class Spiel {
	/**
	 * Liste aller Unternehmen
	 */
	private Vector<Unternehmen> unternehmen = new Vector<Unternehmen>();
	
	/**
	 * Liste aller Spielrunden
	 */
	private Vector<SpielRunde> spielrunden = new Vector<SpielRunde>();
	
	/**
	 * aktuelle Spielrunde
	 */
	private SpielRunde aktuelleSpielRunde;
	
	/**
	 * aktueller Spielstatus
	 */
	private Status status = Status.EINRICHTEN;
	
	/**
	 * Spiel kann sich in drei Zuständen befinden: Einrichten, Spielen oder Auswerten
	 * Dies wird durch diese Enumeration dargestellt
	 * @author fredrik
	 *
	 */
	private enum Status { EINRICHTEN, SPIELEN, AUSWERTEN }
	
	/**
	 * Verwaltet Singleton-Objet von Spiel
	 */
	private static Spiel spiel = new Spiel();
	
	/**
	 * Privater Konstruktor, um Singleton zu schützen
	 */
	private Spiel() {
		
	}
	
	/**
	 * statische Methode, um Spiel-Singleton zu erhalten
	 * @return
	 */
	public static Spiel getSpiel() {
		return spiel;
	}
	
	/**
	 * Fügt die übergebene Spielrunde hinten an die Liste der Spielrunden an,
	 * somit zwingend in richtiger Reihenfolge einzufügen.
	 * Nur möglich, wenn sich das Spiel in der Einrichtungsphase befindet.
	 * @param spielrunde Die anzufügende Spielrunde
	 */
	public void fuegeSpielrundeHinzu(SpielRunde spielrunde) {
		if (status == Status.EINRICHTEN) {
			this.spielrunden.add(spielrunde);
			System.out.printf("Spielrunde %i hinzugefügt\n", this.spielrunden.size());
		} else {
			System.err.println("Spielrunde nicht hinzugefügt: nur in der Phase 'Einrichten' möglich");
		}
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
	 * Startet das Spiel, nur möglich wenn in Einrichtungs-Phase
	 */
	public void starteSpiel() {
		if (status != Status.EINRICHTEN) {
			System.err.println("Spiel nicht gestartet: wurde bereits gestartet");
			return;
		}
		status = Status.SPIELEN;
		aktuelleSpielRunde = spielrunden.firstElement();
		System.out.println("Spiel gestartet");
	}
	
	/**
	 * Beendet das Spiel, nur möglich wenn in Spielen-Phase
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
	 * Initialisierung des Spiels: Anlegen aller Daten
	 */
	public void initialisieren() {
		
	}

	/**
	 * Sobald alle Benutzer ihre Eingaben getätigt haben, kann die Spielrunde simuliert werden,
	 * dies setzt die aktuelle Spielrunde um eins weiter.
	 * Nur möglich im Status Spielen
	 */
	public void naechsteSpielRunde() {
		if (status != Status.SPIELEN) {
			System.out.println("Die nächste Runde kann nur im Modus Spielen erreicht werden");
		}
		aktuelleSpielRunde.simuliere();
		SpielRunde vorherigeSpielrunde = aktuelleSpielRunde;
		aktuelleSpielRunde = getNaechsteSpielRunde();
		if (aktuelleSpielRunde == null) {
			beendeSpiel();
			return;
		}
		// Preise in Bauteilmarkt neu berechnen
		aktuelleSpielRunde.getBauteilMarkt().berechnePreise(vorherigeSpielrunde.getBauteilMarkt());
	}
	
	/**
	 * Gibt die nächste Spielrunde zurück, oder {@code null} falls dies bereits die Letzte war
	 * @return
	 */
	private SpielRunde getNaechsteSpielRunde() {
		int index = spielrunden.indexOf(aktuelleSpielRunde) + 1;
		if (index >= spielrunden.size()) {
			System.out.println("Letzte Spielrunde abgeschlossen");
			return null;
		}
		return spielrunden.get(index);
	}

	public SpielRunde getAktuelleSpielRunde() {
		return aktuelleSpielRunde;
	}
}
