package de.dhbw.stargreg.code;

/**
 * Eine Spielrunde verwaltet die Märkte im aktuellen Zustand, und somit indirekt alle
 * für diese Runde wichtigen Daten.
 * @author fredrik
 *
 */
public class Spielrunde {
	/**
	 * Bauteilmarkt für diese Spielrunde
	 */
	private Bauteilmarkt bauteilmarkt;
	
	/**
	 * Raumschiffmarkt für diese Spielrunde
	 */
	private Raumschiffmarkt raumschiffmarkt;
	
	/**
	 * Personalmarkt für diese Spielrunde
	 */
	private Personalmarkt personalmarkt;
	
	/**
	 * Führt die Simulationen auf allen Märkten aus
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
