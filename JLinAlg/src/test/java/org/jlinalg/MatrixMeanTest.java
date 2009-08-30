package org.jlinalg;

import static org.junit.Assert.assertTrue;

import org.jlinalg.DoubleWrapper;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.junit.Test;

/**
 * Tests the method mean() for matrices
 * 
 * @author Georg Thimm
 */
public class MatrixMeanTest
{
	/**
	 * for data type Rational
	 */
	@Test
	public void testRational()
	{
		Matrix<Rational> m = new Matrix<Rational>(3, 4, Rational.FACTORY);
		for (int r = 1; r <= m.getRows(); r++) {
			for (int c = 1; c <= m.getCols(); c++) {
				m.set(r, c, Rational.FACTORY.get(r, c));
			}
		}
		System.err.println(m);
		assertTrue("wrong mean = " + m.mean() + " for matrix \n" + m, m.mean()
				.equals(Rational.FACTORY.get(25, 24)));
	}

	/**
	 * for data type DoubleWrapper
	 */
	@Test
	public void testDoubleWrapper()
	{
		Matrix<DoubleWrapper> m = new Matrix<DoubleWrapper>(3, 4,
				DoubleWrapper.FACTORY);
		for (int r = 1; r <= m.getRows(); r++) {
			for (int c = 1; c <= m.getCols(); c++) {
				m.set(r, c, new DoubleWrapper((double) r / (double) c));
			}
		}
		System.err.println(m);
		assertTrue("wrong mean = " + m.mean(), m.mean().subtract(
				new DoubleWrapper((25.0 / 24.0)).abs()).le(
				new DoubleWrapper(1e-10)));
	}
}
