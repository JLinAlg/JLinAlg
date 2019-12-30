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
package org.jlinalg.testutil;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.Matrix;

public class TestMatrices
{
	final static Object[][] m2x2a = {
			{
					2, -1
			}, {
					-1, 1
			}
	};

	private static Object[][] m2x2a_inv = {
			{
					1, 1
			}, {
					1, 2
			}
	};

	public static <RE extends IRingElement<RE>> Matrix<RE> getM2x2A(
			IRingElementFactory<RE> iRingElementFactory)
	{
		return new Matrix<>(m2x2a, iRingElementFactory);
	}

	public static <RE extends IRingElement<RE>> Matrix<RE> getM2x2A_inv(
			IRingElementFactory<RE> factory)
	{
		return new Matrix<>(m2x2a_inv, factory);
	}

	final static String[][] largeNonInversible = {
			{
					"0", "0", "0", "-7/6", "0", "-1", "0", "-5/3", "0", "0"
			}, {
					"0", "0", "-1/2", "0", "0", "-4/5", "0", "7/3", "2/7", "0"
			}, {
					"-3", "0", "0", "-1/3", "0", "0", "0", "2", "1/2", "-5/7"
			}, {
					"1/3", "0", "0", "0", "-7", "0", "0", "-5/7", "-1/2", "0"
			}, {
					"-5/7", "0", "-1", "1/2", "-5/6", "0", "0", "0", "0", "2/5"
			}, {
					"0", "0", "2/7", "1/2", "-3", "0", "0", "0", "0", "0"
			}, {
					"0", "0", "0", "0", "0", "-7/3", "0", "-1/4", "0", "1/8"
			}, {
					"0", "-2", "0", "0", "0", "-7/4", "0", "0", "-1/8", "-1/2"
			}, {
					"-4/5", "-1/2", "-4/5", "4/5", "-4/5", "-2", "0", "0",
					"1/4", "-1/2"
			}, {
					"0", "0", "0", "-1", "0", "3", "0", "6/5", "0", "-2/5"
			}
	};

	public static <RE extends IRingElement<RE>> Matrix<RE> getLargeNonInversible(
			IRingElementFactory<RE> factory)
	{
		return new Matrix<>(largeNonInversible, factory);
	}

	final static String[][] largeInversible = {
			{
					"0", "0", "0", "-4", "0", "1/4", "-4", "0", "0", "-7/3"
			}, {
					"0", "-1", "0", "0", "0", "0", "0", "0", "-6/7", "0", "-2/3"
			}, {
					"0", "-1/3", "0", "0", "-5/7", "0", "0", "0", "0", "0",
					"2/3"
			}, {
					"0", "1/2", "0", "2/7", "0", "-1/5", "0", "-1", "0", "0",
					"0"
			}, {
					"0", "-1", "0", "7/8", "0", "0", "-1", "0", "-1/2", "2/3",
					"1/2"
			}, {
					"0", "-4", "0", "2/7", "0", "0", "0", "-7/8", "0", "0", "0"
			}, {
					"7/8", "5/2", "1/2", "0", "0", "1/2", "0", "-7", "0",
					"-4/7", "0"
			}, {
					"-2", "0", "2/3", "0", "1/2", "0", "-2/3", "0", "0", "3/7",
					"0"
			}, {
					"6", "0", "0", "0", "0", "0", "0", "-7/8", "0", "4", "0"
			}, {
					"0", "2/5", "3", "0", "-1", "0", "0", "0", "-1/3", "0", "0"
			},
	};

	public static <RE extends IRingElement<RE>> Matrix<RE> getLargeInversible(
			IRingElementFactory<RE> factory)
	{
		return new Matrix<>(largeInversible, factory);
	}

}
