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
package org.jlinalg.doublewrapper;

import static org.junit.Assert.assertTrue;

import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper.DoubleWrapperFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.rational.Rational.RationalFactory;
import org.jlinalg.testutil.FactoryTestBase;
import org.jlinalg.testutil.RandomNumberList;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Georg Thimm
 */
public class DoubleWrapperFactoryTest
		extends FactoryTestBase<DoubleWrapper>
{

	private static DoubleWrapperFactory fac;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		fac = DoubleWrapper.FACTORY;
	}

	/**
	 * Test
	 * {@link org.jlinalg.doublewrapper.DoubleWrapper.DoubleWrapperFactory#get(Object)}
	 */
	@Test
	public void get() throws Exception
	{
		double d = 1.234;
		assertTrue(d == fac.get(d).doubleValue());
		assertTrue(d == fac.get(Double.toString(d)).doubleValue());
		assertTrue(-0.375 == fac.get("-3/8").doubleValue());
	}

	/**
	 * Test
	 */
	@Test(expected = InvalidOperationException.class)
	public void get1() throws Exception
	{
		fac.get("a");
	}

	/**
	 * Test
	 */
	@Test(expected = InvalidOperationException.class)
	public void get2() throws Exception
	{
		fac.get("1/2b");
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
		double[][] matrix = {
				{
						-100, -.001, 0, -1.2345e-20
				}, {
						1, 2, 3e20, -3e20
				}, {
						100, 1111, 5.999, 1.6789e-20
				}
		};
		Matrix<Rational> mRational = rfac.buildMatrix(matrix);
		Matrix<DoubleWrapper> mDouble = fac.convert(mRational);
		for (int row = 1; row <= mRational.getRows(); row++) {
			for (int col = 1; col <= mRational.getCols(); col++) {
				DoubleWrapper d = mDouble.get(row, col);
				Rational r = mRational.get(row, col);
				assertTrue("d=" + d + " r=" + r, Math.abs(d.doubleValue()
						- r.doubleValue()) <= (d.abs())
						.doubleValue() / 1000);
			}
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
					- r.doubleValue()) <= (d.abs())
					.doubleValue() / 1000);
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
		Vector<DoubleWrapper> vDouble = rfac.gaussianNoise(10);
		Vector<Rational> vRational = fac.convert(vDouble);
		for (int row = 1; row <= vRational.length(); row++) {
			DoubleWrapper d = vDouble.getEntry(row);
			Rational r = vRational.getEntry(row);
			assertTrue(d + "!=" + r + "(=" + r.doubleValue() + ")", Math.abs(d
					.doubleValue()
					- r.doubleValue()) <= (d.abs())
					.doubleValue() / 1000);
		}
	}

	@Override
	public IRingElementFactory<DoubleWrapper> getFactory()
	{
		return DoubleWrapper.FACTORY;
	}

}
