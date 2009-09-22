package org.jlinalg.doublewrapper;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.doublewrapper.DoubleWrapper.DoubleWrapperFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.RandomNumberList;
import org.junit.Test;

/**
 * @author Georg Thimm
 */

public class DoubleWrapperBlackBoxTest
{
	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper} using
	 * {@link org.jlinalg.RingElementFactory#get(Object)}. objects
	 */
	@Test
	public void rationalToDoubleWrapper() throws Exception
	{
		for (Rational r : new RandomNumberList<Rational>(Rational.FACTORY, 20))
		{
			DoubleWrapper d = DoubleWrapper.FACTORY.get(r);
			assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < (d.abs())
					.doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * matrices
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void rationalToDoubleWrapperMatrices() throws Exception
	{
		DoubleWrapperFactory fac = DoubleWrapper.FACTORY;

		LinAlgFactory<Rational> rfac = new LinAlgFactory<Rational>(
				Rational.FACTORY);

		// for a matrix
		Matrix<Rational> mRational = rfac.gaussianNoise(10, 5, new Random());
		Matrix<DoubleWrapper> mDouble = fac.convert(mRational);
		for (int i = 0; i < 10; i++) {
			for (int row = 1; row <= mRational.getRows(); row++) {
				for (int col = 1; col <= mRational.getCols(); col++) {
					DoubleWrapper d = mDouble.get(row, col);
					Rational r = mRational.get(row, col);
					assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < (d
							.abs()).doubleValue() / 1000);
				}
			}
		}
	}

}
