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
package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests extending this class must implement {@link #getFactory()}.
 * 
 * @author Georg Thimm
 */
public abstract class LinAlgFactoryTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{

	@Test
	public void testLinAlgFactory_base()
	{
		assertNotNull(getFactory());
		assertNotNull(getLinAlgFactory());
	}

	/**
	 * test {@link LinAlgFactory#ones(int,int)}
	 */
	@Test
	public void testOnesIntInt_base()
	{
		Matrix<RE> ones = getLinAlgFactory().ones(3, 5);
		assertEquals(3, ones.getRows());
		assertEquals(5, ones.getCols());
		for (int r = 1; r <= ones.getRows(); r++) {
			for (int c = 1; c <= ones.getCols(); c++) {
				assertEquals(getFactory().one(), ones.get(r, c));
			}
		}
	}

	/**
	 * test {@link LinAlgFactory#zeros(int,int)}
	 */
	@Test
	public void testZerosIntInt_base()
	{
		Matrix<RE> zeros = getLinAlgFactory().zeros(3, 5);
		assertEquals(3, zeros.getRows());
		assertEquals(5, zeros.getCols());
		for (int r = 1; r <= zeros.getRows(); r++) {
			for (int c = 1; c <= zeros.getCols(); c++) {
				assertEquals(getFactory().zero(), zeros.get(r, c));
			}
		}
	}

	/**
	 * Test only if the size of the matrix is OK, all elements are non-null, and
	 * the a matrix includes more than one distinct value.
	 * 
	 * @param m
	 *            the matrix to be tested.
	 * @param rows
	 *            the number of rows the matrix should have
	 * @param cols
	 *            the number of columns the matrix should have.
	 */
	void testRandomMatrix(Matrix<RE> m, int rows, int cols)
	{
		assertEquals(rows, m.getRows());
		assertEquals(cols, m.getCols());
		RE v0 = m.get(1, 1);
		for (int r = 1; r <= m.getRows(); r++) {
			for (int c = 1; c <= m.getCols(); c++) {
				if (!v0.equals(m.get(r, c))) return;
			}
		}
		fail("Matrix\n" + m + " does not contain random values.");
	}

	/**
	 * @see #testRandomMatrix(Matrix, int, int)
	 */
	@Test
	public void testUniformNoiseIntInt_base()
	{
		assumeTrue(!methodIsDepreciated(getFactory(), "randomValue",
				new Class<?>[] {
					Random.class
				}));
		Matrix<RE> m = getLinAlgFactory().uniformNoise(numRandomNum,
				numRandomNum / 2);
		testRandomMatrix(m, numRandomNum, numRandomNum / 2);
	}

	/**
	 * @see #testRandomMatrix(Matrix, int, int)
	 */
	@Test
	public void testGaussianNoiseIntInt_base()
	{
		assumeTrue(!methodIsDepreciated(getFactory(), "gaussianRandomValue",
				new Class<?>[] {
					Random.class
				}));
		Matrix<RE> m = getLinAlgFactory().gaussianNoise(numRandomNum,
				numRandomNum / 2);
		testRandomMatrix(m, numRandomNum, numRandomNum / 2);
	}

	/**
	 * test {@link LinAlgFactory#ones(int)}
	 */
	@Test
	public void testOnesInt()
	{
		Vector<RE> ones = getLinAlgFactory().ones(7);
		assertEquals(7, ones.length());
		for (int i = 1; i <= 7; i++)
			assertEquals(getFactory().one(), ones.getEntry(i));
	}

	/**
	 * test {@link LinAlgFactory#zeros(int)}
	 */
	@Test
	public void testZerosInt()
	{
		Vector<RE> zeros = getLinAlgFactory().zeros(7);
		assertEquals(7, zeros.length());
		for (int i = 1; i <= 7; i++)
			assertEquals(getFactory().zero(), zeros.getEntry(i));
	}

	/**
	 * The number of random numbers used to test the random number generators.
	 */
	private final int numRandomNum = 20;

	/**
	 * test {@link LinAlgFactory#uniformNoise(int)}
	 */
	@Test
	public void testUniformNoiseInt_base()
	{
		assumeTrue(!methodIsDepreciated(getFactory(), "randomValue", null));
		Vector<RE> m = getLinAlgFactory().uniformNoise(numRandomNum);
		testRandomVector(m, numRandomNum);
	}

	/**
	 * test {@link LinAlgFactory#gaussianNoise(int)}
	 */

	@Test
	public void testGaussianNoiseInt_base()
	{
		assumeTrue(!methodIsDepreciated(getFactory(), "gaussianRandomValue",
				null));
		Vector<RE> m = getLinAlgFactory().gaussianNoise(numRandomNum);
		testRandomVector(m, numRandomNum);
	}

	/**
	 * As {@link #testRandomMatrix(Matrix, int, int)} but for vectors
	 * 
	 * @param m
	 *            the vector
	 * @param i
	 *            the number of expected elements
	 */
	private void testRandomVector(Vector<RE> m, int i)
	{
		assertEquals(i, m.length());
		RE v0 = m.getEntry(1);
		for (int r = 1; r <= m.length(); r++) {
			if (!v0.equals(m.getEntry(r))) return;
		}
		fail("Vector\n" + m + " does not contain random values.");
	}

	@Test
	public void testIdentity()
	{
		Matrix<RE> id = getLinAlgFactory().identity(5);
		assertEquals(5, id.getRows());
		assertEquals(5, id.getCols());
		for (int r = 1; r <= id.getRows(); r++) {
			for (int c = 1; c <= id.getCols(); c++) {
				if (r != c)
					assertEquals(getFactory().zero(), id.get(r, c));
				else
					assertEquals(getFactory().one(), id.get(r, c));
			}
		}
	}

	@Ignore
	@Test
	public void testBuildMatrix()
	{
		fail("tests depreceated method");
	}

	@Ignore
	@Test
	public void testBuildVector()
	{
		fail("tests depreceated method");
	}

	@Ignore
	@Test
	public void testWrapDoubleArray()
	{
		fail("tests depreceated method");
	}

	@Ignore
	@Test
	public void testWrapDoubleArrayArray()
	{
		fail("tests depreceated method");
	}

}
