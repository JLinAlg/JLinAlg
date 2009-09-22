package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * difference of two FieldElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
@SuppressWarnings("deprecation")
public class SubtractOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static DyadicOperator<? extends IRingElement<?>> INSTANCE = new SubtractOperator();

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
	public SubtractOperator()
	{
	}

	public RE apply(RE x, RE y)
	{
		return x.subtract(y);
	}

}
