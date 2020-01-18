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
package org.jlinalg.complex;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.FieldElement;
import org.jlinalg.InvalidOperationException;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.RingElementFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.rational.RationalFactory;

/**
 * This class represents a complex number with two Rationals as a real- and
 * imaginary part. Therefore there will not be any rounding errors.
 * 
 * @author Andreas Keilhauer, Simon D. Levy, Georg Thimm
 */
public class Complex
		extends
		FieldElement<Complex>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return imaginaryPart.hashCode() ^ realPart.hashCode();
	}

	/**
	 * The real part of this Complex as a Rational.
	 */

	protected final Rational realPart;

	/**
	 * The imaginary part of this Complex as a Rational.
	 */

	protected final Rational imaginaryPart;

	/**
	 * Creates a Complex out of two Rationals, representing real- and imaginary
	 * part.
	 * 
	 * @param realPart
	 * @param imaginaryPart
	 */

	Complex(Rational realPart, Rational imaginaryPart)
	{
		this.realPart = realPart;
		this.imaginaryPart = imaginaryPart;
	}

	/**
	 * Creates a Complex out of two doubles, representing real- and imaginary
	 * part.
	 * 
	 * @param realPart
	 * @param imaginaryPart
	 */

	Complex(double realPart, double imaginaryPart)
	{
		this.realPart = Rational.FACTORY.get(realPart);
		this.imaginaryPart = Rational.FACTORY.get(imaginaryPart);
	}

	/**
	 * Gets the real part of this complex.
	 * 
	 * @return real part as a Rational
	 */

	public Rational getReal()
	{
		return realPart;
	}

	/**
	 * Gets the imaginary part of this complex.
	 * 
	 * @return imaginary part as a Rational
	 */

	public Rational getImaginary()
	{
		return imaginaryPart;
	}

	/**
	 * Returns the result of this Complex added to another one.
	 */
	@Override
	public Complex add(Complex val)
	{
		Complex added = val;
		Rational newRealPart = this.realPart.add(added.getReal());
		Rational newImaginaryPart = this.imaginaryPart
				.add(added.getImaginary());
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * Returns the result of this Complex multiplied with another one.
	 */
	@Override
	public Complex multiply(Complex val)
	{
		Complex mult = val;
		Rational newRealPart = (this.realPart.multiply(mult.getReal()))
				.subtract(this.imaginaryPart.multiply(mult.getImaginary()));
		Rational newImaginaryPart = (this.realPart
				.multiply(mult.getImaginary()))
						.add(this.imaginaryPart.multiply(mult.getReal()));
		return new Complex(newRealPart, newImaginaryPart);
	}

	@Override
	public Complex negate()
	{
		return new Complex(realPart.negate(), imaginaryPart.negate());
	}

	@Override
	public Complex invert() throws DivisionByZeroException
	{
		if (this.isZero()) {
			throw new DivisionByZeroException("Tried to invert zero.");
		}
		Rational normalize = realPart.multiply(realPart)
				.add(imaginaryPart.multiply(imaginaryPart));
		return new Complex(realPart.divide(normalize),
				imaginaryPart.negate().divide(normalize));
	}

	/**
	 * Returns a Complex that is this Complex conjugated.
	 * 
	 * @return this Complex conjugated
	 */

	public Complex conjugate()
	{
		return new Complex(realPart, imaginaryPart.negate());
	}

	/**
	 * Determines whether two Complex numbers are mathematically equal.
	 * 
	 * @param obj
	 *            another Complex/Rational
	 * @return true if and only if the real- as well as the imaginary parts are
	 *         equal.
	 */

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (obj instanceof Rational) {
			Rational comp = (Rational) obj;
			if (!this.imaginaryPart.isZero()) {
				return false;
			} // else {
			return this.realPart.equals(comp);
			// }
		}
		Complex comp = (Complex) obj;
		return this.realPart.equals(comp.getReal())
				&& this.imaginaryPart.equals(comp.getImaginary());
	}

	/**
	 * Returns a String representation of this Complex.
	 */

	@Override
	public String toString()
	{
		String tmp = "";

		boolean reIsZero = this.realPart.isZero();
		boolean imAbsIsOne = this.imaginaryPart.abs().isOne();
		int compImZero = this.imaginaryPart.compareTo(Rational.FACTORY.zero());

		if (!reIsZero || (reIsZero && compImZero == 0)) {
			tmp += this.realPart.toString();
		}

		if (compImZero != 0) {
			if (compImZero > 0 && !reIsZero) {
				tmp += " + ";
			}
			else if (compImZero < 0) {
				tmp += " - ";
			}

			if (!imAbsIsOne) {
				tmp += imaginaryPart.abs().toString() + " ";
			}
			tmp += "i";
		}

		return tmp;
	}

	/**
	 * Implements Comparable.compareTo(Object). Comparison is based on Rational
	 * magnitude value.
	 * 
	 * @param o
	 *            the object
	 * @return -1,+1,0} as this object is less than, equal to, or greater than
	 *         the specified object.
	 */

	@Override
	public int compareTo(Complex o)
	{
		Rational normThis = norm().realPart;
		Rational normOther = o.norm().realPart;
		return normThis.compareTo(normOther);
	}

	@Override
	public ComplexFactory getFactory()
	{
		return FACTORY;
	}

	/**
	 * the constant -1
	 */
	private static final Complex M_ONE = new Complex(-1.0, 0.0);

	/**
	 * the constant 1
	 */
	private static final Complex ONE = new Complex(1.0, 0.0);

	/**
	 * the constant 0
	 */
	private static final Complex ZERO = new Complex(0.0, 0.0);

	/**
	 * the (singleton) factory for complex numbers.
	 */
	public static ComplexFactory FACTORY = ZERO.new ComplexFactory();

	/**
	 * The class defining the complex number factory.
	 */
	@JLinAlgTypeProperties(isDiscreet = false, isExact = true, isCompound = false, hasNegativeValues = false)
	public class ComplexFactory
			extends
			RingElementFactory<Complex>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * The constructor. This should be called only once to instantiate
		 * {@link Complex#FACTORY}.
		 */
		private ComplexFactory()
		{
			super();
		}

		@Override
		public Complex[] getArray(int size)
		{
			return new Complex[size];
		}

		/**
		 * create a complex number from two arbitrary objects. Permissible
		 * classes are those permissible for
		 * {@link org.jlinalg.rational.RationalFactory#get(Object)}.
		 * 
		 * @param real
		 * @param imaginary
		 * @return a complex number.
		 */
		public Complex get(Object real, Object imaginary)
		{
			Rational r_real = Rational.FACTORY.get(real);
			Rational r_imaginary = Rational.FACTORY.get(imaginary);
			return new Complex(r_real, r_imaginary);
		}

		/**
		 * create a complex number for arbitrary objects. Permissible classes
		 * are those permissible for
		 * {@link org.jlinalg.rational.RationalFactory#get(Object)}.
		 * The
		 * imaginary part is assumed to be zero.
		 * 
		 * @param o
		 *            a {@link Complex} or an object that can be translated into
		 *            a {@link Rational}
		 * @return a complex number.
		 */
		@Override
		public Complex get(Object o)
		{
			if (o instanceof Complex) return (Complex) o;
			Rational r_real = Rational.FACTORY.get(o);
			return new Complex(r_real, Rational.FACTORY.zero());
		}

		@Override
		public Complex get(int i)
		{
			return new Complex(i, 0.0);
		}

		@Override
		public Complex get(double d)
		{
			return get(d, 0.0);
		}

		@Override
		public Complex m_one()
		{
			return M_ONE;
		}

		@Override
		public Complex one()
		{
			return ONE;
		}

		@Override
		public Complex zero()
		{
			return ZERO;
		}

		@Override
		public Complex[][] getArray(int rows, int columns)
		{
			return new Complex[rows][columns];
		}

		/**
		 * @param d
		 *            the real part for the complex number
		 * @return a complex number with the imaginary part equals to zero
		 *         * @see org.jlinalg.IRingElementFactory#get(long)
		 */
		@Override
		public Complex get(long d)
		{
			return new Complex(d, 0.0);
		}

		/**
		 * @param r
		 *            real part
		 * @param i
		 *            imaginary part
		 * @return a complex number
		 */
		public Complex get(double r, double i)
		{
			return new Complex(r, i);
		}

		@Override
		public Complex gaussianRandomValue()
		{
			return new Complex(random.nextGaussian(), random.nextGaussian());
		}

		@Override
		public Complex randomValue(Complex min_, Complex max_)
		{
			Complex min = min_;
			Complex max = max_;
			Rational r = max.realPart.subtract(min.realPart)
					.multiply(Rational.FACTORY.randomValue()).add(min.realPart);
			Rational i = max.imaginaryPart.subtract(min.imaginaryPart)
					.multiply(Rational.FACTORY.randomValue())
					.add(min.imaginaryPart);
			return new Complex(r, i);
		}

		@Override
		public Complex randomValue()
		{
			return new Complex(random.nextDouble(), random.nextDouble());
		}

		/**
		 * @return The factory for the rational and irrational part.
		 */
		public RationalFactory getBaseFactory()
		{
			return Rational.FACTORY;
		}
	}

	/**
	 * @throws InvalidOperationException
	 * @see org.jlinalg.FieldElement#abs()
	 * @deprecated the usual definition of the absolute value requires the
	 *             square root, which in general leaves the domain of the
	 *             rational numbers.
	 */
	@Override
	@Deprecated
	public Complex abs()
	{
		throw new InvalidOperationException(
				"Abs() requires the square root, which in general leaves the domain of the rational numbers.");
	}

	/**
	 * The field norm from the complex numbers to the real numbers sends x + iy
	 * to x^2 + y^2.
	 * 
	 * @return a complex number with the real part being the
	 */
	@Override
	public Complex norm()
	{
		Rational a = getReal();
		Rational b = getImaginary();
		return FACTORY.get(b.multiply(b).add(a.multiply(a)));
	}

}
