package org.jlinalg.rational;

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
public class RationalMatrixMultiplicationTest
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
	 * an instance of {@link LinAlgFactory} for base type {@link Rational}
	 */
	protected static LinAlgFactory<Rational> factoryRational = new LinAlgFactory<Rational>(
			Rational.FACTORY);

	/**
	 * a 3x3 identity matrix
	 */
	protected static Matrix<Rational> identity3 = null;

	/**
	 * a vector with values (1,2,3)
	 */
	protected static Vector<Rational> vector123 = null;

	/**
	 * a vector with values (4,5,6)
	 */
	protected static Vector<Rational> vector456 = null;

	/**
	 * a vector with values (7,8,9)
	 */
	protected static Vector<Rational> vector789 = null;

	/**
	 * a combination of vector123, vector456, vector789
	 */
	protected static Matrix<Rational> m3t3 = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<Rational> m3t3FullRank = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<Rational> m3t3Squared = null;

	/**
	 * a 3x3 matrix
	 */
	protected static Matrix<Rational> m3t3FullRankInverse = null;

	/**
	 * assign values to various vectors and matrices.
	 */
	@BeforeClass
	public static void setUp()
	{
		identity3 = factoryRational.identity(3);
		vector123 = new Vector<Rational>(new Rational[]
		{
				Rational.FACTORY.get(1.0), Rational.FACTORY.get(2.0),
				Rational.FACTORY.get(3.0)
		});
		vector456 = new Vector<Rational>(new Rational[]
		{
				Rational.FACTORY.get(4.0), Rational.FACTORY.get(5.0),
				Rational.FACTORY.get(6.0)
		});
		vector789 = new Vector<Rational>(new Rational[]
		{
				Rational.FACTORY.get(7.0), Rational.FACTORY.get(8.0),
				Rational.FACTORY.get(9.0)
		});
		m3t3 = new Matrix<Rational>(3, 3, Rational.FACTORY);
		m3t3.setRow(1, vector123);
		m3t3.setRow(2, vector456);
		m3t3.setRow(3, vector789);

		m3t3Squared = new Matrix<Rational>(new Rational[]
		{
				Rational.FACTORY.get(30), Rational.FACTORY.get(36),
				Rational.FACTORY.get(42), Rational.FACTORY.get(66),
				Rational.FACTORY.get(81), Rational.FACTORY.get(96),
				Rational.FACTORY.get(102), Rational.FACTORY.get(126),
				Rational.FACTORY.get(150)
		}, 3);

		m3t3FullRank = m3t3.copy();
		m3t3FullRankInverse = new Matrix<Rational>(new Rational[][]
		{
				{
						Rational.FACTORY.get(-16, 9),
						Rational.FACTORY.get(8, 9), Rational.FACTORY.get(-1, 9)
				},
				{
						Rational.FACTORY.get(14, 9),
						Rational.FACTORY.get(-7, 9), Rational.FACTORY.get(2, 9)
				},
				{
						Rational.FACTORY.get(-1, 9),
						Rational.FACTORY.get(2, 9), Rational.FACTORY.get(-1, 9)
				}
		});
		m3t3FullRank.set(3, 3, Rational.FACTORY.get(0));
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
