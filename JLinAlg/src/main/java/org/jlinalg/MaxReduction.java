package org.jlinalg;

/**
 * computes maximum over elements of Vector or Matrix
 * 
 * @author Simon Levy, Andreas Keilhauer
 * @param <RE>
 *            the type of the elements in the vectors to be reduced.
 */
class MaxReduction<RE extends IRingElement>
		extends Reduction<RE>
{

	@SuppressWarnings("unchecked")
	@Override
	public void init(RE firstValue)
	{
		reducedValue = (RE) firstValue.getFactory().zero();
	}

	@Override
	public void track(RE currValue)
	{
		if (currValue.gt(reducedValue)) {
			reducedValue = currValue;
		}
	}
}
