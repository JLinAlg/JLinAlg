package org.jlinalg;

/**
 * logical negation of FieldElement
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects on which is operated
 */
@SuppressWarnings("deprecation")
class NotOperator<RE extends IRingElement>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<? extends IRingElement> INSTANCE = new NotOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<? extends IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	@Deprecated
	public NotOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x)
	{
		return (RE) (x.isZero() ? x.getFactory().one() : x.getFactory().zero());
	}
}
