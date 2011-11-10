package de.dhbw.stargreg.code;

public abstract class TypTransaktion<T extends Typ> extends Transaktion {
	protected final int menge;
	protected final T typ;
	
	
	public TypTransaktion(T typ, Unternehmen unternehmen, int menge, double preis) {
		super(unternehmen, preis);
		this.menge = menge;
		this.typ = typ;
	}

	public int getMenge() {
		return menge;
	}
	
	public String toString() {
		return String.format("%d %s zu %.2f Credits", menge, typ, einzelBetrag);
	}
	
	public T getTyp() {
		return typ;
	}
	
	public double getGesamtBetrag() {
		return menge * einzelBetrag;
	}
}
