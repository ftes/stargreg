package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

/**
 * Auf dem Personalmarkt können Unternehmen zusätzliches Personal einstellen, schulen oder entlassen.
 * Personal existiert in drei Stufen, die sich in ihrer Qualität und den Kosten unterscheiden.
 * Da die Werbungs- und laufenden Kosten aufgrund des Spielverlaufes variieren, werden diese für
 * jede Runde seperat im Personalmarkt gespeichert.
 * @author fredrik
 *
 */
public class PersonalMarkt extends Markt {
	
	/**
	 * Liste aller Personaltypen
	 */
	private static Vector<PersonalTyp> personalTypen = new Vector<PersonalTyp>();

	/**
	 * laufende Kosten für das Personal, also Wartungskosten / Gehalt
	 */
	private HashMap<PersonalTyp, Double> laufendeKosten = new HashMap<PersonalTyp, Double>();
	
	/**
	 * einmalige Werbungskosten für das Personal, also Einkauf / Bewerberauswahl
	 */
	private HashMap<PersonalTyp, Double> werbungsKosten = new HashMap<PersonalTyp, Double>();
	
	/**
	 * Schulungskosten für das Personal
	 */
	private static HashMap<PersonalTyp, Double> schulungsKosten = new HashMap<PersonalTyp, Double>();
	
	/**
	 * Liste mit allen Personal-Transaktionen dieser Runde
	 */
	private Vector<PersonalTransaktion> transaktionen = new Vector<PersonalTransaktion>();
	
	/**
	 * Fügt den PersonalTyp an die Liste aller PersonalTypen an
	 * @param personalTyp Anzufügender PersonalTyp
	 */
	public static void fuegePersonalTypHinzu(PersonalTyp personalTyp) {
		personalTypen.add(personalTyp);
	}
	
	/**
	 * Fügt die Schulungskosten zu der Liste hinzu
	 * @param personalTyp PersonalTyp, der geschult werden soll
	 * @param kosten Dabei anfallende Kosten
	 */
	public static void setSchulungsKosten(PersonalTyp personalTyp, double kosten) {
		schulungsKosten.put(personalTyp, kosten);
	}
	
	/**
	 * gibt die durch eine Schulung anfallenden Kosten zurück
	 * @param personalTyp
	 */
	public static double getSchulungsKosten(PersonalTyp personalTyp) {
		return schulungsKosten.get(personalTyp);
	}

	/**
	 * Speichert die zum PersonalTyp zugehörigen laufenden Kosten
	 * @param personalTyp PersonalTyp
	 * @param laufendeKosten Laufende Kosten
	 */
	public void setLaufendeKosten(PersonalTyp personalTyp, double laufendeKosten) {
		this.laufendeKosten.put(personalTyp, laufendeKosten);
	}
	
	/**
	 * Speichert die zum PersonlTyp zugehörigen Werbungskosten
	 * @param personalTyp PersonalTyp
	 * @param werbungsKosten Werbungskosten
	 */
	public void setWerbungsKosten(PersonalTyp personalTyp, double werbungsKosten) {
		this.werbungsKosten.put(personalTyp, werbungsKosten);
	}
	
	public double getLaufendeKosten(PersonalTyp personalTyp) {
		return this.laufendeKosten.get(personalTyp);
	}
	
	public double getWerbungsKosten(PersonalTyp personalTyp) {
		return this.werbungsKosten.get(personalTyp);
	}
	
	public void fuegeTransaktionHinzu(PersonalTransaktion transaktion) {
		transaktionen.add(transaktion);
	}
	
	public static Vector<PersonalTyp> getPersonalTypen() {
		return personalTypen;
	}
	
	public static void loeschePersonalTypen() {
		personalTypen.clear();
		schulungsKosten.clear();
	}
	
	public static void fuegeTypHinzu(Typ typ) {
		
	}
}
