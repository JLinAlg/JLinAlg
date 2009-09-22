package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * The operator for adding RingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
@SuppressWarnings( {
		"deprecation", "unchecked"
})
public class AddOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final AddOperator<? extends IRingElement<?>> INSTANCE = new AddOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static AddOperator<? extends IRingElement<?>> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 * 
	 * @deprecated use {@link #getInstance()}
	 */
	@Deprecated
	public AddOperator()
	{
	}

	public RE apply(RE x, RE y)
	{
		return x.add(y);
	}

}
