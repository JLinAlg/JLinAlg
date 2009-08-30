package org.jlinalg;

/**
 * product of two RingElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
@SuppressWarnings("deprecation")
class MultiplyOperator<RE extends IRingElement>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<IRingElement> INSTANCE = new MultiplyOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static DyadicOperator<IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	@Deprecated
	public MultiplyOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x, ARG y)
	{
		return (RE) x.multiply(y);
	}

}
