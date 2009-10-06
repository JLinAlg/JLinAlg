package org.jlinalg.rational;

import java.math.BigInteger;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.complex.Complex;
import org.jlinalg.doublewrapper.DoubleWrapper;

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
		extends FieldElement<Rational>
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

		String strValue = (new Double(value)).toString();

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

		Rational tmp = ((new Rational(newNumerator)).add(new Rational(
				newNumerator2, newDenominator)));
		if (exp > 0) {
			tmp = tmp.multiply(new Rational(BigInteger.TEN.pow(exp)));
		}
		else {
			if (exp < 0)
				tmp = tmp.multiply(new Rational(BigInteger.ONE, BigInteger.TEN
						.pow(-exp)));
		}

		BigInteger numerator = tmp.getNumerator();
		if (isNegative) {
			numerator = numerator.negate();
		}
		this.numerator = numerator;
		this.denominator = tmp.getDenominator();
	}

	/**
	 * used in {@link #Rational(String)} to parse numbers in exponential
	 * denotation.
	 */
	public final static Pattern expPattern = Pattern
			.compile("([+-]?\\d+)\\.?(\\d*)([eE]([-+]?\\d+))?");

	/**
	 * used in {@link #Rational(String)} to parse numbers in fractional
	 * denotation.
	 */
	public final static Pattern fracPattern = Pattern
			.compile("([+-]?\\d+)/([-+]?\\d+)");

	/**
	 * @param number
	 *            A string in the format of an integer, integer fraction or
	 *            exponential notation aka scientific notation) to be converted
	 *            into a rational.
	 * @deprecated use {@link RationalFactory#get(Object)}.
	 * @throws Error
	 *             if called
	 */
	@Deprecated
	Rational(@SuppressWarnings("unused") String number)
	{
		denominator = numerator = null;
		throw new Error("use RationalFactory.get(String)");
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
	 *@return the result of adding this rational and another one.
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
		BigInteger newNumerator = this.numerator.divide(d1).multiply(
				factor.getNumerator().divide(d2));
		BigInteger newDenominator = this.denominator.divide(d2).multiply(
				factor.getDenominator().divide(d1));
		Rational tmp = FACTORY.get(newNumerator, newDenominator, false);
		return tmp;
	}

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
		if (obj instanceof Complex) {
			Complex comp = (Complex) obj;
			if (!comp.getImaginary().isZero()) {
				return false;
			}
			return this.equals(comp.getReal());
		}
		Rational comp = (Rational) obj;
		return (this.numerator.equals(comp.getNumerator()) && this.denominator
				.equals(comp.getDenominator()));
	}

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
	private final static Rational ZERO = new Rational(BigInteger.ZERO);

	/**
	 * The constant for the value -1.
	 */
	private final static Rational M_ONE = new Rational(BigInteger.ONE.negate());

	/**
	 * The constant for the value 1.
	 */
	private final static Rational ONE = new Rational(BigInteger.ONE);

	/**
	 * used in {@link RationalFactory#get(Object)}
	 */
	private final static BigInteger bigIntMOne = new BigInteger("-1");

	/**
	 * The default factory for this type.
	 */
	public final static RationalFactory FACTORY = ZERO.new RationalFactory();

	/**
	 * The factory for Rationals
	 * 
	 * @author Georg Thimm
	 */
	@JLinAlgTypeProperties(isExact = true)
	public class RationalFactory
			extends RingElementFactory<Rational>
	{
		/**
		 * this should only be used by {@link Rational} to create a single
		 * instance.
		 */
		private RationalFactory()
		{
			super();
		}

		@Override
		public Rational[] getArray(int size)
		{
			return new Rational[size];
		}

		/**
		 * @return the singleton factory for rational numbers.
		 */
		public RationalFactory getFactory()
		{
			return FACTORY;
		}

		/**
		 * Translate other objects into rationals. Objects in the following
		 * classes are permissible:
		 * <UL>
		 * <li> {@link Double}
		 * <li> {@link Integer}
		 * <li> {@link BigInteger}
		 * <li> {@link Rational}
		 * <li> {@link String} in the form of integers, exponential or fractional
		 * representation.
		 * </UL>
		 * If the argument is a rational, it is returned.
		 * 
		 * @exception InvalidOperationException
		 *                if the object is a String in an unknown format.
		 */
		@Override
		public Rational get(Object o)
		{
			if (o instanceof Double) {
				return get(((Double) o).doubleValue());
			}
			if (o instanceof DoubleWrapper) {
				return get(((DoubleWrapper) o).doubleValue());
			}
			if (o instanceof Integer) {
				return get(((Integer) o).intValue());
			}
			if (o instanceof BigInteger) {
				if (BigInteger.ONE.equals(o)) {
					return ONE;
				}
				if (BigInteger.ZERO.equals(o)) {
					return ZERO;
				}
				if (bigIntMOne.equals(o)) {
					return M_ONE;
				}
				return new Rational((BigInteger) o);
			}
			if (o instanceof String) {
				try {
					String number = (String) o;
					Matcher m = expPattern.matcher(number);
					BigInteger numerator;
					BigInteger denominator;
					if (m.matches()) {
						;
						// parse exponential writing.
						int offset = m.group(2).length();
						int power = 0;
						if (m.group(3) != null)
							power = Integer.parseInt(m.group(4)) - offset;
						BigInteger mantissa = new BigInteger(m.group(1).concat(
								m.group(2)));
						if (power > 0) {
							// exponent is positive
							numerator = mantissa.multiply(BigInteger.TEN
									.pow(power));
							denominator = BigInteger.ONE;
							return get(numerator, denominator, false);
						}
						// exponent is negative.
						numerator = mantissa;
						denominator = BigInteger.TEN.pow(-power);
						return get(numerator, denominator, true);

					}
					// consider fractional notation
					m = fracPattern.matcher(number);
					if (m.matches()) {
						numerator = new BigInteger(m.group(1));
						denominator = new BigInteger(m.group(2));
						return get(numerator, denominator, true);
					}
					// at last, try whether this is a plain integer
					BigInteger i = new BigInteger(number);
					numerator = i;
					denominator = BigInteger.ONE;
				} catch (NumberFormatException e) {
					throw new InvalidOperationException(
							o
									+ " does not represent a rational number (exception was: "
									+ e.getMessage() + ")");
				}
			}
			// the dummy-case...
			if (o instanceof Rational) {
				return (Rational) o;
			}
			// and the last-resort try
			return get(o.toString());
		}

		/**
		 * Returns the zero element for this field.
		 * 
		 * @return zero
		 */
		@Override
		public Rational zero()
		{
			return ZERO;
		}

		/**
		 * Returns the one element for this field: 1/1
		 * 
		 * @return one
		 */
		@Override
		public Rational one()
		{
			return ONE;
		}

		/**
		 * Returns the minus one for this field: -1/1
		 */
		@Override
		public Rational m_one()
		{
			return M_ONE;
		}

		/**
		 * create rationals from integers.
		 */
		@Override
		public Rational get(int i)
		{
			if (i == 0) return ZERO;
			if (i == 1) return ONE;
			if (i == -1) return M_ONE;
			return new Rational(i);
		}

		@Override
		public Rational get(double d)
		{
			if (d == 0) return ZERO;
			if (d == 1.0) return ONE;
			if (d == -1.0) return M_ONE;
			return new Rational(d);
		}

		@Override
		public Rational[][] getArray(int rows, int columns)
		{
			return new Rational[rows][columns];
		}

		@SuppressWarnings("deprecation")
		@Override
		@Deprecated
		public Rational gaussianRandomValue(
				@SuppressWarnings("unused") Random random)
		{
			return gaussianRandomValue();
		}

		@SuppressWarnings("deprecation")
		@Deprecated
		@Override
		public Rational randomValue(@SuppressWarnings("unused") Random random)
		{
			return randomValue();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#get(long)
		 */
		public Rational get(long i)
		{
			if (i == 0) return ZERO;
			if (i == 1) return ONE;
			if (i == -1) return M_ONE;
			return new Rational(i);
		}

		/**
		 * @param n
		 * @param d
		 * @return a rational with value (n/d)
		 * @see org.jlinalg.IRingElementFactory#get(long)
		 */
		public Rational get(long n, long d)
		{
			if (n == 0) return ZERO;
			if (n == 1 && d == 1) return ONE;
			if (n == -1 && d == 1) return M_ONE;
			return new Rational(n, d);
		}

		/**
		 * @param n
		 *            the nomiator
		 * @param d
		 *            the denominator
		 * @param b
		 *            if true, cancel the fraction
		 * @return a rational number
		 */
		public Rational get(BigInteger n, BigInteger d, boolean b)
		{
			if (n.equals(BigInteger.ZERO)) return ZERO;
			if (n.equals(BigInteger.ONE) && d.equals(BigInteger.ONE))
				return ONE;
			if (n.equals(BigInteger.ONE) && d.equals(bigIntMOne)) return M_ONE;
			return new Rational(n, d, b);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.RingElementFactory#randomValue(java.util.Random,
		 * org.jlinalg.IRingElement, org.jlinalg.IRingElement)
		 */
		@SuppressWarnings("deprecation")
		@Override
		@Deprecated
		public Rational randomValue(@SuppressWarnings("unused") Random random,
				Rational min, Rational max)
		{
			return randomValue(min, max);
		}

		/**
		 * @see org.jlinalg.IRingElementFactory#gaussianRandomValue()
		 */
		@Override
		public Rational gaussianRandomValue()
		{
			return get(random.nextGaussian());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jlinalg.IRingElementFactory#randomValue(org.jlinalg.IRingElement,
		 * org.jlinalg.IRingElement)
		 */
		@Override
		public Rational randomValue(Rational min, Rational max)
		{
			return get(randomValue().multiply(max.subtract(min))).add(min);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#randomValue()
		 */
		@Override
		public Rational randomValue()
		{
			return get(random.nextDouble());
		}
	}

}
