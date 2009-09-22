/**
 * 
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
