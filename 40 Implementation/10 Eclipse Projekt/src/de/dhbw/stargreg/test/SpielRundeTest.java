package de.dhbw.stargreg.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.BauteilMarkt;
import de.dhbw.stargreg.code.PersonalMarkt;
import de.dhbw.stargreg.code.PersonalTyp;
import de.dhbw.stargreg.code.RaumschiffMarkt;
import de.dhbw.stargreg.code.RaumschiffTyp;
import de.dhbw.stargreg.code.Spiel;
import de.dhbw.stargreg.code.SpielRunde;

public class SpielRundeTest {
	
	private static RaumschiffTyp xwing = new RaumschiffTyp("X-Wing", 5);
	private static PersonalTyp r2d2 = new PersonalTyp("R2D2", 90);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RaumschiffMarkt.fuegeRaumschiffTypHinzu(xwing);
		PersonalMarkt.fuegePersonalTypHinzu(r2d2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Spiel.getSpiel().loescheAlles();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() {
		SpielRunde s1 = new SpielRunde();
		s1.getRaumschiffMarkt().setNachfrage(xwing, 10);
		s1.getPersonalMarkt().setLaufendeKosten(r2d2, 20.0);
		s1.getPersonalMarkt().setWerbungsKosten(r2d2, 30.0);
		
		
		SpielRunde s2 = s1.clone();
		s2.getRaumschiffMarkt().setNachfrage(xwing, 20);
		s2.getPersonalMarkt().setLaufendeKosten(r2d2, 21.0);
		s2.getPersonalMarkt().setWerbungsKosten(r2d2, 31.0);
		
		Assert.assertFalse(s1.getRaumschiffMarkt().getNachfrage(xwing) == s2.getRaumschiffMarkt().getNachfrage(xwing));
		Assert.assertFalse(s1.getPersonalMarkt().getLaufendeKosten(r2d2) == s2.getPersonalMarkt().getLaufendeKosten(r2d2));
		Assert.assertFalse(s1.getPersonalMarkt().getWerbungsKosten(r2d2) == s2.getPersonalMarkt().getWerbungsKosten(r2d2));
	}

}
