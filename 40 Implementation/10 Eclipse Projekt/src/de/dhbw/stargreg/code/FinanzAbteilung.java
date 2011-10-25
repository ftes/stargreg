package de.dhbw.stargreg.code;

/**
 * Finanzabteilung eines Unternehmens.
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
	private double kapital = 0;

	public FinanzAbteilung(Unternehmen unternehmen) {
		super(unternehmen);
	}

	/**
	 * Zahlt einen beliebigen Betrag auf das Konto ein.
	 * @param betrag
	 */
	public void einzahlen (double betrag){
		if (betrag <= 0) {
			System.err.println("Negative Beträge nicht einzahlbar");
			return;
		}
		this.kapital += betrag;
	}

	/**
	 * 
	 * @param betrag
	 * @return
	 */
	public void abbuchen (double betrag){
		if (betrag <= 0) {
			System.err.println("Negative Beträge nicht abbuchbar");
			return;
		}
		this.kapital -= betrag;
	}

	public double getKontostand(){
		return this.kapital;
	}
	
	public double getZinskosten() {
		double zinskosten = 0;
		if (this.kapital < 0) {
			zinskosten = -this.kapital * unternehmen.getSpiel().getKapitalMarkt().getZinssatz();
		}
		return zinskosten;
	}
	
	/**
	 * Kosten bei Überziehung berechnen. Dies ist nötig, weil beispielsweise unklar
	 * ist, wie viele Raumschiffe gelagert werden, da dies von der absetzbaren menge
	 * abhängig ist.
	 */
	public void simuliere() {
		abbuchen(getZinskosten());
	}

}
