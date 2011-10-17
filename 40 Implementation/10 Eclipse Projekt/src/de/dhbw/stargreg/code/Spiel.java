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
	private enum Status { EINRICHTEN, SPIELEN, AUSWERTEN }
	
	/**
	 * Privater Konstruktor, um Singleton zu sch�tzen
	 */
	private Spiel() {
		
	}
	
	/**
	 * F�gt die �bergebene Spielrunde hinten an die Liste der Spielrunden an,
	 * somit zwingend in richtiger Reihenfolge einzuf�gen.
	 * Nur m�glich, wenn sich das Spiel in der Einrichtungsphase befindet.
	 * @param spielRunde Die anzuf�gende Spielrunde
	 */
	public void fuegeSpielRundeHinzu(SpielRunde spielRunde) {
		if (status == Status.EINRICHTEN) {
			this.spielrunden.add(spielRunde);
			System.out.printf("Spielrunde %d hinzugef�gt\n", this.spielrunden.size());
		} else {
			System.err.println("Spielrunde nicht hinzugef�gt: nur in der Phase 'Einrichten' m�glich");
		}
	}
	
	/**
	*Gibt die Anzahl der Unternehmen zur�ck
	*/
	public int getAnzahlUnternehmen() {
		return unternehmen.size();
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
		aktuelleSpielRunde = spielrunden.firstElement();
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
	 * Sobald alle Benutzer ihre Eingaben get�tigt haben, kann die Spielrunde simuliert werden,
	 * dies setzt die aktuelle Spielrunde um eins weiter.
	 * Nur m�glich im Status Spielen
	 */
	public void simuliere() {
		if (status != Status.SPIELEN) {
			System.out.println("Die n�chste Runde kann nur im Modus Spielen erreicht werden");
			return;
		}
		
		for (Unternehmen unternehmen : this.unternehmen) {
			if (! unternehmen.getRundeEingecheckt()) {
				System.out.printf("%s hat die Runde noch nicht eingecheckt\n", unternehmen);
				return;
			}
		}
		
		
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
	
	/**
	 * Setzt das Spiel zur�ck. Es werden alle Unternehmen gel�scht und die M�rkte
	 * zur�ckgesetzt.
	 */
	public void setzeZurueck() {
		raumschiffMarkt = new RaumschiffMarkt();
		bauteilMarkt = new BauteilMarkt();
		personalMarkt = new PersonalMarkt();
		unternehmen.clear();
		spielrunden.clear();
	}
}
