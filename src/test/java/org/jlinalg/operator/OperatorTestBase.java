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
package org.jlinalg.operator;

import java.util.List;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.tests.sweeping.AllFactories;
import org.jlinalg.testutil.TestBaseFixture;
import org.junit.runners.Parameterized.Parameters;

/**
 * The base for all operator tests.
 * 
 * @author Georg Thimm
 */
public class OperatorTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{

	final static String[] s_1to5 = new String[] {
			"1", "2", "3", "4", "5"
	};
	final static String[] s_m2to2 = new String[] {
			"-2", "-1", "0", "1", "1"
	};

	final IRingElementFactory<RE> factory;

	/**
	 * Constructor: used by the test suite runner.
	 */
	public OperatorTestBase(IRingElementFactory<RE> factory)
	{
		this.factory = factory;
	}

	/**
	 * @return the parameters for the fixtures
	 */
	@Parameters
	public static List<Object[]> data()
	{
		return AllFactories.factories();
	}

	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}
}
