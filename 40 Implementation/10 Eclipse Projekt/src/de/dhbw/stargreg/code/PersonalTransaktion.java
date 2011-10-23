package de.dhbw.stargreg.code;

/**
 * abstrakte Klasse für alle Personal-bezogenen Transaktionen
 * @author fredrik
 *
 */
public abstract class PersonalTransaktion extends Transaktion {

	public PersonalTransaktion(Typ typ, Unternehmen unternehmen, int menge, double preis) {
		super(typ, unternehmen, menge, preis);
	}

}
