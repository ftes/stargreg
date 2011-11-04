package de.dhbw.stargreg.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
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
	
	private static PersonalTyp droideka = new PersonalTyp("Droideka", 0.99, null, null);
	private static PersonalTyp kampfDroide = new PersonalTyp("Kampf-Droide", 0.90, 300.0, droideka);
	private static PersonalTyp r2d2 = new PersonalTyp("R2D2", 0.80, 300.0, kampfDroide);
	
	private static Unternehmen galactic = spiel.fuegeUnternehmenHinzu("Galactic", startKapital);
	private static Unternehmen foederation = spiel.fuegeUnternehmenHinzu("Föderation", startKapital);
	private static Unternehmen rebellen = spiel.fuegeUnternehmenHinzu("Rebellen", startKapital);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.print("Interkativer Modus (j/n): ");
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
		bauteilMarkt.setLagerPlatzEinheitKosten(5);
		
		
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
		
		PersonalTyp[] p = {r2d2, kampfDroide, droideka};
		//laufende Kosten
		double[][] l = {{100, 150, 200}, //1
						{100, 150, 200}, //2
						{150, 225, 300}, //3
						{120, 180, 240}, //4
						{100, 150, 200}, //5
						{100, 150, 200}, //6
						{80,  120, 160}, //7
						{90,  135, 180}, //8
						{120, 180, 240}, //9
						{100, 150, 200}};//10
		//Werbungskosten
		double[][] w = {{400, 600, 800}, //1
						{400, 600, 800}, //2
						{600, 900, 1200}, //3
						{480, 720, 960}, //4
						{400, 600, 800}, //5
						{400, 600, 800}, //6
						{320, 480, 640}, //7
						{360, 540, 720}, //8
						{480, 720, 960}, //9
						{400, 600, 800}};//10
		
		String[] s = {"Keine besonderen Vorkommnisse.", //1
					  "Keine besonderen Vorkommnisse.", //2
					  "Aufgrund der angekündigten Verkürzung der Restlaufzeit von Droiden mit defektem Motivator kam es zu erheblichen Unruhen, die in einem intergalaktischem Streik mündeten. Daher sind die Personalkosten stark erhöht.", //3
					  "Die angespannte Stimmung der Droiden hat sich nach einigen Konzessionen der Regierung weitesgehend gelegt.", //4
					  "Die Erfindung des Warp-Antriebs sorgt für einen Boom des Raumschiff-Marktes. Die Nachfrage hat sich erhöht!", //5
					  "Keine besonderen Vorkommnisse.", //6
					  "Ein Forschungsschiff entdeckte kürzlich den Wüstenplaneten Kaellon. Hohe Aufkommen wertvoller Mineralien und eine handelsfreudige Bevölkerung beflügeln den intergalaktischen Handel.", //7
					  "Keine besonderen Vorkommnisse.", //8
					  "Ein Missverständnis zwischen Diplomaten der verfeindeten Zwillingsplaneten im Orion-System führte zur völligen Vernichtung des Sonnensystems. Dies nahmen die kriegsfreudigen Völker umliegender System zum Anlass, einen Krieg anzuzetteln, der sich mittlerweile alle Ecken des Universums erfasst hat.", //9
					  "Die Kriegszustände halten nach wie vor an, allerdings ist durch Bemühungen der friedlichen Handelsvölker ein Ende der Auseinandersetzungen in Sicht."};//10
		
		for (int i=0; i<n.length; i++) {
			HashMap<RaumschiffTyp, Integer> nachfrage = new HashMap<RaumschiffTyp, Integer>();
			HashMap<PersonalTyp, Double> laufendeKosten = new HashMap<PersonalTyp, Double>();
			HashMap<PersonalTyp, Double> werbungsKosten = new HashMap<PersonalTyp, Double>();
			String nachricht = s[i];
			
			for (int j=0; j<n[i].length; j++) {
				nachfrage.put(r[j], n[i][j]);
			}
			
			for (int j=0; j<l[i].length; j++) {
				laufendeKosten.put(p[j], l[i][j]);
				werbungsKosten.put(p[j], w[i][j]);
			}
			
			spiel.fuegeSpielRundeHinzu(nachfrage, laufendeKosten, werbungsKosten, nachricht);
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
	public void testSpiel() {
		spiel.starteSpiel();
		
		runde1();		
		spiel.simuliere();
		
		runde2();
		spiel.simuliere();
		
		runde3();
		spiel.simuliere();
		
//		runde4();
		
		spiel.beendeSpiel();
		spiel.bewerteUnternehmen();
		
		//Asserts ganz zum Schluss!

		
	}
	
	private void runde1() {
		//Informieren, Handeln und einchecken
		//Galactic: wie in Datenbasis
		galactic.gebeAnfangsInformationenAus();
		galactic.getPersonal().einstellen(r2d2, 900);
		galactic.kaufeEinProduziereVerkaufe(xwing, 60, 12000);
		galactic.kaufeEinProduziereVerkaufe(corvette, 30, 24000);
		galactic.kaufeEinProduziereVerkaufe(falke, 20, 36000);
		galactic.gebeEndInformationenAus();
		galactic.rundeEinchecken();

		//Kampfpreise
		foederation.gebeAnfangsInformationenAus();
		foederation.getPersonal().einstellen(r2d2, 2000);
		foederation.kaufeEinProduziereVerkaufe(xwing, 120, 8000);
		foederation.kaufeEinProduziereVerkaufe(corvette, 140, 15000);
		galactic.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//Qualitäts-fokussiert
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().einstellen(droideka, 300);
		rebellen.kaufeEinProduziereVerkaufe(falke, 20, 50000);
		galactic.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}
	
	private void runde2() {
		//soweit alles i.O., macht ähnlich weiter
		galactic.gebeAnfangsInformationenAus();
		galactic.getPersonal().schulen(r2d2, 900);
		galactic.getPersonal().einstellen(kampfDroide, 900);
		galactic.kaufeEinProduziereVerkaufe(xwing, 120, 12000);
		galactic.kaufeEinProduziereVerkaufe(corvette, 60, 24000);
		galactic.kaufeEinProduziereVerkaufe(falke, 40, 36000);
		galactic.gebeEndInformationenAus();
		galactic.rundeEinchecken();

		//stellt fest, dass wenig Corvettes verkauft
		foederation.gebeAnfangsInformationenAus();
		foederation.getVerkauf().macheAngebot(corvette, 14000);
		foederation.kaufeEinProduziereVerkaufe(xwing, 120, 8000);
		foederation.kaufeEinProduziereVerkaufe(falke, 30, 22000);
		galactic.gebeEndInformationenAus();
		foederation.rundeEinchecken();

		//denkt sich: weiter so!
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().einstellen(droideka, 600);
		rebellen.kaufeEinProduziereVerkaufe(falke, 60, 50000);
		galactic.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}

	private void runde3() {
		//muss Preise senken
		//will kein neues Personal wegen Streik einstellen
		//sieht Chancen bei Falke
		galactic.gebeAnfangsInformationenAus();
		galactic.kaufeEinProduziereVerkaufe(falke, 40, 27000);
		galactic.getVerkauf().macheAngebot(xwing, 9000);
		galactic.getVerkauf().macheAngebot(corvette, 18000);
		galactic.gebeEndInformationenAus();
		galactic.rundeEinchecken();
		
		//ähnlich wie in Vorrunde weiter
		//hat vorhin Personal nicht voll ausgenutzt
		foederation.gebeAnfangsInformationenAus();
		foederation.kaufeEinProduziereVerkaufe(xwing, 120, 8000);
		foederation.kaufeEinProduziereVerkaufe(falke, 80, 22000);
		galactic.gebeEndInformationenAus();
		foederation.rundeEinchecken();
		
		//kaum Falken verkauft
		//diversifizieren und Preise runter
		//etwas Personal entlassen
		rebellen.gebeAnfangsInformationenAus();
		rebellen.getPersonal().entlassen(droideka, 150);
		rebellen.getVerkauf().macheAngebot(falke, 30000);
		rebellen.kaufeEinProduziereVerkaufe(xwing,  70, 12000);
		rebellen.kaufeEinProduziereVerkaufe(corvette, 40, 24000);
		galactic.gebeEndInformationenAus();
		rebellen.rundeEinchecken();
	}
	
	private void runde4() {
		//Falken noch günstiger
		//X-Wing Preis anheben
		//Personal entlassen
		galactic.gebeAnfangsInformationenAus();
		galactic.getPersonal().entlassen(kampfDroide, 800);
		galactic.getVerkauf().macheAngebot(falke, 20000);
		galactic.kaufeEinProduziereVerkaufe(xwing, 100, 11000);
		galactic.kaufeEinProduziereVerkaufe(corvette, 50, 16000);
		galactic.gebeEndInformationenAus();
		
		foederation.gebeAnfangsInformationenAus();
	}
}
