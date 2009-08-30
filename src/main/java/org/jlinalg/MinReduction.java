package org.jlinalg;

/**
 * computes minimum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
class MinReduction<RE extends IRingElement>
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
