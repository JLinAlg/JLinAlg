/**
 * 
 */
package org.jlinalg;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.jlinalg.DoubleWrapper.DoubleWrapperFactory;
import org.jlinalg.Rational.RationalFactory;
import org.junit.Test;

/**
 * @author Georg Thimm (2009)
 */
public class TypeConverterTest
{

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper} using
	 * {@link org.jlinalg.RingElementFactory#get(Object)}. objects
	 */
	@Test
	public void rationalToDoubleWrapper() throws Exception
	{
		DoubleWrapperFactory dc = DoubleWrapper.FACTORY;
		for (Rational r : new RandomNumberList<Rational>(Rational.FACTORY, 20))
		{
			DoubleWrapper d = dc.get(r);
			assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < ((DoubleWrapper) d
					.abs()).doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * matrices
	 */
	@Test
	public void rationalToDoubleWrapperMatrices() throws Exception
	{
		DoubleWrapperFactory fac = DoubleWrapper.FACTORY;

		LinAlgFactory<Rational> rfac = new LinAlgFactory<Rational>(
				Rational.FACTORY);

		// for a matrix
		Matrix<Rational> mRational = rfac.gaussianNoise(10, 5, new Random());
		Matrix<DoubleWrapper> mDouble = fac.convert(mRational);
		for (int row = 1; row <= mRational.getRows(); row++) {
			for (int col = 1; col <= mRational.getCols(); col++) {
				DoubleWrapper d = mDouble.get(row, col);
				Rational r = mRational.get(row, col);
				assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < ((DoubleWrapper) d
						.abs()).doubleValue() / 1000);
			}
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * vectors
	 */
	@Test
	public void rationalToDoubleWrapperVectors() throws Exception
	{
		DoubleWrapperFactory fac = DoubleWrapper.FACTORY;

		LinAlgFactory<Rational> rfac = new LinAlgFactory<Rational>(
				Rational.FACTORY);

		// for a vector
		Vector<Rational> vRational = rfac.gaussianNoise(10, new Random());
		Vector<DoubleWrapper> vDouble = fac.convert(vRational);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			Rational r = vRational.getEntry(row);
			assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < ((DoubleWrapper) d
					.abs()).doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link DoubleWrapper} to {@link Rational} using
	 * {@link org.jlinalg.RingElementFactory#get(Object)}. objects
	 */
	@Test
	public void doubleToRationalWrapper() throws Exception
	{
		RationalFactory fac = Rational.FACTORY;
		for (DoubleWrapper d : new RandomNumberList<DoubleWrapper>(
				DoubleWrapper.FACTORY, 20))
		{
			Rational r = fac.get(d);
			assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")", Math.abs(d
					.doubleValue()
					- r.doubleValue()) <= ((DoubleWrapper) d.abs())
					.doubleValue() / 1000);
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * matrices
	 */
	@Test
	public void doubleToRationalMatrices() throws Exception
	{
		RationalFactory fac = Rational.FACTORY;

		LinAlgFactory<DoubleWrapper> rfac = new LinAlgFactory<DoubleWrapper>(
				DoubleWrapper.FACTORY);

		// for a matrix
		Matrix<DoubleWrapper> mDouble = rfac.gaussianNoise(10, 5, new Random());
		Matrix<Rational> mRational = fac.convert(mDouble);
		for (int row = 1; row <= mRational.getRows(); row++) {
			for (int col = 1; col <= mRational.getCols(); col++) {
				DoubleWrapper d = mDouble.get(row, col);
				Rational r = mRational.get(row, col);
				assertTrue(
						d + "!=" + r + "(=" + r.doubleValue() + ")",
						Math.abs(d.doubleValue() - r.doubleValue()) <= ((DoubleWrapper) d
								.abs()).doubleValue() / 1000);
			}
		}
	}

	/**
	 * Test the conversion from {@link Rational} to {@link DoubleWrapper}
	 * vectors
	 */
	@Test
	public void doubleToRationalVectors() throws Exception
	{
		RationalFactory fac = Rational.FACTORY;

		LinAlgFactory<DoubleWrapper> rfac = new LinAlgFactory<DoubleWrapper>(
				DoubleWrapper.FACTORY);

		// for a vector
		Vector<DoubleWrapper> vDouble = rfac.gaussianNoise(10, new Random());
		Vector<Rational> vRational = fac.convert(vDouble);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			Rational r = vRational.getEntry(row);
			assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")", Math.abs(d
					.doubleValue()
					- r.doubleValue()) <= ((DoubleWrapper) d.abs())
					.doubleValue() / 1000);
		}
	}

	private static String[] numbers =
	{
			"0.0010826180013877584", "-0.0025913353586868144",
			"-0.0028414767757680925"
	};

	/**
	 * Test Double to rational to double
	 */
	@Test
	public void testDoubleRational()
	{
		for (int i = 0; i < numbers.length; i++) {
			String number = numbers[i];
			DoubleWrapper d1 = DoubleWrapper.FACTORY.get(number);
			String s1 = d1.toString();
			assertTrue("number=" + number + " s1=" + s1, number.regionMatches(
					0, s1, 0, 10));
			Rational r = Rational.FACTORY.get(d1);
			DoubleWrapper d2 = DoubleWrapper.FACTORY.get(r);
			String s2 = d2.toString();
			assertTrue("number=" + number + " s2=" + s2, number.regionMatches(
					0, s2, 0, 10));
		}
	}
}
