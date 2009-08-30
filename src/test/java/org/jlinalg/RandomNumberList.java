/**
 * 
 */
package org.jlinalg;

import java.util.ArrayList;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;

/**
 * A list of random numbers for the convenience of the tests.
 * 
 * @author Georg Thimm
 * @param <E>
 *            The type of which random elements are created.
 */
@SuppressWarnings("serial")
public class RandomNumberList<E extends IRingElement>
		extends ArrayList<E>
{
	/**
	 * @param fac
	 *            the factory for elements of type E
	 * @param n
	 *            the number of elements.
	 */
	public RandomNumberList(IRingElementFactory<E> fac, int n)
	{
		for (int i = 0; i < n; i++) {
			this.add(fac.randomValue());
		}
	}

	/**
	 * @param factory
	 * @param n
	 *            the number of random values
	 * @param min
	 *            the minimal value
	 * @param max
	 *            the maximal value
	 */
	public RandomNumberList(IRingElementFactory<E> factory, int n, E min, E max)
	{
		for (int i = 0; i < n; i++) {
			this.add(factory.randomValue(min, max));
		}
	}
}
