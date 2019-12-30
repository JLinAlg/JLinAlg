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
public class AbsOperator<RE extends IRingElement<RE>>
		implements
		MonadicOperator<RE>
{
	@Override
	public RE apply(RE x)
	{
		return x.abs();
	}

}
