package de.dhbw.stargreg.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import de.dhbw.stargreg.code.RaumschiffTyp;
import de.dhbw.stargreg.code.Unternehmen;
import de.dhbw.stargreg.code.Verkauf;


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
	
	public static HashMap<Unternehmen, Vector<Verkauf>> gruppiereVerkaeufeNachUnternehmen(Vector<Verkauf> verkaeufe) {
		return gruppiereVector(verkaeufe, new Gruppierung<Unternehmen, Verkauf>() {
			public Unternehmen nach(Verkauf verkauf) {
				return verkauf.getUnternehmen();
			}
		});
	}
	
	public static <O> Vector<O> filtereVector(Vector<O> vector, Filter<O> filter) {
		Vector<O> ergebnis = new Vector<O>();
		for (O object : vector) {
			if (filter.nach(object)) {
				ergebnis.add(object);
			}
		}
		return ergebnis;
	}
	
	public static <T> double summiereVector(Vector<T> vector, Summe<T> summe) {
		double tmp = 0;
		for (T object : vector) {
			tmp += summe.von(object);
		}
		return tmp;		
	}
	
	public static double summiereVerkaeufe(Vector<Verkauf> verkaeufe) {
		return summiereVector(verkaeufe, new Summe<Verkauf>() {
			public double von(Verkauf verkauf) {
				return verkauf.getKosten();
			}
		});
	}
	
	public static double summiereHashMap(HashMap<?, Double> hashMap) {
		Vector<Double> vector = new Vector<Double>(hashMap.values());
		return summiereVector(vector, new Summe<Double>() {
			public double von(Double dbl) {
				return dbl;
			}
		});
	}
	
	public static <G, O> HashMap<G, Double> gruppiereUndSummiereVector(Vector<O> vector, Gruppierung<G, O> gruppierung, Summe<O> summe) {
		HashMap<G, Vector<O>> gruppiert = gruppiereVector(vector, gruppierung);
		HashMap<G, Double> summiert = new HashMap<G, Double>();
		for (G gruppe : gruppiert.keySet()) {
			summiert.put(gruppe, summiereVector(gruppiert.get(gruppe), summe));
		}
		return summiert;
	}
	
	public static HashMap<Unternehmen, Double> gruppiereUndSummiereVerkaeufeNachUnternehmen(Vector<Verkauf> verkaeufe) {
		return gruppiereUndSummiereVector(
				verkaeufe,
				new Gruppierung<Unternehmen, Verkauf>() {
					public Unternehmen nach(Verkauf verkauf) {
						return verkauf.getUnternehmen();
					}
				},
				new Summe<Verkauf>() {
					public double von(Verkauf verkauf) {
						return verkauf.getKosten();
					}
				}
		);
	}
	
	public static HashMap<RaumschiffTyp, Double> gruppiereUndSummiereVerkaeufeNachRaumschiffTyp(Vector<Verkauf> verkaeufe) {
		return gruppiereUndSummiereVector(
				verkaeufe,
				new Gruppierung<RaumschiffTyp, Verkauf>() {
					public RaumschiffTyp nach(Verkauf verkauf) {
						return verkauf.getTyp();
					}
				},
				new Summe<Verkauf>() {
					public double von(Verkauf verkauf) {
						return verkauf.getKosten();
					}
				}
		);
	}
	
	public static <G> Vector<G> sortiere(final HashMap<G, Double> hashMap, boolean aufsteigend) {
		Vector<G> vector = new Vector<G>(hashMap.keySet());
		Collections.sort(vector, new Comparator<G>() {
			public int compare(G arg1, G arg2) {
				return hashMap.get(arg1).compareTo(hashMap.get(arg2));
			}
		});
		if (! aufsteigend) Collections.reverse(vector);
		return vector;
	}
	
	public static void printSpacer() {
		System.out.println("----------------------------------------");
	}
}