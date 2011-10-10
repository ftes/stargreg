package de.dhbw.stargreg.code;
import java.util.Vector;


/**
 * Verwaltet den gesamten Spielablauf, darunter alle Unternehmen
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
	private SpielRunde aktuelleSpielrunde;
	
	/**
	 * aktueller Spielstatus
	 */
	private Status status = Status.EINRICHTEN;
	
	/**
	 * Spiel kann sich in drei Zust�nden befinden: Einrichten, Spielen oder Auswerten
	 * Dies wird durch diese Enumeration dargestellt
	 * @author fredrik
	 *
	 */
	private static enum Status { EINRICHTEN, SPIELEN, AUSWERTEN }
	
	/**
	 * F�gt die �bergebene Spielrunde hinten an die Liste der Spielrunden an,
	 * somit zwingend in richtiger Reihenfolge einzuf�gen.
	 * Nur m�glich, wenn sich das Spiel in der Einrichtungsphase befindet.
	 * @param spielrunde Die anzuf�gende Spielrunde
	 */
	public void fuegeSpielrundeHinzu(SpielRunde spielrunde) {
		if (status == Status.EINRICHTEN) {
			this.spielrunden.add(spielrunde);
			System.out.printf("Spielrunde %i hinzugef�gt\n", this.spielrunden.size());
		} else {
			System.err.println("Spielrunde nicht hinzugef�gt: nur in der Phase 'Einrichten' m�glich");
		}
	}
	
	/**
	 * F�gt das �bergebene Unternehmen hinten an die Liste der Unternehmen an.
	 * Nur m�glich, wenn das Spiel sich in der Einrichtungsphase befindet.
	 * @param unternehmen Das anzuf�gende Unternehmen
	 */
	public void fuegeUnternehmenHinzu(Unternehmen unternehmen) {
		if (status == Status.EINRICHTEN) {
			this.unternehmen.add(unternehmen);
			System.out.printf("Unternehmen %s hinzugef�gt\n", unternehmen);
		} else {
			System.err.println("Unternehmen nicht hinzugef�gt: nur in der Phase 'Einrichten' m�glich");
		}
	}
	
	/**
	 * Startet das Spiel, nur m�glich wenn in Einrichtungs-Phase
	 */
	public void starteSpiel() {
		if (status != Status.EINRICHTEN) {
			System.err.println("Spiel nicht gestartet: wurde bereits gestartet");
			return;
		}
		status = Status.SPIELEN;
		aktuelleSpielrunde = spielrunden.firstElement();
		System.out.println("Spiel gestartet");
	}
	
	/**
	 * Beendet das Spiel, nur m�glich wenn in Spielen-Phase
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
	 * Sobald alle Benutzer ihre Eingaben get�tigt haben, kann die Spielrunde simuliert werden
	 * Dies setzt die aktuelle Spielrunde um eins weiter und f�gt die neuen M�rkte in diese ein
	 */
	public void simuliere() {

	}
}
