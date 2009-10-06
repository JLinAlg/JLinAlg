/**
 * 
 */
package org.jlinalg.fastrational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.Matrix;
import org.jlinalg.testutil.MatrixTestBase;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class FastRationalMatrixTest
		extends MatrixTestBase<FastRational>
{

	/**
	 * used in the test
	 */
	static FastRational[][] fes1;

	/**
	 * used in the test
	 */
	static FastRational[][] fes2;

	/**
	 * used in the test
	 */
	static FastRational[][] fes3;

	/**
	 * used in the test
	 */
	static Matrix<FastRational> m1;

	/**
	 * used in the test
	 */
	static Matrix<FastRational> m2;

	/**
	 * used in the test
	 */
	static Matrix<FastRational> m3;

	/**
	 * setup the variables above.
	 */
	@BeforeClass
	public static void setup()
	{
		fes1 = FastRational.FACTORY.getArray(2, 3);
		for (int i = 0; i < fes1.length; i++)
			for (int k = 0; k < fes1[0].length; k++) {
				fes1[i][k] = FastRational.FACTORY.get(i, k + 1);
			}
		fes2 = FastRational.FACTORY.getArray(3, 4);
		for (int i = 0; i < fes2.length; i++)
			for (int k = 0; k < fes2[0].length; k++) {
				fes2[i][k] = FastRational.FACTORY.get(i, k + 1);
			}
		fes3 = FastRational.FACTORY.getArray(3, 4);
		for (int i = 0; i < fes3.length; i++)
			for (int k = 0; k < fes3[0].length; k++) {
				fes3[i][k] = FastRational.FACTORY.get(-i, k + 1);
			}
		m1 = new Matrix<FastRational>(fes1);
		m2 = new Matrix<FastRational>(fes2);
		m3 = new Matrix<FastRational>(fes3);
	}

	/**
	 * test {@link Matrix#order(int)}
	 */
	@Test
	public final void testOrder()
	{
		int o = getLinAlgFactory().identity(3).order(10);
		assertTrue("unit order=1 (is: " + o + ")", o == 1);

		o = getLinAlgFactory().identity(3).multiply(
				FastRational.FACTORY.m_one()).order(10);
		assertTrue("unit order=2 (is: " + o + ")", o == 2);

		Matrix<FastRational> m = getLinAlgFactory().identity(3);
		m.set(1, 1, FastRational.FACTORY.m_one());
		m.set(1, 2, FastRational.FACTORY.one());
		// m.set(2, 1, FastRational.FACTORY.one());
		m.set(3, 3, FastRational.FACTORY.m_one());

		o = m.order(20);
		assertEquals("order for matrix\n" + m.toString(), 2, o);
	}

	/**
	 * test {@link Matrix#add(Matrix)}
	 */
	@Test
	public void testAdd_()
	{
		Matrix<FastRational> m = getLinAlgFactory().identity(3);
		m = m.add(getLinAlgFactory().zeros(3, 3));
		assertTrue("1+0=1", m.equals(getLinAlgFactory().identity(3)));
		m = getLinAlgFactory().zeros(3, 3).add(getLinAlgFactory().identity(3));
		assertEquals(m, getLinAlgFactory().identity(3));
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
	private void _setRowFromMatrix(Matrix<FastRational> m0)
	{
		Matrix<FastRational> m = new Matrix<FastRational>(m0.getRows(), m0
				.getCols(), FastRational.FACTORY);
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
	private void _setColFromMatrix(Matrix<FastRational> m0)
	{
		Matrix<FastRational> m = new Matrix<FastRational>(m0.getRows(), m0
				.getCols(), m0.getFactory());
		for (int c = 1; c <= m0.getCols(); c++)
			m.setColFromMatrix(c, m0, c);
		assertTrue(m.toString() + "\n!=\n" + m0, m0.equals(m));
	}

	/**
	 * test {@link Matrix#setRow(int, IRingElement)}
	 */
	@Test
	public void testSetRow_()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(7, 4,
				FastRational.FACTORY);
		for (int i = 1; i <= m.getRows(); i++)
			m.setRow(i, FastRational.FACTORY.get(i, 8));
		for (int r = 1; r <= 7; r++) {
			FastRational v = FastRational.FACTORY.get(r, 8);
			for (int c = 1; c <= 4; c++)
				assertTrue(r + "!=" + m.get(r, c) + " in " + m + " at [" + r
						+ "," + c + "]", v.equals(m.get(r, c)));
		}

		m = new Matrix<FastRational>(17, 41, FastRational.FACTORY);
		for (int i = 1; i <= m.getRows(); i++)
			m.setRow(i, FastRational.FACTORY.get(i, 81));
		for (int r = 1; r <= 17; r++) {
			FastRational v = FastRational.FACTORY.get(r, 81);
			for (int c = 1; c <= 41; c++)
				assertTrue(r + "!=" + m.get(r, c) + " in " + m + " at [" + r
						+ "," + c + "]", v.equals(m.get(r, c)));
		}
	}

	/**
	 *test {@link Matrix#mean()} for data type FastRational
	 */
	@Test
	public void testMean_()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(3, 4,
				FastRational.FACTORY);
		for (int r = 1; r <= m.getRows(); r++) {
			for (int c = 1; c <= m.getCols(); c++) {
				m.set(r, c, FastRational.FACTORY.get(r, c));
			}
		}
		System.err.println(m);
		assertTrue("wrong mean = " + m.mean() + " for matrix \n" + m, m.mean()
				.equals(FastRational.FACTORY.get(25, 24)));
	}

	@Override
	public IRingElementFactory<FastRational> getFactory()
	{
		return FastRational.FACTORY;
	}

	Matrix<FastRational> fractions(final int k)
	{
		Matrix<FastRational> m = new Matrix<FastRational>(k, k,
				FastRational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				m.set(r, c, FastRational.FACTORY.get(r, c));
		return m;
	}

	Matrix<FastRational> funny(final int k)
	{
		Matrix<FastRational> m = new Matrix<FastRational>(k, k,
				FastRational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				m.set(r, c, FastRational.FACTORY.get(r + c, c * c));
		m.addReplace(getLinAlgFactory().identity(k));
		return m;
	}

	/**
	 * test {@link Matrix#det()}
	 */
	@Test
	public void testDet()
	{
		Matrix<FastRational> m = fractions(3);
		testDetSub(m, FastRational.FACTORY.zero());
		m.multiply(FastRational.FACTORY.m_one());
		testDetSub(m, FastRational.FACTORY.zero());
		m = fractions(6);
		testDetSub(m, FastRational.FACTORY.zero());
		m.multiply(FastRational.FACTORY.m_one());
		testDetSub(m, FastRational.FACTORY.zero());

		m = funny(3);
		testDetSub(m, FastRational.FACTORY.get(71, 18));
		m.multiply(FastRational.FACTORY.m_one());
		testDetSub(m, FastRational.FACTORY.get(71, 18));
		m = funny(6);
		m.multiplyReplace(FastRational.FACTORY.get(2));
		FastRational d = FastRational.FACTORY.get(2836, 15);
		testDetSub(m, d);
		m.multiply(FastRational.FACTORY.m_one());
		testDetSub(m, d);
	}
}
