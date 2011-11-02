package de.dhbw.stargreg.code;

import java.util.Vector;

import de.dhbw.stargreg.util.Util;

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
	
	private double bewertung;
	
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
		
		System.out.printf("Simulation von %s:\n", this);
		personal.simuliere();
		produktion.produziere();
		finanzen.simuliere();
		verkauf.verkaufe(verkaeufe);
		lager.simuliere();
		
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
	
	public double getROI() {
		double rOI = finanzen.getKontostand();
		for (BauteilTyp bauteilTyp : spiel.getBauteilMarkt().getTypen()) {
			rOI += lager.getAnzahl(bauteilTyp) * bauteilTyp.getPreis() * 0.5;
		}
		for (RaumschiffTyp raumschiffTyp : spiel.getRaumschiffMarkt().getTypen()) {
			rOI += lager.getAnzahl(raumschiffTyp) * raumschiffTyp.getKosten() * 0.75;
		}
		rOI = (rOI - finanzen.getStartKapital()) / finanzen.getStartKapital();
		return rOI;
	}
	
	public double getUmsatz() {
		Vector<Verkauf> verkaeufe = new Vector<Verkauf>();
		for (SpielRunde spielRunde : spiel.getSpielRunden()) {
			verkaeufe.addAll(spielRunde.getVerkaeufe());
		}
		
		double umsatz = 0;
		for (Verkauf verkauf : verkaeufe) {
			if (verkauf.getUnternehmen() == this) umsatz += verkauf.getKosten();
		}
		
		return umsatz;
	}

	public double getBewertung() {
		return bewertung;
	}

	public void setBewertung(double bewertung) {
		this.bewertung = bewertung;
	}
	
	public void gebeInformationenAus() {
		Util.printSpacer();
		System.out.printf("Informationen für %s:\n", this);
		if (spiel.getStarDerLetztenRunde() != null) System.out.printf("Star der letzten Runde: %s\n", spiel.getStarDerLetztenRunde());
//		einkauf.gebeInformationenAus(false);
//		produktion.gebeInformationenAus(false);
		verkauf.gebeInformationenAus(false);
		finanzen.gebeInformationenAus(true);
		lager.gebeInformationenAus(true);
		personal.gebeInformationenAus(true);
	}
}
