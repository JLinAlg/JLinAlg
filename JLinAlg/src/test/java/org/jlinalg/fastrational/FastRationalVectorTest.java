package org.jlinalg.fastrational;

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
 * Test the class {@link Vector} for {@link FastRational} elements
 * 
 * @author Georg Thimm
 */
public class FastRationalVectorTest
		extends VectorTestBase<FastRational>
{

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<FastRational> v1;

	/**
	 * a rational vector used in the tests. Value: [0,0,0]
	 */
	private static Vector<FastRational> v_zero;

	/**
	 * a rational vector used in the tests. Value: [0,0,1/2]
	 */
	private static Vector<FastRational> v3;

	/**
	 * a rational vector used in the tests. Value: [0,0,-1/2]
	 */
	private static Vector<FastRational> v4;

	/**
	 * a rational vector used in the tests. Value: [0,0,0,0]
	 */
	private static Vector<FastRational> w;

	/**
	 * part of the fixture
	 */
	static Vector<FastRational> v1a;

	/**
	 * part of the fixture
	 */
	static Vector<FastRational> v1b;

	/**
	 * part of the fixture
	 */
	static Vector<FastRational> v2;

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

		v1a = new Vector<FastRational>(dim, FastRational.FACTORY);
		v1b = new Vector<FastRational>(dim, FastRational.FACTORY);
		v2 = new Vector<FastRational>(dim, FastRational.FACTORY);
		for (int i = 1; i <= v1a.length(); i++) {
			v1a.set(i, FastRational.FACTORY.get(i, dim));
			v1b.set(i, FastRational.FACTORY.get(i, dim));
			v2.set(i, FastRational.FACTORY.get(100 + i, dim));
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
		Vector<FastRational> v = new Vector<FastRational>(dim + 1,
				FastRational.FACTORY);
		v.set(v1a);
	}

	/**
	 * test {@link Vector#elementProduct()}
	 */
	@Test
	public final void testElementProduct()
	{
		Vector<FastRational> v = new Vector<FastRational>(4,
				FastRational.FACTORY);
		v.setAll(FastRational.FACTORY.get(1, 2));
		assertTrue("1/2^4=1/16",
				v.elementProduct().equals(FastRational.FACTORY.get(1, 16)));
		v.setAll(FastRational.FACTORY.get(0, 2));
		assertTrue("0^4=0",
				v.elementProduct().equals(FastRational.FACTORY.get(0, 1)));
	}

	/**
	 * test {@link Vector#compareTo(Vector)}
	 */
	@Test
	public final void testCompareTo()
	{
		Vector<FastRational> v1 = new Vector<FastRational>(3,
				FastRational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, FastRational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<FastRational> v2 = new Vector<FastRational>(3,
				FastRational.FACTORY);
		for (int i = 1; i <= v2.length(); i++) {
			v2.set(i, FastRational.FACTORY.get(i * 3 - 1, i));
		}
		assertTrue(v1.compareTo(v2) == 0);
		v1.set(2, FastRational.FACTORY.get(-111));
		assertTrue(v1.compareTo(v2) == -1);
		v1.set(2, FastRational.FACTORY.get(111));
		assertTrue(v1.compareTo(v2) == 1);
	}

	/**
	 * test whether InvalidOperationException is thrown if vectors with
	 * mismatching vector sizes are compared
	 */
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public final void testCompareToException()
	{
		Vector<FastRational> v1 = new Vector<FastRational>(3,
				FastRational.FACTORY);
		for (int i = 1; i <= v1.length(); i++) {
			v1.set(i, FastRational.FACTORY.get(i * 3 - 1, i));
		}
		Vector<FastRational> v3 = new Vector<FastRational>(4,
				FastRational.FACTORY);
		for (int i = 1; i <= v3.length(); i++) {
			v3.set(i, FastRational.FACTORY.get(i * 3 - 1, i));
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
		Matrix<FastRational> m = new Matrix<FastRational>(dim, dim,
				FastRational.FACTORY);
		for (int i = 1; i <= dim; i++) {
			for (int j = 1; j <= dim; j++) {
				if (i == j)
					m.set(i, j, FastRational.FACTORY.get(i * j, i + j));
				else
					m.set(i, j, FastRational.FACTORY.get(0));
			}
		}
		FastRational r = m.det();
		assertFalse("det nonzero", r.equals(FastRational.FACTORY.get(0)));
		assertFalse(m.getRows() != m.getCols());
		Matrix<FastRational> minv = m.inverse();
		assertNotNull(minv);
		FastRational detinv = minv.det();
		assertTrue("det reasonable",
				detinv.multiply(r).equals(FastRational.FACTORY.get(1)));
		// System.out.println(m.toString());
		// System.out.println(minv.toString());
	}

	/**
	 * setup the a rational numbers and vectors used in the tests
	 */
	@Before
	public void setup()
	{
		v1 = new Vector<FastRational>(3, getFactory());
		for (int i = 1; i <= 3; i++)
			v1.set(i, getFactory().zero());
		v_zero = v1.copy();
		v3 = v1.copy();
		v3.set(3, getFactory().get("1/2"));
		v4 = v1.copy();
		v4.set(3, getFactory().get("1/-2"));

		w = new Vector<FastRational>(4, getFactory());
		for (int i = 1; i <= 4; i++)
			w.set(i, getFactory().zero());
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
	 * test {@link FastRational#compareTo(org.jlinalg.IRingElement)}
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
	public IRingElementFactory<FastRational> getFactory()
	{
		return FastRational.FACTORY;
	}
}
