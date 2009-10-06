package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.Matrix;
import org.jlinalg.f2.F2.F2Factory;
import org.jlinalg.fastrational.FastRationalFactory;
import org.jlinalg.rational.Rational;
import org.junit.Assume;
import org.junit.Test;

public abstract class MatrixTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
		implements TestBaseInterface<RE>
{

	protected static final String[][] mat_1to9 = {
			{
					"1", "2", "3"
			}, {
					"4", "5", "6"
			}, {
					"7", "8", "9"
			}
	};
	protected static final String[][] mat_2to10 = {
			{
					"2", "3", "4"
			}, {
					"5", "6", "7"
			}, {
					"8", "9", "10"
			}
	};

	/**
	 * quick test for {@link Matrix#inverse()} for some matrices.
	 */
	@Test
	public void matrixInverse_base()
	{
		testInversion_subroutine(getLinAlgFactory().identity(5));
		Assume.assumeTrue(!(getFactory() instanceof F2Factory));
		testInversion_subroutine(TestMatrices.getM2x2A(getFactory()));
		testInversion_subroutine(TestMatrices.getM2x2A_inv(getFactory()));
		if (!dataTypeIsDiscreet()
				&& !(getFactory() instanceof FastRationalFactory))
		// the matrix large includes fractions
			testInversion_subroutine(TestMatrices
					.getLargeInversible(getFactory()));
	}

	/**
	 * quick test for {@link Matrix#inverse()} for a non-inversible matrix.
	 */
	@Test(expected = InvalidOperationException.class)
	public void testInverseFail1_base()
	{
		Matrix<RE> inv = getLinAlgFactory().ones(5, 5).inverse();
		fail("inversion of\n" + getLinAlgFactory().ones(5, 5)
				+ "\nresults into \n" + inv);
	}

	/**
	 * quick test for {@link Matrix#inverse()} for a non-inversible matrix.
	 */
	@Test(expected = InvalidOperationException.class)
	public void testInverseFail2_base()
	{
		Matrix<RE> inv = getLinAlgFactory().zeros(5, 5).inverse();
		fail("inversion of\n" + getLinAlgFactory().zeros(5, 5)
				+ "\nresults into \n" + inv);
	}

	/**
	 * quick test for {@link Matrix#inverse()} for a non-inversible matrix.
	 */
	@Test(expected = InvalidOperationException.class)
	public void testInverseFail3_base()
	{
		Matrix<RE> m = getLinAlgFactory().identity(5);
		m.set(3, 2, getFactory().one());
		m.set(2, 3, getFactory().one());
		Matrix<RE> inv = m.inverse();
		fail("inversion of\n" + m + "\nresults into \n" + inv);
	}

	/**
	 * Test the inversion of {@link TestMatrices#large}. This is aware of
	 * whether or not the base type is affected by rounding errors: for inexact
	 * data types nothing is done.
	 */
	public void testInverseLarge_base()
	{
		if (!dataTypeIsExact()) {
			System.out.println("Skipping testRank_base for base type "
					+ getFactory().getClass().getName());
			return;
		}
		Matrix<RE> m = TestMatrices.getLargeNonInversible(getFactory());
		m.inverse(); // this should fail.
	}

	/**
	 * Test the inversion of some given matrix. This is aware of
	 * whether or not the base type is affected by rounding errors.
	 * 
	 * @param the
	 *            matrix to be tested for.
	 */
	void testInversion_subroutine(Matrix<RE> m)
	{
		Matrix<RE> inv = m.inverse();
		Matrix<RE> id = getLinAlgFactory().identity(m.getCols());
		Matrix<RE> prod = m.multiply(inv);
		assertSimilar(id, prod, "0.00001");
	}

	@Test
	public void matrixEquals_base()
	{
		Matrix<?> m1 = TestMatrices.getM2x2A(getFactory());
		Matrix<?> m2 = TestMatrices.getM2x2A(getFactory());
		assertTrue(m1.equals(m2));
	}

	/**
	 * test {@link Matrix#trace()}
	 */
	@Test
	public void testTrace_base()
	{
		assertNotNull(getFactory().get("5"));
		assertNotNull(getLinAlgFactory());
		assertEquals("Trace of matrix\n" + getLinAlgFactory().identity(5)
				+ " is wrong", getFactory().get("5"), getLinAlgFactory()
				.identity(5).trace());
		assertEquals("Trace zero matrix=0", getFactory().get("0"),
				getLinAlgFactory().zeros(4, 4).trace());
	}

	/**
	 * test {@link Matrix#add(Matrix)}
	 */
	@Test
	public void testAdd_base()
	{
		Matrix<RE> m0 = new Matrix<RE>(mat_1to9, getFactory());
		Matrix<RE> m1 = new Matrix<RE>(mat_2to10, getFactory());
		Matrix<RE> ones = getLinAlgFactory().ones(3, 3);
		m0 = m0.add(ones);
		assertEquals(m0, m1);
	}

	/**
	 * test {@link Matrix#rank()} if the base type is exact.
	 */
	@Test
	public void testRank_base()
	{
		if (!dataTypeIsExact()) {
			System.out.println("Skipping testRank_base for base type "
					+ getFactory().getClass().getName());
			return;
		}

		String[][] values = {
				{
						"2", "3", "5"
				}, {
						"2", "4", "4"
				}, {
						"2", "3", "4"
				}, {
						"5", "6", "7"
				}, {
						"8", "9", "10"
				}
		};
		Matrix<RE> m = new Matrix<RE>(values, getFactory());

		assertEquals(3, m.rank());
		m = m.transpose();
		assertEquals(3, m.rank());
	}

	/**
	 * test {@link Matrix#det()}
	 */
	@Test
	public void testDet_base()
	{
		testDetSub(getLinAlgFactory().zeros(3, 3), getFactory().zero());
		testDetSub(getLinAlgFactory().identity(3), getFactory().one());

		String[][] values = {
				{
						"1", "1", "1"
				}, {
						"0", "2", "3"
				}, {
						"0", "0", "5"
				}
		};
		Matrix<RE> m = new Matrix<RE>(values, getFactory());

		testDetSub(m, getFactory().get("10"));
		String[][] values2 = {
				{
						"1", "1", "1"
				}, {
						"1", "2", "3"
				}, {
						"1", "2", "-5"
				}
		};
		testDetSub(new Matrix<RE>(values2, getFactory()), getFactory()
				.get("-8"));

		m = getLinAlgFactory().identity(5);
		m.multiplyReplace(getFactory().m_one());
		testDetSub(m, getFactory().m_one());

		m = getLinAlgFactory().identity(5);
		m.addReplace(getFactory().one());
		testDetSub(m, getFactory().get(6));
	}

	/**
	 * used in the tests for the determinants. This is aware of whether a data
	 * type is exact and, if necessary allows for rounding errors.
	 * 
	 * @param m
	 *            a matrix
	 * @param f
	 *            the expected value of det().
	 */
	protected void testDetSub(Matrix<RE> m, RE f)
	{
		RE d = m.det();
		assertSimilar(f, d, "0.00001");
	}

	/**
	 * test whether a matrix can be created from an array with arbitrary
	 * objects.
	 */
	@Test
	public void testMatrixFromObjectArray_base()
	{
		Assume.assumeTrue(!(getFactory() instanceof F2Factory));
		// objects are numbered in sequence: the test relies on this!
		Object[][] o = {
				{
						Integer.valueOf(0), "1", "2"

				}, {
						Rational.FACTORY.get(3), new Long(4), new Double(5),
				}
		};
		Matrix<RE> m = new Matrix<RE>(o, getFactory());
		assertNotNull(m);
		assertEquals(o.length, m.getRows());
		assertEquals(o[0].length, m.getCols());
		int count = 0;
		for (int row = 1; row <= m.getRows(); row++) {
			for (int col = 1; col <= m.getCols(); col++) {
				String s = m.get(row, col).toString();
				assertEquals("element " + m.get(row, col) + " is wrong.",
						count, Integer.parseInt(s.substring(0, 1)));
				count++;
			}
		}
	}

	/**
	 * test {@link Matrix#order(int)} for
	 */
	@Test
	public final void testOrder_base()
	{
		if (!dataTypeIsExact()) {
			System.out.println("Skip test Matrix.order() for factory "
					+ getFactory());
			return;
		}
		int o = getLinAlgFactory().identity(3).order(10);
		assertTrue("unit order=1 (is: " + o + ")", o == 1);

		if (!(getFactory() instanceof F2Factory)) {
			o = getLinAlgFactory().identity(3).multiply(getFactory().m_one())
					.order(10);
			assertTrue("unit order=2 (is: " + o + ")", o == 2);
		}

		Matrix<RE> m = getLinAlgFactory().identity(3);
		m.set(1, 1, getFactory().m_one());
		m.set(1, 2, getFactory().one());
		// m.set(2, 1, Rational.FACTORY.one());
		m.set(3, 3, getFactory().m_one());

		o = m.order(20);
		assertEquals("order for matrix\n" + m.toString(), 2, o);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Matrix#Matrix(org.jlinalg.Matrix, org.jlinalg.IRingElementFactory)}
	 * .
	 */
	@Test
	public void testMatrixIRingElementMatrixIRingElementFactoryOfRE_base()
	{
		Matrix<StringWrapper> matS = new Matrix<StringWrapper>(mat_1to9,
				StringWrapper.FACTORY);
		Matrix<RE> matRE1 = new Matrix<RE>(mat_1to9, getFactory());
		Matrix<RE> matRE2 = new Matrix<RE>(matS, getFactory());
		assertNotNull(matRE2);
		assertEquals(matRE1, matRE2);
	}
}
