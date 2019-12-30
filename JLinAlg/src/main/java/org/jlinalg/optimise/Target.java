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

import org.jlinalg.IRingElement;

/**
 * This interface is to be used in combination with {@link Optimiser}. It is the
 * contract to be fulfilled by a class that is to be optimised. This is the
 * interface the optimiser will use. The target has to provide access to a set
 * of minimum and maximum values for set of parameters. The parameters can be
 * mixed, as long as <code>{@link #getParameters()}[i]</code>,
 * <code>{@link #maxParameterValues}[i]</code>, and
 * <code>{@link #minParameterValues}[i]</code> are of the same type.
 * 
 * @author Georg Thimm 2009
 * @param <RE>
 *            The type of the residual.
 */
public interface Target<RE extends IRingElement<RE>>
{
	/**
	 * Give access to the residual for the parameters as they are stored in the
	 * target object.
	 * 
	 * @return the residual for the state of the target object or
	 *         <code>null</code> if the parameters are not a valid state for the
	 *         target.
	 */
	public RE getResidual();

	/**
	 * @return the parameters as they are stored in the target.
	 */
	public IRingElement<RE>[] getParameters();

	/**
	 * Change the parameters
	 * 
	 * @param values
	 *            the new value
	 */
	public void setParameters(IRingElement<?>[] values);

	/**
	 * Change one parameter
	 * 
	 * @param index
	 *            the position of the new parameter.
	 * @param value
	 *            the new value of a parameter
	 * @return true if value is in the range specified by
	 *         <code>{@link #maxParameterValues()}[index]</code> and
	 *         <code>{@link #minParameterValues()}[index]</code>
	 */
	public boolean setParameter(int index, IRingElement<RE> value);

	/**
	 * @return the minimum permissible values for the parameters. This may be
	 *         <code>null</code> (no lower limit is then used), or individual
	 *         elements may be <code>null</code> (and then for this element no
	 *         lower limit exists).
	 */
	public RE[] minParameterValues();

	/**
	 * @return the maximum permissible values for the parameters. This may be
	 *         <code>null</code> (no upper limit is then used), or individual
	 *         elements may be <code>null</code> (and then for this element no
	 *         upper limit exists).
	 */
	public RE[] maxParameterValues();

	/**
	 * @param index
	 *            the number of the parameter
	 * @return a parameter
	 */
	public RE getParameter(int index);
}
