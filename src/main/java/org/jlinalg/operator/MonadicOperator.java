package org.jlinalg.operator;

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
{

	/**
	 * Applies the function to an element.
	 * 
	 * @param x
	 *            the value of the element
	 * @return the result of applying the function to <tt>x</tt>
	 */
	public RE apply(RE x);

}
