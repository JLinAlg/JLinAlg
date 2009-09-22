package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * An operator for the absolute value computation for IRingElements. This
 * operator relies on an implementation of {@link IRingElement#abs()}.
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a type implementing on {@link IRingElement}.
 */
@SuppressWarnings( {
		"deprecation", "unchecked"
})
public class AbsOperator<RE extends IRingElement<RE>>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<? extends IRingElement<?>> INSTANCE = new AbsOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<? extends IRingElement<?>> getInstance()
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
	public AbsOperator()
	{
	}

	public RE apply(RE x)
	{
		return x.abs();
	}

}
