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
	private BauteilMarkt bauteilMarkt = new BauteilMarkt();
	
	/**
	 * Raumschiffmarkt f�r diese Spielrunde
	 */
	private RaumschiffMarkt raumschiffMarkt = new RaumschiffMarkt();
	
	/**
	 * Personalmarkt f�r diese Spielrunde
	 */
	private PersonalMarkt personalMarkt = new PersonalMarkt();
	
	/**
	 * F�hrt die Simulationen auf allen M�rkten aus
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
