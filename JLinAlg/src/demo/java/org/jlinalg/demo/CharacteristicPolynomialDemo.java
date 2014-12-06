package org.jlinalg.demo;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.rational.Rational;

/**
 * Demonstrate characteristic polynomials for a matrix over the domain of
 * rational numbers.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 */
public class CharacteristicPolynomialDemo
{
	final static Object[][] s_matrix = {
			{
					"2", new Double(1.5)
			}, {
					"-1/6", new Integer(0)
			}
	};

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
		// this factory allows it to create matrices (or vectors)
		Matrix<Rational> id = factory.identity(3);
		System.out.println("id=\n" + id);
		// alternatively, the constructors of matrixes (alike those for vectors)
		// allow to build them from arrays - even from arrays with mixed
		// objects.
		Matrix<Rational> a = new Matrix<Rational>(s_matrix, Rational.FACTORY);

		System.out.println("a=\n" + a);

		// calculate the characteristic polynomial"char(a)"
		Polynomial<Rational> characteristicPolynomial = a
				.characteristicPolynomial();
		System.out.println("char(a): " + characteristicPolynomial);

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
