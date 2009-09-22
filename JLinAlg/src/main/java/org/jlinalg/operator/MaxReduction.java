package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * computes maximum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 * @param <RE>
 *            the type of the elements in the vectors to be reduced.
 */
public class MaxReduction<RE extends IRingElement<RE>>
		extends Reduction<RE>
{

	@Override
	public void init(RE firstValue)
	{
		reducedValue = firstValue.getFactory().zero();
	}

	@Override
	public void track(RE currValue)
	{
		if (currValue.gt(reducedValue)) {
			reducedValue = currValue;
		}
	}
}
