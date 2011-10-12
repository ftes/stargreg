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
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	/**
	 * Raumschiffmarkt für diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt für diese Spielrunde
	 */
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	/**
	 * Führt die Simulationen auf allen Märkten aus
	 */
	public void simuliere() {

	}

	public BauteilMarkt getBauteilMarkt() {
		return bauteilMarkt;
	}

	public void setBauteilMarkt(BauteilMarkt bauteilMarkt) {
		this.bauteilMarkt = bauteilMarkt;
	}

	public RaumschiffMarkt getRaumschiffMarkt() {
		return raumschiffMarkt;
	}

	public void setRaumschiffMarkt(RaumschiffMarkt raumschiffMarkt) {
		this.raumschiffMarkt = raumschiffMarkt;
	}

	public PersonalMarkt getPersonalMarkt() {
		return personalMarkt;
	}

	public void setPersonalMarkt(PersonalMarkt personalMarkt) {
		this.personalMarkt = personalMarkt;
	}

}
