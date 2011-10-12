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
	private HashMap<PersonalTyp, Double> laufendeKosten;
	
	/**
	 * einmalige Werbungskosten für das Personal, also Einkauf / Bewerberauswahl
	 */
	private HashMap<PersonalTyp, Double> werbungsKosten;
	
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

	public void setLaufendeKosten(HashMap<PersonalTyp, Double> laufendeKosten) {
		this.laufendeKosten = laufendeKosten;
	}
	
	public void setWerbungsKosten(HashMap<PersonalTyp, Double> werbungsKosten) {
		this.werbungsKosten = werbungsKosten;
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
}
