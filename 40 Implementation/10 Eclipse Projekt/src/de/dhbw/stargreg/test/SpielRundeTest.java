package de.dhbw.stargreg.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.code.Einkauf;
import de.dhbw.stargreg.code.SpielRunde;
import de.dhbw.stargreg.code.Verkauf;

public class SpielRundeTest {
	
	private static SpielRunde spielRunde = new SpielRunde();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		spielRunde.fuegeTransaktionHinzu(new Verkauf(null, null, 0, 0));
		spielRunde.fuegeTransaktionHinzu(new Einkauf(null, null, 0, 0));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() {
		Assert.assertTrue(spielRunde.getVerkaeufe().size() == 1);
		Assert.assertTrue(spielRunde.getEinkaeufe().size() == 1);
		Assert.assertTrue(spielRunde.getAngebote().size() == 0);
	}

}
