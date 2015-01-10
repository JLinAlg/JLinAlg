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

import org.jlinalg.IRingElement;

/**
 * An operator calculating the squares of elements in a
 * {@link org.jlinalg.Vector} or {@link org.jlinalg.Matrix}.
 * 
 * @author Georg Thimm 2009
 * @param <RE>
 *            see {@link MonadicOperator}
 */
@SuppressWarnings({
	"unchecked"
})
public class SquareOperator<RE extends IRingElement<RE>>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<? extends IRingElement<?>> INSTANCE = new SquareOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<? extends IRingElement<?>> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 * 
	 * @deprecated use {@link #getInstance()}
	 */
	@Deprecated
	private SquareOperator()
	{
	}

	/**
	 * @param x
	 *            the element to be squared.
	 * @return the sqare of <code>x</code>.
	 */
	@Override
	public RE apply(RE x)
	{
		return x.multiply(x);
	}

}
