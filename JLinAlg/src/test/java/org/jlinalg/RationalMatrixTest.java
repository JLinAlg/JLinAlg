/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class RationalMatrixTest
{

	/**
	 * used in the test
	 */
	static Rational[][] fes1;

	/**
	 * used in the test
	 */
	static Rational[][] fes2;

	/**
	 * used in the test
	 */
	static Rational[][] fes3;

	/**
	 * used in the test
	 */
	static Matrix<Rational> m1;

	/**
	 * used in the test
	 */
	static Matrix<Rational> m2;

	/**
	 * used in the test
	 */
	static Matrix<Rational> m3;

	/**
	 * setup the variables above.
	 */
	@BeforeClass
	public static void setup()
	{
		fes1 = Rational.FACTORY.getArray(2, 3);
		for (int i = 0; i < fes1.length; i++)
			for (int k = 0; k < fes1[0].length; k++) {
				fes1[i][k] = Rational.FACTORY.get(i, k + 1);
			}
		fes2 = Rational.FACTORY.getArray(3, 4);
		for (int i = 0; i < fes2.length; i++)
			for (int k = 0; k < fes2[0].length; k++) {
				fes2[i][k] = Rational.FACTORY.get(i, k + 1);
			}
		fes3 = Rational.FACTORY.getArray(3, 4);
		for (int i = 0; i < fes3.length; i++)
			for (int k = 0; k < fes3[0].length; k++) {
				fes3[i][k] = Rational.FACTORY.get(-i, k + 1);
			}
		m1 = new Matrix<Rational>(fes1);
		m2 = new Matrix<Rational>(fes2);
		m3 = new Matrix<Rational>(fes3);
	}

	/**
	 * Test the creation of matrices from fields and the dimensions of their
	 * product
	 */
	@Test
	public void createFromField()
	{
		assertTrue("num cols m1", m1.getCols() == 3);
		assertTrue("num rows m1", m1.getRows() == 2);
		Matrix<Rational> m2 = new Matrix<Rational>(fes2);
		assertTrue("num cols m2", m2.getCols() == 4);
		assertTrue("num rows m2", m2.getRows() == 3);
		Matrix<Rational> m3 = m1.multiply(m2);
		assertTrue("num cols m3", m3.getCols() == 4);
		assertTrue("num rows m3", m3.getRows() == 2);
	}

	/**
	 * test {@link Matrix#trace()}
	 */
	@Test
	public void testTrace()
	{
		assertTrue("Trace unit matrix=1", RationalMatrixFactory.unit(5).trace()
				.equals(Rational.FACTORY.get(5)));
		assertTrue("Trace zero matrix=0", RationalMatrixFactory.zero(4).trace()
				.equals(Rational.FACTORY.get(0)));
	}

	/**
	 * test {@link Matrix#order(int)}
	 */
	@Test
	public final void testOrder()
	{
		int o = RationalMatrixFactory.unit(3).order(10);
		assertTrue("unit order=1 (is: " + o + ")", o == 1);

		o = RationalMatrixFactory.unit(3).multiply(Rational.FACTORY.m_one())
				.order(10);
		assertTrue("unit order=2 (is: " + o + ")", o == 2);

		Matrix<Rational> m = RationalMatrixFactory.zero(3);
		m.set(1, 2, Rational.FACTORY.m_one());
		m.set(2, 1, Rational.FACTORY.one());
		m.set(3, 3, Rational.FACTORY.m_one());

		o = m.order(20);
		assertTrue("unit order=4 (is: " + o + ")", o == 4);
	}

	/**
	 * test {@link Matrix#rank()}
	 */
	@Test
	public void testRank()
	{
		int k = 5;
		int l = 3;
		Matrix<Rational> m = new Matrix<Rational>(l, k, Rational.FACTORY
				.getFactory());
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= l; r++)
				if (r == c)
					m.set(r, c, Rational.FACTORY.one());
				else
					m.set(r, c, Rational.FACTORY.zero());
		assertTrue(m.rank() == 3);
		m.set(1, 2, Rational.FACTORY.m_one());
		m.set(2, 1, Rational.FACTORY.m_one());
		assertTrue(m.rank() == 2);
	}

	/**
	 * used in the tests for the determinants
	 * 
	 * @param m
	 *            a matrix
	 * @param f
	 *            the expected value of det().
	 */
	void testDetSub(Matrix<Rational> m, IRingElement f)
	{
		IRingElement d = m.det();
		assertTrue("Matrix\n" + m + "\nhas det=" + d + " instead of " + f, d
				.equals(f));

	}

	/**
	 * test {@link Matrix#det()}
	 */
	@Test
	public void testDet1()
	{
		Matrix<Rational> m = new Matrix<Rational>(3, 3, Rational.FACTORY
				.getFactory());
		m.setAll(Rational.FACTORY.zero());
		testDetSub(m, Rational.FACTORY.zero());
		m.set(2, 1, Rational.FACTORY.one());
		m.set(1, 2, Rational.FACTORY.one());
		m.set(3, 3, Rational.FACTORY.one());
		testDetSub(m, Rational.FACTORY.m_one());

		m.setAll(Rational.FACTORY.zero());
		m.set(2, 1, Rational.FACTORY.m_one());
		m.set(1, 2, Rational.FACTORY.one());
		m.set(3, 3, Rational.FACTORY.one());
		testDetSub(m, Rational.FACTORY.one());

		m = RationalMatrixFactory.unit(5);
		m.multiplyReplace(Rational.FACTORY.m_one());
		testDetSub(m, Rational.FACTORY.m_one());

		m = RationalMatrixFactory.unit(5);
		m.addReplace(Rational.FACTORY.one());
		testDetSub(m, Rational.FACTORY.get(6));

		m = RationalMatrixFactory.fractions(3);
		testDetSub(m, Rational.FACTORY.zero());
		m.multiply(Rational.FACTORY.m_one());
		testDetSub(m, Rational.FACTORY.zero());
		m = RationalMatrixFactory.fractions(6);
		testDetSub(m, Rational.FACTORY.zero());
		m.multiply(Rational.FACTORY.m_one());
		testDetSub(m, Rational.FACTORY.zero());

		m = RationalMatrixFactory.funny(3);
		testDetSub(m, Rational.FACTORY.get(71, 18));
		m.multiply(Rational.FACTORY.m_one());
		testDetSub(m, Rational.FACTORY.get(71, 18));
		m = RationalMatrixFactory.funny(6);
		m.multiplyReplace(Rational.FACTORY.get(2));
		Rational d = Rational.FACTORY.get(2836, 15);
		testDetSub(m, d);
		m.multiply(Rational.FACTORY.m_one());
		testDetSub(m, d);
	}

	/**
	 * test {@link Matrix#add(Matrix)}
	 */
	@Test
	public void testAdd()
	{
		Matrix<Rational> m = RationalMatrixFactory.unit(3).add(
				RationalMatrixFactory.zero(3));
		assertTrue("1+0=1", m.equals(RationalMatrixFactory.unit(3)));
		m = RationalMatrixFactory.zero(3).add(RationalMatrixFactory.unit(3));
		assertTrue("0+1=1", m.equals(RationalMatrixFactory.unit(3)));
	}

	/**
	 * test {@link Matrix#setRowFromMatrix(int, Matrix, int)}
	 */
	@Test
	public void setRowFromMatrix()
	{
		_setRowFromMatrix(m1);
		_setRowFromMatrix(m2);
		_setRowFromMatrix(m3);
	}

	/**
	 * used by {@link #setRowFromMatrix()}
	 * 
	 * @param m0
	 *            a matrix
	 */
	private void _setRowFromMatrix(Matrix<Rational> m0)
	{
		Matrix<Rational> m = new Matrix<Rational>(m0.getRows(), m0.getCols(),
				Rational.FACTORY);
		for (int r = 1; r <= m0.getRows(); r++)
			m.setRowFromMatrix(r, m0, r);
		assertTrue(m.toString() + "\n!=\n" + m0, m0.equals(m));
	}

	/**
	 * test {@link Matrix#setColFromMatrix(int, Matrix, int)}
	 */
	@Test
	public void setColFromMatrix()
	{
		_setColFromMatrix(m1);
		_setColFromMatrix(m2);
		_setColFromMatrix(m3);
	}

	/**
	 * used by {@link #setColFromMatrix()}
	 * 
	 * @param m0
	 *            a matrix
	 */
	private void _setColFromMatrix(Matrix<Rational> m0)
	{
		Matrix<Rational> m = new Matrix<Rational>(m0.getRows(), m0.getCols(),
				m0.getFactory());
		for (int c = 1; c <= m0.getCols(); c++)
			m.setColFromMatrix(c, m0, c);
		assertTrue(m.toString() + "\n!=\n" + m0, m0.equals(m));
	}

	/**
	 * test {@link Matrix#setRow(int, IRingElement)}
	 */
	@Test
	public void testSetRow()
	{
		Matrix<Rational> m = new Matrix<Rational>(7, 4, Rational.FACTORY);
		for (int i = 1; i <= m.getRows(); i++)
			m.setRow(i, Rational.FACTORY.get(i, 8));
		for (int r = 1; r <= 7; r++) {
			Rational v = Rational.FACTORY.get(r, 8);
			for (int c = 1; c <= 4; c++)
				assertTrue(r + "!=" + m.get(r, c) + " in " + m + " at [" + r
						+ "," + c + "]", v.equals(m.get(r, c)));
		}

		m = new Matrix<Rational>(17, 41, Rational.FACTORY);
		for (int i = 1; i <= m.getRows(); i++)
			m.setRow(i, Rational.FACTORY.get(i, 81));
		for (int r = 1; r <= 17; r++) {
			Rational v = Rational.FACTORY.get(r, 81);
			for (int c = 1; c <= 41; c++)
				assertTrue(r + "!=" + m.get(r, c) + " in " + m + " at [" + r
						+ "," + c + "]", v.equals(m.get(r, c)));
		}
	}
}
