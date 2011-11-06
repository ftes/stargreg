package de.dhbw.stargreg.code;

/**
 * Ein PersonalTyp wird durch seine Bezeichnung und eine Qualitätsstufe beschrieben,
 * die den Ausschuss steuert
 * @author fredrik
 *
 */
public class PersonalTyp extends Typ {
	private final double qualitaet;
	private final double basisLaufendeKosten;
	private final double basisWerbungsKosten;
	private final Double schulungsKosten;
	private final PersonalTyp naechsterPersonalTyp;
	private double konjunkturFaktor;

	public PersonalTyp(String name, double qualitaet, double basisLaufendeKosten, double basisWerbungsKosten, Double schulungsKosten, PersonalTyp naechsterPersonalTyp) {
		super(name);
		this.qualitaet = qualitaet;
		this.schulungsKosten = schulungsKosten;
		this.naechsterPersonalTyp = naechsterPersonalTyp;
		this.basisLaufendeKosten = basisLaufendeKosten;
		this.basisWerbungsKosten = basisWerbungsKosten;
	}

	public double getQualitaet() {
		return qualitaet;
	}

	public double getLaufendeKosten() {
		return basisLaufendeKosten * konjunkturFaktor;
	}

	public Double getSchulungsKosten() {
		return schulungsKosten;
	}

	public double getWerbungsKosten() {
		return basisWerbungsKosten * konjunkturFaktor;
	}

	public PersonalTyp getNaechsterPersonalTyp() {
		return naechsterPersonalTyp;
	}
	
	public void setKonjunkturFaktor(double konjunkturFaktor) {
		this.konjunkturFaktor = konjunkturFaktor;
	}
}
