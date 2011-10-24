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
			return;
		}
		this.kapital += betrag;
	}

	/**
	 * 
	 * @param betrag
	 * @return
	 */
	public boolean abbuchen (double betrag, KapitalMarkt zinsen){
/*		if (betrag > this.kapital) {
			System.err.println("Kapital zu niedrig");
			return false;
		} // Falls Konto nicht überzogen werden kann
*/		
		this.kapital -= betrag;
		return true;
	}

	public double getKontostand(){
		return this.kapital;
	}
	
	public double getZinskosten(KapitalMarkt zinsen) {
		double zinskosten = 0;
		if (this.kapital < 0) {
			zinskosten = this.kapital * zinsen.getZinssatz();
		}
		return zinskosten;
	}
	/**
	 * Kosten bei Überziehung berechnen. Dies ist nötig, weil beispielsweise unklar
	 * ist, wie viele Raumschiffe gelagert werden, da dies von der absetzbaren menge
	 * abhängig ist.
	 */
	public void simuliere() {
		
	}

}
