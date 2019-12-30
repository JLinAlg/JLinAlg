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
package org.jlinalg.demo;

import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.optimise.Optimiser;
import org.jlinalg.optimise.RandomGradientDescent;
import org.jlinalg.optimise.Target;

/**
 * Demonstrates the use of a {@link RandomGradientDescent} to optimise
 * {@link Function}. The following steps are necessary:
 * <UL>
 * <LI>The target function (class) has to implement {@link Target}</LI>
 * <LI>The {@link RandomGradientDescent} (or another clas implementing
 * {@link Optimiser}) has to be instantiated and referenced to the target.</li>
 * <LI>Call method {@link Optimiser#optimise()}</Li>
 * <li>Wait for the created thread to terminate (or interupt it).</li>
 * <li>Inspect the target.</li>
 * </UL>
 * 
 * @author Georg Thimm
 */
public class RandomGradientSearchDemo
{
	/**
	 * run the example.
	 * 
	 * @param args
	 *            is ignored.
	 * @throws InterruptedException
	 *             (for the compiler's satisfaction: Thread)
	 */
	public static void main(String[] args) throws InterruptedException
	{
		Target<DoubleWrapper> target = new Function();
		System.out.println("target function: " + target.toString());
		System.out.println("Residual before the optimisation:"
				+ target.getResidual());
		System.out
				.println("x=" + target.getParameters()[0] + " y="
						+ target.getParameters()[1] + " z="
						+ target.getParameters()[2]);

		Optimiser<DoubleWrapper> optimiser = new RandomGradientDescent<>(
				target);
		Thread optThread = optimiser.optimise();
		optThread.join();
		System.out.println("\n\nResidual after the optimisation:"
				+ target.getResidual());
		System.out
				.println("x=" + target.getParameters()[0] + " y="
						+ target.getParameters()[1] + " z="
						+ target.getParameters()[2]);
	}
}
