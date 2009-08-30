/**
 * 
 */
package org.jlinalg.optimise;

import java.util.Random;

import org.jlinalg.Complex;
import org.jlinalg.IRingElement;
import org.jlinalg.Rational;

/**
 * @author Georg Thimm
 */
public class ComplexFunction
		implements Target<Rational>
{
	/**
	 * the number of variables
	 */
	final static int dim = 3;

	Random random = new Random();

	/**
	 * the actual coefficients of the function
	 */
	final Complex[] vector = new Complex[dim];

	/**
	 * the maximal values for the parameters to be used. These are Rationals,
	 * and twice as many as complex numbers.
	 */
	final Rational[] max = new Rational[dim * 2];

	/**
	 * No minimal values for the parameters to be used.
	 */
	final Rational[] min = null;
	/**
	 * The starting point for the optimisation
	 */
	final static Complex[] start =
	{
			Complex.FACTORY.get(.111, .24), Complex.FACTORY.get(-.37, .41),
			Complex.FACTORY.get(-.20, -.01)
	};

	/**
	 * Create the target: set up the random initial parameters, min and max
	 * values, as well as the coefficients for the polynomial.
	 */
	public ComplexFunction()
	{
		// a random vector
		for (int i = 0; i < vector.length; i++) {
			vector[i] = start[i];
			func[i] = Complex.FACTORY.get(i + 1, i + 1);
		}
		Rational a = Rational.FACTORY.get(10);
		for (int i = 0; i < max.length; i++) {
			max[i] = a;
		}
	}

	/**
	 * @return the best values for the parameters. This is usually not known and
	 *         is here used for validation.
	 */
	public Complex[] best()
	{
		return func;
	}

	/**
	 * @return the true coefficients values for the function
	 */
	public Complex[] is()
	{
		return vector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.optimise.Target#getParameters()
	 */
	@Override
	public IRingElement[] getParameters()
	{
		Rational[] param = new Rational[dim * 2];
		for (int i = 0; i < dim * 2; i += 2) {
			param[i] = vector[i / 2].getReal();
			param[i + 1] = vector[i / 2].getImaginary();
		}
		return param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.optimise.Target#getResidual()
	 */
	@Override
	public Rational getResidual()
	{
		return function(vector);
	}

	/**
	 * The function to be minimised
	 */
	Rational function(IRingElement[] v)
	{
		Rational sum = Rational.FACTORY.zero();
		for (int i = 0; i < v.length; i++) {
			Complex r = (Complex) v[i].subtract(func[i]);
			r = r.multiply(r.conjugate());
			sum = sum.add(r.getReal());
		}
		return sum;
	}

	/**
	 * The parameters of the target function. The residual is zero if
	 * {@link #vector} is equal to this vector.
	 */
	final Complex[] func = new Complex[dim];

	/**
	 * @see org.jlinalg.optimise.Target#maxParameterValues()
	 */
	@Override
	public IRingElement[] maxParameterValues()
	{
		return max;
	}

	/**
	 * For the complex domain, a minimum value makes little sense due to the
	 * definition of the comparison by their length.
	 * 
	 * @see org.jlinalg.optimise.Target#minParameterValues()
	 */
	@Override
	public IRingElement[] minParameterValues()
	{
		return null;
	}

	/**
	 * @see org.jlinalg.optimise.Target#setParameters(org.jlinalg.IRingElement[])
	 */
	@Override
	public void setParameters(IRingElement[] values)
	{
		for (int i = 0; i < values.length; i += 2) {
			vector[i / 2] = Complex.FACTORY.get(values[i], values[i + 1]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(x-" + func[0] + ")^2+(y-" + func[1] + ")^2+(c-" + func[2]
				+ ")^2";
	}

	/**
	 * change a value in vector.
	 * 
	 * @see org.jlinalg.optimise.Target#setParameter(int,
	 *      org.jlinalg.IRingElement)
	 */
	@Override
	public boolean setParameter(int index, IRingElement value)
	{
		if ((min != null && min[index] != null && value.lt(min[index]))
				|| (max != null && max[index] != null && value.gt(max[index])))
			return false;
		Complex c = vector[index / 2];
		if (index % 2 == 0)
			vector[index / 2] = Complex.FACTORY.get(c.getReal(), value);
		else
			vector[index / 2] = Complex.FACTORY.get(value, c.getImaginary());
		for (int i = 0; i < dim; i++) {
			Complex c1 = vector[i];
			// System.out.print(c1.getReal().doubleValue() + "+"
			// + c1.getImaginary().doubleValue() + "i  ");
		}
		// System.out.println("   res=" + getResidual().doubleValue());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.optimise.Target#getParameter(int)
	 */
	@Override
	public IRingElement getParameter(int index)
	{
		if (index % 2 == 0) return vector[index / 2].getReal();
		return vector[index / 2].getImaginary();
	}

}
