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
package org.jlinalg.fastrational;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.Matrix;
import org.jlinalg.MatrixDeterminant;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andreas, Georg Thimm
 */
public class FastRationalDeterminantTest
{

	/**
	 * set of rationals to be used in the tests.
	 */
	FastRational r0, r1, r2, r3, r4, r5, r6, r7, r8, r9;

	/**
	 * initialise r0 .. r9.
	 */
	@Before
	public void setUp()
	{
		r0 = FastRational.FACTORY.get(0);
		r1 = FastRational.FACTORY.get(1);
		r2 = FastRational.FACTORY.get(2);
		r3 = FastRational.FACTORY.get(3);
		r4 = FastRational.FACTORY.get(4);
		r5 = FastRational.FACTORY.get(5);
		r6 = FastRational.FACTORY.get(6);
		r7 = FastRational.FACTORY.get(7);
		r8 = FastRational.FACTORY.get(8);
		r9 = FastRational.FACTORY.get(9);
	}

	/**
	 * Taken from Bug-Report [ 1797067 ] Matrix Determinant Error
	 */
	@Test
	public void det1()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(new FastRational[][] {
				{
						r0, r1
				}, {
						r1, r1
				}
		});
		assertEquals(FastRational.FACTORY.get(-1), MatrixDeterminant
				.gaussianMethod(m));
		assertEquals(FastRational.FACTORY.get(-1), MatrixDeterminant
				.leibnizMethod(m));
	}

	/**
	 * Test MatrixDeterminant.gaussianMethod() and
	 * MatrixDeterminant.gaussianMethod()
	 */
	@Test
	public void det2()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(new FastRational[][] {
				{
						r0, r1, r2
				}, {
						r3, r4, r5
				}, {
						r6, r7, r9
				}
		});
		assertEquals(FastRational.FACTORY.get(-3), MatrixDeterminant
				.gaussianMethod(m));
		assertEquals(FastRational.FACTORY.get(-3), MatrixDeterminant
				.leibnizMethod(m));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */
	@Test
	public void withoutRowAndColumn1()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(new FastRational[][] {
				{
						r0, r1, r2
				}, {
						r3, r4, r5
				}, {
						r6, r7, r9
				}
		});
		Matrix<FastRational> m2 = new Matrix<FastRational>(
				new FastRational[][] {
						{
								r0, r2
						}, {
								r6, r9
						}
				});
		Matrix<FastRational> m3 = MatrixDeterminant
				.withoutRowAndColumn(m, 2, 2);
		assertTrue(m2.equals(m3));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */
	@Test
	public void withoutRowAndColumn2()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(new FastRational[][] {
				{
						r0, r1, r2
				}, {
						r3, r4, r5
				}, {
						r6, r7, r9
				}
		});
		Matrix<FastRational> m2 = new Matrix<FastRational>(
				new FastRational[][] {
						{
								r4, r5
						}, {
								r7, r9
						}
				});
		Matrix<FastRational> m3 = MatrixDeterminant
				.withoutRowAndColumn(m, 1, 1);
		assertTrue(m2.equals(m3));
	}

	/**
	 * test {@link MatrixDeterminant#withoutRowAndColumn(Matrix, int, int)}
	 */

	@Test
	public void withoutRowAndColumn3()
	{
		Matrix<FastRational> m = new Matrix<FastRational>(new FastRational[][] {
				{
						r0, r1, r2
				}, {
						r3, r4, r5
				}, {
						r6, r7, r9
				}
		});
		Matrix<FastRational> m2 = new Matrix<FastRational>(
				new FastRational[][] {
						{
								r0, r1
						}, {
								r3, r4
						}
				});
		Matrix<FastRational> m3 = MatrixDeterminant
				.withoutRowAndColumn(m, 3, 3);
		assertTrue(m2.equals(m3));
	}
}
