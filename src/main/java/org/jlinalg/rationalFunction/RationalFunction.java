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
package org.jlinalg.rationalFunction;

import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.polynomial.Polynomial;
import org.jlinalg.polynomial.PolynomialFactoryMap;

/**
 * Class that represents a rational function over a given BASE.
 * 
 * @author Andreas Keilhauer
 * @param <BASE>
 */
public class RationalFunction<BASE extends IRingElement<BASE>>
		extends
		FieldElement<RationalFunction<BASE>>
{
	private static final long serialVersionUID = 1L;

	private Polynomial<BASE> numerator;
	private Polynomial<BASE> denominator;

	private final IRingElementFactory<BASE> baseFactory;

	// @SuppressWarnings("unchecked")
	public RationalFunction(BASE value)
	{
		if (value == null) {
			throw new InvalidOperationException("value cannot be null!");
		}

		this.baseFactory = value.getFactory();
		this.numerator = new Polynomial<>(value);
		this.denominator = new Polynomial<>(value.getFactory().one());
	}

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
		this.baseFactory = baseFactory;
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public RationalFunction(Polynomial<BASE> numerator,
			final IRingElementFactory<BASE> baseFactory)
	{
		this(numerator, PolynomialFactoryMap.getFactory(baseFactory).one(),
				baseFactory);
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
		return new RationalFunction<>(p1, q1, baseFactory);
	}

	@Override
	public RationalFunction<BASE> negate()
	{
		return new RationalFunction<>(this.numerator.negate(), this.denominator,
				baseFactory);
	}

	@Override
	public RationalFunction<BASE> multiply(RationalFunction<BASE> factor)
	{
		Polynomial<BASE> d1 = this.numerator.gcd(factor.getDenominator());
		Polynomial<BASE> d2 = this.denominator.gcd(factor.getNumerator());
		Polynomial<BASE> newNumerator = this.numerator.divide(d1)
				.multiply(factor.getNumerator().divide(d2));
		Polynomial<BASE> newDenominator = this.denominator.divide(d2)
				.multiply(factor.getDenominator().divide(d1));
		return new RationalFunction<>(newNumerator, newDenominator,
				baseFactory);
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
		int degreeNumerator1 = this.getNumerator().getDegree();
		int degreeDenominator1 = this.getDenominator().getDegree();
		int degreeNumerator2 = o.getNumerator().getDegree();
		int degreeDenominator2 = o.getDenominator().getDegree();
		int degree1Compared = degreeNumerator1 - degreeDenominator1;
		int degree2Compared = degreeNumerator2 - degreeDenominator2;
		if (degree1Compared == degree2Compared) {
			BASE coefficientNumerator1 = null;
			BASE coefficientDenominator1 = null;
			IRingElementFactory<BASE> baseFactory = ((RationalFunctionFactory<BASE>) o
					.getFactory()).getBaseFactory();
			if (degree1Compared == 0) {
				coefficientNumerator1 = this.getNumerator()
						.getHighestCoefficient();
				coefficientDenominator1 = this.getDenominator()
						.getHighestCoefficient();
			}
			else if (degreeNumerator1 > 0) {
				coefficientNumerator1 = this.getNumerator()
						.getHighestCoefficient();
				coefficientDenominator1 = baseFactory.one();
			}
			else {
				coefficientNumerator1 = baseFactory.one();
				coefficientDenominator1 = this.getDenominator()
						.getHighestCoefficient();
			}
			BASE coefficientNumerator2 = null;
			BASE coefficientDenominator2 = null;
			if (degree2Compared == 0) {
				coefficientNumerator2 = o.getNumerator()
						.getHighestCoefficient();
				coefficientDenominator2 = o.getDenominator()
						.getHighestCoefficient();
			}
			else if (degree2Compared > 0) {
				coefficientNumerator2 = o.getNumerator()
						.getHighestCoefficient();
				coefficientDenominator2 = baseFactory.one();
			}
			else {
				coefficientNumerator2 = baseFactory.one();
				coefficientDenominator2 = o.getDenominator()
						.getHighestCoefficient();
			}
			BASE leftSide = coefficientNumerator1
					.multiply(coefficientDenominator2);
			BASE rightSide = coefficientDenominator1
					.multiply(coefficientNumerator2);
			if (leftSide.equals(rightSide)) {
				if (coefficientNumerator1.isZero()) {
					return 0;
				}
				return this.withoutHighestPower()
						.compareTo(o.withoutHighestPower());
			}
			if (leftSide.lt(rightSide)) {
				return -1;
			}
			return 1;
		}
		return Double.valueOf(degree1Compared)
				.compareTo(Double.valueOf(degree2Compared));
	}

	@Override
	public boolean isZero()
	{
		return this.numerator.isZero();
	}

	public RationalFunction<BASE> withoutHighestPower()
	{
		if (numerator.getDegree() > denominator.getDegree()) {
			return new RationalFunction<>(numerator.withoutHighestPower(),
					denominator, baseFactory);
		}
		else if (numerator.getDegree() < denominator.getDegree()) {
			return new RationalFunction<>(numerator,
					denominator.withoutHighestPower(), baseFactory);
		}
		else {
			return new RationalFunction<>(numerator.withoutHighestPower(),
					denominator.withoutHighestPower(), baseFactory);
		}

	}

	@Deprecated
	@Override
	public RationalFunction<BASE> abs()
	{
		throw new InvalidOperationException(
				"The Abs() of a rational function is not a rational function anymore.");
	}

	@Override
	@Deprecated
	public RationalFunction<BASE> norm()
	{
		throw new InvalidOperationException(
				"The function norm() is not implemented for polynomials.");
	}

	@Override
	public IRingElementFactory<RationalFunction<BASE>> getFactory()
	{
		return RationalFunctionFactory.getFactory(baseFactory);
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
		return new RationalFunction<>(this.denominator, this.numerator,
				baseFactory);
	}

	@Override
	public String toString()
	{
		return "(" + this.numerator.toString() + ") / ("
				+ this.denominator.toString() + ")";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((denominator == null) ? 0 : denominator.hashCode());
		result = prime * result
				+ ((numerator == null) ? 0 : numerator.hashCode());
		return result;
	}
}
