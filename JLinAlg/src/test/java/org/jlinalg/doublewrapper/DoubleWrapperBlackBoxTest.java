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

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
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
		for (Rational r : new RandomNumberList<>(Rational.FACTORY, 20)) {
			DoubleWrapper d = DoubleWrapper.FACTORY.get(r);
			assertTrue(Math.abs(d.doubleValue() - r.doubleValue()) < (d.abs())
					.doubleValue() / 1000);
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

		LinAlgFactory<Rational> rfac = new LinAlgFactory<>(Rational.FACTORY);

		// for a matrix
		Matrix<Rational> mRational = rfac.gaussianNoise(10, 5);
		Matrix<DoubleWrapper> mDouble = fac.convert(mRational);
		for (int i = 0; i < 10; i++) {
			for (int row = 1; row <= mRational.getRows(); row++) {
				for (int col = 1; col <= mRational.getCols(); col++) {
					DoubleWrapper d = mDouble.get(row, col);
					Rational r = mRational.get(row, col);
					assertTrue(Math.abs(d.doubleValue()
							- r.doubleValue()) < (d.abs()).doubleValue()
									/ 1000);
				}
			}
		}
	}

}
