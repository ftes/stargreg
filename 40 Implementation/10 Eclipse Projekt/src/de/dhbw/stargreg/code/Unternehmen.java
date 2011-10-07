package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
	private String name;
	private Einkauf einkauf;
	private Produktion produktion;
	private Verkauf verkauf;
	private Finanzen finanzen;
	private Personal personal;
	
	Unternehmen() {
		
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
}
