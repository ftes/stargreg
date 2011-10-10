package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Eine Spielrunde verwaltet die M�rkte im aktuellen Zustand, und somit indirekt alle
 * f�r diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	/**
	 * Bauteilmarkt f�r diese Spielrunde
	 */
	private BauteilMarkt bauteilmarkt;
	
	/**
	 * Raumschiffmarkt f�r diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffmarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt f�r diese Spielrunde
	 */
	private PersonalMarkt personalmarkt = new PersonalMarkt();
	
	/**
	 * Konstruktor, der die vorher definierten Werte entgegenimmt
	 */
	public SpielRunde(HashMap<RaumschiffTyp, Integer> raumschiffNachfrage, HashMap<PersonalTyp, Double> personalLaufendeKosten, HashMap<BauteilTyp, Double> bauteilPreise) {
		
	}
	
	/**
	 * F�hrt die Simulationen auf allen M�rkten aus
	 */
	public void simuliere() {
		
	}

	public BauteilMarkt getBauteilmarkt() {
		return bauteilmarkt;
	}

	public void setBauteilmarkt(BauteilMarkt bauteilmarkt) {
		this.bauteilmarkt = bauteilmarkt;
	}

	public RaumschiffMarkt getRaumschiffmarkt() {
		return raumschiffmarkt;
	}

	public void setRaumschiffmarkt(RaumschiffMarkt raumschiffmarkt) {
		this.raumschiffmarkt = raumschiffmarkt;
	}

	public PersonalMarkt getPersonalmarkt() {
		return personalmarkt;
	}

	public void setPersonalmarkt(PersonalMarkt personalmarkt) {
		this.personalmarkt = personalmarkt;
	}

}
