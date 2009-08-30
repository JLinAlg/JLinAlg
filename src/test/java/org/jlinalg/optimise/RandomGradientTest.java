package org.jlinalg.optimise;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jlinalg.Complex;
import org.jlinalg.DoubleWrapper;
import org.jlinalg.Rational;
import org.jlinalg.demo.Function;
import org.jlinalg.optimise.Optimiser;
import org.jlinalg.optimise.RandomGradientDescent;
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
		System.err.println("\n\n testFunction");
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
		// Function is an implementation of Target<DoubleWrapper>
		System.err.println("\n\ntestComplex ");
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
			assertTrue("Parameter " + coefficients[i]
					+ " is too far off the optimal value " + target.best()[i],
					((Complex) coefficients[i].subtract(target.best()[i]))
							.norm().lt(maxDiff));
		}
	}
}
