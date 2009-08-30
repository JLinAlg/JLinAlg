package org.jlinalg;

/**
 * The operator for adding RingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a specialisation of {@link IRingElement}
 */
@SuppressWarnings("deprecation")
class AddOperator<RE extends IRingElement>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final AddOperator<IRingElement> INSTANCE = new AddOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static AddOperator<IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	@Deprecated
	public AddOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x, ARG y)
	{
		return (RE) x.add(y);
	}

}
