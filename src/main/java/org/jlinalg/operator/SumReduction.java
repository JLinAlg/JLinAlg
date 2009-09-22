package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * computes sum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
public class SumReduction<RE extends IRingElement<RE>>
		extends Reduction<RE>
{

	@Override
	public void track(RE currValue)
	{
		reducedValue = reducedValue.add(currValue);
	}
}
