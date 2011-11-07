package de.dhbw.stargreg.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilMarkt;
import de.dhbw.stargreg.code.BauteilTyp;
import de.dhbw.stargreg.code.KapitalMarkt;
import de.dhbw.stargreg.code.PersonalMarkt;
import de.dhbw.stargreg.code.PersonalTyp;
import de.dhbw.stargreg.code.RaumschiffMarkt;
import de.dhbw.stargreg.code.RaumschiffTyp;
import de.dhbw.stargreg.code.Spiel;
import de.dhbw.stargreg.code.Unternehmen;
import de.dhbw.stargreg.util.Util;

public class SpielTest {

	private static final double startKapital = 1000000;

	private static Spiel spiel = new Spiel();
	private static BauteilMarkt bauteilMarkt = spiel.getBauteilMarkt();
	private static PersonalMarkt personalMarkt = spiel.getPersonalMarkt();
	private static RaumschiffMarkt raumschiffMarkt = spiel.getRaumschiffMarkt();
	private static KapitalMarkt kapitalMarkt = spiel.getKapitalMarkt();

	private static BauteilTyp rumpf = new BauteilTyp("Rumpfbauteil", 1, 100.0, 40.0, BauteilTyp.Art.STANDARD);
	private static BauteilTyp hitzeschild = new BauteilTyp("Hitzeschild", 2, 200.0, 80.0, BauteilTyp.Art.STANDARD);
	private static BauteilTyp triebwerk = new BauteilTyp("Triebwerk", 5, 500.0, 200.0, BauteilTyp.Art.STANDARD);
	private static BauteilTyp geschuetz = new BauteilTyp("Geschütz", 10, 1000.0, 400.0, BauteilTyp.Art.SONDER);
	private static BauteilTyp transport = new BauteilTyp("Transportkapsel", 20, 2000.0, 800.0, BauteilTyp.Art.SONDER);
	private static BauteilTyp forschung = new BauteilTyp("Forschungsausstattung", 30, 3000.0, 1200.0, BauteilTyp.Art.SONDER);

	private static RaumschiffTyp xwing = new RaumschiffTyp("X-Wing", 5, 0.50);
	private static RaumschiffTyp corvette = new RaumschiffTyp ("Correllian Corvette", 10, 0.50);
	private static RaumschiffTyp falke = new RaumschiffTyp("Millenium Falke", 15, 0.50);

	private static PersonalTyp droideka = new PersonalTyp("Droideka", 0.99, 100, 800, null, null);
	private static PersonalTyp kampfDroide = new PersonalTyp("Kampf-Droide", 0.84, 100, 600, 300.0, droideka);
	private static PersonalTyp r2d2 = new PersonalTyp("R2D2", 0.69, 100, 400, 300.0, kampfDroide);

	private static Unternehmen foederation = spiel.fuegeUnternehmenHinzu("Föderation", startKapital);
	private static Unternehmen imperium = spiel.fuegeUnternehmenHinzu("Imperium", startKapital);
	private static Unternehmen rebellen = spiel.fuegeUnternehmenHinzu("Rebellen", startKapital);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.print("Interkativer Modus (j/n) - zum Abbrechen im Spielablauf vor 'Enter' Zeichen eingeben: ");
		Util.setPraesentation(
				new BufferedReader(new InputStreamReader(System.in)).readLine().
				equals("j"));		

		xwing.fuegeBauteilHinzu(rumpf, 18);
		xwing.fuegeBauteilHinzu(hitzeschild, 6);
		xwing.fuegeBauteilHinzu(triebwerk, 4);
		xwing.fuegeBauteilHinzu(geschuetz, 1);

		corvette.fuegeBauteilHinzu(rumpf, 38);
		corvette.fuegeBauteilHinzu(hitzeschild, 16);
		corvette.fuegeBauteilHinzu(triebwerk, 6);
		corvette.fuegeBauteilHinzu(transport, 1);

		falke.fuegeBauteilHinzu(rumpf, 40);
		falke.fuegeBauteilHinzu(hitzeschild, 30);
		falke.fuegeBauteilHinzu(triebwerk, 10);
		falke.fuegeBauteilHinzu(forschung, 1);

		personalMarkt.fuegeTypHinzu(r2d2);
		personalMarkt.fuegeTypHinzu(kampfDroide);
		personalMarkt.fuegeTypHinzu(droideka);

		raumschiffMarkt.fuegeTypHinzu(xwing);
		raumschiffMarkt.fuegeTypHinzu(corvette);
		raumschiffMarkt.fuegeTypHinzu(falke);

		bauteilMarkt.fuegeTypHinzu(rumpf);
		bauteilMarkt.fuegeTypHinzu(hitzeschild);
		bauteilMarkt.fuegeTypHinzu(triebwerk);
		bauteilMarkt.fuegeTypHinzu(geschuetz);
		bauteilMarkt.fuegeTypHinzu(transport);
		bauteilMarkt.fuegeTypHinzu(forschung);

		kapitalMarkt.setZinssatz(0.25);
		bauteilMarkt.setLagerPlatzEinheitKosten(10);


		//Spielrunden erstellen
		RaumschiffTyp[] r = {xwing, corvette, falke};
		//Nachfrage
		int[][] n = {{60, 30, 20}, //1
				{66, 30, 24}, //2
				{72, 27, 24}, //3
				{66, 27, 24}, //4
				{78, 36, 28}, //5
				{78, 36, 28}, //6
				{84, 48, 28}, //7
				{84, 48, 28}, //8
				{96, 39, 22}, //9
				{90, 39, 24}};//10

		//Personal Konjunkturfaktoren
		double[] p = {1, 1, 1.5, 1.2, 1, 1, 0.8, 0.9, 1.2, 1};

		String[] s = {"Keine besonderen Vorkommnisse.", //1
				"Keine besonderen Vorkommnisse.", //2
				"Aufgrund der angekündigten Verkürzung der Restlaufzeit von Droiden mit defektem Motivator kam es zu erheblichen Unruhen, die in einem intergalaktischem Streik mündeten.\n" +
				"Daher sind die Personalkosten stark erhöht.", //3
				"Die angespannte Stimmung der Droiden hat sich nach einigen Konzessionen der Regierung weitesgehend gelegt.", //4
				"Die Erfindung des Warp-Antriebs sorgt für einen Boom des Raumschiff-Marktes. Die Nachfrage hat sich erhöht!", //5
				"Keine besonderen Vorkommnisse.", //6
				"Ein Forschungsschiff entdeckte kürzlich den Wüstenplaneten Kaellon. Hohe Aufkommen wertvoller Mineralien und eine handelsfreudige Bevölkerung beflügeln den intergalaktischen Handel.", //7
				"Keine besonderen Vorkommnisse.", //8
				"Ein Missverständnis zwischen Diplomaten der verfeindeten Zwillingsplaneten im Orion-System führte zur völligen Vernichtung des Sonnensystems.\n" +
				"Dies nahmen die kriegsfreudigen Völker umliegender System zum Anlass, einen Krieg ausbrechen zu lassen, der mittlerweile alle Ecken des Universums erfasst hat.", //9
		"Die Kriegszustände halten nach wie vor an, allerdings ist durch Bemühungen der friedlichen Handelsvölker ein Ende der Auseinandersetzungen in Sicht."};//10

		for (int i=0; i<n.length; i++) {
			HashMap<RaumschiffTyp, Integer> nachfrage = new HashMap<RaumschiffTyp, Integer>();
			String nachricht = s[i];

			for (int j=0; j<n[i].length; j++) {
				nachfrage.put(r[j], n[i][j]);
			}

			double personalKonjunkturFaktor = p[i];

			spiel.fuegeSpielRundeHinzu(nachfrage, personalKonjunkturFaktor, nachricht);
		}

		Util.printHeading("Spiel eingerichtet");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Util.setPraesentation(true);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGesamtAblauf() {
		spiel.starteSpiel();

		runde1();		
		spiel.simuliere();

		runde2();
		spiel.simuliere();

		runde3();
		spiel.simuliere();

		runde4();
		spiel.simuliere();

		runde5();
		spiel.simuliere();

		runde6();
		spiel.simuliere();

		runde7();
		spiel.simuliere();

		runde8();
		spiel.simuliere();

		runde9();
		spiel.simuliere();

		runde10();
		spiel.simuliere();


		Vector<Unternehmen> rangfolge = spiel.bewerteUnternehmen();
		
		Unternehmen[] erwartet = {foederation, rebellen, imperium};
		Assert.assertArrayEquals(erwartet, rangfolge.toArray(erwartet));
	}

	private void runde1() {
		//Informieren, Handeln und einchecken
		//Foederation: wie in Datenbasis
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().einstellen(r2d2, 600);
		foederation.kaufeEinProduziereVerkaufe(xwing, 41, 12000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 20, 24000);
		foederation.kaufeEinProduziereVerkaufe(falke, 13, 36000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//Kampfpreise
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().einstellen(r2d2, 1000);
		imperium.kaufeEinProduziereVerkaufe(xwing, 200, 8000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//Qualitäts-fokussiert
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().einstellen(droideka, 475);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 10, 35000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 25, 50000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde2() {
		//mehr Corvettes, Preise anheben, besseres Personal
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().schulen(r2d2, 300);
		foederation.getPersonal().einstellen(kampfDroide, 250);
		foederation.kaufeEinProduziereVerkaufe(xwing, 46, 14000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 38, 28000);
		foederation.kaufeEinProduziereVerkaufe(falke, 16, 44000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//zu wenig DB und diversifizieren, auch Corvettes
		imperium.gebeAnfangsInformationenAus();
		imperium.kaufeEinProduziereVerkaufe(xwing, 60, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 70, 18000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//denkt sich: weiter so, Preise höher
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().einstellen(droideka, 225);
		rebellen.kaufeEinProduziereVerkaufe(xwing, 20, 25000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 21, 40000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 26, 55000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde3() {
		//weniger Corvette, mehr Falken, kein Personal einstellen!
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().schulen(r2d2, 300);
		foederation.kaufeEinProduziereVerkaufe(xwing, 55, 16000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 20, 24000);
		foederation.kaufeEinProduziereVerkaufe(falke, 25, 44000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//weniger Fehlerkosten, auch Falken
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().schulen(r2d2, 500);
		imperium.kaufeEinProduziereVerkaufe(xwing, 60, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 40, 18000);
		imperium.kaufeEinProduziereVerkaufe(falke, 20, 27000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//weniger Corvette, mehr Falken, gerade kein Personal einstellen
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 21, 25000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 10, 32000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 33, 55000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde4() {
		//Corvette günstiger, mehr Xwing, große Schulung
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().schulen(kampfDroide, 850);
		foederation.kaufeEinProduziereVerkaufe(corvette, 5, 20000);
		foederation.kaufeEinProduziereVerkaufe(xwing, 76, 16000);
		foederation.kaufeEinProduziereVerkaufe(falke, 28, 44000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//weiter so, Produktion ausweiten, Preise leicht anheben
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().einstellen(kampfDroide, 250);
		imperium.kaufeEinProduziereVerkaufe(xwing, 78, 11000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 50, 20000);
		imperium.kaufeEinProduziereVerkaufe(falke, 24, 30000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//läuft sehr gut so, mehr Xwings, andere günstiger
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 60, 25000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 10, 25000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 20, 45000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde5() {
		//weitere so, Produktion ausweiten, Corvette leicht teuerer
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().einstellen(droideka, 450);
		foederation.kaufeEinProduziereVerkaufe(xwing, 80, 16000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 48, 25000);
		foederation.kaufeEinProduziereVerkaufe(falke, 28, 44000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//leicht höhere Preise, Produktion ausweiten (-Corvette), Personal schulen
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().schulen(r2d2, 500);
		imperium.kaufeEinProduziereVerkaufe(xwing, 80, 13000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 40, 22000);
		imperium.kaufeEinProduziereVerkaufe(falke, 30, 34000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//Preise drastisch senken, etwas Personal entlassen
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().entlassen(droideka, 200);
		rebellen.kaufeEinProduziereVerkaufe(xwing, 40, 20000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 15, 25000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 10, 40000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde6() {
		//mehr X-Wing, Corvette und Falke leicht günstiger
		foederation.gebeAnfangsInformationenAus();
		foederation.kaufeEinProduziereVerkaufe(xwing, 100, 18000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 44, 22000);
		foederation.kaufeEinProduziereVerkaufe(falke, 24, 40000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//weniger Corvette, wieder höhere Produktion und niedrigere Preise
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().einstellen(kampfDroide, 500);
		imperium.kaufeEinProduziereVerkaufe(xwing, 130, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 41, 18000);
		imperium.kaufeEinProduziereVerkaufe(falke, 46, 25000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//X-Wing günstiger, andere etwas teuerer
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 20, 16000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 19, 28000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 14, 44000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde7() {
		//X-Wings und Corvette günstiger, mehr Corvette produzieren
		foederation.gebeAnfangsInformationenAus();
		foederation.kaufeEinProduziereVerkaufe(xwing, 60, 14000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 61, 19000);
		foederation.kaufeEinProduziereVerkaufe(falke, 26, 38000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//ist gut gelaufen so, etwas mehr Corvette
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().einstellen(kampfDroide, 200);
		imperium.kaufeEinProduziereVerkaufe(xwing, 130, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 61, 18000);
		imperium.kaufeEinProduziereVerkaufe(falke, 46, 25000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//so weiter, ein paar mehr Xwings
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 26, 16000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 19, 28000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 12, 44000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde8() {
		//im Vgl. mehr Falken, Preise senken, etwas Personal entlassen
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().entlassen(droideka, 150);
		foederation.kaufeEinProduziereVerkaufe(xwing, 47, 12000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 45, 16000);
		foederation.kaufeEinProduziereVerkaufe(falke, 31, 35000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//weiter so, Produktion ausweiten
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().einstellen(kampfDroide, 400);
		imperium.kaufeEinProduziereVerkaufe(xwing, 160, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 74, 18000);
		imperium.kaufeEinProduziereVerkaufe(falke, 54, 25000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//genau so weitere, Corvette und Falken ganz leicht günstiger
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 26, 16000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 19, 26000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 12, 41000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde9() {
		//mehr X-wings, weniger Falken
		foederation.gebeAnfangsInformationenAus();
		foederation.kaufeEinProduziereVerkaufe(xwing, 62, 12000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 45, 16000);
		foederation.kaufeEinProduziereVerkaufe(falke, 26, 35000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//Corvette Markt mehr überschwemmen, mehr Falken
		imperium.gebeAnfangsInformationenAus();
		imperium.kaufeEinProduziereVerkaufe(xwing, 140, 10000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 81, 14000);
		imperium.kaufeEinProduziereVerkaufe(falke, 56, 22000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//etwas mehr Xwings
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 33, 16000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 17, 26000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 11, 41000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde10() {
		//mehr X-Wings, Falken KEINE und günstiger, Personal entlassen
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().entlassen(droideka, 250);
		foederation.kaufeEinProduziereVerkaufe(xwing, 90, 12000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 45, 14000);
		foederation.kaufeEinProduziereVerkaufe(falke, 0, 30000);
		foederation.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//weniger Corvette, günstiger
		imperium.gebeAnfangsInformationenAus();
		imperium.getPersonal().entlassen(kampfDroide, 200);
		imperium.kaufeEinProduziereVerkaufe(xwing, 150, 9000);
		imperium.kaufeEinProduziereVerkaufe(corvette, 41, 13000);
		imperium.kaufeEinProduziereVerkaufe(falke, 66, 20000);
		imperium.gebeEndInformationenAus();
		imperium.rundeEinchecken();

		//Corvette günstiger, mehr XWings
		rebellen.gebeAnfangsInformationenAus();
		rebellen.kaufeEinProduziereVerkaufe(xwing, 43, 17000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 12, 24000);
		rebellen.kaufeEinProduziereVerkaufe(falke, 11, 41000);
		rebellen.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}
}
