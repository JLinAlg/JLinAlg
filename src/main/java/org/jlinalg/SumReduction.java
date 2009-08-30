package org.jlinalg;

/**
 * computes sum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
class SumReduction<RE extends IRingElement>
		extends Reduction<RE>
{

	@SuppressWarnings("unchecked")
	@Override
	public void track(RE currValue)
	{
		reducedValue = (RE) reducedValue.add(currValue);
	}
}
