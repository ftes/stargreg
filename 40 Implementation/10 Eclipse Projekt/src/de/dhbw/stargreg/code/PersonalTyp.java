package de.dhbw.stargreg.code;

/**
 * Ein PersonalTyp wird durch seine Bezeichnung und eine Qualitätsstufe beschrieben,
 * die den Ausschuss steuert
 * @author fredrik
 *
 */
public class PersonalTyp extends Typ {
	private int qualitaet;
	private double laufendeKosten;
	private double werbungsKosten;
	
	public PersonalTyp(String name, int qualitaet) {
		super(name);
		this.qualitaet = qualitaet;
	}
	
	public int getQualitaet() {
		return qualitaet;
	}
	
	public double getLaufendeKosten() {
		return laufendeKosten;
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
}
