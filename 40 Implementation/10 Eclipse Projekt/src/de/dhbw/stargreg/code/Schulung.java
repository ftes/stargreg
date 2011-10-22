package de.dhbw.stargreg.code;

/**
 * Schulung von Personal, von PersonalTyp x zu PersonalTyp y
 * @author fredrik
 *
 */
public class Schulung extends PersonalTransaktion {
	
	public Schulung(PersonalTyp von, Unternehmen unternehmen, int menge, double preis) {
		super(von, unternehmen, menge, preis);
	}
	
	public PersonalTyp getPersonalTypVon() {
		return (PersonalTyp) typ;
	}
	
	public PersonalTyp getPersonalTypZu() {
		return getPersonalTypVon().getNaechsterPersonalTyp();
	}
}