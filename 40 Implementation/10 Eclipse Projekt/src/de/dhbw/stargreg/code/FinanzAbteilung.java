package de.dhbw.stargreg.code;


/**
 * Finanzabteilung eines Unternehmens.
 * @author Britta
 *
 */
public class FinanzAbteilung extends Abteilung {
	private double kapital = 0;
	private final double startKapital;

	public FinanzAbteilung(Unternehmen unternehmen, double startKapital) {
		super(unternehmen);
		this.startKapital = startKapital;
		einzahlen(startKapital);
	}

	/**
	 * Zahlt einen beliebigen Betrag auf das Konto ein.
	 * @param betrag
	 */
	public void einzahlen (double betrag){
		if (betrag < 0) {
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
		if (betrag < 0) {
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
	
	public double getStartKapital() {
		return startKapital;
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		if (!aktuelleSpielRunde) return;
		System.out.printf("Kontostand: %.2f\nZinsaufwendungen: %.2f\n", kapital, getZinskosten());
	}

}
