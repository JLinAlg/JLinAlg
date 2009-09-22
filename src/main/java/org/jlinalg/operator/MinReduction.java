package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * computes minimum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
public class MinReduction<RE extends IRingElement<RE>>
		extends Reduction<RE>
{

	@Override
	public void track(RE currValue)
	{
		if (currValue.lt(reducedValue)) {
			reducedValue = currValue;
		}
	}
}
