package org.jlinalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.Matrix;
import org.jlinalg.MatrixDeterminant;
import org.jlinalg.Rational;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andreas, Georg Thimm
 */
public class DeterminantTest
{

	/**
	 * set of rationals to be used in the tests.
	 */
	Rational r0, r1, r2, r3, r4, r5, r6, r7, r8, r9;

	/**
	 * initialise r0 .. r9.
	 */
	@Before
	public void setUp()
	{
		r0 = Rational.FACTORY.get(0);
		r1 = Rational.FACTORY.get(1);
		r2 = Rational.FACTORY.get(2);
		r3 = Rational.FACTORY.get(3);
		r4 = Rational.FACTORY.get(4);
		r5 = Rational.FACTORY.get(5);
		r6 = Rational.FACTORY.get(6);
		r7 = Rational.FACTORY.get(7);
		r8 = Rational.FACTORY.get(8);
		r9 = Rational.FACTORY.get(9);
	}

	/**
	 * Taken from Bug-Report [ 1797067 ] Matrix Determinant Error
	 */
	@Test
	public void det1()
	{
		Matrix<Rational> m = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1
				},
				{
						r1, r1
				}
		});
		assertEquals(Rational.FACTORY.get(-1), MatrixDeterminant
				.gaussianMethod(m));
		assertEquals(Rational.FACTORY.get(-1), MatrixDeterminant
				.leibnizMethod(m));
	}

	/**
	 * Test MatrixDeterminant.gaussianMethod() and
	 * MatrixDeterminant.gaussianMethod()
	 */
	@Test
	public void det2()
	{
		Matrix<Rational> m = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1, r2
				},
				{
						r3, r4, r5
				},
				{
						r6, r7, r9
				}
		});
		assertEquals(Rational.FACTORY.get(-3), MatrixDeterminant
				.gaussianMethod(m));
		assertEquals(Rational.FACTORY.get(-3), MatrixDeterminant
				.leibnizMethod(m));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */
	@Test
	public void withoutRowAndColumn1()
	{
		Matrix<Rational> m = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1, r2
				},
				{
						r3, r4, r5
				},
				{
						r6, r7, r9
				}
		});
		Matrix<Rational> m2 = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r2
				},
				{
						r6, r9
				}
		});
		Matrix<Rational> m3 = MatrixDeterminant.withoutRowAndColumn(m, 2, 2);
		assertTrue(m2.equals(m3));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */
	@Test
	public void withoutRowAndColumn2()
	{
		Matrix<Rational> m = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1, r2
				},
				{
						r3, r4, r5
				},
				{
						r6, r7, r9
				}
		});
		Matrix<Rational> m2 = new Matrix<Rational>(new Rational[][]
		{
				{
						r4, r5
				},
				{
						r7, r9
				}
		});
		Matrix<Rational> m3 = MatrixDeterminant.withoutRowAndColumn(m, 1, 1);
		assertTrue(m2.equals(m3));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */

	@Test
	public void withoutRowAndColumn3()
	{
		Matrix<Rational> m = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1, r2
				},
				{
						r3, r4, r5
				},
				{
						r6, r7, r9
				}
		});
		Matrix<Rational> m2 = new Matrix<Rational>(new Rational[][]
		{
				{
						r0, r1
				},
				{
						r3, r4
				}
		});
		Matrix<Rational> m3 = MatrixDeterminant.withoutRowAndColumn(m, 3, 3);
		assertTrue(m2.equals(m3));
	}
}
