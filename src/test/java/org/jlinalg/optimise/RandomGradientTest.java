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
package org.jlinalg.optimise;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.complex.Complex;
import org.jlinalg.demo.Function;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.rational.Rational;
import org.junit.Test;

/**
 * tests the use of the {@link RandomGradientDescent} optimiser by the means of
 * {@link Function}.
 * 
 * @author Georg THimm
 */
public class RandomGradientTest
{

	/**
	 * Test the optimisation of {@link Function} using
	 * {@link RandomGradientDescent}
	 * 
	 * @throws InterruptedException
	 *             because of the Thread.
	 */
	@Test
	public void testFunction() throws InterruptedException
	{
		// Function is an implementation of Target<DoubleWrapper>
		// System.err.println("\n\n testFunction");
		Function target = new Function();
		DoubleWrapper resid = target.getResidual();
		Optimiser<DoubleWrapper> optimiser = new RandomGradientDescent<DoubleWrapper>(
				target);
		Thread optThread = optimiser.optimise();

		optThread.join();

		assertSame(Optimiser.State.FINISHED_SUCCESSFULL, optimiser.getState());
		assertTrue("target.getResidual()=" + target.getResidual() + " resid="
				+ resid, target.getResidual().le(resid));
		resid = target.getResidual();
		assertTrue("Residual too big: " + resid, resid.le(DoubleWrapper.FACTORY
				.get(1e-6)));
		DoubleWrapper maxDiff = DoubleWrapper.FACTORY.get(0.0001);
		for (int i = 0; i < target.best().length; i++) {
			assertTrue("Parameter " + target.getParameters()[i]
					+ " is too far off the optimal value " + target.best()[i],
					target.getParameters()[i].subtract(target.best()[i]).abs()
							.lt(maxDiff));
		}
	}

	/**
	 * Test the optimisation of {@link ComplexFunction} using
	 * {@link RandomGradientDescent}
	 * 
	 * @throws InterruptedException
	 *             because of the Thread.
	 */
	@Test
	public void testComplex() throws InterruptedException
	{
		// System.err.println("\n\ntestComplex ");
		ComplexFunction target = new ComplexFunction();
		Rational resid = target.getResidual();
		Optimiser<Rational> optimiser = new RandomGradientDescent<Rational>(
				target);
		Thread optThread = optimiser.optimise();

		optThread.join();

		assertSame("target is " + target.toString() + " residual="
				+ target.getResidual().doubleValue() + "   ",
				Optimiser.State.FINISHED_SUCCESSFULL, optimiser.getState());
		assertTrue("target.getResidual()=" + target.getResidual() + " resid="
				+ resid, target.getResidual().le(resid));
		resid = target.getResidual();
		Rational maxResidual = Rational.FACTORY.get(1e-6);
		assertTrue(
				"Residual is too big (this may occasionally occur due to an unlucky starting point): "
						+ resid.doubleValue(), resid.le(maxResidual));
		Rational maxDiff = Rational.FACTORY.get(0.0001);
		Complex[] coefficients = target.is();
		for (int i = 0; i < target.best().length; i++) {
			Complex n = coefficients[i].subtract(target.best()[i].norm());
			assertTrue("Parameter " + coefficients[i]
					+ " is too far off the optimal value " + target.best()[i],
					n.getReal().lt(maxDiff));
		}
	}
}
