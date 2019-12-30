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
package org.jlinalg.polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.EuclideanAlgorithm;
import org.jlinalg.IEuclideanRingElement;
import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
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
@JLinAlgTypeProperties(isCompound = true)
public class Polynomial<BASE extends IRingElement<BASE>>
		extends
		RingElement<Polynomial<BASE>>
		implements
		IEuclideanRingElement<Polynomial<BASE>>
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

	private final IRingElementFactory<BASE> baseFactory;

	public Polynomial(BASE value)
	{
		if (value == null) {
			throw new InvalidOperationException("value cannot be null!");
		}

		this.coefficientsForExponents = new TreeMap<>();
		this.coefficientsForExponents.put(Integer.valueOf(0), value);
		this.baseFactory = value.getFactory();
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
		if (baseFactory == null) {
			throw new InvalidOperationException(
					"The base factory for a polynomial cannot be null!");
		}

		if (coeff1 == null) {
			coeff1 = new HashMap<>();
		}

		this.coefficientsForExponents = new TreeMap<>();

		this.baseFactory = baseFactory;

		for (final BASE b : coeff1.values()) {
			if (!baseFactory.equals(b.getFactory()))
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

	@Override
	public Polynomial<BASE> add(final Polynomial<BASE> other)
	{
		final SortedMap<Integer, BASE> otherCoefficients = other
				.getCoefficientsForExponents();

		SortedMap<Integer, BASE> resultCoeffs = this
				.addHelper(coefficientsForExponents, otherCoefficients);
		return new Polynomial<>(resultCoeffs,
				other.getFactory().getBaseFactory());
	}

	@Override
	public Polynomial<BASE> subtract(final Polynomial<BASE> other)
	{
		return super.subtract(other);
	}

	/**
	 * used by {@link Polynomial#add(IRingElement)},
	 * {@link Polynomial#multiply(IRingElement)}
	 */
	private SortedMap<Integer, BASE> addHelper(
			final SortedMap<Integer, BASE> leftCoefficients,
			final SortedMap<Integer, BASE> rightCoefficients)
	{
		final Integer[] leftKeys = leftCoefficients.keySet()
				.toArray(new Integer[leftCoefficients.keySet().size()]);
		final Integer[] rightKeys = rightCoefficients.keySet()
				.toArray(new Integer[rightCoefficients.keySet().size()]);

		int leftIndex = 0;
		int rightIndex = 0;
		Integer leftKey = null;
		Integer rightKey = null;
		BASE leftValue = null;
		BASE rightValue = null;

		final SortedMap<Integer, BASE> resultCoefficientsForIndexes = new TreeMap<>();

		while (leftIndex < leftKeys.length && rightIndex < rightKeys.length) {
			leftKey = leftKeys[leftIndex];
			rightKey = rightKeys[rightIndex];
			leftValue = leftCoefficients.get(leftKey);
			rightValue = rightCoefficients.get(rightKey);

			if (leftKey.equals(rightKey)) {
				resultCoefficientsForIndexes.put(leftKey,
						leftValue.add(rightValue));
				leftIndex++;
				rightIndex++;
			}
			else if (leftKey.compareTo(rightKey) < 0) {
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

	@Override
	public int compareTo(final Polynomial<BASE> o)
	{
		final Polynomial<BASE> other = o;
		if (this.getDegree() == other.getDegree()) {
			if (getDegree() != 0 && this.getHighestCoefficient()
					.equals(other.getHighestCoefficient()))
			{
				return this.withoutHighestPower()
						.compareTo(other.withoutHighestPower());
			}
			return this.getHighestCoefficient()
					.compareTo(other.getHighestCoefficient());
		}
		return this.getDegree() - other.getDegree();
	}

	public Polynomial<BASE> withoutHighestPower()
	{
		return new Polynomial<>(
				this.getCoefficientsForExponents()
						.headMap(this.getHighestPower()),
				this.getFactory().getBaseFactory());
	}

	@Override
	public Polynomial<BASE> multiply(final Polynomial<BASE> other)
	{
		SortedMap<Integer, BASE> resultCoefficientsForIndexes = new TreeMap<>();

		final SortedMap<Integer, BASE> otherCoefficients = other
				.getCoefficientsForExponents();

		for (final Integer currentOtherKey : otherCoefficients.keySet()) {
			final Map<Integer, BASE> currentCoefficients = new HashMap<>(
					this.coefficientsForExponents);
			final BASE currentOtherCoefficient = otherCoefficients
					.get(currentOtherKey);

			/*
			 * 1. Make scalar multiplication by other coefficient.
			 * 
			 * 2. Shift everything by the power of the exponent.
			 */
			final SortedMap<Integer, BASE> currentCoefficientsShifted = new TreeMap<>();
			for (final Integer currentKey : currentCoefficients.keySet()) {
				BASE v = currentCoefficients.get(currentKey);
				currentCoefficientsShifted.put(currentKey + currentOtherKey,
						v.multiply(currentOtherCoefficient));
			}

			resultCoefficientsForIndexes = this.addHelper(
					resultCoefficientsForIndexes, currentCoefficientsShifted);
		}

		PolynomialFactory<BASE> polynomialFactory = getFactory();
		return polynomialFactory.get(resultCoefficientsForIndexes,
				polynomialFactory.getBaseFactory());
	}

	@Override
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
		final Map<Integer, BASE> resultCoefficientsForIndexes = new HashMap<>();
		for (final Integer currentKey : this.coefficientsForExponents
				.keySet())
		{
			resultCoefficientsForIndexes.put(currentKey,
					(this.coefficientsForExponents.get(currentKey)).negate());
		}
		PolynomialFactory<BASE> polynomialFactory = getFactory();
		return polynomialFactory.get(resultCoefficientsForIndexes, baseFactory);
	}

	@Override
	public boolean isZero()
	{
		return this.getHighestPower().intValue() == 0
				&& this.getHighestCoefficient().isZero();
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
		return getHighestPower().intValue();
	}

	/**
	 * Performs polynomial long division on this Polynomial and returns the
	 * result.
	 * 
	 * @param divisor
	 * @return the result of the division
	 */
	@Override
	public DivisionResultWithRest<Polynomial<BASE>> euclideanDivision(
			Polynomial<BASE> divisor)
	{
		if (this.isZero()) {
			return new DivisionResultWithRest<>(this, this);
		}
		if (divisor.isZero()) {
			throw new DivisionByZeroException(
					"Zero polynomial cannot be used as divisor!");
		}
		PolynomialFactory<BASE> polynomialFactory = getFactory();
		if (this.getDegree() >= divisor.getDegree()) {

			final Map<Integer, BASE> factorCoeffs = new HashMap<>();
			factorCoeffs.put(this.getDegree() - divisor.getDegree(),
					this.getHighestCoefficient()
							.divide(divisor.getHighestCoefficient()));

			final Polynomial<BASE> factor = polynomialFactory.get(factorCoeffs,
					polynomialFactory.getBaseFactory());

			final Polynomial<BASE> subtrahend = factor.multiply(divisor);

			final Polynomial<BASE> newDividend = this.subtract(subtrahend);

			final DivisionResultWithRest<Polynomial<BASE>> newResult = newDividend
					.euclideanDivision(divisor);

			return new DivisionResultWithRest<>(
					factor.add(newResult.getQuotient()),
					newResult.getRemainder());

		}
		return new DivisionResultWithRest<>(polynomialFactory.zero(), this);
	}

	/**
	 * @return this Polynomial differentiated
	 */
	public Polynomial<BASE> differentiate()
	{
		PolynomialFactory<BASE> polynomialFactory = getFactory();
		final HashMap<Integer, BASE> resultCoefficients = new HashMap<>();
		for (final Integer currentKey : this.coefficientsForExponents
				.keySet())
		{
			if (currentKey.intValue() != 0) {
				final BASE currentCoefficient = this.coefficientsForExponents
						.get(currentKey);
				resultCoefficients.put(currentKey - 1,
						currentCoefficient.multiply(polynomialFactory
								.getBaseFactory().get(currentKey.intValue())));
			}
		}
		return polynomialFactory.get(resultCoefficients,
				polynomialFactory.getBaseFactory());
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
		PolynomialFactory<BASE> polynomialFactory = getFactory();
		final Map<Integer, BASE> resultCoefficients = new HashMap<>();
		for (final Integer currentKey : this.coefficientsForExponents
				.keySet())
		{
			final BASE currentCoefficient = this.coefficientsForExponents
					.get(currentKey);
			resultCoefficients.put(currentKey + 1,
					currentCoefficient.multiply(polynomialFactory
							.getBaseFactory().get(currentKey + 1).invert()));
		}
		return polynomialFactory.get(resultCoefficients,
				polynomialFactory.getBaseFactory());
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

			final BASE currentCoefficient = coefficientsForExponents
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
				if (currentKey.intValue() != 0 && !m.matches()) sb.append('(');
				sb.append(currentCoefficient);
				if (currentKey.intValue() != 0 && !m.matches()) sb.append(')');
				if (currentKey.intValue() > 0) {
					sb.append("*x");
				}

				if (currentKey.intValue() > 1) {
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
			PolynomialFactory<BASE> polynomialFactory = getFactory();
			return polynomialFactory.getBaseFactory().zero();
		}
		final Integer highestExponent = this.coefficientsForExponents.lastKey();
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
			PolynomialFactory<BASE> polynomialFactory = getFactory();
			return polynomialFactory.getBaseFactory().zero();
		}

		final Integer lowestExponent = this.coefficientsForExponents.firstKey();
		return this.coefficientsForExponents.get(lowestExponent);
	}

	/**
	 * @return the highest power in this polynomial (or zero)
	 */
	public Integer getHighestPower()
	{
		if (this.coefficientsForExponents.isEmpty()) {
			return Integer.valueOf(0);
		}
		return this.coefficientsForExponents.lastKey();
	}

	/**
	 * @return the polynomialFactory
	 */
	@Override
	public PolynomialFactory<BASE> getFactory()
	{
		return PolynomialFactoryMap.getFactory(baseFactory);
	}

	public Polynomial<BASE> gcd(final Polynomial<BASE> another)
	{
		Polynomial<BASE> gcdWithConstant = EuclideanAlgorithm.gcd(this,
				another);
		if (gcdWithConstant.getDegree() > 0) {
			return gcdWithConstant
					.divideByScalar(gcdWithConstant.getHighestCoefficient());
		}
		return gcdWithConstant;
	}

	/**
	 * @return the minimal polynomial
	 */
	public Polynomial<BASE> minimalPolynomial()
	{
		if (this.getDegree() <= 1) {
			return this.divideByScalar(this.getHighestCoefficient());
		}
		final Polynomial<BASE> gcd = this.gcd(this.differentiate());
		final Polynomial<BASE> unnormalized = this.euclideanDivision(gcd)
				.getQuotient();
		return unnormalized
				.divideByScalar(unnormalized.getHighestCoefficient());
	}

	/**
	 * divide this by another polynomial if no remainder exists. See
	 * {@link #euclideanDivision(Polynomial)} if a remainder is expected.
	 * 
	 * @return the result if the remainder is zero.
	 * @throws InvalidOperationException
	 *             if the remainder is non-zero
	 */
	@Override
	public Polynomial<BASE> divide(final Polynomial<BASE> val)
	{
		if (val != null) {
			final DivisionResultWithRest<Polynomial<BASE>> divisionResult = this
					.euclideanDivision(val);
			if (divisionResult.getRemainder().isZero()) {
				return divisionResult.getQuotient();
			}
			throw new InvalidOperationException(this + " cannot be divided by "
					+ val
					+ " without a remainder. Use method longDivision() instead.");
		}
		throw new InternalError("divisor=null");
	}

	/**
	 * Division with a base value/
	 * 
	 * @param val
	 * @return a polynomial with all coefficients divided by val.
	 */
	public Polynomial<BASE> divideByScalar(final BASE val)
	{
		final Map<Integer, BASE> newCoefficientsForExponents = new HashMap<>();
		for (final Integer currentKey : this.coefficientsForExponents
				.keySet())
		{
			final BASE currentCoefficient = this.coefficientsForExponents
					.get(currentKey);
			BASE newCoeff = currentCoefficient.divide(val);
			newCoefficientsForExponents.put(currentKey, newCoeff);
		}

		PolynomialFactory<BASE> polynomialFactory = getFactory();
		return new Polynomial<>(newCoefficientsForExponents,
				polynomialFactory.getBaseFactory());

	}

	@Override
	public Polynomial<BASE> invert()
	{
		if (getDegree() <= 0) {
			return this.getFactory().get(this.getHighestCoefficient().invert());
		}
		throw new InvalidOperationException(
				this + " inverted does not result in a polynomial!");
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement<BASE>> Polynomial<BASE> apply(final ARG x)
	{
		return (Polynomial<BASE>) (x.lt(x.getFactory().zero()) ? x.negate()
				: x);
	}

	/**
	 * @throws InvalidOperationException
	 *             when called.
	 * @deprecated The absolute value of a polynomial is not anymore a
	 *             polynomial.
	 */
	@Deprecated
	@Override
	public Polynomial<BASE> abs()
	{
		throw new InvalidOperationException(
				"The Abs() of a polynomial is not a polynomial anymore.");
	}

	// /**
	// * It can be shown that the degree of a polynomial over a field satisfies
	// * all of the requirements of a norm function in the Euclidean domain.
	// That
	// * is, given two polynomials f(x) and g(x), the degree of the product
	// * f(x)*g(x) must be larger than both the degrees of f and g individually.
	// * In fact, something stronger holds:
	// * degree( f(x) * g(x) ) = degree(f(x)) + degree(g(x))
	// *
	// * @return the degree of this polynomial as a polynomial (of degree 0)
	// */
	// @Override
	// public Polynomial<BASE> norm()
	// {
	// return new Polynomial<BASE>(this.getPolynomialFactory()
	// .getBaseFactory().get(this.getDegree()));
	// }
	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Polynomial<BASE> norm()
	{
		throw new InvalidOperationException(
				"The function norm() is not implemented for polynomials.");
	}

	/**
	 * the hashcode for polynomials is an xor-combination of all hashcodes of
	 * the coefficients.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int hash = 0;
		// the final value is independent of the sequence of the coefficients as
		// xor on truth values forms an abelian group.
		for (BASE b : coefficientsForExponents.values()) {
			hash ^= b.hashCode();
		}
		return hash;
	}

	public BASE getCoefficinet(int i)
	{
		if (i < 0) {
			throw new Error("Invalid coefficient: " + i);
		}
		return coefficientsForExponents.getOrDefault(i, baseFactory.zero());

	}

}
