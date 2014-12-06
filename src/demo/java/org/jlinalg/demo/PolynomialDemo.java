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
				+ polynomial.longDivision(polynomial.minimalPolynomial()));
	}
}
