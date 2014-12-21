package org.jlinalg.rationalFunction;

import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.polynomial.Polynomial;

/**
 * Class that represents a rational function over a given BASE.
 * 
 * @author Andreas Keilhauer
 * @param <BASE>
 */
public class RationalFunction<BASE extends IRingElement<BASE>>
		extends FieldElement<RationalFunction<BASE>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Polynomial<BASE> numerator;
	private Polynomial<BASE> denominator;

	/**
	 * the singleton factory for rational functions
	 */
	private final RationalFunctionFactory<BASE> rationalFunctionFactory;

	@SuppressWarnings("unchecked")
	public RationalFunction(BASE value)
	{
		if (value == null) {
			throw new InvalidOperationException("value cannot be null!");
		}
		this.rationalFunctionFactory = (RationalFunctionFactory<BASE>) RationalFunctionFactoryMap.INSTANCE
				.get(value.getFactory());
		this.numerator = new Polynomial<BASE>(value);
		this.denominator = new Polynomial<BASE>(value.getFactory().one());
	}

	@SuppressWarnings("unchecked")
	public RationalFunction(Polynomial<BASE> numerator,
			Polynomial<BASE> denominator,
			final IRingElementFactory<BASE> baseFactory)
	{
		if (baseFactory == null) {
			throw new InvalidOperationException(
					"The factory of a Polynomial cannot be null!");
		}
		if (numerator == null) {
			throw new InvalidOperationException(
					"The numerator of a rational function cannot be null!");
		}
		if (denominator == null) {
			throw new InvalidOperationException(
					"The denominator of a rational function cannot be null!");
		}
		this.rationalFunctionFactory = (RationalFunctionFactory<BASE>) RationalFunctionFactoryMap.INSTANCE
				.get(baseFactory);
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public RationalFunction<BASE> add(RationalFunction<BASE> added)
	{
		Polynomial<BASE> a, b, c, d, gcdBD, p1, q1;
		a = this.numerator;
		b = this.denominator;
		c = added.getNumerator();
		d = added.getDenominator();
		gcdBD = b.gcd(d);
		p1 = a.multiply(d.divide(gcdBD)).add(c.multiply(b.divide(gcdBD)));
		q1 = b.multiply(d).divide(gcdBD);
		return new RationalFunction<BASE>(p1, q1,
				rationalFunctionFactory.BASEFACTORY);
	}

	@Override
	public RationalFunction<BASE> negate()
	{
		return new RationalFunction<BASE>(this.numerator.negate(),
				this.denominator, rationalFunctionFactory.BASEFACTORY);
	}

	@Override
	public RationalFunction<BASE> multiply(RationalFunction<BASE> factor)
	{
		Polynomial<BASE> d1 = this.numerator.gcd(factor.getDenominator());
		Polynomial<BASE> d2 = this.denominator.gcd(factor.getNumerator());
		Polynomial<BASE> newNumerator = this.numerator.divide(d1).multiply(
				factor.getNumerator().divide(d2));
		Polynomial<BASE> newDenominator = this.denominator.divide(d2).multiply(
				factor.getDenominator().divide(d1));
		return new RationalFunction<BASE>(newNumerator, newDenominator,
				rationalFunctionFactory.BASEFACTORY);
	}

	/**
	 * The degree of a rational function is the maximum of the degrees of the
	 * numerator and the denominator polynomials.
	 * 
	 * @return the degree of this RationalFunction
	 */
	public int degree()
	{
		int degreeNumerator = this.getNumerator().getDegree();
		int degreeDenominator = this.getDenominator().getDegree();
		if (degreeNumerator > degreeDenominator) {
			return degreeNumerator;
		}
		return degreeDenominator;
	}

	@Override
	public int compareTo(RationalFunction<BASE> o)
	{
		double degreeNumerator1 = this.getNumerator().getDegree();
		double degreeNumerator2 = o.getNumerator().getDegree();
		double degreeDenominator1 = this.getDenominator().getDegree();
		double degreeDenominator2 = o.getDenominator().getDegree();
		double ratio1 = degreeNumerator1 / degreeDenominator1;
		double ratio2 = degreeNumerator2 / degreeDenominator2;
		if (ratio1 == ratio2) {
			BASE coefficientNumerator1 = this.getNumerator()
					.getHighestCoefficient();
			BASE coefficientDenominator1 = this.getDenominator()
					.getHighestCoefficient();
			BASE coefficientNumerator2 = o.getNumerator()
					.getHighestCoefficient();
			BASE coefficientDenominator2 = o.getDenominator()
					.getHighestCoefficient();
			BASE compareRatio = coefficientNumerator1.multiply(
					coefficientDenominator2).subtract(
					coefficientDenominator1.multiply(coefficientNumerator2));
			if (compareRatio.isZero()) {
				return this.withoutHighestPower().compareTo(
						o.withoutHighestPower());
			}
			BASE zero = compareRatio.getFactory().zero();
			if (compareRatio.lt(zero)) {
				return -1;
			}
			return 1;
		}
		return new Double(ratio1).compareTo(new Double(ratio2));
	}

	public RationalFunction<BASE> withoutHighestPower()
	{
		return new RationalFunction<BASE>(numerator.withoutHighestPower(),
				denominator.withoutHighestPower(),
				rationalFunctionFactory.BASEFACTORY);
	}

	@Override
	public RationalFunction<BASE> abs()
	{
		throw new InvalidOperationException(
				"The Abs() of a rational function is not a rational function anymore.");
	}

	@Override
	public IRingElementFactory<RationalFunction<BASE>> getFactory()
	{
		return rationalFunctionFactory;
	}

	public Polynomial<BASE> getNumerator()
	{
		return numerator;
	}

	public Polynomial<BASE> getDenominator()
	{
		return denominator;
	}

	@Override
	public RationalFunction<BASE> invert()
	{
		return new RationalFunction<BASE>(this.denominator, this.numerator,
				rationalFunctionFactory.BASEFACTORY);
	}

	@Override
	public String toString()
	{
		return "(" + this.numerator.toString() + ") / ("
				+ this.denominator.toString() + ")";
	}

}
