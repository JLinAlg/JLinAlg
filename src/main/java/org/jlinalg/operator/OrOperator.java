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
import org.jlinalg.IRingElementFactory;

/**
 * logical OR of two FieldElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
public class OrOperator<RE extends IRingElement<RE>>
		implements
		DyadicOperator<RE>
{

	@Override
	public RE apply(RE x, RE y)
	{
		IRingElementFactory<RE> factory = x.getFactory();
		return (x.isZero() && y.isZero()) ? factory.zero() : factory.one();
	}
}
