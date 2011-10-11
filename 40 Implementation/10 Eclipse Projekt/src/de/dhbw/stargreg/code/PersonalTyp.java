package de.dhbw.stargreg.code;

/**
 * Ein PersonalTyp wird durch seine Bezeichnung und eine Qualitätsstufe beschrieben,
 * die den Ausschuss steuert
 * @author fredrik
 *
 */
public class PersonalTyp extends Typ {
	private int qualitaet;
	
	public PersonalTyp(String name, int qualitaet) {
		super(name);
		this.qualitaet = qualitaet;
	}
	
	public int getQualitaet() {
		return qualitaet;
	}
}
