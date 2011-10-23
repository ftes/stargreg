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
	private final double schulungsKosten;
	private final boolean schulbar;
	private final PersonalTyp naechsterPersonalTyp;
	
	public PersonalTyp(String name, double qualitaet, double schulungsKosten, PersonalTyp naechsterPersonalTyp) {
		super(name);
		this.qualitaet = qualitaet;
		if (schulungsKosten <= 0.0) {
			this.schulbar = false;
			this.schulungsKosten = 0.0;
		} else {
			this.schulbar = true;
			this.schulungsKosten = schulungsKosten;
		}
		this.naechsterPersonalTyp = naechsterPersonalTyp;
	}
	
	public double getQualitaet() {
		return qualitaet;
	}
	
	public double getLaufendeKosten() {
		return laufendeKosten;
	}
	
	public double getSchulungsKosten() {
		if (! schulbar) {
			System.err.printf("%s ist nicht weiter schulbar\n", this);
		}
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
