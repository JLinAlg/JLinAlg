/**
 * 
 */
package org.jlinalg.demo;

import org.junit.Test;

/**
 * @author mgeorg
 */
public class DemoTest
{
	/**
	 * try whether main methods of the demos throw exceptions.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testMain() throws InterruptedException
	{
		ArbitraryPrecisionDemo.main(null);
		CharacteristicPolynomialDemo.main(null);
		EigenvaluesDemo.main(null);
		F2Demo.main(null);
		FieldPDemo.main(null);
		HilbertMatrixDemo.main(null);
		LinearEquationSystemDemo.main(null);
		MatrixOperationsDemo.main(null);
		PolynomialDemo.main(null);
		RandomGradientSearchDemo.main(null);
		RationalFunctionDemo.main(null);
		Xor.main(null);

	}
}
