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

/**
 * Demonstrate the use of polynomials for the domain of rational numbers.
 * 
 * @author Andreas Keilhauer
 */
public class PolynomialDemo
{

	/**
	 * start the demonstration
	 * 
	 * @param argv
	 *            is ignored
	 */
	@SuppressWarnings("boxing")
	public static void main(String[] argv)
	{
		PolynomialFactory<Rational> factory = PolynomialFactory
				.getFactory(Rational.FACTORY);

		Map<Integer, Rational> coefficients = new HashMap<Integer, Rational>();
		coefficients.put(2, Rational.FACTORY.get(1));
		coefficients.put(1, Rational.FACTORY.get(2));
		coefficients.put(0, Rational.FACTORY.get(1));
		Polynomial<Rational> polynomial = factory.get(coefficients);
		System.out.println("poly: " + polynomial);
		System.out.println("Int(poly): " + polynomial.integrate());
		System.out.println("Diff(poly): " + polynomial.differentiate());
		System.out.println("min(poly): " + polynomial.minimalPolynomial());
		System.out.println("poly / min(poly): "
				+ polynomial.euclideanDivision(polynomial.minimalPolynomial()));
	}
}
