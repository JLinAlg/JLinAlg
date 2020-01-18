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

import java.util.function.BinaryOperator;

import org.jlinalg.IRingElement;

/**
 * The <i>DyadicOperator</i> interface supports application of arbitrary dyadic
 * (two-argument) functions to the elements of two Matrix or Vector objects, via
 * the Matrix or Vector's <tt>apply</tt> methods.
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the elements returned from this operator.
 */

public interface DyadicOperator<RE extends IRingElement<RE>>
		extends
		BinaryOperator<RE>
{

}
