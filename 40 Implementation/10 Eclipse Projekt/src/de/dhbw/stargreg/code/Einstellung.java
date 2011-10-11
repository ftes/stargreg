package de.dhbw.stargreg.code;

/**
 * Einstellung von Personal
 * @author fredrik
 *
 */
public class Einstellung extends Transaktion {
	
	public Einstellung(PersonalTyp personalTyp, Unternehmen unternehmen, int menge, double preis) {
		super(personalTyp, unternehmen, menge, preis);
	}
	
	public PersonalTyp getPersonalTyp() {
		return (PersonalTyp) typ;
	}
}