package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class SpielRunde {
	/**
	 * Bauteilmarkt für diese Spielrunde
	 */
	private BauteilMarkt bauteilmarkt;
	
	/**
	 * Raumschiffmarkt für diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffmarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt für diese Spielrunde
	 */
	private PersonalMarkt personalmarkt = new PersonalMarkt();
	
	/**
	 * Konstruktor, der die vorher definierten Werte entgegenimmt
	 */
	public SpielRunde(HashMap<RaumschiffTyp, Integer> raumschiffNachfrage, HashMap<PersonalTyp, Double> personalLaufendeKosten, HashMap<BauteilTyp, Double> bauteilPreise) {
		
	}
	
	/**
	 * Führt die Simulationen auf allen Märkten aus
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
