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

import static org.junit.Assert.assertTrue;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.MatrixMultiplication;
import org.jlinalg.Vector;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andreas Keilhauer
 */
public class FastRationalMatrixMultiplicationTest
{
	/**
	 * a kludge to give access to the constants in MatrixMultiplication
	 */
	static class MMAccess
			extends MatrixMultiplication
	{
		static void set_STRASSEN_ORIGINAL_TRUNCATION_POINT(int v)
		{
			STRASSEN_ORIGINAL_TRUNCATION_POINT = v;
		}

		static void set_STRASSEN_WINOGRAD_TRUNCATION_POINT(int v)
		{
			STRASSEN_WINOGRAD_TRUNCATION_POINT = v;
		}

		static void set_STRASSEN_BODRATO_TRUNCATION_POINT(int v)
		{
			STRASSEN_BODRATO_TRUNCATION_POINT = v;
		}
	}

	/**
	 * an instance of {@link LinAlgFactory} for base type {@link FastRational}
	 */
	protected static LinAlgFactory<FastRational> factoryRational = new LinAlgFactory<FastRational>(
			FastRational.FACTORY);

	/**
	 * a 3x3 identity matrix
	 */
	protected static Matrix<FastRational> identity3 = null;

	/**
	 * a vector with values (1,2,3)
	 */
	protected static Vector<FastRational> vector123 = null;

	/**
	 * a vector with values (4,5,6)
	 */
	protected static Vector<FastRational> vector456 = null;

	/**
	 * a vector with values (7,8,9)
	 */
	protected static Vector<FastRational> vector789 = null;

	/**
	 * a combination of vector123, vector456, vector789
	 */
	protected static Matrix<FastRational> m3t3 = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<FastRational> m3t3FullRank = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<FastRational> m3t3Squared = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<FastRational> m3t3FullRankInverse = null;

	/**
	 * assign values to various vectors and matrices.
	 */
	@BeforeClass
	public static void setUp()
	{
		identity3 = factoryRational.identity(3);
		vector123 = new Vector<FastRational>(new FastRational[] {
				FastRational.FACTORY.get(1.0), FastRational.FACTORY.get(2.0),
				FastRational.FACTORY.get(3.0)
		});
		vector456 = new Vector<FastRational>(new FastRational[] {
				FastRational.FACTORY.get(4.0), FastRational.FACTORY.get(5.0),
				FastRational.FACTORY.get(6.0)
		});
		vector789 = new Vector<FastRational>(new FastRational[] {
				FastRational.FACTORY.get(7.0), FastRational.FACTORY.get(8.0),
				FastRational.FACTORY.get(9.0)
		});
		m3t3 = new Matrix<FastRational>(3, 3, FastRational.FACTORY);
		m3t3.setRow(1, vector123);
		m3t3.setRow(2, vector456);
		m3t3.setRow(3, vector789);

		m3t3Squared = new Matrix<FastRational>(new FastRational[] {
				FastRational.FACTORY.get(30), FastRational.FACTORY.get(36),
				FastRational.FACTORY.get(42), FastRational.FACTORY.get(66),
				FastRational.FACTORY.get(81), FastRational.FACTORY.get(96),
				FastRational.FACTORY.get(102), FastRational.FACTORY.get(126),
				FastRational.FACTORY.get(150)
		}, 3);

		m3t3FullRank = m3t3.copy();
		m3t3FullRankInverse = new Matrix<FastRational>(new FastRational[][] {
				{
						FastRational.FACTORY.get(-16, 9),
						FastRational.FACTORY.get(8, 9),
						FastRational.FACTORY.get(-1, 9)
				},
				{
						FastRational.FACTORY.get(14, 9),
						FastRational.FACTORY.get(-7, 9),
						FastRational.FACTORY.get(2, 9)
				},
				{
						FastRational.FACTORY.get(-1, 9),
						FastRational.FACTORY.get(2, 9),
						FastRational.FACTORY.get(-1, 9)
				}
		});
		m3t3FullRank.set(3, 3, FastRational.FACTORY.get(0));
	}

	/**
	 * test {@link MatrixMultiplication#simple(Matrix, Matrix)}
	 */
	@Test
	public void testSimple1()
	{
		assertTrue(m3t3.equals(MatrixMultiplication.simple(m3t3, identity3)));
	}

	/**
	 * test {@link MatrixMultiplication#simple(Matrix, Matrix)}
	 */
	@Test
	public void testSimple2()
	{
		assertTrue(m3t3Squared.equals(MatrixMultiplication.simple(m3t3, m3t3)));
	}

	/**
	 * test {@link MatrixMultiplication#simple(Matrix, Matrix)}
	 */
	@Test
	public void testSimple3()
	{
		assertTrue(MatrixMultiplication.simple(m3t3FullRankInverse,
				(m3t3FullRank)).equals(identity3));
	}

	/**
	 * test {@link MatrixMultiplication#school(Matrix, Matrix)}
	 */
	@Test
	public void testSchool1()
	{
		assertTrue(m3t3.equals(MatrixMultiplication.school(m3t3, identity3)));
	}

	/**
	 * test {@link MatrixMultiplication#school(Matrix, Matrix)}
	 */
	@Test
	public void testSchool2()
	{
		assertTrue(m3t3Squared.equals(MatrixMultiplication.school(m3t3, m3t3)));
	}

	/**
	 * test {@link MatrixMultiplication#school(Matrix, Matrix)}
	 */
	@Test
	public void testSchool3()
	{
		assertTrue(MatrixMultiplication.school(m3t3FullRankInverse,
				(m3t3FullRank)).equals(identity3));
	}

	/**
	 * test {@link MatrixMultiplication#strassenOriginal(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenOriginal1()
	{
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(2);
		assertTrue(m3t3.equals(MatrixMultiplication.strassenOriginal(m3t3,
				identity3)));
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(48);
	}

	/**
	 * test {@link MatrixMultiplication#strassenOriginal(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenOriginal2()
	{
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(2);
		assertTrue(m3t3Squared.equals(MatrixMultiplication.strassenOriginal(
				m3t3, m3t3)));
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(48);
	}

	/**
	 * test {@link MatrixMultiplication#strassenOriginal(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenOriginal3()
	{
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(2);
		assertTrue(MatrixMultiplication.strassenOriginal(m3t3FullRankInverse,
				(m3t3FullRank)).equals(identity3));
		MMAccess.set_STRASSEN_ORIGINAL_TRUNCATION_POINT(48);
	}

	/**
	 * test {@link MatrixMultiplication#strassenWinograd(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenWinograd1()
	{
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(2);
		assertTrue(m3t3.equals(MatrixMultiplication.strassenWinograd(m3t3,
				identity3)));
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(48);
	}

	/**
	 * test {@link MatrixMultiplication#strassenWinograd(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenWinograd2()
	{
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(2);
		assertTrue(m3t3Squared.equals(MatrixMultiplication.strassenWinograd(
				m3t3, m3t3)));
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(48);
	}

	/**
	 * test {@link MatrixMultiplication#strassenWinograd(Matrix, Matrix)}
	 */
	@Test
	public void testStrassenWinograd3()
	{
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(2);
		assertTrue(MatrixMultiplication.strassenWinograd(m3t3FullRankInverse,
				(m3t3FullRank)).equals(identity3));
		MMAccess.set_STRASSEN_WINOGRAD_TRUNCATION_POINT(48);
	}

	@Test
	public void testStrassenBodrato1()
	{
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(2);
		assertTrue(m3t3.equals(MatrixMultiplication.strassenBodrato(m3t3,
				identity3)));
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(48);
	}

	@Test
	public void testStrassenBodrato2()
	{
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(2);
		assertTrue(m3t3Squared.equals(MatrixMultiplication.strassenBodrato(
				m3t3, m3t3)));
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(48);
	}

	@Test
	public void testStrassenBodrato3()
	{
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(2);
		assertTrue(MatrixMultiplication.strassenBodrato(m3t3FullRankInverse,
				(m3t3FullRank)).equals(identity3));
		MMAccess.set_STRASSEN_BODRATO_TRUNCATION_POINT(48);
	}

}
