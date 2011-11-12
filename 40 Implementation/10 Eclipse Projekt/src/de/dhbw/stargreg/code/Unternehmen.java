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
	private final SpielWelt spielWelt;
	private final EinkaufsAbteilung einkauf = new EinkaufsAbteilung(this);
	private final ProduktionsAbteilung produktion = new ProduktionsAbteilung(this);
	private final VerkaufsAbteilung verkauf = new VerkaufsAbteilung(this);
	private final FinanzAbteilung finanzen;
	private final PersonalAbteilung personal = new PersonalAbteilung(this);
	private final LagerAbteilung lager = new LagerAbteilung(this);
	
	private double bewertung;
	
	private boolean rundeEingecheckt = false;
	
	public Unternehmen(SpielWelt spielWelt, String name, double startKapital) {
		this.spielWelt = spielWelt;
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
//		System.out.printf("%s hat Runde eingecheckt\n", this);
		rundeEingecheckt = true;
	}
	
	public void simuliere() {
		if (! rundeEingecheckt) {
			System.out.printf("In %s wurde Runde noch nicht eingecheckt\n", this);
		}
		
//		System.out.printf("Simulation von %s:\n", this);
		personal.simuliere();
		produktion.simuliere();
		verkauf.simuliere();
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
	
	public SpielWelt getSpielWelt() {
		return spielWelt;
	}
	
	public double getROI() {
		double rOI = finanzen.getKontostand();
		for (BauteilTyp bauteilTyp : spielWelt.getBauteilMarkt().getTypen()) {
			rOI += lager.getAnzahl(bauteilTyp) * bauteilTyp.getPreis() * 0.5;
		}
		for (RaumschiffTyp raumschiffTyp : spielWelt.getRaumschiffMarkt().getTypen()) {
			rOI += lager.getAnzahl(raumschiffTyp) * raumschiffTyp.getKosten() * 0.75;
		}
		rOI = (rOI - finanzen.getStartKapital()) / finanzen.getStartKapital();
		return rOI;
	}
	
	/**
	 * Wert des Absatzes des Unternehmens im gesamten Spiel, gemessen an den Raumschiffwerten.
	 * @return Absatzwert.
	 */
	public double getAbsatzWert() {
		Vector<Verkauf> verkaeufe = new Vector<Verkauf>();
		for (SpielRunde spielRunde : spielWelt.getSpielRunden()) {
			verkaeufe.addAll(spielRunde.getTransaktionen(Verkauf.class, this));
		}
		
		double absatzWert = 0;
		for (Verkauf verkauf : verkaeufe) {
			absatzWert += verkauf.getGesamtWert();
		}
		
		return absatzWert;
	}

	public double getBewertung() {
		return bewertung;
	}

	public void setBewertung(double bewertung) {
		this.bewertung = bewertung;
	}
	
	public void gebeAnfangsInformationenAus() {
		Util.printHeading(this.toString() + ": Informationsphase");
		System.out.println(spielWelt.getAktuelleSpielRunde().getNachricht() + "\n");
		if (spielWelt.getStarDerLetztenRunde() != null) System.out.printf("Star der letzten Runde: %s\n\n", spielWelt.getStarDerLetztenRunde());
		spielWelt.getBauteilMarkt().gebePreiseAus();
		spielWelt.getPersonalMarkt().gebeKostenAus();
		finanzen.gebeInformationenAus(false);
		verkauf.gebeInformationenAus(false);
		lager.gebeInformationenAus(true);
		personal.gebeInformationenAus(true);
		Util.pause();
	}
	
	public void gebeEndInformationenAus() {
		Util.printHeading(this.toString() + ": Aktionsübersicht");
		finanzen.gebeInformationenAus(true);
		personal.gebeInformationenAus(true);
		produktion.gebeInformationenAus(true);
		verkauf.gebeInformationenAus(true);
		lager.gebeInformationenAus(true);
		Util.pause();
	}
	
	/**
	 * Hilfsfunktion, die alle drei Schritte ausführt
	 * @param raumschiffTyp
	 * @param menge
	 * @param preis
	 */
	public void kaufeEinProduziereVerkaufe(RaumschiffTyp raumschiffTyp, int menge, double preis) {
		einkauf.kaufeEinFuer(raumschiffTyp, menge);
		produktion.fuegeAuftragHinzu(raumschiffTyp, menge);
		verkauf.macheAngebot(raumschiffTyp, preis);
	}
}
