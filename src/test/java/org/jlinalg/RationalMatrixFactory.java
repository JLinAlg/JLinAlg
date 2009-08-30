package org.jlinalg;

import org.jlinalg.Matrix;
import org.jlinalg.Rational;

/**
 * create square unit and zero matrices with rational numbers for other tests
 * 
 * @author Georg Thimm
 */
public class RationalMatrixFactory
{

	public static Matrix<Rational> unit(final int k)
	{
		Matrix<Rational> m = new Matrix<Rational>(k, k, Rational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				if (r == c)
					m.set(r, c, Rational.FACTORY.one());
				else
					m.set(r, c, Rational.FACTORY.zero());
		return m;
	}

	public static Matrix<Rational> zero(final int k)
	{
		Matrix<Rational> m = new Matrix<Rational>(k, k, Rational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				m.set(r, c, Rational.FACTORY.zero());
		return m;
	}

	public static Matrix<Rational> fractions(final int k)
	{
		Matrix<Rational> m = new Matrix<Rational>(k, k, Rational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				m.set(r, c, Rational.FACTORY.get(r, c));
		return m;
	}

	public static Matrix<Rational> funny(final int k)
	{
		Matrix<Rational> m = new Matrix<Rational>(k, k, Rational.FACTORY);
		for (int c = 1; c <= k; c++)
			for (int r = 1; r <= k; r++)
				m.set(r, c, Rational.FACTORY.get(r + c, c * c));
		m.addReplace(unit(k));
		return m;
	}
}
