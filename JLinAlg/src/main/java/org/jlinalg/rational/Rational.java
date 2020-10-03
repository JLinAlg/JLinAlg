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
package org.jlinalg.rational;

import java.math.BigInteger;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.complex.Complex;

/**
 * This class represents a rational number with a numerator and a denominator as
 * BigInteger. The rational number will be kept canceled all the time.
 * Furthermore complex numbers and rational number are compatible in all
 * operations.
 * 
 * @author Andreas Keilhauer, Simon D. Levy, Georg Thimm
 */
@JLinAlgTypeProperties(isExact = true, isDiscreet = false)
public class Rational
		extends
		FieldElement<Rational>
{
	/**
	 * for serialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the numerator of the rational
	 */
	protected final BigInteger numerator;

	/**
	 * the denominator of the rational
	 */
	protected final BigInteger denominator;

	/**
	 * This constructor takes two longs as numerator and denominator.
	 * 
	 * @param numerator
	 * @param denominator
	 */
	protected Rational(long numerator, long denominator)
	{
		this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator),
				true);
	}

	/**
	 * This creates a Rational with denominator 1.
	 * 
	 * @param value
	 *            numerator
	 */
	protected Rational(BigInteger value)
	{
		this.numerator = value;
		this.denominator = BigInteger.ONE;
	}

	/**
	 * This creates a Rational. After creation canceling is attempted.
	 * 
	 * @param numerator
	 * @param denominator
	 */
	protected Rational(BigInteger numerator, BigInteger denominator)
	{
		this(numerator, denominator, true);
	}

	/**
	 * This creates a Rational and lets you decide whether the rational still
	 * needs to be cancelled (normalised).
	 * 
	 * @param numerator
	 * @param denominator
	 * @param cancel
	 *            if true, try to cancel the fraction
	 */

	protected Rational(BigInteger numerator, BigInteger denominator,
			boolean cancel)
	{
		if (cancel) {
			BigInteger cancelledBy = numerator.gcd(denominator);
			if (!cancelledBy.equals(BigInteger.ONE)) {
				numerator = numerator.divide(cancelledBy);
				denominator = denominator.divide(cancelledBy);
			}
		}
		if (denominator.signum() == -1) {
			this.numerator = numerator.negate();
			this.denominator = denominator.negate();
		}
		else {
			this.numerator = numerator;
			this.denominator = denominator;
		}
	}

	/**
	 * This creates a Rational out of a double. So for example 1.2 will become
	 * 6/5 and Math.PI will become 3141592653589793/1000000000000000.
	 * 
	 * @param value
	 */
	protected Rational(double value)
	{
		boolean isNegative = false;
		if (value < 0) {
			isNegative = true;
			value = Math.abs(value);
		}

		String strValue = (Double.valueOf(value)).toString();

		int preDotDigits = strValue.indexOf(".");

		String preDotString = strValue.substring(0, preDotDigits);
		String postDotString = strValue.substring(preDotDigits + 1);

		int expDigitIndex = postDotString.indexOf("E");

		int exp = 0;

		if (expDigitIndex != -1) {
			exp = Integer.parseInt(postDotString.substring(expDigitIndex + 1));
			postDotString = postDotString.substring(0, expDigitIndex);
		}

		BigInteger newNumerator = new BigInteger(preDotString);
		BigInteger newNumerator2 = new BigInteger(postDotString);
		BigInteger newDenominator = BigInteger.TEN.pow(postDotString.length());

		Rational tmp = ((new Rational(newNumerator))
				.add(new Rational(newNumerator2, newDenominator)));
		if (exp > 0) {
			tmp = tmp.multiply(new Rational(BigInteger.TEN.pow(exp)));
		}
		else {
			if (exp < 0) tmp = tmp.multiply(
					new Rational(BigInteger.ONE, BigInteger.TEN.pow(-exp)));
		}

		BigInteger numerator = tmp.getNumerator();
		if (isNegative) {
			numerator = numerator.negate();
		}
		this.numerator = numerator;
		this.denominator = tmp.getDenominator();
	}

	/**
	 * Returns a String representation of this Rational.
	 */
	@Override
	public String toString()
	{
		if (this.denominator.equals(BigInteger.valueOf(1))) {
			return numerator.toString();
		}
		return numerator + "/" + denominator;
	}

	/**
	 * @param added
	 *            the value to be added to <code>this</code>.
	 * @return the result of adding this rational and another one.
	 */
	@Override
	public Rational add(Rational added)
	{
		BigInteger a, b, c, d, gcdBD, p1, q1;
		a = this.numerator;
		b = this.denominator;
		c = added.getNumerator();
		d = added.getDenominator();
		gcdBD = b.gcd(d);
		p1 = a.multiply(d.divide(gcdBD)).add(c.multiply(b.divide(gcdBD)));
		q1 = b.multiply(d).divide(gcdBD);
		return new Rational(p1, q1);
	}

	/**
	 * Returns the result of this Rational multiplied with another one.
	 */
	@Override
	public Rational multiply(Rational factor)
	{
		BigInteger d1 = this.numerator.gcd(factor.getDenominator());
		BigInteger d2 = this.denominator.gcd(factor.getNumerator());
		BigInteger newNumerator = this.numerator.divide(d1)
				.multiply(factor.getNumerator().divide(d2));
		BigInteger newDenominator = this.denominator.divide(d2)
				.multiply(factor.getDenominator().divide(d1));
		Rational tmp = FACTORY.get(newNumerator, newDenominator, false);
		return tmp;
	}

	@Override
	public Rational negate()
	{
		return FACTORY.get(numerator.negate(), denominator, false);
	}

	/**
	 * @return the inverted Rational of this rational. {@code 6/5} it will
	 *         return {@code 5/6}.
	 * @exception DivisionByZeroException
	 *                if this is zero.
	 */
	@Override
	public Rational invert() throws DivisionByZeroException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Tried to invert zero.");
		}
		return new Rational(this.getDenominator(), this.getNumerator(), false);
	}

	/**
	 * Returns the numerator of this Rational.
	 * 
	 * @return numerator
	 */
	public BigInteger getNumerator()
	{
		return this.numerator;
	}

	/**
	 * Returns the denominator of this Rational.
	 * 
	 * @return denominator
	 */
	public BigInteger getDenominator()
	{
		return this.denominator;
	}

	/**
	 * Returns the value of this Rational as a double.
	 * 
	 * @return <code> numerator.doubleValue() /
	 * denominator.doubleValue();</code>
	 */
	public double doubleValue()
	{
		return numerator.doubleValue() / denominator.doubleValue();
	}

	/**
	 * Determines whether or not two Rationals are mathematically equal.
	 * 
	 * @param obj
	 *            another Rational/Complex
	 * @return Equal or not.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (obj instanceof Complex) {
			Complex comp = (Complex) obj;
			if (!comp.getImaginary().isZero()) {
				return false;
			}
			return this.equals(comp.getReal());
		}
		Rational comp = (Rational) obj;
		return (this.numerator.equals(comp.getNumerator())
				&& this.denominator.equals(comp.getDenominator()));
	}

	@Override
	public int compareTo(Rational o)
	{
		Rational comp = this.subtract(o);
		if (comp.getNumerator().equals(BigInteger.ZERO)) return 0;
		return comp.getNumerator().signum();
	}

	/**
	 * Returns the absolute value of this Rational number.
	 * 
	 * @return absolute value
	 */
	@Override
	public Rational abs()
	{
		if (this.numerator.compareTo(BigInteger.ZERO) >= 0) return this;
		return new Rational(this.numerator.abs(), this.denominator, false);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return numerator.intValue() ^ denominator.intValue();
	}

	/**
	 * give access to the factory for this type
	 */
	@Override
	public IRingElementFactory<Rational> getFactory()
	{
		return FACTORY;
	}

	/*
	 * ===================== CONSTANTS FOR RATIONALFACTORY
	 * ===================================================================
	 */

	/**
	 * The constant for the value 0.
	 */
	final static Rational ZERO = new Rational(BigInteger.ZERO);

	/**
	 * The constant for the value -1.
	 */
	final static Rational M_ONE = new Rational(BigInteger.ONE.negate());

	/**
	 * The constant for the value 1.
	 */
	final static Rational ONE = new Rational(BigInteger.ONE);

	/**
	 * used in {@link RationalFactory#get(Object)}
	 */
	final static BigInteger bigIntMOne = new BigInteger("-1");

	/**
	 * The default factory for this type.
	 */
	public final static RationalFactory FACTORY = new RationalFactory();

}
