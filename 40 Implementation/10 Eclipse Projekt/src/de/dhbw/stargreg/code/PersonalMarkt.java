package de.dhbw.stargreg.code;

import java.util.HashMap;

/**
 * Auf dem Personalmarkt k�nnen Unternehmen zus�tzliches Personal einstellen, schulen oder entlassen.
 * Personal existiert in drei Stufen, die sich in ihrer Qualit�t und den Kosten unterscheiden.
 * Da die Werbungs- und laufenden Kosten aufgrund des Spielverlaufes variieren, werden diese f�r
 * jede Runde seperat im Personalmarkt gespeichert.
 * @author fredrik
 *
 */
public class PersonalMarkt extends Markt<PersonalTyp, PersonalTransaktion> {
	
	/**
	 * Schulungskosten f�r das Personal
	 */
	private static HashMap<PersonalTyp, Double> schulungsKosten = new HashMap<PersonalTyp, Double>();

	/**
	 * F�gt die Schulungskosten zu der Liste hinzu
	 * @param personalTyp PersonalTyp, der geschult werden soll
	 * @param kosten Dabei anfallende Kosten
	 */
	public static void setSchulungsKosten(PersonalTyp personalTyp, double kosten) {
		schulungsKosten.put(personalTyp, kosten);
	}
	
	/**
	 * gibt die durch eine Schulung anfallenden Kosten zur�ck
	 * @param personalTyp
	 */
	public static double getSchulungsKosten(PersonalTyp personalTyp) {
		return schulungsKosten.get(personalTyp);
	}
}
