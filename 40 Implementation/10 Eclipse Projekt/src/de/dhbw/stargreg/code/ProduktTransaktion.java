package de.dhbw.stargreg.code;

public abstract class ProduktTransaktion<T extends ProduktTyp> extends TypTransaktion<T> {

	public ProduktTransaktion(T typ, Unternehmen unternehmen, int menge,
			double preis) {
		super(typ, unternehmen, menge, preis);
	}

}