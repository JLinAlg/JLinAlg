package org.jlinalg.bigdecimalwrapper;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.InvalidOperationException;

/**
 * This class wraps a BigDecimal value and performs all FieldElement operations
 * on that BigDecimal. Fast but not without rounding errors.
 * 
 * @author Georg Thimm
 */

public class BigDecimalWrapper
		extends FieldElement<BigDecimalWrapper>
		implements IRingElement<BigDecimalWrapper>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the hashCode of the encapsulated value.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	/**
	 * The encapsulated value.
	 */
	protected BigDecimal value;

	/**
	 * Builds an element from a BigDecimal value.
	 * 
	 * @param value
	 *            a BigDecimal value to be wrapped
	 * @param factory
	 *            the factory encapsulating the context in which the new value
	 *            is created. See java.math.MathContext
	 */
	protected <FAC extends BigDecimalWrapperFactory> BigDecimalWrapper(
			BigDecimal value, FAC factory)
	{
		if (value.precision() != factory.mathContext.getPrecision())
			this.value = value.round(factory.mathContext);
		else
			this.value = value;
		FACTORY = factory;
	}

	/**
	 * @return the BigDecimal value this instance wraps.
	 */
	public BigDecimal getValue()
	{
		return value;
	}

	/**
	 * Calculates the sum of this element and another one.
	 * 
	 * @param val
	 * @return sum
	 */
	public BigDecimalWrapper add(BigDecimalWrapper val)
	{
		if (FACTORY != val.FACTORY)
			throw new InvalidOperationException(
					"Can not add instances of BigDecimalWrapper with distinct factories.");
		return FACTORY.get(value.add(val.getValue()));
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	@Override
	public BigDecimalWrapper subtract(BigDecimalWrapper val)
	{
		if (FACTORY != val.FACTORY)
			throw new InvalidOperationException(
					"Can not add instances of BigDecimalWrapper with distinct factories.");
		return FACTORY.get(value.subtract(val.getValue()));
	}

	/**
	 * Calculates the product of this element and another one.
	 * 
	 * @param val
	 * @return product
	 */
	public BigDecimalWrapper multiply(BigDecimalWrapper val)
	{
		if (FACTORY != val.FACTORY)
			throw new InvalidOperationException(
					"Can not multiply instances of BigDecimalWrapper with distinct factories.");
		return FACTORY.get(value.multiply(val.getValue()));
	}

	/**
	 * Calculates the quotient of this element and another one.
	 * 
	 * @param val
	 * @return this / val
	 */
	@Override
	public BigDecimalWrapper divide(BigDecimalWrapper val)
			throws DivisionByZeroException
	{
		if (FACTORY != val.FACTORY)
			throw new InvalidOperationException(
					"Can not divide instances of BigDecimalWrapper with distinct factories.");
		if (val.isZero()) {
			throw new DivisionByZeroException("Tried to divide " + this + "by"
					+ val + ".");
		}
		return FACTORY.get(value.divide(val.getValue(), FACTORY.mathContext));
	}

	/**
	 * Calculates the inverse element of addition for this element.
	 * 
	 * @return negated (-value)
	 */
	public BigDecimalWrapper negate()
	{
		return FACTORY.get(value.negate());
	}

	/**
	 * Calculates the inverse element of multiplication for this element.
	 * 
	 * @return inverted (1/value)
	 * @throws InvalidOperationException
	 *             if original value is zero
	 */
	@Override
	public BigDecimalWrapper invert() throws DivisionByZeroException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Tried to invert zero.");
		}
		return FACTORY.get(FACTORY.one().value.divide(value,
				FACTORY.mathContext));
	}

	/**
	 * Checks two FieldElements for equality.
	 * 
	 * @param obj
	 * @return true if the two FieldElements are mathematically equal.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		BigDecimalWrapper comp = (BigDecimalWrapper) obj;
		return value.equals(comp.value);
	}

	/**
	 * @see org.jlinalg.RingElement#isZero()
	 */
	@Override
	public boolean isZero()
	{
		// overwritten, as in BigDecimals, zero is not equal to zero 8-(
		return value.signum() == 0;
	}

	/**
	 * Returns a String representation of this element.
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return value.toEngineeringString();
	}

	/**
	 * Returns the double-precision floating-point value of this element.
	 * 
	 * @return value
	 */
	public double doubleValue()
	{
		return value.doubleValue();
	}

	/**
	 * Implements Comparable.compareTo(Object).
	 * 
	 * @param o
	 *            the object
	 * @return -,+,0} as this object is less than, equal to, or greater than the
	 *         specified object.
	 */
	public int compareTo(BigDecimalWrapper o)
	{
		return this.value.compareTo(o.value);
	}

	/**
	 * @return the square root of this value.
	 * @exception InvalidOperationException
	 *                if the precision of the factory is 0 (that is infinite
	 *                precision) or the value is negative.
	 */
	public BigDecimalWrapper sqrt()
	{
		int precision = FACTORY.getMathContext().getPrecision();
		if (precision == 0)
			throw new InvalidOperationException(
					"Cannot calculate the square root of infinite precision decimal numbers.");
		if (value.signum() < 0)
			throw new InvalidOperationException(
					"Cannot calculate the square root of negative numbers.");
		if (value.signum() == 0) return FACTORY.ZERO;

		BigDecimal one = BigDecimal.ONE.setScale(precision + 10);
		BigDecimal two = one.add(one);
		BigDecimal maxerr = one.movePointLeft(FACTORY.getMathContext()
				.getPrecision() + 1);

		BigDecimal lower;
		BigDecimal upper;
		int i = value.compareTo(one);
		if (i < 0) {
			lower = value.movePointLeft(precision / 2);
			upper = one;
		}
		else {
			if (i == 0) return FACTORY.ONE;
			lower = one;
			upper = value;
		}

		BigDecimal mid;
		while (true) {
			mid = lower.add(upper).divide(two, BigDecimal.ROUND_HALF_UP);
			BigDecimal sqr = mid.multiply(mid);
			BigDecimal error = value.subtract(sqr).abs();
			// System.out.println("error=" + error);
			if (error.compareTo(maxerr) <= 0) {
				break;
			}
			if (sqr.compareTo(value) < 0) {
				lower = mid;
			}
			else {
				upper = mid;
			}
		}
		return FACTORY.get(mid);
	}

	/**
	 * @return the singleton factory for this type.
	 */
	public BigDecimalWrapperFactory getFactory()
	{
		return FACTORY;
	}

	/**
	 * The factory for BigDecimalWrapper
	 */
	final BigDecimalWrapperFactory FACTORY;

	/**
	 * used in {@link BigDecimalWrapperFactory#get(Object)} to parse fractions.
	 */
	static final Pattern fractionPattern = Pattern
			.compile("\\s*([-+]{0,1}\\d+)\\s*/\\s*([-+]{0,1}\\d+)\\s*");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElement#abs()
	 */
	@Override
	public BigDecimalWrapper abs()
	{
		if (value.signum() == -1)
			return new BigDecimalWrapper(value.negate(FACTORY.mathContext),
					FACTORY);
		return this;
	}

}
