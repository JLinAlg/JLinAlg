package org.jlinalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.Rational.RationalFactory;
import org.junit.Test;

public class MatrixInversionTest
{
	/**
	 * test the inversion of
	 * 
	 * <pre>
	 *    [ [ 0, 0, 0, -7/6, 0, -1, 0, -5/3, 0, 0 ],
	 * 	    [ 0, 0, -1/2, 0, 0, -4/5, 0, 7/3, 2/7, 0 ],
	 * 	    [ -3, 0, 0, -1/3, 0, 0, 0, 2, 1/2, -5/7 ],
	 * 	    [ 1/3, 0, 0, 0, -7, 0, 0, -5/7, -1/2, 0 ],
	 * 	    [ -5/7, 0, -1, 1/2, -5/6, 0, 0, 0, 0, 2/5 ],
	 * 	    [ 0, 0, 2/7, 1/2, -3, 0, 0, 0, 0, 0 ],
	 * 	    [ 0, 0, 0, 0, 0, -7/3, 0, -1/4, 0, 1/8 ],
	 * 	    [ 0, -2, 0, 0, 0, -7/4, 0, 0, -1/8, -1/2 ],
	 * 	    [ -4/5, -1/2, -4/5, 4/5, -4/5, -2, 0, 0, 1/4, -1/2 ],
	 * 	    [ 0, 0, 0, -1, 0, 3, 0, 6/5, 0, -2/5 ] ]
	 * </pre>
	 */
	@Test
	public void inverseTest()
	{
		RationalFactory f = Rational.FACTORY;

		Rational[][] entries = new Rational[10][10];
		Rational z = f.zero();
		entries[0] = new Rational[]
		{
				z, z, z, f.get(-7, 6), z, f.get(-1), z, f.get(-5, 3), z, z
		};
		entries[1] = new Rational[]
		{
				z, z, f.get(-1, 2), z, z, f.get(-4, 5), z, f.get(7, 3),
				f.get(2, 7), z
		};
		entries[2] = new Rational[]
		{
				f.get(-3), z, z, f.get(-1, 3), z, z, z, f.get(2), f.get(1, 2),
				f.get(-5, 7)
		};
		entries[3] = new Rational[]
		{
				f.get(1, 3), z, z, z, f.get(-7), z, z, f.get(-5, 7),
				f.get(-1, 2), z
		};
		entries[4] = new Rational[]
		{
				f.get(-5, 7), z, f.get(-1), f.get(1, 2), f.get(-5, 6), z, z, z,
				z, f.get(2, 5)
		};
		entries[5] = new Rational[]
		{
				z, z, f.get(2, 7), f.get(1, 2), f.get(-3), z, z, z, z, z
		};
		entries[6] = new Rational[]
		{
				z, z, z, z, z, f.get(-7, 3), z, f.get(-1, 4), z, f.get(1, 8)
		};
		entries[7] = new Rational[]
		{
				z, f.get(-2), z, z, z, f.get(-7, 4), z, z, f.get(-1, 8),
				f.get(-1, 2)
		};
		entries[8] = new Rational[]
		{
				f.get(-4, 5), f.get(-1, 2), f.get(-4, 5), f.get(4, 5),
				f.get(-4, 5), f.get(-2), z, z, f.get(1, 4), f.get(-1, 2)
		};
		entries[9] = new Rational[]
		{
				z, z, z, f.get(-1), z, f.get(3), z, f.get(6, 5), z,
				f.get(-2, 5)
		};
		Matrix<Rational> m = new Matrix<Rational>(entries);

		assertEquals(9, m.rank());
		assertEquals(Rational.FACTORY.zero(), m.det());
		assertEquals(null, m.inverse());
	}

	@Test
	public void inverseTest2()
	{
		RationalFactory f = Rational.FACTORY;

		Rational[][] entries = new Rational[10][10];
		Rational z = f.zero();
		entries[0] = new Rational[]
		{
				z, z, z, f.get(-4), z, f.get(1, 4), f.get(-4), z, z,
				f.get(-7, 3)
		};
		entries[1] = new Rational[]
		{
				z, f.get(-1), z, z, z, z, z, z, f.get(-6, 7), z, f.get(-2, 3)
		};
		entries[2] = new Rational[]
		{
				z, f.get(-1, 3), z, z, f.get(-5, 7), z, z, z, z, z, f.get(2, 3)
		};
		entries[3] = new Rational[]
		{
				z, f.get(1, 2), z, f.get(2, 7), z, f.get(-1, 5), z, f.get(-1),
				z, z, z
		};
		entries[4] = new Rational[]
		{
				z, f.get(-1), z, f.get(7, 8), z, z, f.get(-1), z, f.get(-1, 2),
				f.get(2, 3), f.get(1, 2)
		};
		entries[5] = new Rational[]
		{
				z, f.get(-4), z, f.get(2, 7), z, z, z, f.get(-7, 8), z, z, z
		};
		entries[6] = new Rational[]
		{
				f.get(7, 8), f.get(5, 2), f.get(1, 2), z, z, f.get(1, 2), z,
				f.get(-7), z, f.get(-4, 7), z
		};
		entries[7] = new Rational[]
		{
				f.get(-2), z, f.get(2, 3), z, f.get(1, 2), z, f.get(-2, 3), z,
				z, f.get(3, 7), z
		};
		entries[8] = new Rational[]
		{
				f.get(6), z, z, z, z, z, z, f.get(-7, 8), z, f.get(4), z
		};
		entries[9] = new Rational[]
		{
				z, f.get(2, 5), f.get(3), z, f.get(-1), z, z, z, f.get(-1, 3),
				z, z
		};

		Matrix<Rational> m = new Matrix<Rational>(entries);
		assertEquals(10, m.rank());
		assertTrue(m.multiply(m.inverse()).isIdentity());
	}

	@Test
	public void inverseTest3()
	{
		LinAlgFactory<Rational> f = new LinAlgFactory<Rational>(
				Rational.FACTORY);
		Matrix<Rational> m = f.identity(2);

		assertTrue(m.multiply(m.inverse()).isIdentity());
		assertEquals(2, m.rank());
	}

	@Test
	public void inverseTest4()
	{
		LinAlgFactory<Rational> f = new LinAlgFactory<Rational>(
				Rational.FACTORY);
		Matrix<Rational> m = f.zeros(2, 2);

		assertEquals(null, m.inverse());
		assertEquals(0, m.rank());
	}

}
