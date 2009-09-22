package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * Performs reductions (sum, max, min) on Matrix and Vector objects
 * 
 * @author Simon Levy, Andreas Keilhauer
 */
public abstract class Reduction<RE extends IRingElement<RE>>
{

	public RE reducedValue;

	public void init(RE firstValue)
	{
		reducedValue = firstValue;
	}

	public abstract void track(RE currValue);
}
