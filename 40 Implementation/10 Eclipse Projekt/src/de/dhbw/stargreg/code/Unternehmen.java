package de.dhbw.stargreg.code;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
	private final String name;
	private final EinkaufsAbteilung einkauf = new EinkaufsAbteilung();
	private final ProduktionsAbteilung produktion = new ProduktionsAbteilung();
	private final VerkaufsAbteilung verkauf = new VerkaufsAbteilung();
	private final FinanzAbteilung finanzen = new FinanzAbteilung();
	private final PersonalAbteilung personal = new PersonalAbteilung();
	
	private boolean rundeEingecheckt = false;
	
	public Unternehmen(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public boolean getRundeEingecheckt() {
		return rundeEingecheckt;
	}
	
	public void rundeEinchecken() {
		System.out.printf("%s hat Runde eingecheckt\n", this);
		rundeEingecheckt = true;
	}
	
	public void simuliere() {
		rundeEingecheckt = false;
	}
}
