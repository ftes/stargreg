package de.dhbw.stargreg.test;

import java.util.HashMap;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.Angebot;
import de.dhbw.stargreg.code.RaumschiffMarkt;
import de.dhbw.stargreg.code.RaumschiffTyp;
import de.dhbw.stargreg.code.Spiel;
import de.dhbw.stargreg.code.Verkauf;
import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

public class RaumschiffMarktTest {
	
	private static Spiel spiel = new Spiel();
	private static RaumschiffMarkt raumschiffMarkt = spiel.getRaumschiffMarkt();
	private static RaumschiffTyp xwing;
	private static RaumschiffTyp corvette;
	private static int nachfrageXwing = 5;
	private static int nachfrageCorvette = 3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing = new RaumschiffTyp("X-Wing", 0, 0) {
			public double getKosten() {
				return 5;
			}
		};
		corvette = new RaumschiffTyp("Corvette", 0, 0) {
			public double getKosten() {
				 return 12;
			}
		};		
		raumschiffMarkt.fuegeTypHinzu(xwing);
		raumschiffMarkt.fuegeTypHinzu(corvette);
		spiel.fuegeUnternehmenHinzu(null, 1);
		spiel.fuegeUnternehmenHinzu(null, 1);
		spiel.fuegeUnternehmenHinzu(null, 1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		xwing.setNachfrage(nachfrageXwing);
		corvette.setNachfrage(nachfrageCorvette);		
	}

	@After
	public void tearDown() throws Exception {
		raumschiffMarkt.simuliere();
	}

	@Test
	public void testBerechneTypAbsatz() {
		Vector<Angebot> angebote = new Vector<Angebot>();
		angebote.add(new Angebot(xwing, null, 10, 10.0));
		angebote.add(new Angebot(xwing, null, 5, 15.0));
		angebote.add(new Angebot(xwing, null, 10, 20.0));
		
		int gesamtMenge = 0;
		Vector<Verkauf> verkaeufe = raumschiffMarkt.berechneTypAbsatz(xwing, angebote);
		for (Verkauf verkauf : verkaeufe) {
			int menge = verkauf.getMenge();
			Assert.assertTrue(menge >= 0);
			gesamtMenge += menge;
		}
		
		Assert.assertTrue(gesamtMenge <= nachfrageXwing * spiel.getAnzahlUnternehmen());
	}
	
	@Test
	public void testBerechneGesamtAbsatz() {
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 10, 10.0));
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 5, 15.0));
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 10, 20.0));
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 3, 20.0));
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 5, 18.0));
		raumschiffMarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 2, 27.0));
		
		Vector<Verkauf> verkaeufe = raumschiffMarkt.berechneGesamtAbsatz();
		HashMap<RaumschiffTyp, Vector<Verkauf>> map = Util.gruppiereVector(verkaeufe, new Gruppierung<RaumschiffTyp, Verkauf>() {
			@Override
			public RaumschiffTyp nach(Verkauf verkauf) {
				return verkauf.getRaumschiffTyp();
			}
		});
		Assert.assertTrue(map.containsKey(xwing));
		Assert.assertTrue(map.get(xwing).size() > 0);
		Assert.assertTrue(map.containsKey(corvette));
		Assert.assertTrue(map.get(corvette).size() > 0);
	}

}
