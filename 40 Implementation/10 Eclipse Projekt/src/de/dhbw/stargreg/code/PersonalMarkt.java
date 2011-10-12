package de.dhbw.stargreg.code;

import java.util.HashMap;
import java.util.Vector;

/**
 * Auf dem Personalmarkt k�nnen Unternehmen zus�tzliches Personal einstellen, schulen oder entlassen.
 * Personal existiert in drei Stufen, die sich in ihrer Qualit�t und den Kosten unterscheiden.
 * Da die Werbungs- und laufenden Kosten aufgrund des Spielverlaufes variieren, werden diese f�r
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
	 * laufende Kosten f�r das Personal, also Wartungskosten / Gehalt
	 */
	private HashMap<PersonalTyp, Double> laufendeKosten;
	
	/**
	 * einmalige Werbungskosten f�r das Personal, also Einkauf / Bewerberauswahl
	 */
	private HashMap<PersonalTyp, Double> werbungsKosten;
	
	/**
	 * Liste mit allen Personal-Transaktionen dieser Runde
	 */
	private Vector<PersonalTransaktion> transaktionen = new Vector<PersonalTransaktion>();
	
	/**
	 * F�gt den PersonalTyp an die Liste aller PersonalTypen an
	 * @param personalTyp Anzuf�gender PersonalTyp
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
