package de.dhbw.stargreg.code;

/**
 * abstrakte Klasse für alle Personal-bezogenen Transaktionen
 * @author fredrik
 *
 */
public abstract class PersonalTransaktion extends TypTransaktion<PersonalTyp> {

	public PersonalTransaktion(PersonalTyp personalTyp, Unternehmen unternehmen, int menge, double preis) {
		super(personalTyp, unternehmen, menge, preis);
	}

}
