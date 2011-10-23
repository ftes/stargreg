package de.dhbw.stargreg.code;

/**
 * Am Kapitalmarkt können sich Unternehmen refinanzieren.
 * @author fredrik
 *
 */
public class KapitalMarkt extends Markt<Typ, Transaktion> {
	private static Double zinssatz = null;
	
	public void setZinssatz(double zinssatz) {
		KapitalMarkt.zinssatz = zinssatz;
	}
	
	public double getZinssatz() {
		if (zinssatz == null) {
			System.err.println("Zinssatz wurde noch nicht gesetzt");
			return 0;
		}
		return zinssatz;
	}

}
