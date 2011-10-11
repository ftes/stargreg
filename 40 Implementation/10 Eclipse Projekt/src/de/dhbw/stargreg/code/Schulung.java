package de.dhbw.stargreg.code;

/**
 * Schulung von Personal, von PersonalTyp x zu PersonalTyp y
 * @author fredrik
 *
 */
public class Schulung extends Transaktion {
	private PersonalTyp personalTypZu;
	
	public Schulung(PersonalTyp von, PersonalTyp zu, Unternehmen unternehmen, int menge, double preis) {
		super(von, unternehmen, menge, preis);
		personalTypZu = zu;
	}
	
	public PersonalTyp getPersonalTypVon() {
		return (PersonalTyp) typ;
	}
	
	public PersonalTyp getPersonalTypZu() {
		return personalTypZu;
	}
}