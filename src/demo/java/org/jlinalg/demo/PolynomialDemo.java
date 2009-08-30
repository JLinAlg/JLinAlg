package org.jlinalg.demo;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Rational;
import org.jlinalg.polynomial.Polynomial;

/**
 * Demonstrate the use of polynomials for the domain of rational numbers.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class PolynomialDemo
{

	/**
	 * start the demonstration
	 * 
	 * @param argv
	 *            is ignored
	 */
	public static void main(String[] argv)
	{
		// Create a factory for complex objects made from rational numbers.
		LinAlgFactory<Rational> factory = new LinAlgFactory<Rational>(
				Rational.FACTORY);
		// this factory allows it to create matrices (or vectors) from rational
		// numbers - even from an array of double numbers.
		Matrix<Rational> a = factory.buildMatrix(new double[][]
		{
				{
						2.0, 1.5
				},
				{
						-1.0, 0.0
				}
		});

		System.out.println("a=\n" + a);

		// calculate the characteristic polynomial"char(a)"
		Polynomial<Rational> characteristicPolynomial = a
				.characteristicPolynomial();
		System.out.println("char(a): " + characteristicPolynomial);

		System.out.println("norm(char(a)): " + characteristicPolynomial.norm());

		System.out.println("invert(norm(char(a))): "
				+ characteristicPolynomial.norm().invert());

		// calculate the minimal polynomial "min(a)"
		Polynomial<Rational> minimalPolynomial = a.minimalPolynomial();
		System.out.println("min(a): " + minimalPolynomial);

		// use a long division to calculate "char(a) / min(a)"
		System.out.println("char(a) / min(a): "
				+ characteristicPolynomial.longDivision(minimalPolynomial));

		// integrate "char(a)"
		System.out.println("Int(char(a)): "
				+ characteristicPolynomial.integrate());

	}
}
