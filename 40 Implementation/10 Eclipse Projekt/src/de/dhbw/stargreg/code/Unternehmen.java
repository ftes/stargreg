package de.dhbw.stargreg.code;

import java.util.Vector;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
<<<<<<< HEAD
	private String name;
	private EinkaufsAbteilung einkauf = new EinkaufsAbteilung();
	private ProduktionsAbteilung produktion = new ProduktionsAbteilung();
	private VerkaufsAbteilung verkauf = new VerkaufsAbteilung();
	private FinanzAbteilung finanzen = new FinanzAbteilung();
	private PersonalAbteilung personal = new PersonalAbteilung();
	private LagerAbteilung lager = new LagerAbteilung();
=======
	private final String name;
	private final EinkaufsAbteilung einkauf = new EinkaufsAbteilung();
	private final ProduktionsAbteilung produktion = new ProduktionsAbteilung();
	private final VerkaufsAbteilung verkauf = new VerkaufsAbteilung();
	private final FinanzAbteilung finanzen = new FinanzAbteilung();
	private final PersonalAbteilung personal = new PersonalAbteilung();
	
	private boolean rundeEingecheckt = false;
>>>>>>> branch 'master' of git@github.com:ch3ck3r/Fallstudie.git
	
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
	
	public void simuliere(Vector<Verkauf> verkaeufe) {
		if (! rundeEingecheckt) {
			System.out.printf("In %s wurde Runde noch nicht ausgecheckt\n", this);
		}
		
		personal.simuliere();
		produktion.produziere();
		verkauf.verkaufe(verkaeufe);
		
		rundeEingecheckt = false;
		System.out.printf("%s hat Runde simuliert\n", this);
	}
}
