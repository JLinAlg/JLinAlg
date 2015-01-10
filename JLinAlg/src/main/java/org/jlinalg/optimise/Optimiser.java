/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jlinalg.optimise;

import org.jlinalg.IRingElement;

/**
 * The contract for an optimiser. The target (that is the class that is
 * optimised) has to provide the methods as specified in {@link Target}. The
 * following steps are necessary:
 * <UL>
 * <LI>The target function (class) has to implement {@link Target}</LI>
 * <LI>The class implementing {@link Optimiser}) has to be instantiated and
 * referenced to the target.</li>
 * <LI>Call method {@link Optimiser#optimise()} to start the optimisation.</Li>
 * <li>Wait for the created thread to terminate (or interrupt it).</li>
 * <li>Inspect the target and/or the optimiser.</li>
 * </UL>
 * 
 * @author Georg Thimm
 * @param <RE>
 *            the type of the residual
 */
public interface Optimiser<RE extends IRingElement<RE>>
{
	/**
	 * Set the {@link Target} for the optimiser. Does not start the
	 * optimisation.
	 * 
	 * @param target
	 *            The target for the optimisation
	 */
	public void setTarget(Target<RE> target);

	/**
	 * run the optimiser in the background. This is done in a second thread and
	 * this function return the id of the thread doing the execution.
	 * 
	 * @return the thread that does the optimisation.
	 */
	public Thread optimise();

	/**
	 * @return The state of the optimisation.
	 */
	public State getState();

	/**
	 * the states of the optimiser
	 */
	public enum State {
		UNINITIALISED, INITIALISED, RUNNING, FINISHED_SUCCESSFULL, FINISHED_UNSUCESSFUL, ERROR
	}

	/**
	 * @return The residual for the current state of the target. This is
	 *         intended for an observation of the progress of the optimisation
	 *         only.
	 */
	public RE getResidual();

	/**
	 * @return the maximal number of optimisation steps taken by the optimiser
	 */
	public int getMaxOptimisationSteps();

	/**
	 * @param maxOptimisationSteps
	 *            the maxOptimisationSteps to set
	 */
	public void setMaxOptimisationSteps(int maxOptimisationSteps);

	/**
	 * @return the number of steps taken during the last call of
	 *         {@link #optimise()}. If optimise did not yet terminate, this is
	 *         the current number of steps. -1 if no optimisation was executed.
	 */
	public int getOptimisationStepsTaken();
}
