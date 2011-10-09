package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
	private String name;
	private EinkaufsAbteilung einkauf;
	private ProduktionsAbteilung produktion;
	private VerkaufsAbteilung verkauf;
	private FinanzAbteilung finanzen;
	private PersonalAbteilung personal;
	
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
