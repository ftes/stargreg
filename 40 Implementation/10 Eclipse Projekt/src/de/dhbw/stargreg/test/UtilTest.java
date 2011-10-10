package de.dhbw.stargreg.test;

import java.util.HashMap;
import java.util.Vector;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.dhbw.stargreg.util.Gruppierung;
import de.dhbw.stargreg.util.Util;

public class UtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testGruppiereVector() {
		Vector<Integer> vector = new Vector<Integer>();
		vector.add(-2);
		vector.add(-5);
		vector.add(1);
		vector.add(20);
		vector.add(-100);
		
		HashMap<Boolean, Vector<Integer>> ergebnis = Util.gruppiereVector(vector, new Gruppierung<Boolean, Integer>() {
			@Override
			public Boolean nach(Integer i) {
				return (i >= 0);
			}
		});
		
		for (Boolean b : ergebnis.keySet()) {
			System.out.println("Gruppe " + b);
			for (Integer i : ergebnis.get(b)) {
				System.out.println("   " + i);
				Assert.assertTrue(i >= 0 == b);
			}
		}
	}

}
