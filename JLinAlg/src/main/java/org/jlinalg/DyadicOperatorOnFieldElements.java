package org.jlinalg;

/**
 * The <i>DyadicOperator</i> interface supports application of arbitrary dyadic
 * (two-argument) functions to the elements of two Matrix or Vector objects, via
 * the Matrix or Vector's <tt>apply</tt> methods.
 * 
 * @author Andreas Keilhauer
 */

public interface DyadicOperatorOnFieldElements
{

	/**
	 * Applies the function to two elements.
	 * 
	 * @param x
	 *            the value of one element
	 * @param y
	 *            the value of the other element
	 * @return the result of applying the function to <tt>x</tt> and <tt>y</tt>
	 */
	public FieldElement apply(FieldElement x, FieldElement y);

}
