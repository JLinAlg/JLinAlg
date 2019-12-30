package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * The operator for adding RingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
public class AddOperator<RE extends IRingElement<RE>>
		implements
		DyadicOperator<RE>
{
	@Override
	public RE apply(RE x, RE y)
	{
		return x.add(y);
	}

}
