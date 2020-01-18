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

import java.util.function.Function;

import org.jlinalg.IReducible;
import org.jlinalg.IRingElement;

/**
 * Performs reductions (sum, max, min) on Matrix and Vector objects
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
public abstract class Reduction<RE extends IRingElement<RE>>
		implements
		Function<IReducible<RE>, RE>
{

}
