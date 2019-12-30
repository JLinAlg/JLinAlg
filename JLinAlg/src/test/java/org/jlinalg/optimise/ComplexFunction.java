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
package org.jlinalg.optimise;

import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.complex.Complex;
import org.jlinalg.rational.Rational;

/**
 * @author Georg Thimm
 */
public class ComplexFunction
		implements
		Target<Rational>
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
	final static Complex[] start = {
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

	@Override
	public Rational[] getParameters()
	{
		Rational[] param = new Rational[dim * 2];
		for (int i = 0; i < dim * 2; i += 2) {
			param[i] = vector[i / 2].getReal();
			param[i + 1] = vector[i / 2].getImaginary();
		}
		return param;
	}

	@Override
	public Rational getResidual()
	{
		return function(vector);
	}

	/**
	 * The function to be minimised
	 */
	Rational function(Complex[] v)
	{
		Rational sum = Rational.FACTORY.zero();
		for (int i = 0; i < v.length; i++) {
			Complex r = v[i].subtract(func[i]);
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
	public Rational[] maxParameterValues()
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
	public Rational[] minParameterValues()
	{
		return null;
	}

	/**
	 * @see org.jlinalg.optimise.Target#setParameters(org.jlinalg.IRingElement[])
	 */
	@Override
	public void setParameters(IRingElement<?>[] values)
	{
		for (int i = 0; i < values.length; i += 2) {
			vector[i / 2] = Complex.FACTORY.get(values[i], values[i + 1]);
		}
	}

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
	public boolean setParameter(int index, IRingElement<Rational> value)
	{
		if ((min != null && min[index] != null && value.lt(min[index]))
				|| (max != null && max[index] != null && value.gt(max[index])))
		{
			return false;
		}
		Complex c = vector[index / 2];
		if (index % 2 == 0) {
			vector[index / 2] = Complex.FACTORY.get(c.getReal(), value);
		}
		else {
			vector[index / 2] = Complex.FACTORY.get(value, c.getImaginary());
		}
		return true;
	}

	@Override
	public Rational getParameter(int index)
	{
		if (index % 2 == 0) return vector[index / 2].getReal();
		return vector[index / 2].getImaginary();
	}

}
