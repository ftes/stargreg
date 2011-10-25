package de.dhbw.stargreg.code;

import java.util.Vector;

/**
 * 
 * @author fredrik
 *
 */
public class Unternehmen {
	private final String name;
	private final Spiel spiel;
	private final EinkaufsAbteilung einkauf = new EinkaufsAbteilung(this);
	private final ProduktionsAbteilung produktion = new ProduktionsAbteilung(this);
	private final VerkaufsAbteilung verkauf = new VerkaufsAbteilung(this);
	private final FinanzAbteilung finanzen;
	private final PersonalAbteilung personal = new PersonalAbteilung(this);
	private final LagerAbteilung lager = new LagerAbteilung(this);
	
	private boolean rundeEingecheckt = false;
	
	public Unternehmen(Spiel spiel, String name, double startKapital) {
		this.spiel = spiel;
		this.name = name;
		this.finanzen = new FinanzAbteilung(this, startKapital);
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
			System.out.printf("In %s wurde Runde noch nicht eingecheckt\n", this);
		}
		
		personal.simuliere();
		produktion.produziere();
		verkauf.verkaufe(verkaeufe);
		lager.simuliere();
		finanzen.simuliere();
		
		rundeEingecheckt = false;
	}

	public EinkaufsAbteilung getEinkauf() {
		return einkauf;
	}

	public ProduktionsAbteilung getProduktion() {
		return produktion;
	}

	public VerkaufsAbteilung getVerkauf() {
		return verkauf;
	}

	public FinanzAbteilung getFinanzen() {
		return finanzen;
	}

	public PersonalAbteilung getPersonal() {
		return personal;
	}
	
	public LagerAbteilung getLager() {
		return lager;
	}
	
	public Spiel getSpiel() {
		return spiel;
	}
}
