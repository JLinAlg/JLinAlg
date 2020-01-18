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
package org.jlinalg.operator;

import java.util.function.UnaryOperator;

import org.jlinalg.IRingElement;

/**
 * The <i>MonadicOperator</i> interface supports application of arbitrary
 * monadic (one-argument) functions to the elements of a Matrix or Vector, via
 * the Matrix or Vector's <tt>apply</tt> methods.
 * 
 * @author Simon Levy
 * @param <RE>
 *            The return type of the operator
 */

public interface MonadicOperator<RE extends IRingElement<RE>>
		extends
		UnaryOperator<RE>
{
}
