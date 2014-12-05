package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * The division operator for IRingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
public class DivideOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final DyadicOperator<? extends IRingElement<?>> INSTANCE = new DivideOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static DyadicOperator<? extends IRingElement<?>> getInstance()
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
	public DivideOperator()
	{
	}

	@Override
	public RE apply(RE x, RE y)
	{
		return x.multiply(y.invert());
	}

}
