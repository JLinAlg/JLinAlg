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
package org.jlinalg.polynomial;

import java.io.Serializable;

import org.jlinalg.IRingElement;

/**
 * @author Andreas Keilhauer, Georg Thimm; Created on 15.06.2008 by Andreas
 *         Keilhauer
 * @param <BASE>
 *            the type of the elements in the polynomial Created on 15.06.2008
 *            by Andreas Keilhauer
 */
public class PolynomialLongDivisionResult<BASE extends IRingElement<BASE>>
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the result of a division with remainder
	 */
	private Polynomial<BASE> quotient = null;

	/**
	 * the remainder of a division.
	 */
	private Polynomial<BASE> remainder = null;

	/**
	 * create a result (but do not calculate it)
	 * 
	 * @param quotient
	 * @param remainder
	 */
	public PolynomialLongDivisionResult(Polynomial<BASE> quotient,
			Polynomial<BASE> remainder)
	{
		this.quotient = quotient;
		this.remainder = remainder;
	}

	/**
	 * @return the quotient
	 */
	public Polynomial<BASE> getQuotient()
	{
		return quotient;
	}

	/**
	 * @param quotient
	 *            the quotient to set
	 */
	public void setQuotient(Polynomial<BASE> quotient)
	{
		this.quotient = quotient;
	}

	/**
	 * @return the remainder
	 */
	public Polynomial<BASE> getRemainder()
	{
		return remainder;
	}

	/**
	 * @param remainder
	 *            the remainder to set
	 */
	public void setRemainder(Polynomial<BASE> remainder)
	{
		this.remainder = remainder;
	}

	@Override
	public String toString()
	{
		return this.quotient
				+ ((!remainder.isZero()) ? " rem " + remainder : "");
	}

}
