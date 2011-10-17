package de.dhbw.stargreg.util;

import java.util.HashMap;
import java.util.Vector;


/**
 * Hilfsfunktionen, die inhaltlich keiner anderen Klasse zugeordnet werden können
 * 
 * @author fredrik
 * 
 */
public class Util {
	/**
	 * Gruppiert die Elemente im {@code vector} nach einem Merkmal, das durch {@code gruppierung.nach()} definiert wird
	 * 
	 * @param vector Eingabe-{@code Vector}, der die zu gruppierenden Objekte enthält
	 * @param gruppierung Enthält die Methode {@code nach()}, die das Gruppierungs-Merkmal zurückliefert
	 * @return Alle Objekte aus {@code vector} in einer{@code HashMap} gruppiert
	 */
	public static <G, O> HashMap<G, Vector<O>> gruppiereVector(Vector<O> vector, Gruppierung<G, O> gruppierung) {
		HashMap<G, Vector<O>> ergebnis = new HashMap<G, Vector<O>>();
		for (O object : vector) {
			G gruppe = gruppierung.nach(object);
			if (! ergebnis.containsKey(gruppe)) {
				ergebnis.put(gruppe, new Vector<O>());
			}
			ergebnis.get(gruppe).add(object);
		}
		
		return ergebnis;
	}
}