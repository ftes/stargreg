package de.dhbw.stargreg.code;

/**
 * Ein PersonalTyp wird durch seine Bezeichnung und eine Qualitätsstufe beschrieben,
 * die den Ausschuss steuert
 * @author fredrik
 *
 */
public class PersonalTyp extends Typ {
	private final double qualitaet;
	private double laufendeKosten;
	private double werbungsKosten;
	private final Double schulungsKosten;
	private final PersonalTyp naechsterPersonalTyp;
	
	public PersonalTyp(String name, double qualitaet, Double schulungsKosten, PersonalTyp naechsterPersonalTyp) {
		super(name);
		this.qualitaet = qualitaet;
		this.schulungsKosten = schulungsKosten;
		this.naechsterPersonalTyp = naechsterPersonalTyp;
	}
	
	public double getQualitaet() {
		return qualitaet;
	}
	
	public double getLaufendeKosten() {
		return laufendeKosten;
	}
	
	public Double getSchulungsKosten() {
		return schulungsKosten;
	}

	public void setLaufendeKosten(double laufendeKosten) {
		this.laufendeKosten = laufendeKosten;
	}

	public double getWerbungsKosten() {
		return werbungsKosten;
	}

	public void setWerbungsKosten(double werbungsKosten) {
		this.werbungsKosten = werbungsKosten;
	}
	
	public PersonalTyp getNaechsterPersonalTyp() {
		return naechsterPersonalTyp;
	}
}
