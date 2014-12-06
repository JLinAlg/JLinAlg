package org.jlinalg.rationalFunction;

import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.polynomial.Polynomial;

/**
 * Class that represents a rational function over a given BASE.
 * 
 * @author Andreas Andreas Keilhauer
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

	public RationalFunction(Polynomial<BASE> numerator,
			Polynomial<BASE> denominator)
	{
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
		return new RationalFunction<BASE>(p1, q1);
	}

	@Override
	public RationalFunction<BASE> negate()
	{
		return new RationalFunction<BASE>(this.numerator.negate(),
				this.denominator);
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
		return new RationalFunction<BASE>(newNumerator, newDenominator);
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
			// TODO: Continue here
			return 0;
		}
		return new Double(ratio1).compareTo(new Double(ratio2));
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
		// TODO: Add Factory here
		return null;
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
		return new RationalFunction<BASE>(this.denominator, this.numerator);
	}

	@Override
	public String toString()
	{
		return this.numerator.toString() + " / " + this.denominator.toString();
	}

}
