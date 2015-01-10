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
package org.jlinalg.demo;

import java.util.Random;

import org.jlinalg.IRingElement;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.optimise.Target;

/**
 * A target for an optimisation: a 2-degree polynomial in the 3-d Space.
 * 
 * @author Georg Thimm
 */
public class Function
		implements Target<DoubleWrapper>
{
	/**
	 * the number of variables
	 */
	final int dim = 3;

	Random random = new Random();

	/**
	 * the parameters of the function
	 */
	final DoubleWrapper[] vector = new DoubleWrapper[dim];

	/**
	 * the minimal parameters to be used.
	 */
	final DoubleWrapper[] min = new DoubleWrapper[dim];

	/**
	 * the maximal values for the parameters to be used.
	 */
	final DoubleWrapper[] max = new DoubleWrapper[dim];

	/**
	 * Create the target: set up the random initial parameters, {@link #min} and
	 * {@link #max}, as well as the coefficients for the polynomial.
	 */
	public Function()
	{
		DoubleWrapper a = DoubleWrapper.FACTORY.get(-10);
		DoubleWrapper b = DoubleWrapper.FACTORY.get(10);
		// a random vector
		for (int i = 0; i < vector.length; i++) {
			vector[i] = DoubleWrapper.FACTORY.randomValue(a, b);
			min[i] = a;
			max[i] = b;
			func[i] = DoubleWrapper.FACTORY.get(i + 1);
		}

	}

	/**
	 * @return the best values for the parameters. This is usually not known and
	 *         is here used for validation.
	 */
	public DoubleWrapper[] best()
	{
		return func;
	}

	/**
	 * @see org.jlinalg.optimise.Target#getParameters()
	 */
	@Override
	public DoubleWrapper[] getParameters()
	{
		return vector;
	}

	/**
	 * @see org.jlinalg.optimise.Target#getResidual()
	 */
	@Override
	public DoubleWrapper getResidual()
	{
		return function(vector);
	}

	/**
	 * The function to be minimised
	 */
	DoubleWrapper function(DoubleWrapper[] v)
	{
		DoubleWrapper sum = DoubleWrapper.FACTORY.zero();
		for (int i = 0; i < v.length; i++) {
			DoubleWrapper r = v[i].subtract(func[i]);
			r = r.multiply(r);
			sum = sum.add(r);
		}
		return sum;
	}

	/**
	 * The parameters of the target function. The residual is zero if
	 * {@link #vector} is equal to this vector.
	 */
	final DoubleWrapper[] func = new DoubleWrapper[dim];

	/**
	 * @see org.jlinalg.optimise.Target#maxParameterValues()
	 */
	@Override
	public DoubleWrapper[] maxParameterValues()
	{
		return max;
	}

	/**
	 * @see org.jlinalg.optimise.Target#minParameterValues()
	 */
	@Override
	public DoubleWrapper[] minParameterValues()
	{
		return min;
	}

	/**
	 * @see org.jlinalg.optimise.Target#setParameters(org.jlinalg.IRingElement[])
	 */
	@Override
	public void setParameters(IRingElement<?>[] values)
	{
		System.arraycopy(values, 0, vector, 0, vector.length);
	}

	/**
	 * @return a textual representation of the function with {@link #vector}
	 *         filled in the parameters.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(x-" + func[0] + ")^2+(y-" + func[1] + ")^2+(c-" + func[2]
				+ ")^2";
	}

	/**
	 * assume the parameters are in range
	 * 
	 * @return always {@code true}
	 * @see org.jlinalg.optimise.Target#setParameter(int,
	 *      org.jlinalg.IRingElement)
	 */
	@Override
	public boolean setParameter(int index, IRingElement<?> value)
	{
		func[index] = (DoubleWrapper) value;
		return true;
	}

	/**
	 * the {@code index}'s optimised parameter
	 * 
	 * @see org.jlinalg.optimise.Target#getParameter(int)
	 */
	@Override
	public DoubleWrapper getParameter(int index)
	{
		return func[index];
	}

}
