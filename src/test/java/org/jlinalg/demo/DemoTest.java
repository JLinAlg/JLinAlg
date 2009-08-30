/**
 * 
 */
package org.jlinalg.demo;

import org.jlinalg.demo.EigenvaluesDemo;
import org.jlinalg.demo.F2Demo;
import org.jlinalg.demo.FieldPDemo;
import org.jlinalg.demo.LinearEquationSystemDemo;
import org.jlinalg.demo.MatrixOperationsDemo;
import org.jlinalg.demo.PolynomialDemo;
import org.jlinalg.demo.RandomGradientSearchDemo;
import org.jlinalg.demo.Xor;
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
		EigenvaluesDemo.main(null);
		F2Demo.main(null);
		FieldPDemo.main(null);
		LinearEquationSystemDemo.main(null);
		MatrixOperationsDemo.main(null);
		Xor.main(null);
		PolynomialDemo.main(null);
		RandomGradientSearchDemo.main(null);
	}
}
