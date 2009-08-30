package org.jlinalg;

/**
 * The division operator for IRingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
@SuppressWarnings("deprecation")
class DivideOperator<RE extends IRingElement>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<IRingElement> INSTANCE = new DivideOperator<IRingElement>();

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
	public DivideOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x, ARG y)
	{
		return (RE) x.multiply(y.invert());
	}

}
