package de.dhbw.stargreg.util;

/**
 * Interface f�r die Gruppierungs-Funktion
 * Mit diesem Interface muss beim Ausf�hren von {@code Util.gruppiereVector()} eine Insatz erzeugt werden
 * @author fredrik
 *
 * @param <G> Generischer Typ der Gruppen
 * @param <O> Generischer Typ der zu gruppierenden Objekte
 */
public interface Gruppierung<G, O> {
	public G nach(O object);
}