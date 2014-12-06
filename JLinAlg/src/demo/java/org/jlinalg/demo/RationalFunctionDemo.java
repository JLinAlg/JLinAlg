package org.jlinalg.demo;

import java.util.HashMap;
import java.util.Map;

import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.rationalFunction.RationalFunction;

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
	@SuppressWarnings("boxing")
	public static void main(String[] argv)
	{
		PolynomialFactory<Rational> factory = PolynomialFactory
				.getFactory(Rational.FACTORY);

		Map<Integer, Rational> coefficients = new HashMap<Integer, Rational>();
		coefficients.put(2, Rational.FACTORY.get(1));
		coefficients.put(1, Rational.FACTORY.get(2));
		coefficients.put(0, Rational.FACTORY.get(1));
		Polynomial<Rational> poly = factory.get(coefficients);

		Map<Integer, Rational> coefficientsDenominator = new HashMap<Integer, Rational>();
		coefficientsDenominator.put(1, Rational.FACTORY.get(1));
		coefficientsDenominator.put(0, Rational.FACTORY.get(1));
		Polynomial<Rational> polyDenominator = factory
				.get(coefficientsDenominator);

		RationalFunction<Rational> ratFun = new RationalFunction<>(poly,
				polyDenominator);
		System.out.println(ratFun);
		ratFun.invert();

	}
}
