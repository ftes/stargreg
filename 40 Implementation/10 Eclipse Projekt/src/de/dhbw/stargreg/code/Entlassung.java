package de.dhbw.stargreg.code;

/**
 * Entlassung von Personal
 * @author fredrik
 *
 */
public class Entlassung extends Transaktion {
	
	public Entlassung(PersonalTyp personaltyp, Unternehmen unternehmen, int menge) {
		super(personaltyp, unternehmen, menge, 0);
	}
	
	public PersonalTyp getPersonalTyp() {
		return (PersonalTyp) typ;
	}
}