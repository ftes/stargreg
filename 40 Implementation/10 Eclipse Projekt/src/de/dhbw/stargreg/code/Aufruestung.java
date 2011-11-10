package de.dhbw.stargreg.code;

/**
 * Aufruestung von Personal, von PersonalTyp x zu PersonalTyp y
 * @author fredrik
 *
 */
public class Aufruestung extends PersonalTransaktion {
	
	public Aufruestung(PersonalTyp von, Unternehmen unternehmen, int menge, double preis) {
		super(von, unternehmen, menge, preis);
	}
	
	public PersonalTyp getPersonalTypVon() {
		return (PersonalTyp) typ;
	}
	
	public PersonalTyp getPersonalTypZu() {
		return getPersonalTypVon().getNaechsterPersonalTyp();
	}
}