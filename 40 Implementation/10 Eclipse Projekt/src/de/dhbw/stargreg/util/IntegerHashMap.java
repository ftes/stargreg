package de.dhbw.stargreg.util;

import java.util.HashMap;

/**
 * Vereinfachte HashMap zum Umgang mit Integers als value-Elemente.
 * Die get-Methode wurde überschrieben, sodass bei Nicht-Vorhandensein
 * des Keys stall {@code null} 0 zurückgeliefert wird, und es gibt nun
 * eine {@code add}-Methode.
 * @author fredrik
 *
 * @param <K>
 */
public class IntegerHashMap<K> extends HashMap<K, Integer> {

	private static final long serialVersionUID = 1L;
	
	public Integer get(Object key) {
		if (! super.containsKey(key)) return 0;
		return super.get(key);
	}
	
	public void add(K key, Integer value) {
		super.put(key, get(key) + value);
	}
	
	public boolean subtract(K key, Integer value) {
		if (get(key) - value < 0) return false;
		super.put(key, get(key) - value);
		return true;
	}

}
