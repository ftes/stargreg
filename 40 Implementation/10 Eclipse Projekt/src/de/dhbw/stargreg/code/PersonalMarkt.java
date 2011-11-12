package de.dhbw.stargreg.code;

import de.dhbw.stargreg.util.TableBuilder;


/**
 * Auf dem Personalmarkt können Unternehmen zusätzliches Personal einstellen, aufrüsten oder entlassen.
 * Personal existiert in drei Stufen, die sich in ihrer Qualität und den Kosten unterscheiden.
 * Da die Werbungs- und laufenden Kosten aufgrund des Spielverlaufes variieren, werden diese für
 * jede Runde seperat im Personalmarkt gespeichert.
 * @author fredrik
 *
 */
public class PersonalMarkt extends TypMarkt<PersonalTyp, PersonalTransaktion> {
	public void gebeKostenAus() {
		System.out.println("Personalmarkt");
		TableBuilder tb = new TableBuilder("PersonalTyp", "Qualität", "Laufende Kosten", "Werbungskosten", "Aufrüstungskosten");
		for (PersonalTyp typ : typen) {
			tb.addNewRow(typ,
					String.format("%.0f", typ.getQualitaet() * 100) + " %",
					String.format("%.2f", typ.getLaufendeKosten()),
					String.format("%.2f", typ.getWerbungsKosten()),
					(typ.getAufruestungsKosten() == null) ? "" : String.format("%.2f", typ.getAufruestungsKosten()));
		}
		tb.print();
	}

	public void simuliere() {
		
	}
}
