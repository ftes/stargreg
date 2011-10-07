package de.dhbw.stargreg.code;
import java.util.Vector;


/**
 * 
 * @author fredrik
 * 
 */
public class Spiel {
	private Vector<Unternehmen> unternehmen = new Vector<Unternehmen>();
	private Vector<Spielrunde> spielrunden = new Vector<Spielrunde>();
	private Bauteilmarkt bauteilmarkt;
	private Raumschiffmarkt raumschiffmarkt;
	private Personalmarkt personalmarkt;
	private Spielrunde aktuelleSpielrunde;
	
	private Status status = Status.EINRICHTEN;
	
	private enum Status { EINRICHTEN, SPIELEN, AUSWERTEN }
	
	/**
	 * Konstruktor
	 */
	public Spiel() {
		
	}
	
	/**
	 * Fügt die übergebene Spielrunde hinten an die Liste der Spielrunden an,
	 * somit zwingend in richtiger Reihenfolge einzufügen.
	 * Nur möglich, wenn sich das Spiel in der Einrichtungsphase befindet.
	 * @param spielrunde Die anzufügende Spielrunde
	 */
	public void erstelleSpielrunde(Spielrunde spielrunde) {
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
	public void erstelleUnternehmen(Unternehmen unternehmen) {
		if (status == Status.EINRICHTEN) {
			this.unternehmen.add(unternehmen);
			System.out.printf("Unternehmen %s hinzugefügt\n", unternehmen.getName());
		} else {
			System.err.println("Unternehmen nicht hinzugefügt: nur in der Phase 'Einrichten' möglich");
		}
	}
	
	public void starteSpiel() {
		if (status == Status.EINRICHTEN) {
			status = Status.SPIELEN;
			System.out.println("Spiel gestartet");
		} else {
			System.err.println("Spiel nicht gestartet: wurde bereits gestartet");
		}
	}
	
	public void beendeSpiel() {
		if (status == Status.SPIELEN) {
			status = Status.AUSWERTEN;
			System.out.println("Spiel beendet");
		} else {
			System.out.println("Spiel nicht beendet: wurde noch nicht gestartet oder schon beendet");
		}
	}
	
	public void initialisieren() {
		
	}
}
