package org.jlinalg;

/**
 * abstract class to compare two <code>RE</code>'s.
 * 
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
abstract class FEComparator<RE extends IRingElement>
{

	/**
	 * @param a
	 *            an instance of {@link IRingElement}
	 * @param b
	 *            an instance of {@link IRingElement}
	 * @return true if a and b have the same value.
	 */
	public abstract boolean compare(RE a, RE b);
}
