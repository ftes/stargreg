package de.dhbw.stargreg.code;

/**
 * Entlassung von Personal.
 * @author fredrik
 *
 */
public class Entlassung extends PersonalTransaktion {
	
	public Entlassung(PersonalTyp personalTyp, Unternehmen unternehmen, int menge) {
		super(personalTyp, unternehmen, menge, 0);
	}
}