package org.jlinalg.polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.RingElement;

/**
 * A class of polynomials. This constructor of class requires that a the factory
 * of a base type is created first and then passed to the constructor.
 * Alternatively, the factory can be used. For example, the following creates a
 * factory for polynomials for a rational domain:
 * 
 * <PRE>
 * PolynomialFactory&lt;Rational&gt;== PolynomialFactory.getFactory(Rational.FACTORY);
 * </PRE>
 * 
 * Then, the polynomial "1+2*x^2+2*x^3" can be created as follows:
 * 
 * <PRE>
 * Map&lt;Integer, Rational&gt; coeff1 = new HashMap&lt;Integer, Rational&gt;();
 * coeff1.put(0, new Rational(1));
 * coeff1.put(1, new Rational(2));
 * coeff1.put(2, new Rational(3));
 * Polynomial&lt;Rational&gt; polynomial = rationalPolyFactory.get(coeff1);
 * </PRE>
 * 
 * @author Andreas Keilhauer, Georg Thimm
 * @param <BASE>
 *            The type for the domain of the polynomials' coefficients.
 */
public class Polynomial<BASE extends IRingElement>
		extends RingElement
		implements IRingElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the internal representation of polynomials
	 */
	private SortedMap<Integer, BASE> coefficientsForExponents = null;

	/**
	 * the singleton factory for polynomials
	 */
	private final PolynomialFactory<BASE> polynomialFactory;

	public Polynomial(BASE value)
	{
		if (value == null) {
			throw new InvalidOperationException("value cannot be null!");
		}
		this.polynomialFactory = (PolynomialFactory<BASE>) PolynomialFactoryMap.INSTANCE
				.get(value.getFactory());
		this.coefficientsForExponents = new TreeMap<Integer, BASE>();
		this.coefficientsForExponents.put(0, value);
	}

	/**
	 * @param coeff1
	 *            a map for the coefficients of the polynomial
	 * @param baseFactory
	 *            the factory for the creation of the elements in the
	 *            polynomial.
	 */
	public Polynomial(Map<Integer, BASE> coeff1,
			final IRingElementFactory<BASE> baseFactory)
	{
		if (coeff1 == null) {
			coeff1 = new HashMap<Integer, BASE>();
		}

		this.coefficientsForExponents = new TreeMap<Integer, BASE>();
		if (baseFactory == null) {
			throw new InvalidOperationException(
					"factory of a Polynomial cannot be null!");
		}

		this.polynomialFactory = (PolynomialFactory<BASE>) PolynomialFactoryMap.INSTANCE
				.get(baseFactory);

		for (final BASE b : coeff1.values()) {
			if (baseFactory != b.getFactory())
				throw new InternalError("Found inconsistent Factories.");
		}

		for (final Integer currentKey : coeff1.keySet()) {
			final BASE currentCoefficient = coeff1.get(currentKey);
			if (!currentCoefficient.isZero()) {
				this.coefficientsForExponents.put(currentKey,
						currentCoefficient);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Polynomial<BASE> add(final IRingElement other)
	{
		final SortedMap<Integer, BASE> otherCoefficients = ((Polynomial<BASE>) other)
				.getCoefficientsForExponents();

		SortedMap<Integer, BASE> resultCoeffs = this.addHelper(
				coefficientsForExponents, otherCoefficients);
		Polynomial<BASE> result = new Polynomial<BASE>(resultCoeffs,
				((PolynomialFactory<BASE>) ((Polynomial<BASE>) other)
						.getFactory()).getBaseFactory());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Polynomial<BASE> subtract(final IRingElement other)
	{
		return (Polynomial<BASE>) super.subtract(other);
	}

	/**
	 * used by {@link Polynomial#add(IRingElement)},
	 * {@link Polynomial#multiply(IRingElement)}
	 */
	private SortedMap<Integer, BASE> addHelper(
			final SortedMap<Integer, BASE> leftCoefficients,
			final SortedMap<Integer, BASE> rightCoefficients)
	{
		final Integer[] leftKeys = leftCoefficients.keySet().toArray(
				new Integer[leftCoefficients.keySet().size()]);
		final Integer[] rightKeys = rightCoefficients.keySet().toArray(
				new Integer[rightCoefficients.keySet().size()]);

		int leftIndex = 0;
		int rightIndex = 0;
		Integer leftKey = null;
		Integer rightKey = null;
		BASE leftValue = null;
		BASE rightValue = null;

		final SortedMap<Integer, BASE> resultCoefficientsForIndexes = new TreeMap<Integer, BASE>();

		while (leftIndex < leftKeys.length && rightIndex < rightKeys.length) {
			leftKey = leftKeys[leftIndex];
			rightKey = rightKeys[rightIndex];
			leftValue = leftCoefficients.get(leftKey);
			rightValue = rightCoefficients.get(rightKey);

			if (leftKey == rightKey) {
				resultCoefficientsForIndexes.put(leftKey, (BASE) leftValue
						.add(rightValue));
				leftIndex++;
				rightIndex++;
			}
			else if (leftKey < rightKey) {
				resultCoefficientsForIndexes.put(leftKey, leftValue);
				leftIndex++;
			}
			else {
				resultCoefficientsForIndexes.put(rightKey, rightValue);
				rightIndex++;
			}
		}

		if (leftIndex < leftKeys.length) {
			while (leftIndex < leftKeys.length) {
				leftKey = leftKeys[leftIndex++];
				leftValue = leftCoefficients.get(leftKey);
				resultCoefficientsForIndexes.put(leftKey, leftValue);
			}
		}
		else if (rightIndex < rightKeys.length) {
			while (rightIndex < rightKeys.length) {
				rightKey = rightKeys[rightIndex++];
				rightValue = rightCoefficients.get(rightKey);
				resultCoefficientsForIndexes.put(rightKey, rightValue);
			}
		}

		return resultCoefficientsForIndexes;
	}

	public int compareTo(final IRingElement o)
	{
		final Polynomial<BASE> other = (Polynomial<BASE>) o;
		if (this.getDegree() == other.getDegree()) {
			if (this.getHighestPower() != 0
					&& this.getHighestCoefficient().equals(
							other.getHighestCoefficient()))
			{

				Polynomial<BASE> thisWithoutHightestPower = new Polynomial<BASE>(
						this.getCoefficientsForExponents().headMap(
								this.getHighestPower()), this
								.getPolynomialFactory().getBaseFactory());
				Polynomial<BASE> otherWithoutHightestPower = new Polynomial<BASE>(
						other.getCoefficientsForExponents().headMap(
								other.getHighestPower()), other
								.getPolynomialFactory().getBaseFactory());
				return thisWithoutHightestPower
						.compareTo(otherWithoutHightestPower);
			}
			else {
				return this.getHighestCoefficient().compareTo(
						other.getHighestCoefficient());
			}
		}
		else {
			return new Integer(this.getDegree()).compareTo(other.getDegree());
		}
	}

	public Polynomial<BASE> multiply(final IRingElement other)
	{
		SortedMap<Integer, BASE> resultCoefficientsForIndexes = new TreeMap<Integer, BASE>();

		final SortedMap<Integer, BASE> otherCoefficients = ((Polynomial<BASE>) other)
				.getCoefficientsForExponents();

		for (final Integer currentOtherKey : otherCoefficients.keySet()) {
			final Map<Integer, IRingElement> currentCoefficients = new HashMap<Integer, IRingElement>(
					this.coefficientsForExponents);
			final IRingElement currentOtherCoefficient = otherCoefficients
					.get(currentOtherKey);

			/*
			 * 1. Make scalar multiplication by other coefficient.
			 * 
			 * 2. Shift everything by the power of the exponent.
			 */
			final SortedMap<Integer, BASE> currentCoefficientsShifted = new TreeMap<Integer, BASE>();
			for (final Integer currentKey : currentCoefficients.keySet()) {
				currentCoefficientsShifted.put(currentKey + currentOtherKey,
						(BASE) currentCoefficients.get(currentKey).multiply(
								currentOtherCoefficient));
			}

			resultCoefficientsForIndexes = this.addHelper(
					resultCoefficientsForIndexes, currentCoefficientsShifted);
		}

		return polynomialFactory.get(resultCoefficientsForIndexes,
				polynomialFactory.BASEFACTORY);
	}

	public Polynomial<BASE> negate()
	{
		/*
		 * the zero-valued polynomial needs extra treatment because of the
		 * Base-factory.
		 */
		if (this.isZero()) {
			return this;
		}
		// handle non-empty polynomials.
		final Map<Integer, BASE> resultCoefficientsForIndexes = new HashMap<Integer, BASE>();
		for (final Integer currentKey : this.coefficientsForExponents.keySet())
		{
			resultCoefficientsForIndexes.put(currentKey,
					(BASE) (this.coefficientsForExponents.get(currentKey))
							.negate());
		}
		return polynomialFactory.get(resultCoefficientsForIndexes,
				polynomialFactory.BASEFACTORY);
	}

	@Override
	public boolean isZero()
	{
		return this.getDegree() == 0 && this.getHighestCoefficient().isZero();
	}

	/**
	 * @return the coefficientsForIndexes
	 */
	protected SortedMap<Integer, BASE> getCoefficientsForExponents()
	{
		return coefficientsForExponents;
	}

	/**
	 * @return the degree of this polynomial
	 */
	public int getDegree()
	{
		if (this.coefficientsForExponents.isEmpty()) {
			return 0;
		}
		return this.coefficientsForExponents.lastKey();
	}

	/**
	 * Performs polynomial long division on this Polynomial and returns the
	 * result.
	 * 
	 * @param other
	 * @return the result of the division
	 */
	public PolynomialLongDivisionResult<BASE> longDivision(
			final Polynomial<BASE> other)
	{
		if (this.isZero()) {
			return new PolynomialLongDivisionResult<BASE>(this, this);
		}
		if (other.isZero()) {
			throw new DivisionByZeroException(
					"Zero polynomial cannot be used as divisor!");
		}

		if (this.getDegree() >= other.getDegree()) {

			final Map<Integer, BASE> factorCoeffs = new HashMap<Integer, BASE>();
			factorCoeffs.put(this.getHighestPower() - other.getHighestPower(),
					(BASE) this.getHighestCoefficient().divide(
							other.getHighestCoefficient()));

			final Polynomial<BASE> factor = polynomialFactory.get(factorCoeffs,
					polynomialFactory.BASEFACTORY);

			final Polynomial<BASE> subtrahend = factor.multiply(other);

			final Polynomial<BASE> newDividend = this.subtract(subtrahend);

			final PolynomialLongDivisionResult<BASE> newResult = newDividend
					.longDivision(other);

			return new PolynomialLongDivisionResult<BASE>(factor.add(newResult
					.getQuotient()), newResult.getRemainder());

		}
		return new PolynomialLongDivisionResult<BASE>(polynomialFactory.zero(),
				this);
	}

	/**
	 * @return this Polynomial differentiated
	 */
	public Polynomial<BASE> differentiate()
	{
		final HashMap<Integer, BASE> resultCoefficients = new HashMap<Integer, BASE>();
		for (final Integer currentKey : this.coefficientsForExponents.keySet())
		{
			if (currentKey != 0) {
				final IRingElement currentCoefficient = this.coefficientsForExponents
						.get(currentKey);
				resultCoefficients.put(currentKey - 1,
						(BASE) currentCoefficient.multiply(polynomialFactory
								.getBaseFactory().get(currentKey.intValue())));
			}
		}
		return polynomialFactory.get(resultCoefficients,
				polynomialFactory.BASEFACTORY);
	}

	/**
	 * This will fail, if
	 * <code>!(this.coefficientType instanceof FieldElement)</code>
	 * 
	 * @return a polynomial that is the the result of integrating this
	 *         polynomial.
	 */
	public Polynomial<BASE> integrate()
	{
		final Map<Integer, BASE> resultCoefficients = new HashMap<Integer, BASE>();
		for (final Integer currentKey : this.coefficientsForExponents.keySet())
		{
			final BASE currentCoefficient = this.coefficientsForExponents
					.get(currentKey);
			resultCoefficients.put(currentKey + 1, (BASE) currentCoefficient
					.multiply(polynomialFactory.getBaseFactory().get(
							currentKey + 1).invert()));
		}
		return polynomialFactory.get(resultCoefficients,
				polynomialFactory.BASEFACTORY);
	}

	/**
	 * if coefficients match this, then they do not require to be surrounded by
	 * parenthesis
	 */
	final static Pattern simpleNumber = Pattern
			.compile("\\d+\\.?\\d*([eE][+-]\\d+)?|\\d+/\\d+");

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		boolean firstKey = true;
		for (final Integer currentKey : coefficientsForExponents.keySet()) {

			final IRingElement currentCoefficient = coefficientsForExponents
					.get(currentKey);
			if (!currentCoefficient.isZero()) {
				final String s = currentCoefficient.toString();
				if (!firstKey) {
					sb.append("+");
				}
				else {
					firstKey = false;
				}
				final Matcher m = simpleNumber.matcher(s);
				if (currentKey != 0 && !m.matches()) sb.append('(');
				sb.append(currentCoefficient);
				if (currentKey != 0 && !m.matches()) sb.append(')');
				if (currentKey > 0) {
					sb.append("*x");
				}

				if (currentKey > 1) {
					sb.append("^" + currentKey);
				}
			}

		}
		if (sb.length() == 0) {
			return "0";
		}
		return sb.toString();

	}

	/**
	 * @return the coefficient for the highest exponent (zero if the polynomial
	 *         is empty).
	 */
	public BASE getHighestCoefficient()
	{
		if (this.coefficientsForExponents == null
				|| this.coefficientsForExponents.isEmpty())
		{
			return this.polynomialFactory.BASEFACTORY.zero();
		}
		final int highestExponent = this.coefficientsForExponents.lastKey();
		return this.coefficientsForExponents.get(highestExponent);
	}

	/**
	 * @return the coefficient for the lowest exponent (zero if the polynomial
	 *         is empty).
	 */
	public BASE getLowestCoefficient()
	{
		if (this.coefficientsForExponents == null
				|| this.coefficientsForExponents.isEmpty())
		{
			return this.polynomialFactory.BASEFACTORY.zero();
		}

		final int lowestExponent = this.coefficientsForExponents.firstKey();
		return this.coefficientsForExponents.get(lowestExponent);
	}

	/**
	 * @return the higherst power in this polynomial (or zero)
	 */
	public int getHighestPower()
	{
		if (this.coefficientsForExponents.isEmpty()) {
			return 0;
		}
		return this.coefficientsForExponents.lastKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.IRingElement#getFactory()
	 */
	public IRingElementFactory<? extends IRingElement> getFactory()
	{
		return polynomialFactory;
	}

	/**
	 * @return the polynomialFactory
	 */
	public PolynomialFactory<BASE> getPolynomialFactory()
	{
		return polynomialFactory;
	}

	/**
	 * @param another
	 *            a polynomial
	 * @return the GCD of this and <code>another</code> polynomial
	 */
	public Polynomial<BASE> gcd(final Polynomial<BASE> another)
	{
		if (another.gt(this)) {
			return another.gcd(this);
		}

		final PolynomialLongDivisionResult<BASE> divisionResult = this
				.longDivision(another);
		final Polynomial<BASE> remainder = divisionResult.getRemainder();

		if (remainder.isZero()) {
			return another;
		}
		else if (remainder.getDegree() == 0) {
			return this.polynomialFactory.one();
		}
		else {
			return another.gcd(remainder);
		}
	}

	/**
	 * @return the minimal polynomial
	 */
	public Polynomial<BASE> minimalPolynomial()
	{
		if (this.getDegree() <= 1) {
			return this.divide(this.getHighestCoefficient());
		}
		final Polynomial<BASE> gcd = this.gcd(this.differentiate());
		final Polynomial<BASE> unnormalized = this.longDivision(gcd)
				.getQuotient();
		return unnormalized.divide(unnormalized.getHighestCoefficient());
	}

	/**
	 * divide this by another polynomial if no remainder exists. See
	 * {@link #longDivision(Polynomial)} if a remainder is expected.
	 * 
	 * @return the result if the remainder is zero.
	 * @throws InvalidOperationException
	 *             if the remainder is non-zero
	 */
	@Override
	public Polynomial<BASE> divide(final IRingElement val)
	{
		if (val instanceof Polynomial<?>) {
			final PolynomialLongDivisionResult<BASE> divisionResult = this
					.longDivision((Polynomial<BASE>) val);
			if (divisionResult.getRemainder().isZero()) {
				return divisionResult.getQuotient();
			}
			throw new InvalidOperationException(
					this
							+ " cannot be divided by "
							+ val
							+ " without a remainder. Use method longDivision() instead.");
		}
		final Map<Integer, BASE> newCoefficientsForExponents = new HashMap<Integer, BASE>();
		for (final Integer currentKey : this.coefficientsForExponents.keySet())
		{
			final BASE currentCoefficient = this.coefficientsForExponents
					.get(currentKey);
			newCoefficientsForExponents.put(currentKey,
					(BASE) currentCoefficient.divide(val));
		}

		return new Polynomial<BASE>(newCoefficientsForExponents,
				this.polynomialFactory.BASEFACTORY);

	}

	@Override
	public Polynomial<BASE> invert()
	{
		if (getDegree() <= 0) {
			return this.getPolynomialFactory().get(
					this.getHighestCoefficient().invert());
		}
		else {
			throw new InvalidOperationException(this
					+ " inverted does not result in a polynomial!");
		}
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> Polynomial<BASE> apply(final ARG x)
	{
		return (Polynomial<BASE>) (x.lt(x.getFactory().zero()) ? x.negate() : x);
	}

	/**
	 * @throws InvalidOperationException
	 * @see org.jlinalg.FieldElement#abs()
	 */
	@Override
	public FieldElement abs()
	{
		throw new InvalidOperationException(
				"The Abs() of a polynomial is not a polynomial anymore.");
	}

	/**
	 * It can be shown that the degree of a polynomial over a field satisfies
	 * all of the requirements of a norm function in the euclidean domain. That
	 * is, given two polynomials f(x) and g(x), the degree of the product
	 * f(x)�g(x) must be larger than both the degrees of f and g individually.
	 * In fact, something stronger holds:
	 * degree( f(x) * g(x) ) = degree(f(x)) + degree(g(x))
	 * 
	 * @return the degree of this polynomial as a polynomial (of degree 0)
	 */
	@Override
	public Polynomial<BASE> norm()
	{
		return new Polynomial<BASE>(this.getPolynomialFactory()
				.getBaseFactory().get(this.getDegree()));
	}
}
