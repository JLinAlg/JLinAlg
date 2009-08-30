package org.jlinalg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jlinalg.InvalidOperationException;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.Vector;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the class {@link Vector} for {@link Rational} elements
 * 
 * @author Georg Thimm
 */
public class RationalVectorTest
{
	/**
	 * part of the fixture
	 */
	static Vector<Rational> v1a;

	/**
	 * part of the fixture
	 */
	static Vector<Rational> v1b;

	/**
	 * part of the fixture
	 */
	static Vector<Rational> v2;

	/**
	 * part of the fixture
	 */
	static int dim = 4;

	/**
	 * initialise the fixture.
	 */
	@BeforeClass
	public static void initVectors()
	{

		v1a = new Vector<Rational>(dim, Rational.FACTORY);
		v1b = new Vector<Rational>(dim, Rational.FACTORY);
		v2 = new Vector<Rational>(dim, Rational.FACTORY);
		for (int i = 1; i <= v1a.length(); i++) {
			v1a.set(i, Rational.FACTORY.get(i, dim));
			v1b.set(i, Rational.FACTORY.get(i, dim));
			v2.set(i, Rational.FACTORY.get(100 + i, dim));
		}

	}

	/**
	 * test {@link Vector#hashCode()}
	 */
	@Test
	public final void testHashCode()
	{
		assertTrue(v1a.hashCode() == v1b.hashCode());
		assertFalse(v1a.hashCode() == v2.hashCode());
	}

	/**
	 * test {@link Vector#length()}
	 */
	@Test
	public final void testLength()
	{
		assertTrue(v1a.length() == dim);
	}

	/**
	 * test Vector.set(Vector)
	 */
	@Test
	public void testVectorSet()
	{
		assertFalse(v1a.equals(v2));// make sure the test is valid;
		v2.set(v1a);
		assertTrue(v1a.equals(v2));
	}

	/**
	 * test Vector.set(Vector) when the dimensions missmatch
	 */
	@Test(expected = InvalidOperationException.class)
	public void testVectorSetDimMissmatch()
	{
		Vector<Rational> v = new Vector<Rational>(dim + 1, Rational.FACTORY);
		v.set(v1a);
	}

	/**
	 * test {@link Vector#elementProduct()}
	 */
	@Test
	public final void testElementProduct()
	{
		Vector<Rational> v = new Vector<Rational>(4, Rational.FACTORY);
		v.setAll(Rational.FACTORY.get(1, 2));
		assertTrue("1/2^4=1/16", v.elementProduct().equals(
				Rational.FACTORY.get(1, 16)));
		v.setAll(Rational.FACTORY.get(0, 2));
		assertTrue("0^4=0", v.elementProduct().equals(
				Rational.FACTORY.get(0, 1)));
	}

	/**
	 * test {@link Vector#compareTo(Vector)}
	 */
	@Test
	public final void testCompareTo()
	{
		Vector<Rational> v1 = new Vector<Rational>(3, Rational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<Rational> v2 = new Vector<Rational>(3, Rational.FACTORY);
		for (int i = 1; i <= v2.length(); i++) {
			v2.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		assertTrue(v1.compareTo(v2) == 0);
		v1.set(2, Rational.FACTORY.get(-111));
		assertTrue(v1.compareTo(v2) == -1);
		v1.set(2, Rational.FACTORY.get(111));
		assertTrue(v1.compareTo(v2) == 1);
	}

	/**
	 * test whether InvalidOperationException is thrown if vectors with
	 * mismatching vector sizes are compared
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public final void testCompareToException()
	{
		Vector<Rational> v1 = new Vector<Rational>(3, Rational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<Rational> v3 = new Vector<Rational>(4, Rational.FACTORY);
		for (int i = 1; i <= v3.length(); i++) {
			v3.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		assertTrue("comparison of vectors with different length", v1
				.compareTo(v3) == 0);
	}

	/**
	 * test {@link Matrix#det()}
	 */
	@Test
	public void testDeterminante()
	{
		int dim = 3;
		Matrix<Rational> m = new Matrix<Rational>(dim, dim, Rational.FACTORY);
		for (int i = 1; i <= dim; i++) {
			for (int j = 1; j <= dim; j++) {
				if (i == j)
					m.set(i, j, Rational.FACTORY.get(i * j, i + j));
				else
					m.set(i, j, Rational.FACTORY.get(0));
			}
		}
		Rational r = m.det();
		assertFalse("det nonzero", r.equals(Rational.FACTORY.get(0)));
		assertFalse(m.getRows() != m.getCols());
		Matrix<Rational> minv = m.inverse();
		assertTrue(minv != null);
		Rational detinv = minv.det();
		assertTrue("det reasonable", detinv.multiply(r).equals(
				Rational.FACTORY.get(1)));
		System.out.println(m.toString());
		System.out.println(minv.toString());
	}
}
