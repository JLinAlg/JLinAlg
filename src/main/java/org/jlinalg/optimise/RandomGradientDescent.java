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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.jlinalg.IRingElement;

/**
 * An implementation of an optimiser: Among the parameters, one is randomly
 * choosen and then this dimension is searched along the gradient with a
 * decreasing step size if the residual does not decreases. if the residual
 * increases, the step size is increased somewhat. All dimensions of the
 * parameter vector are searched in random order, but all are searched as
 * often.<br>
 * The minima and maxima for the search domain are respected. <br>
 * The search is perpetuated until no decrease in the residual is observed or
 * the maximal number of optimisation steps is reached (see
 * {@link #maxOptimisationSteps}).
 * <P>
 * If syncronisation (i.e. during access to the residal) is necessary, this
 * should be done on the target.
 * </P>
 * 
 * @author Georg Thimm
 * @param <RESIDUAL>
 *            the type of the residual.
 */
public class RandomGradientDescent<RESIDUAL extends IRingElement<RESIDUAL>>
		implements
		Optimiser<RESIDUAL>
{
	/**
	 * the state of the optimiser
	 */
	State state = State.UNINITIALISED;

	/**
	 * Initialise the optimiser. No optimisation is done; this is started by
	 * {@link #optimise()}.
	 * 
	 * @param target
	 *            the target to be optimised.
	 */
	public RandomGradientDescent(Target<RESIDUAL> target)
	{
		this.target = target;
	}

	/**
	 * the maximal number of optimisation steps to be taken.
	 */
	int maxOptimisationSteps = 1000;

	/**
	 * The number of optimisation steps taken during the last optimisation.
	 */
	public int steps = -1;

	/**
	 * @return the maxOptimisationSteps
	 */
	@Override
	public int getMaxOptimisationSteps()
	{
		return maxOptimisationSteps;
	}

	/**
	 * @param maxOptimisationSteps
	 *            the maxOptimisationSteps to set
	 */
	@Override
	public void setMaxOptimisationSteps(int maxOptimisationSteps)
	{
		this.maxOptimisationSteps = maxOptimisationSteps;
	}

	/**
	 * set to true to get some feedback on the progress of the optimisation.
	 */
	private final static boolean DEBUG = false;

	/**
	 * the actual optimiser (a thread)
	 */
	private class Worker
			extends
			Thread
	{
		private final Random random;

		/**
		 * the performance of the unoptimised target.
		 */
		private RESIDUAL startingResidual;

		Worker()
		{
			Thread.currentThread().setName("RandomGradientDescent");
			random = new Random();
			currentParameters = currentParameters.clone();
			nParameters = currentParameters.length;
		}

		@Override
		public void run()
		{
			steps = 0;
			try {
				createMaxStep();
				state = Optimiser.State.RUNNING;
				startingResidual = currentResidual;
				ArrayList<Integer> parameterList = new ArrayList<>();
				for (int i = 0; i < nParameters; i++)
					parameterList.add(Integer.valueOf(i));
				ArrayList<Integer> tempParameterList = new ArrayList<>();
				while (!isInterrupted() && steps++ < maxOptimisationSteps) {
					RESIDUAL r = currentResidual;
					tempParameterList.addAll(parameterList);
					while (!tempParameterList.isEmpty()) {
						int p = tempParameterList
								.remove(random
										.nextInt(tempParameterList.size()))
								.intValue();
						changeParameter(p);
						// System.err.println(steps + " param " + p +
						// " residual="
						// + currentResidual);
					}
					if (r.equals(currentResidual)) break;// end the optimisation
				}

				if (state != Optimiser.State.ERROR) {
					if (currentResidual.lt(startingResidual)) {
						state = Optimiser.State.FINISHED_SUCCESSFULL;
					}
					else
						state = Optimiser.State.FINISHED_UNSUCESSFUL;
				}
			} catch (Throwable e) {
				System.err.println(e.getMessage());
				state = Optimiser.State.ERROR;
			} finally {// clean up to make space.
				maxStep = null;
				currentParameters = null;
			}
		}

		/**
		 * create the array for the maximal steps.
		 */
		private void createMaxStep()
		{
			maxStep = Arrays.copyOf(max, nParameters);
			for (int i = 0; i < nParameters; i++) {
				if (max != null && max[i] != null && min != null
						&& min[i] != null)
				{
					maxStep[i] = max[i].subtract(min[i])
							.divide(currentParameters[i].getFactory().get(10));
				}
				else {
					maxStep[i] = currentParameters[i].getFactory().get(1);
				}
			}
		}

		/**
		 * the number of parameters that are optimised.
		 */
		private final int nParameters;

		/**
		 * the maximal and initial steps taken during the optimsation.
		 */
		private RESIDUAL[] maxStep;

		/**
		 * find a minimum for varying parameter paraNum.
		 */
		private void changeParameter(final int paraNum)
		{

			IRingElement<RESIDUAL> oldParam = target.getParameter(paraNum);
			RESIDUAL step = maxStep[paraNum];

			final RESIDUAL stop = step
					.divide(oldParam.getFactory().get("1e20"));
			final RESIDUAL reduce = oldParam.getFactory().get(.5);
			final RESIDUAL increment = oldParam.getFactory().get(1.5);
			while (step.gt(stop) && !isInterrupted()) {
				synchronized (target) {
					if (DEBUG)
						System.err.println("step=" + step + "\t" + paraNum + " "
								+ oldParam + "\t redidual=" + currentResidual);
					// try smaller values for the parameter.
					RESIDUAL newParam = oldParam.add(step);
					if (max == null || max[paraNum] == null
							|| newParam.le(max[paraNum]))
					{
						target.setParameter(paraNum, newParam);
						RESIDUAL newResidual = target.getResidual();
						if (DEBUG) System.err.println("Added " + newParam
								+ "\t redidual=" + newResidual);
						if (newResidual == null
								|| newResidual.ge(currentResidual))
						{
							target.setParameter(paraNum, oldParam);
						}
						else {
							oldParam = newParam;
							currentResidual = newResidual;
							RESIDUAL nstep = step.multiply(increment);
							if (nstep.le(maxStep[paraNum])) step = nstep;
							continue;
						}
					}
					// try higher lower values
					newParam = oldParam.subtract(step);
					if (min == null || min[paraNum] == null
							|| newParam.ge(min[paraNum]))
					{
						target.setParameter(paraNum, newParam);
						RESIDUAL newResidual = target.getResidual();
						if (DEBUG) System.err.println("subtracted " + newParam
								+ "\t redidual=" + newResidual);
						if (newResidual == null
								|| newResidual.ge(currentResidual))
						{
							target.setParameter(paraNum, oldParam);
						}
						else {
							oldParam = newParam;
							currentResidual = newResidual;
							RESIDUAL nstep = step.multiply(increment);
							if (nstep.le(maxStep[paraNum])) step = nstep;
							continue;
						}
					}
				}
				// reduce the step size when the residual did not change
				step = step.multiply(reduce);
			}
		}
	}

	/**
	 * the object to be optimised
	 */
	private Target<RESIDUAL> target;

	/**
	 * the thread doing the optimisation
	 */
	private Worker worker;

	/**
	 * the minimal values for the parameters.
	 */
	private RESIDUAL[] min;

	/**
	 * the maximal values for the parameters
	 */
	private RESIDUAL[] max;

	/**
	 * the residual of the currently optimised target.
	 */
	private RESIDUAL currentResidual;

	/**
	 * the parameters of the currently optimised target.
	 */
	private IRingElement<RESIDUAL>[] currentParameters;

	@Override
	public Thread optimise()
	{
		if (worker != null)
			throw new InternalError("can run only one optimsation at a time.");
		min = target.minParameterValues();
		max = target.maxParameterValues();
		currentParameters = target.getParameters();
		currentResidual = target.getResidual();
		worker = new Worker();
		worker.start();
		return worker;
	}

	@Override
	public void setTarget(Target<RESIDUAL> target)
	{
		if (worker != null) throw new OptimiserError(
				"setTarget can not be called while an optimsation is ongoing.");
		this.target = target;
	}

	@Override
	public org.jlinalg.optimise.Optimiser.State getState()
	{
		synchronized (target) {
			return state;
		}
	}

	@Override
	public RESIDUAL getResidual()
	{
		synchronized (target) {
			return currentResidual;
		}
	}

	@Override
	public int getOptimisationStepsTaken()
	{
		return steps;
	}

}
