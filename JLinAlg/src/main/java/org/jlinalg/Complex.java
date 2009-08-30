package org.jlinalg;

import java.util.Random;

/**
 * This class represents a complex number with two Rationals as a real- and
 * imaginary part. Therefore there will not be any rounding errors.
 * 
 * @author Andreas Keilhauer, Simon D. Levy, Georg Thimm
 */

public class Complex
		extends FieldElement
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

	protected Rational realPart;

	/**
	 * The imaginary part of this Complex as a Rational.
	 */

	protected Rational imaginaryPart;

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
		this.realPart = new Rational(realPart);
		this.imaginaryPart = new Rational(imaginaryPart);
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
	public Complex add(IRingElement val)
	{
		Complex added = (Complex) val;
		Rational newRealPart = this.realPart.add(added.getReal());
		Rational newImaginaryPart = this.imaginaryPart
				.add(added.getImaginary());
		return new Complex(newRealPart, newImaginaryPart);
	}

	/**
	 * Returns the result of this Complex multiplied with another one.
	 */
	public Complex multiply(IRingElement val)
	{
		Complex mult = (Complex) val;
		Rational newRealPart = (Rational) (this.realPart.multiply(mult
				.getReal())).subtract(this.imaginaryPart.multiply(mult
				.getImaginary()));
		Rational newImaginaryPart = (this.realPart
				.multiply(mult.getImaginary())).add(this.imaginaryPart
				.multiply(mult.getReal()));
		return new Complex(newRealPart, newImaginaryPart);
	}

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
		Rational normalize = this.realPart.multiply(this.realPart).add(
				this.imaginaryPart.multiply(this.imaginaryPart));
		return new Complex((Rational) this.realPart.divide(normalize),
				(Rational) this.imaginaryPart.negate().divide(normalize));
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
		int compImZero = this.imaginaryPart.compareTo(new Rational(0));

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

	public int compareTo(IRingElement o)
	{
		Complex comp = (Complex) o;
		return this.norm().compareTo(comp.norm());
	}

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
	public class ComplexFactory
			extends RingElementFactory<Complex>
	{
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
		 * {@link org.jlinalg.Rational.RationalFactory#get(Object)}.
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
		 * {@link org.jlinalg.Rational.RationalFactory#get(Object)}. The
		 * imaginary part is assumed to be zero.
		 * 
		 * @param real
		 * @return a complex number.
		 */
		@Override
		public Complex get(Object real)
		{
			Rational r_real = Rational.FACTORY.get(real);
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
			return new Complex(d, 0.0);
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

		/**
		 * create complex number from a 2-d Gaussian distribution
		 * 
		 * @param random
		 *            a random number generator used for this purpose
		 * @return a complex random number
		 */
		@Override
		@Deprecated
		public Complex gaussianRandomValue(Random random)
		{
			return new Complex(random.nextGaussian(), random.nextGaussian());
		}

		@Override
		public Complex[][] getArray(int rows, int columns)
		{
			return new Complex[rows][columns];
		}

		/**
		 * create complex number from a 2-d uniform distribution
		 * 
		 * @param random
		 *            a random number generator used for this purpose
		 * @return a complex random number
		 */
		@Override
		@Deprecated
		public Complex randomValue(Random random)
		{
			return new Complex(random.nextDouble(), random.nextDouble());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#get(long)
		 */
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

		/**
		 * @return a random number in the square formed by min and max.
		 * @see org.jlinalg.IRingElementFactory#randomValue(Random,
		 *      IRingElement, IRingElement)
		 */
		@Override
		@Deprecated
		public Complex randomValue(Random random, IRingElement min_,
				IRingElement max_)
		{
			Complex min = (Complex) min_;
			Complex max = (Complex) max_;
			Rational r = (Rational) max.realPart.subtract(min.realPart)
					.multiply(Rational.FACTORY.randomValue(random)).add(
							min.realPart);
			Rational i = (Rational) max.imaginaryPart.subtract(
					min.imaginaryPart).multiply(
					Rational.FACTORY.randomValue(random))
					.add(min.imaginaryPart);
			return new Complex(r, i);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#gaussianRandomValue()
		 */
		@Override
		public Complex gaussianRandomValue()
		{
			return new Complex(random.nextGaussian(), random.nextGaussian());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.jlinalg.IRingElementFactory#randomValue(org.jlinalg.IRingElement,
		 * org.jlinalg.IRingElement)
		 */
		@Override
		public Complex randomValue(IRingElement min_, IRingElement max_)
		{
			Complex min = (Complex) min_;
			Complex max = (Complex) max_;
			Rational r = (Rational) max.realPart.subtract(min.realPart)
					.multiply(Rational.FACTORY.randomValue()).add(min.realPart);
			Rational i = (Rational) max.imaginaryPart.subtract(
					min.imaginaryPart).multiply(Rational.FACTORY.randomValue())
					.add(min.imaginaryPart);
			return new Complex(r, i);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jlinalg.IRingElementFactory#randomValue()
		 */
		@Override
		public Complex randomValue()
		{
			return new Complex(random.nextDouble(), random.nextDouble());
		}
	}

	/**
	 * @throws InvalidOperationException
	 * @see org.jlinalg.FieldElement#abs()
	 */
	@Override
	public FieldElement abs()
	{
		throw new InvalidOperationException(
				"Abs() requires the square root, which in general leaves the domain of the rational numbers.");
	}

	/**
	 * The field norm from the complex numbers to the real numbers sends x + iy
	 * to x^2 + y^2. In this case a Rational is used as the return value.
	 */
	@Override
	public Rational norm()
	{
		Rational a = getReal();
		Rational b = getImaginary();
		return b.multiply(b).add(a.multiply(a));
	}

}
