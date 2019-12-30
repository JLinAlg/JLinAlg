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
package org.jlinalg.demo;

import java.util.HashMap;
import java.util.Map;

import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.rationalFunction.RationalFunction;
import org.jlinalg.rationalFunction.RationalFunctionFactory;

/**
 * Demonstrate the use of rational functions for the domain of rational numbers.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class RationalFunctionDemo
{

	/**
	 * start the demonstration
	 * 
	 * @param argv
	 *            is ignored
	 */
	public static void main(String[] argv)
	{
		PolynomialFactory<Rational> polynomialFactory = PolynomialFactory
				.getFactory(Rational.FACTORY);
		RationalFunctionFactory<Rational> factory = RationalFunctionFactory
				.getFactory(Rational.FACTORY);
		Map<Integer, Rational> coefficients = new HashMap<>();
		coefficients.put(2, Rational.FACTORY.get(1));
		coefficients.put(1, Rational.FACTORY.get(2));
		coefficients.put(0, Rational.FACTORY.get(1));
		Polynomial<Rational> poly = polynomialFactory.get(coefficients);

		Map<Integer, Rational> coefficientsDenominator = new HashMap<>();
		coefficientsDenominator.put(1, Rational.FACTORY.get(1));
		coefficientsDenominator.put(0, Rational.FACTORY.get(1));
		Polynomial<Rational> polyDenominator = polynomialFactory
				.get(coefficientsDenominator);

		RationalFunction<Rational> ratFun = factory.get(poly, polyDenominator,
				Rational.FACTORY);
		System.out.println(ratFun);
		ratFun.invert();

	}
}
