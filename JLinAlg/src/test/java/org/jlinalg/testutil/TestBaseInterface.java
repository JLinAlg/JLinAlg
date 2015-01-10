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
package org.jlinalg.testutil;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.LinAlgFactory;

/**
 * This is used to enforce that tests use a generic method to obtain a factory
 * for the base type to be tested.
 * 
 * @author Georg Thimm
 */
public interface TestBaseInterface<RE extends IRingElement<RE>>
{
	/**
	 * This method provides access to the factory of the base type. This is used
	 * to make tests generic for all types.
	 * 
	 * @return the factory of a {@link IRingElement} upon which the factory's
	 *         methods are to be tested.
	 **/
	IRingElementFactory<RE> getFactory();

	/**
	 * @return the {@link LinAlgFactory} created on the base of
	 *         {@link #getFactory()}
	 */
	LinAlgFactory<RE> getLinAlgFactory();
}
