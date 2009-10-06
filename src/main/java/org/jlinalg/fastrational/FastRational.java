package org.jlinalg.fastrational;

import java.math.BigInteger;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;

public class FastRational
		extends FieldElement<FastRational>
{

	private static final long serialVersionUID = 1L;

	public static final FastRationalFactory FACTORY = new FastRationalFactory();

	private final long numerator;

	private final long denominator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.Rational#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj instanceof FastRational) {
			FastRational r = (FastRational) obj;
			return r.numerator == numerator && r.denominator == denominator;
		}
		throw new Error("can no compare with object in class " + obj.getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldElement#isZero()
	 */
	@Override
	public boolean isZero()
	{
		return numerator == 0;
	}

	protected FastRational(BigInteger numerator, BigInteger denominator,
			boolean cancel)
	{
		long n = numerator.longValue();
		long d = denominator.longValue();
		if (cancel) {
			if (n == 0) {
				this.numerator = 0;
				this.denominator = 1;
				return;
			}
			long g = gcd(n, d);
			if (d < 0) {
				this.numerator = -n / g;
				this.denominator = -d / g;
			}
			else {
				this.numerator = n / g;
				this.denominator = d / g;
			}
		}
		else {
			this.numerator = n;
			this.denominator = d;
		}
	}

	public static long gcd(long a, long b)
	{
		while (b != 0) {
			long t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	protected FastRational(long n, long d, boolean cancel)
	{
		if (cancel) {
			if (n == 0) {
				this.numerator = 0;
				this.denominator = 1;
				return;
			}
			long g = gcd(n, d);
			if (d < 0) {
				this.numerator = -n / g;
				this.denominator = -d / g;
			}
			else {
				this.numerator = n / g;
				this.denominator = d / g;
			}
		}
		else {
			this.numerator = n;
			this.denominator = d;
		}
	}

	/**
	 * This creates a FastRational out of a double. So for example 1.2 will
	 * become
	 * 6/5 and Math.PI will become 3141592653589793/1000000000000000.
	 * 
	 * @param value
	 */
	protected FastRational(double value)
	{
		boolean isNegative = false;
		if (value < 0) {
			isNegative = true;
			value = Math.abs(value);
		}

		String strValue = Double.toString(value);

		int preDotDigits = strValue.indexOf(".");

		String preDotString = strValue.substring(0, preDotDigits);
		String postDotString = strValue.substring(preDotDigits + 1);

		int expDigitIndex = postDotString.indexOf("E");

		int exp = 0;

		if (expDigitIndex != -1) {
			exp = Integer.parseInt(postDotString.substring(expDigitIndex + 1));
			postDotString = postDotString.substring(0, expDigitIndex);
		}

		long newNumerator = Long.parseLong(preDotString);
		long newNumerator2 = Long.parseLong(postDotString);
		long newDenominator = FastRationalFactory.long10toPower(postDotString
				.length());

		FastRational tmp = (new FastRational(newNumerator, 1, false))
				.add(new FastRational(newNumerator2, newDenominator, true));
		if (exp > 0) {
			tmp = tmp.multiply(new FastRational(FastRationalFactory
					.long10toPower(exp), 1, false));
		}
		else {
			if (exp < 0)
				tmp = tmp.multiply(new FastRational(1, FastRationalFactory
						.long10toPower(-exp), false));
		}

		long numerator = tmp.getNumerator();
		if (isNegative) {
			numerator = -numerator;
		}
		this.numerator = numerator;
		this.denominator = tmp.getDenominator();
	}

	@Override
	public FastRational divide(FastRational r) throws DivisionByZeroException
	{
		if (r.denominator == 0)
			throw new DivisionByZeroException("can not divide by zero");
		if (r.numerator == 1 && r.denominator == 1) return this;
		if (numerator == 1 && denominator == 1) {
			if (r.numerator > 0)
				return FastRational.FACTORY.get(r.denominator, r.numerator,
						false);
			return FastRational.FACTORY
					.get(-r.denominator, -r.numerator, false);
		}
		long g = gcd(numerator, r.numerator);
		long n1 = numerator / g;
		long n2 = r.numerator / g;
		g = gcd(r.denominator, denominator);
		long d2 = r.denominator / g;
		long d1 = denominator / g;
		long d = d1 * n2;
		if (d > 0) return FastRational.FACTORY.get(n1 * d2, d, false);
		return FastRational.FACTORY.get(-n1 * d2, -d, false);
	}

	@Override
	public FastRational negate()
	{
		if (this == FastRationalFactory.ZERO) return this;
		if (this == FastRationalFactory.ONE) return FastRationalFactory.M_ONE;
		if (this == FastRationalFactory.M_ONE) return FastRationalFactory.ONE;
		return FastRational.FACTORY.get(-numerator, denominator, false);
	}

	@Override
	public FastRational add(FastRational r)
	{
		if (numerator == 0) return r;
		if (r.numerator == 0) return this;
		if (denominator == 1 && r.denominator == 1)
			return new FastRational(numerator + r.numerator, 1, false);
		// long g = gcd( denominator , r.denominator);

		return FastRational.FACTORY.get(numerator * r.denominator + r.numerator
				* denominator, denominator * r.denominator, true);
	}

	@Override
	public FastRational subtract(FastRational r)
	{
		if (numerator == 0) return r.negate();
		if (r.numerator == 0) return this;
		if (denominator == 1 && r.denominator == 1)
			return FastRational.FACTORY.get(numerator - r.numerator, 1, false);
		return FastRational.FACTORY.get(numerator * r.denominator - r.numerator
				* denominator, denominator * r.denominator, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldElement#abs()
	 */
	@Override
	public FastRational abs()
	{
		if (numerator >= 0) return this;
		return FastRational.FACTORY.get(-numerator, denominator);
	}

	@Override
	public FastRational multiply(FastRational r)
	{
		if (numerator == 0 || r.numerator == 0)
			return FastRationalFactory.ZERO;
		if (this.equals(FastRationalFactory.ONE)) return r;
		if (r.equals(FastRationalFactory.ONE)) return this;
		long g = gcd(numerator, r.denominator);
		long n1 = numerator / g;
		long d2 = r.denominator / g;
		g = gcd(r.numerator, denominator);
		long n2 = r.numerator / g;
		long d1 = denominator / g;
		return FastRational.FACTORY.get(n1 * n2, d1 * d2, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.Rational#invert()
	 */
	@Override
	public FastRational invert() throws DivisionByZeroException
	{
		return FastRational.FACTORY.get(denominator, numerator, false);
	}

	@Override
	public int compareTo(FastRational r)
	{
		long diff = numerator * r.denominator - r.numerator * denominator;
		return diff < 0 ? -1 : (diff == 0 ? 0 : 1);
	}

	public double doubleValue()
	{
		return (double) numerator / denominator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldElement#gt(org.jlinalg.FieldElement)
	 */
	@Override
	public boolean gt(FastRational r)
	{
		long diff = numerator * r.denominator - r.numerator * denominator;
		return diff > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldElement#lt(org.jlinalg.FieldElement)
	 */
	@Override
	public boolean lt(FastRational r)
	{
		long diff = numerator * r.denominator - r.numerator * denominator;
		return diff < 0;
	}

	/**
	 * Returns a String representation of this Rational.
	 */
	@Override
	public String toString()
	{
		if (denominator == 1) {
			return Long.toString(numerator);
		}
		return Long.toString(numerator) + "/" + Long.toString(denominator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) (numerator ^ denominator * 1137);
	}

	@Override
	public FastRationalFactory getFactory()
	{
		return FastRational.FACTORY;
	}

	/**
	 * @return the denominator
	 */
	public long getDenominator()
	{
		return denominator;
	}

	/**
	 * @return the numerator
	 */
	public long getNumerator()
	{
		return numerator;
	}
}
