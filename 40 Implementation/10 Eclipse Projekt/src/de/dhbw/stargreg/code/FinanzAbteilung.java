package de.dhbw.stargreg.code;

import de.dhbw.stargreg.util.TableBuilder;
import de.dhbw.stargreg.util.Util;


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
	 * Bucht den angegebenen Betrag vom Konto ab.
	 * @param betrag Muss positiv sein.
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
		unternehmen.getSpiel().getAktuelleSpielRunde().fuegeZahlungHinzu(new Zahlung(getZinskosten(), Zahlung.Art.ZINSEN, unternehmen));
//		System.out.printf("%.2f Zinsaufwendungen abgebucht\n", getZinskosten());
	}
	
	public double getStartKapital() {
		return startKapital;
	}

	@Override
	public void gebeInformationenAus(boolean aktuelleSpielRunde) {
		SpielRunde spielRunde = getSpielRunde(aktuelleSpielRunde);
		System.out.println("Finanzen");
		TableBuilder tb = new TableBuilder("Art", "Betrag");
		if (! aktuelleSpielRunde) {
			if (spielRunde != null) {
				double einkaeufe = -spielRunde.getSummeTransaktionen(Einkauf.class, unternehmen);
				double verkaeufe = spielRunde.getSummeTransaktionen(Verkauf.class, unternehmen);
				double personal = -spielRunde.getSummeTransaktionen(Einstellung.class, unternehmen) + 
						spielRunde.getSummeTransaktionen(Aufruestung.class, unternehmen);
				tb.addNewRow("Verkäufe", String.format("%.2f", verkaeufe));
				tb.addNewRow("Einkäufe", String.format("%.2f", einkaeufe));
				tb.addNewRow("Personal (einmalig)", String.format("%.2f", personal));
				tb.addNewRow("Personal (laufend)", String.format("%.2f", -spielRunde.getZahlung(Zahlung.Art.PERSONAL, unternehmen).getBetrag()));
				tb.addNewRow("Fehler", String.format("%.2f", -spielRunde.getZahlung(Zahlung.Art.FEHLER, unternehmen).getBetrag()));
				tb.addNewRow("Lager", String.format("%.2f", -spielRunde.getZahlung(Zahlung.Art.LAGER, unternehmen).getBetrag()));
				tb.addNewRow("Zinsen", String.format("%.2f", -spielRunde.getZahlung(Zahlung.Art.ZINSEN, unternehmen).getBetrag()));
			}
		} else {
			double einkaeufe = -Util.summiereTypTransaktionen(unternehmen.getSpiel().getBauteilMarkt().getTransaktionen(unternehmen));
			double personal = -Util.summiereTypTransaktionen(unternehmen.getSpiel().getPersonalMarkt().getTransaktionen(unternehmen));
			tb.addNewRow("Einkäufe", String.format("%.2f", einkaeufe));
			tb.addNewRow("Personal (einmalig)", String.format("%.2f", personal));
		}
		
		tb.hline();
		tb.addNewRow("Kontostand", String.format("%.2f", kapital));
		tb.print();
	}

}
