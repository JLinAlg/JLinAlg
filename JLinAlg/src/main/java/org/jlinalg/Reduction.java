package org.jlinalg;

/**
 * Performs reductions (sum, max, min) on Matrix and Vector objects
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
abstract class Reduction<RE extends IRingElement>
{

	public RE reducedValue;

	public void init(RE firstValue)
	{
		reducedValue = firstValue;
	}

	public abstract void track(RE currValue);
}
