package de.dhbw.stargreg.util;

public interface Gruppierung<G, O> {
	public G nach(O object);
}