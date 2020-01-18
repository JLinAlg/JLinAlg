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

package org.jlinalg.field_p;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NumberComparatorTests
{
	private static NumberComparator comparator = new NumberComparator();

	@ParameterizedTest
	@MethodSource("data")
	void isSmaller(Number n1, Number n2)
	{
		assertTrue(comparator.compare(n1, n2) < 0);
	}

	@ParameterizedTest
	@MethodSource("data")
	void isLarger(Number n1, Number n2)
	{
		assertTrue(comparator.compare(n2, n1) > 0);
	}

	@ParameterizedTest
	@MethodSource("dataEqual")
	void areEqual(Number n1, Number n2)
	{
		assertTrue(comparator.compare(n2, n1) == 0);
	}

	static Stream<Arguments> data()
	{
		return Stream.of(arguments(-1L, 0L),
				arguments(-1L, new BigInteger("100000000000")),
				arguments(new BigInteger("-10000000000000"),
						new BigInteger("100000000000")));
	}

	static Stream<Arguments> dataEqual()
	{
		return Stream.of(arguments(9L, 9L),
				arguments(77L, new BigInteger("77")),
				arguments(new BigInteger("88"), new BigInteger("88")));
	}
}
