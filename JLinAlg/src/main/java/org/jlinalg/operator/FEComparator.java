package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * abstract class to compare two <code>RE</code>'s.
 * 
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
public abstract class FEComparator<RE extends IRingElement<RE>>
{

	/**
	 * @param x
	 *            an instance of {@link IRingElement}
	 * @param y
	 *            an instance of {@link IRingElement}
	 * @return true if a and b have the same value.
	 */
	public abstract boolean compare(RE x, RE y);
}
