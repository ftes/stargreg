package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
	private String name;
	private EinkaufsAbteilung einkauf = new EinkaufsAbteilung();
	private ProduktionsAbteilung produktion = new ProduktionsAbteilung();
	private VerkaufsAbteilung verkauf = new VerkaufsAbteilung();
	private FinanzAbteilung finanzen = new FinanzAbteilung();
	private PersonalAbteilung personal = new PersonalAbteilung();
	
	public Unternehmen(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
