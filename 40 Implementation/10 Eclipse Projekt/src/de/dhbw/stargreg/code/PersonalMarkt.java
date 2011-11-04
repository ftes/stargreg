package de.dhbw.stargreg.code;

import de.dhbw.stargreg.util.TableBuilder;


/**
 * Auf dem Personalmarkt können Unternehmen zusätzliches Personal einstellen, schulen oder entlassen.
 * Personal existiert in drei Stufen, die sich in ihrer Qualität und den Kosten unterscheiden.
 * Da die Werbungs- und laufenden Kosten aufgrund des Spielverlaufes variieren, werden diese für
 * jede Runde seperat im Personalmarkt gespeichert.
 * @author fredrik
 *
 */
public class PersonalMarkt extends Markt<PersonalTyp, PersonalTransaktion> {
	public void gebeKostenAus() {
		TableBuilder tb = new TableBuilder("PersonalTyp", "Qualität", "Laufende Kosten", "Werbungskosten", "Schulungskosten");
		for (PersonalTyp typ : typen) {
			tb.addNewRow(typ,
					String.format("%.0f", typ.getQualitaet() * 100) + " %",
					String.format("%.2f", typ.getLaufendeKosten()),
					String.format("%.2f", typ.getWerbungsKosten()),
					(typ.getSchulungsKosten() == null) ? "" : String.format("%.2f", typ.getSchulungsKosten()));
		}
		tb.print();
	}
}
