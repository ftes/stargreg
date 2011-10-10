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
import de.dhbw.stargreg.code.Raumschiffmarkt;
import de.dhbw.stargreg.code.Raumschifftyp;
import de.dhbw.stargreg.code.Verkauf;
import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

public class RaumschiffmarktTest {
	
	private Raumschiffmarkt raumschiffmarkt;
	private static Raumschifftyp xwing;
	private static Raumschifftyp corvette;
	private static int nachfrageXwing = 20;
	private static int nachfrageCorvette = 10;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xwing = new Raumschifftyp("test", 10) {
			public double getKosten() {
				return 5;
			}
		};
		corvette = new Raumschifftyp("Corvette", 20) {
			public double getKosten() {
				 return 12;
			}
		};		
		Raumschiffmarkt.fuegeRaumschifftypHinzu(xwing);
		Raumschiffmarkt.fuegeRaumschifftypHinzu(corvette);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		raumschiffmarkt = new Raumschiffmarkt();
		raumschiffmarkt.setNachfrage(xwing, nachfrageXwing);
		raumschiffmarkt.setNachfrage(corvette, nachfrageCorvette);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBerechneTypAbsatz() {
		Vector<Angebot> angebote = new Vector<Angebot>();
		angebote.add(new Angebot(xwing, null, 10, 10.0));
		angebote.add(new Angebot(xwing, null, 5, 15.0));
		angebote.add(new Angebot(xwing, null, 10, 20.0));
		
		int gesamtMenge = 0;
		Vector<Verkauf> verkaeufe = raumschiffmarkt.berechneTypAbsatz(xwing, angebote);
		for (Verkauf verkauf : verkaeufe) {
			int menge = verkauf.getMenge();
			Assert.assertTrue(menge >= 0);
			gesamtMenge += menge;
		}
		
		Assert.assertTrue(gesamtMenge <= nachfrageXwing);
	}
	
	@Test
	public void testBerechneGesamtAbsatz() {
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 10, 10.0));
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 5, 15.0));
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(xwing, null, 10, 20.0));
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 3, 20.0));
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 5, 18.0));
		raumschiffmarkt.fuegeAngebotHinzu(new Angebot(corvette, null, 2, 27.0));
		
		Vector<Verkauf> verkaeufe = raumschiffmarkt.berechneGesamtAbsatz();
		HashMap<Raumschifftyp, Vector<Verkauf>> map = Util.gruppiereVector(verkaeufe, new Gruppierung<Raumschifftyp, Verkauf>() {
			@Override
			public Raumschifftyp nach(Verkauf verkauf) {
				return verkauf.getRaumschifftyp();
			}
		});
		Assert.assertTrue(map.containsKey(xwing));
		Assert.assertTrue(map.get(xwing).size() > 0);
		Assert.assertTrue(map.containsKey(corvette));
		Assert.assertTrue(map.get(corvette).size() > 0);
	}

}
