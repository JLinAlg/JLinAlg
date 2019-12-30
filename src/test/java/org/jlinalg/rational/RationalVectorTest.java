/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jlinalg.rational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.testutil.VectorTestBase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the class {@link Vector} for {@link Rational} elements
 * 
 * @author Georg Thimm
 */
public class RationalVectorTest
		extends VectorTestBase<Rational>
{
	private static RationalFactory rFac = Rational.FACTORY;

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<Rational> v1;

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<Rational> v_zero;

	/**
	 * a rational vector used in the tests. Value: [0,0,1/2]
	 */
	private static Vector<Rational> v3;

	/**
	 * a rational vector used in the tests. Value: [0,0,-1/2]
	 */
	private static Vector<Rational> v4;

	/**
	 * a rational vector used in the tests. Value: [0,0,0,0]
	 */
	private static Vector<Rational> w;

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

		v1a = new Vector<>(dim, Rational.FACTORY);
		v1b = new Vector<>(dim, Rational.FACTORY);
		v2 = new Vector<>(dim, Rational.FACTORY);
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
	public void testHashCode()
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
		assertFalse(v3.equals(v_zero));// make sure the test is valid;
		v3.set(v_zero);
		assertEquals(v_zero, v3);
	}

	/**
	 * test Vector.set(Vector) when the dimensions missmatch
	 */
	@Test(expected = InvalidOperationException.class)
	public void testVectorSetDimMissmatch()
	{
		Vector<Rational> v = new Vector<>(dim + 1, Rational.FACTORY);
		v.set(v1a);
	}

	/**
	 * test {@link Vector#elementProduct()}
	 */
	@Test
	public final void testElementProduct()
	{
		Vector<Rational> v = new Vector<>(4, Rational.FACTORY);
		v.setAll(Rational.FACTORY.get(1, 2));
		assertTrue("1/2^4=1/16",
				v.elementProduct().equals(Rational.FACTORY.get(1, 16)));
		v.setAll(Rational.FACTORY.get(0, 2));
		assertTrue("0^4=0",
				v.elementProduct().equals(Rational.FACTORY.get(0, 1)));
	}

	/**
	 * test {@link Vector#compareTo(Vector)}
	 */
	@Test
	public final void testCompareTo()
	{
		Vector<Rational> v1 = new Vector<>(3, Rational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<Rational> v2 = new Vector<>(3, Rational.FACTORY);
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
		Vector<Rational> v1 = new Vector<>(3, Rational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<Rational> v3 = new Vector<>(4, Rational.FACTORY);
		for (int i = 1; i <= v3.length(); i++) {
			v3.set(i, Rational.FACTORY.get(i * 3 - 1, i));
		}
		assertTrue("comparison of vectors with different length",
				v1.compareTo(v3) == 0);
	}

	/**
	 * test {@link Matrix#det()} for truely rational numbers
	 */
	@Test
	public void testDeterminante()
	{
		int dim = 3;
		Matrix<Rational> m = new Matrix<>(dim, dim, Rational.FACTORY);
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
		assertNotNull(minv);
		Rational detinv = minv.det();
		assertTrue("det reasonable",
				detinv.multiply(r).equals(Rational.FACTORY.get(1)));
		// System.out.println(m.toString());
		// System.out.println(minv.toString());
	}

	/**
	 * setup the a rational numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		v1 = new Vector<>(3, rFac);
		for (int i = 1; i <= 3; i++)
			v1.set(i, rFac.zero());
		v_zero = v1.copy();
		v3 = v1.copy();
		v3.set(3, rFac.get(1, 2));
		v4 = v1.copy();
		v4.set(3, rFac.get(1, -2));

		w = new Vector<>(4, rFac);
		for (int i = 1; i <= 4; i++)
			w.set(i, rFac.zero());
	}

	/**
	 * verify whether a comparison between a different size vectors throws an
	 * exception.
	 * 
	 * @throws InvalidOperationException
	 *             which is expected.
	 */
	@Test(expected = InvalidOperationException.class)
	public void testCompareToExceptions() throws InvalidOperationException
	{
		v1.compareTo(w);
	}

	/**
	 * test {@link Rational#compareTo(org.jlinalg.IRingElement)}
	 */
	@Test
	public void testCompareToVectors()
	{
		assertTrue(v1 + "!=" + v_zero, v1.compareTo(v_zero) == 0);
		assertTrue("v1!=v1  (v1=" + v1 + ")", v1.compareTo(v1) == 0);
		assertTrue("v1<v3", v1.compareTo(v3) == -1);
		assertTrue("v1<v3 (inv)", v3.compareTo(v1) == 1);
		assertTrue("v1>v4", v1.compareTo(v4) == 1);
		assertTrue("v1>v4 (inv)", v4.compareTo(v1) == -1);
	}

	@Override
	public IRingElementFactory<Rational> getFactory()
	{
		return Rational.FACTORY;
	}
}
