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
package org.jlinalg.demotest;

import org.jlinalg.demo.ArbitraryPrecisionDemo;
import org.jlinalg.demo.CharacteristicPolynomialDemo;
import org.jlinalg.demo.EigenvaluesDemo;
import org.jlinalg.demo.F2Demo;
import org.jlinalg.demo.FieldPDemo;
import org.jlinalg.demo.HilbertMatrixDemo;
import org.jlinalg.demo.LinearEquationSystemDemo;
import org.jlinalg.demo.MatrixOperationsDemo;
import org.jlinalg.demo.PolynomialDemo;
import org.jlinalg.demo.RandomGradientSearchDemo;
import org.jlinalg.demo.RationalFunctionDemo;
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
