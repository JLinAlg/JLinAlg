package org.jlinalg;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an affine linear subspace.
 * 
 * @author Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the domain for the space.
 */

public class AffineLinearSubspace<RE extends IRingElement<RE>>
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * the inhomogenous part of the space
	 */
	protected Vector<RE> inhomogenousPart = null;

	protected Vector<RE>[] generatingSystem = null;

	protected int dimension = -1;

	/**
	 * Flag: is this space normalised
	 */
	protected boolean normalized = false;

	/**
	 * This creates an affine linear subspace by taking an inhomogeneous part
	 * and a generating System of Vectors. The subspace will be inhomogenousPart
	 * + < generatingSystem >.
	 * 
	 * @param inhomogenousPart
	 * @param generatingSystem
	 * @throws InvalidOperationException
	 */
	@SuppressWarnings("unchecked")
	public AffineLinearSubspace(Vector<RE> inhomogenousPart,
			Vector<RE>[] generatingSystem) throws InvalidOperationException
	{
		if (generatingSystem != null /* && generatingSystem.length > 0 */) {
			this.generatingSystem = generatingSystem;
		}
		else {
			// this should read: this.generatingSystem = new Vector<RE>[0];
			// (but java does not like this).
			this.generatingSystem = new Vector[0];
		}
		assert this.generatingSystem.length > 0;
		IRingElementFactory<RE> factory;
		if (inhomogenousPart != null && inhomogenousPart.length() > 0)
			factory = inhomogenousPart.getEntry(0).getFactory();
		else if (this.generatingSystem.length > 0
				&& this.generatingSystem[0].entries.length > 0)
			factory = this.generatingSystem[0].entries[0].getFactory();
		else
			throw new InvalidOperationException(
					"both, the inhomogenous part and the generating system are empty.");
		if (inhomogenousPart == null) {
			if (this.generatingSystem.length > 0) {
				Vector<RE> tmp = this.generatingSystem[0];
				Vector<RE> zeroVector = new Vector<RE>(tmp.length(), factory);
				for (int i = 1; i <= zeroVector.length(); i++) {
					zeroVector.set(i, factory.zero());
				}
				this.inhomogenousPart = zeroVector;
			}
			else {
				this.inhomogenousPart = null;
			}
		}
		else {
			this.inhomogenousPart = inhomogenousPart;
		}
	}

	/**
	 * This creates an affine linear subspace by taking an inhomogeneous part
	 * and a generating System of Vectors. The subspace will be inhomogenousPart
	 * + < generatingSystem >. The normalised flag, which is usually set after
	 * normalise is executed, is also set, but it won't be checked.
	 * 
	 * @param inhomogenousPart
	 * @param generatingSystem
	 * @param normalized
	 * @throws InvalidOperationException
	 */
	@SuppressWarnings("unchecked")
	public AffineLinearSubspace(Vector<RE> inhomogenousPart,
			Vector<RE>[] generatingSystem, boolean normalized)
			throws InvalidOperationException
	{
		if (generatingSystem != null && generatingSystem.length > 0) {
			this.normalized = normalized;
			this.generatingSystem = generatingSystem;
			if (normalized) {
				if (this.generatingSystem.length == 1
						&& this.generatingSystem[0].isZero())
				{
					this.dimension = 0;
				}
				else {
					this.dimension = generatingSystem.length;
				}
			}
		}
		else {
			this.normalized = true;
			this.generatingSystem = new Vector[0];
			this.dimension = 0;
		}

		this.inhomogenousPart = inhomogenousPart;
	}

	/**
	 * Gets the dimension of the affine linear subspace.
	 * 
	 * @return dimension (number of independent Vectors of the generating
	 *         system).
	 */
	public int getDimension()
	{
		if (this.normalized) {
			return this.dimension;
		}
		return new Matrix<RE>(generatingSystem).rank();
	}

	/**
	 * Gets the inhomogenous part of this affine linear vector space.
	 * 
	 * @return inhomogenous part
	 */
	public Vector<RE> getInhomogenousPart()
	{
		return inhomogenousPart;
	}

	/**
	 * Gets the generating system of this affine linear vector space.
	 * 
	 * @return generating system
	 */
	public Vector<RE>[] getGeneratingSystem()
	{
		return generatingSystem;
	}

	/**
	 * Returns a String representation of this affine linear subspace
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		String tmp = this.inhomogenousPart + " + < { ";
		for (int i = 0; i < this.generatingSystem.length - 1; i++) {
			tmp += this.generatingSystem[i].toString() + ", ";
		}
		if (this.generatingSystem.length > 0) {
			tmp += this.generatingSystem[this.generatingSystem.length - 1];
		}
		return tmp + " } >";
	}

	/**
	 * This method calculates the normalised version of this
	 * AffineLinearSubspace. I.e. it eliminates all dependent vectors from the
	 * generating System. In a 1-dimensional AffineLinearSubspace it is also
	 * possible, that the inhomogeneous part is dependent to the generating
	 * vector and therefore the inhomogeneous can be dropped.
	 * 
	 * @return the normalised version of this
	 */
	@SuppressWarnings("unchecked")
	public AffineLinearSubspace<RE> normalize()
	{
		if (this.generatingSystem.length > 0) {
			Matrix<RE> normalized = new Matrix<RE>(generatingSystem)
					.gausselim();
			List<Vector<RE>> generatingVectors = new LinkedList<Vector<RE>>();
			int i = 1;
			while (i <= normalized.getRows() && !normalized.isZeroRow(i)) {
				generatingVectors.add(normalized.getRow(i));
				i++;
			}

			if (generatingVectors.isEmpty()) {
				LinAlgFactory<RE> f = new LinAlgFactory<RE>(normalized
						.get(1, 1).getFactory());
				generatingVectors.add(f.zeros(normalized.getCols()));
			}
			Vector<RE>[] newGeneratingSystem = generatingVectors
					.toArray(new Vector[generatingVectors.size()]);
			if (newGeneratingSystem.length == 1
					&& this.inhomogenousPart != null)
			{
				Matrix<RE> testInhomogenousPart = new Matrix<RE>(new Vector[] {
						newGeneratingSystem[0], this.inhomogenousPart
				});
				if (testInhomogenousPart.rank() != 2) {
					return new LinearSubspace<RE>(newGeneratingSystem);
				}
			}
			if (this instanceof LinearSubspace<?>) {
				return new LinearSubspace<RE>(newGeneratingSystem, true);
			}
			return new AffineLinearSubspace<RE>(this.inhomogenousPart,
					newGeneratingSystem, true);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o)
	{
		AffineLinearSubspace<RE> other = (AffineLinearSubspace<RE>) o;
		if ((this.inhomogenousPart == null || this.inhomogenousPart.isZero())) {
			if (other.getInhomogenousPart() == null
					|| other.getInhomogenousPart().isZero())
			{
				return this.generatingSystem
						.equals(other.getGeneratingSystem());
			}
			return false;
		}
		if (inhomogenousPart.equals(other.getInhomogenousPart())) {
			if (this.generatingSystem.length == this.getGeneratingSystem().length)
			{
				for (int i = 0; i < this.generatingSystem.length; i++) {
					if (!this.generatingSystem[i].equals(this
							.getGeneratingSystem()[i]))
					{
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * @return the value of the normalized flag
	 */
	public boolean isNormalized()
	{
		return this.normalized;
	}

}
