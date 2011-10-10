package de.dhbw.stargreg.code;

/**
 * Eine Spielrunde verwaltet die M�rkte im aktuellen Zustand, und somit indirekt alle
 * f�r diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class Spielrunde {
	/**
	 * Bauteilmarkt f�r diese Spielrunde
	 */
	private Bauteilmarkt bauteilmarkt;
	
	/**
	 * Raumschiffmarkt f�r diese Spielrunde
	 */
	private Raumschiffmarkt raumschiffmarkt;
	
	/**
	 * Personalmarkt f�r diese Spielrunde
	 */
	private Personalmarkt personalmarkt;
	
	/**
	 * F�hrt die Simulationen auf allen M�rkten aus
	 */
	public void simuliere() {
		
	}

	public Bauteilmarkt getBauteilmarkt() {
		return bauteilmarkt;
	}

	public void setBauteilmarkt(Bauteilmarkt bauteilmarkt) {
		this.bauteilmarkt = bauteilmarkt;
	}

	public Raumschiffmarkt getRaumschiffmarkt() {
		return raumschiffmarkt;
	}

	public void setRaumschiffmarkt(Raumschiffmarkt raumschiffmarkt) {
		this.raumschiffmarkt = raumschiffmarkt;
	}

	public Personalmarkt getPersonalmarkt() {
		return personalmarkt;
	}

	public void setPersonalmarkt(Personalmarkt personalmarkt) {
		this.personalmarkt = personalmarkt;
	}

}
