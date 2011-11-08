package de.dhbw.stargreg.code;

import java.util.Vector;

public class TypMarkt<T extends Typ, A extends TypTransaktion<T>> extends Markt<A>{
	
	/**
	 * Verwaltung der Typen dieses Marktes.
	 */
	protected final Vector<T> typen = new Vector<T>();
	
	/**
	 * Fügt den Typ zur Liste der Typen hinzu.
	 * @param typ Anzufügender Typ.
	 */
	public void fuegeTypHinzu (T typ) {
		typen.add(typ);
	}
	
	public Vector<T> getTypen() {
		return typen;
	}
}
