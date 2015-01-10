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

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jlinalg.IRingElement;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Georg Thimm, Andreas Keilhauer
 */
public abstract class VectorTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{
	protected final static String[] vec_0_1_9 = {
			"0", "1", "9"
	};

	protected final static String[] vec_0_1_2 = {
			"0", "1", "2"
	};

	final static String[] vec_0_1_4 = {
			"0", "1", "4"
	};
	final static String[] vec_0_3_6 = {
			"0", "3", "6"
	};
	final static String[] vec_0_1_9_0 = {
			"0", "1", "9", "0"
	};
	final static String[] vec_1_2_2 = {
			"1", "2", "2"
	};
	protected final static String[] vec_m1_m2_2 = {
			"-1", "-2", "2"
	};
	protected final static String[] vec_m2_3_6 = {
			"-2", "3", "6"
	};

	/**
	 * Test method for {@link org.jlinalg.Vector#hashCode()}.
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testHashCode_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v3 = new Vector<RE>(vec_0_1_4, getFactory());
		Vector<RE> v4 = new Vector<RE>(vec_0_1_9_0, getFactory());
		assertTrue(v1.hashCode() == v2.hashCode());
		assertNotSame(v1.hashCode(), v3.hashCode());
		assertNotSame(v1.hashCode(), v4.hashCode());
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#Vector(java.lang.Object[], org.jlinalg.IRingElementFactory)}
	 * .
	 */
	@Test
	public void testVectorObjectArrayIRingElementFactoryOfRE_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		assertNotNull(vec);
		assertEquals(vec_0_1_9.length, vec.length());
		for (int i = 1; i <= vec.length(); i++) {
			assertNotNull(vec.getEntry(i));
			assertThat(vec.getEntry(i), instanceOf(IRingElement.class));
		}
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#Vector(org.jlinalg.Vector, org.jlinalg.IRingElementFactory)}
	 * .
	 */
	@Test
	public void testVectorIRingElementVectorIRingElementFactoryOfRE_base()
	{
		Vector<StringWrapper> vecS = new Vector<StringWrapper>(vec_0_1_9,
				StringWrapper.FACTORY);
		Vector<RE> vecRE1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> vecRE2 = new Vector<RE>(vecS, getFactory());
		assertNotNull(vecRE2);
		assertEquals(vec_0_1_9.length, vecS.length());
		assertEquals(vecRE1, vecRE2);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#Vector(int, org.jlinalg.IRingElementFactory)}.
	 */
	@Test
	public void testVectorIntIRingElementFactoryOfRE_base()
	{
		Vector<RE> vec = new Vector<RE>(5, getFactory());
		assertNotNull(vec);
		assertEquals(5, vec.length());
		for (int i = 1; i <= vec.length(); i++) {
			assertNull(vec.getEntry(i));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#Vector(RE[])}.
	 */
	@Test
	public void testVectorREArray_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		for (int i = 0; i < vec_0_1_9.length; i++) {
			assertEquals(getFactory().get(vec_0_1_9[i]), vec.getEntry(i + 1));
		}
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#length()}.
	 */
	@Test
	public void testLength_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		assertEquals(vec_0_1_9.length, vec.length());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#L1Norm()}.
	 */
	@Test
	public void testL1Norm_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		RE norm = vec.L1Norm();
		assertEquals(getFactory().get("10"), norm);

		Assume.assumeTrue(dataTypeHasNegativeValues());
		vec = new Vector<RE>(vec_m1_m2_2, getFactory());
		norm = vec.L1Norm();
		assertEquals(getFactory().get("5"), norm);
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#L1Norm()}.
	 */
	@Test
	public void testSquaredDistance()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		Assume.assumeTrue(dataTypeHasNegativeValues());
		Vector<RE> vec2 = new Vector<RE>(vec_m1_m2_2, getFactory());
		assertEquals(getFactory().get("59"), vec.squaredDistance(vec2));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#L2Norm()}.
	 */
	@Test
	public void testL2Norm_base()
	{
		Assume.assumeTrue(hasMethod(getFactory().one(), "sqrt", null));
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		RE normSquare = vec.L2Norm();
		normSquare = normSquare.multiply(normSquare);
		assertSimilar(getFactory().get("82"), normSquare, "0.0000001");
		vec = new Vector<RE>(vec_m1_m2_2, getFactory());
		RE norm = vec.L2Norm();
		assertSimilar(getFactory().get("3"), norm, "0.0000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#nycDist(org.jlinalg.Vector)}.
	 */
	@Test
	public void testNycDist_base()
	{
		Vector<RE> vec1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> vec2 = new Vector<RE>(vec_m1_m2_2, getFactory());
		RE norm = vec1.nycDist(vec2);
		assertEquals(getFactory().get("11"), norm);
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#cosine(org.jlinalg.Vector)}.
	 * Uses Pythagorean quadruples in order to remain in the rational domain.
	 * Only applicable if the base type has a square root.
	 */
	@Test
	public void testCosine_base()
	{
		Assume.assumeTrue(hasMethod(getFactory().one(), "sqrt", null)
				&& !methodIsDepreciated(getFactory().one(), "sqrt", null));
		Vector<RE> vec1 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> vec2 = new Vector<RE>(vec_m1_m2_2, getFactory());
		Vector<RE> vec3 = new Vector<RE>(vec_m2_3_6, getFactory());
		RE cos = vec1.cosine(vec2);
		RE should = getFactory().get("-1/9");
		assertTrue("is=" + cos + " should=" + should, should.subtract(cos)
				.abs().lt(getFactory().get(0.0001)));
		cos = vec1.cosine(vec3);
		should = getFactory().get("16/21");
		assertTrue("is=" + cos + " should=" + should, should.subtract(cos)
				.abs().lt(getFactory().get(0.0001)));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#set(int, org.jlinalg.IRingElement)} and
	 * {@link org.jlinalg.Vector#getEntry(int)}.
	 */
	@Test
	public void testSetIntIRingElement_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		RE two = getFactory().get("2");
		vec.set(3, two);
		assertEquals(two, vec.getEntry(3));
		RE three = getFactory().get("3");
		vec.set(1, three);
		assertEquals(three, vec.getEntry(1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#toMatrix()}.
	 */
	@Test
	public void testToMatrix_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		Matrix<RE> mat = vec.toMatrix();
		assertEquals(1, mat.getCols());
		assertEquals(vec.length(), mat.getRows());
		for (int row = 1; row < vec.length(); row++)
			assertEquals(vec.getEntry(row), mat.get(row, 1));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#divide(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testDivide_base()
	{
		Vector<RE> v = new Vector<RE>(new String[] {
				"-8", "0", "2"
		}, getFactory());
		RE r = getFactory().get("2");
		Vector<RE> res = new Vector<RE>(new String[] {
				"-4", "0", "1"
		}, getFactory());
		assertSimilar(res, v.divide(r), "0.00001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#divideReplace(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testDivideReplace_base()
	{
		Vector<RE> v = new Vector<RE>(new String[] {
				"-8", "0", "2"
		}, getFactory());
		RE r = getFactory().get("2");
		Vector<RE> res = new Vector<RE>(new String[] {
				"-4", "0", "1"
		}, getFactory());
		v.divideReplace(r);
		assertSimilar(res, v, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#multiply(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testMultiplyRE_base()
	{
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"-6", "9", "18"
		}, getFactory());
		assertEquals(res, v.multiply(r));
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#arrayMultiply(org.jlinalg.Vector)}.
	 */
	@Test
	public void testArrayMultiply_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "2", "18"
		}, getFactory());
		assertSimilar(res, v1.arrayMultiply(v2), "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#multiplyReplace(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testMultiplyReplaceRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "7", "63"
		}, getFactory());
		v1.multiplyReplace(getFactory().get("7"));
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#multiplyReplace(org.jlinalg.Vector)}.
	 */
	@Test
	public void testMultiplyReplaceVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "2", "18"
		}, getFactory());
		v1.multiplyReplace(v2);
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#add(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testAddRE_base()
	{
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "6", "9"
		}, getFactory());
		assertSimilar(res, v.add(r), "0.000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#add(org.jlinalg.Vector)}.
	 */
	@Test
	public void testAddVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "3", "11"
		}, getFactory());
		v1 = v1.add(v2);
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#addReplace(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testAddReplaceRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"7", "8", "16"
		}, getFactory());
		v1.addReplace(getFactory().get("7"));
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#addReplace(org.jlinalg.Vector)}
	 * .
	 */
	@Test
	public void testAddReplaceVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "3", "11"
		}, getFactory());
		v1.addReplace(v2);
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#subtract(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testSubtractRE_base()
	{
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"-5", "0", "3"
		}, getFactory());
		assertEquals(res, v.subtract(r));
		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		r = getFactory().get("1");
		res = new Vector<RE>(new String[] {
				"-1", "0", "8", "-1"
		}, getFactory());
		assertEquals(res, v.subtract(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#subtract(org.jlinalg.Vector)}.
	 */
	@Test
	public void testSubtractVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"-1", "-1", "7"
		}, getFactory());
		v1 = v1.subtract(v2);
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#subtractReplace(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testSubtractReplaceRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"-7", "-6", "2"
		}, getFactory());
		v1.subtractReplace(getFactory().get("7"));
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#subtractReplace(org.jlinalg.Vector)}.
	 */
	@Test
	public void testSubtractReplaceVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"-1", "-1", "7"
		}, getFactory());
		v1.subtractReplace(v2);
		assertSimilar(res, v1, "0.000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#multiply(org.jlinalg.Vector)}.
	 */
	@Test
	public void testMultiplyVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		assertSimilar(getFactory().get(20), v1.multiply(v2), "0.000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> v3 = new Vector<RE>(vec_1_2_2, getFactory());
		assertFalse(v1.equals(v2));
		assertTrue(v2.equals(v3));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#swapEntries(int, int)}.
	 */
	@Test
	public void testSwapEntries_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(new String[] {
				"9", "1", "0"
		}, getFactory());
		v1.swapEntries(1, 3);
		assertEquals(v2, v1);
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#toString()}.
	 */
	@Test
	public void testToString_base()
	{
		Vector<RE> vec = new Vector<RE>(vec_0_1_9, getFactory());
		assertNotNull(vec);
		assertTrue(vec.toString().length() > 5);
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#and(org.jlinalg.Vector)}.
	 */
	@Test
	public void testAnd_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "1", "1"
		}, getFactory());
		assertEquals(res, v1.and(v2));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#or(org.jlinalg.Vector)}.
	 */
	@Test
	public void testOr_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9_0, getFactory());
		Vector<RE> res = v1.ge(getFactory().one());
		assertEquals(res, v1.or(getLinAlgFactory().zeros(4)));

		Vector<RE> v2 = new Vector<RE>(new String[] {
				"0", "1", "0", "3"
		}, getFactory());
		res = new Vector<RE>(new String[] {
				"0", "1", "1", "1"
		}, getFactory());
		assertEquals(res, v1.or(v2));
		assertEquals(res, v2.or(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#not()}.
	 */
	@Test
	public void testNot_base()
	{
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "0", "0"
		}, getFactory());
		assertEquals(res, v.not());
		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		res = new Vector<RE>(new String[] {
				"1", "0", "0", "1"
		}, getFactory());
		assertEquals(res, v.not());
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#applyReplace(org.jlinalg.operator.MonadicOperator)}
	 * .
	 */
	@Ignore
	@Test
	public void testApplyReplaceMonadicOperatorOfRE_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#apply(org.jlinalg.operator.MonadicOperator)}.
	 */
	@Ignore
	@Test
	public void testApplyMonadicOperatorOfQextendsIRingElement_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#applyReplace(org.jlinalg.Vector, org.jlinalg.operator.DyadicOperator)}
	 * .
	 */
	@Ignore
	@Test
	public void testApplyReplaceVectorOfREDyadicOperatorOfRE_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#apply(org.jlinalg.Vector, org.jlinalg.operator.DyadicOperator)}
	 * .
	 */
	@Ignore
	@Test
	public void testApplyVectorOfREDyadicOperatorOfRE_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#applyReplace(org.jlinalg.IRingElement, org.jlinalg.operator.DyadicOperator)}
	 * .
	 */
	@Ignore
	@Test
	public void testApplyReplaceREDyadicOperatorOfRE_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#apply(org.jlinalg.IRingElement, org.jlinalg.operator.DyadicOperator)}
	 * .
	 */
	@Ignore
	@Test
	public void testApplyREDyadicOperatorOfRE_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#copy()}.
	 */
	@Test
	public void testCopy_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_m1_m2_2, getFactory());
		assertEquals(v1, v1.copy());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#lt(org.jlinalg.Vector)}.
	 */
	@Test
	public void testLtVectorOfRE_base()
	{
		Vector<RE> v1;
		if (dataTypeHasNegativeValues())
			v1 = new Vector<RE>(vec_m1_m2_2, getFactory());
		else
			v1 = new Vector<RE>(vec_0_1_2, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "1", "0"
		}, getFactory());
		assertEquals(res, v1.lt(v2));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#lt(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testLtRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_0_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "0", "0"
		}, getFactory());
		assertEquals(res, v.lt(r));
		v = new Vector<RE>(vec_1_2_2, getFactory());
		r = getFactory().get("2");
		res = new Vector<RE>(new String[] {
				"1", "0", "0"
		}, getFactory());
		assertEquals(res, v.lt(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#le(org.jlinalg.Vector)}.
	 */
	@Test
	public void testLeVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());

		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "1", "0"
		}, getFactory());
		assertEquals(res, v1.le(v2));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#le(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testLeRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_0_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "1", "0"
		}, getFactory());
		assertEquals(res, v.le(r));
		v = new Vector<RE>(vec_1_2_2, getFactory());
		r = getFactory().get("1");
		res = new Vector<RE>(new String[] {
				"1", "0", "0"
		}, getFactory());
		assertEquals(res, v.le(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#gt(org.jlinalg.Vector)}.
	 */
	@Test
	public void testGtVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_3_6, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_0_1_9, getFactory());

		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "0", "1"
		}, getFactory());
		assertEquals(res, v2.gt(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#gt(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testGtRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_0_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "0", "1"
		}, getFactory());
		assertEquals(res, v.gt(r));
		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		r = getFactory().get("1");
		res = new Vector<RE>(new String[] {
				"0", "0", "1", "0"
		}, getFactory());
		assertEquals(res, v.gt(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#ge(org.jlinalg.Vector)}.
	 */
	@Test
	public void testGeVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_3_6, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_0_1_9, getFactory());

		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "0", "1"
		}, getFactory());
		assertEquals(res, v2.gt(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#ge(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testGeRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_0_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "1", "1"
		}, getFactory());
		assertEquals(res, v.ge(r));
		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		r = getFactory().get("1");
		res = new Vector<RE>(new String[] {
				"0", "1", "1", "0"
		}, getFactory());
		assertEquals(res, v.ge(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#eq(org.jlinalg.Vector)}.
	 */
	@Test
	public void testEqVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_3_6, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_0_1_9, getFactory());

		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "0", "0"
		}, getFactory());
		assertEquals(res, v2.eq(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#eq(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testEqRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "1", "0"
		}, getFactory());
		assertEquals(res, v.eq(r));
		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		r = getFactory().get("0");
		res = new Vector<RE>(new String[] {
				"1", "0", "0", "1"
		}, getFactory());
		assertEquals(res, v.eq(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#ne(org.jlinalg.Vector)}.
	 */
	@Test
	public void testNeVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_0_1_9_0, getFactory());
		assertEquals(getLinAlgFactory().zeros(4), v1.ne(v1));

		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "1", "1", "0"
		}, getFactory());
		assertEquals(res, v1.ne(getLinAlgFactory().zeros(4)));

		Vector<RE> v2 = new Vector<RE>(new String[] {
				"0", "1", "0", "0"
		}, getFactory());

		res = new Vector<RE>(new String[] {
				"0", "0", "1", "0"
		}, getFactory());
		assertEquals(res, v1.ne(v2));
		assertEquals(res, v2.ne(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#ne(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testNeRE_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		RE r = getFactory().get("3");
		Vector<RE> res = new Vector<RE>(new String[] {
				"1", "0", "1"
		}, getFactory());
		assertEquals(res, v.ne(r));
		v = new Vector<RE>(vec_1_2_2, getFactory());
		r = getFactory().get("2");
		res = new Vector<RE>(new String[] {
				"1", "0", "0"
		}, getFactory());
		assertEquals(res, v.ne(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#sum()}.
	 */
	@Test
	public void testSum_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		assertEquals(getFactory().get("7"), v.sum());
		v = new Vector<RE>(vec_m1_m2_2, getFactory());
		assertEquals(getFactory().get("-1"), v.sum());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#min()}.
	 */
	@Test
	public void testMin_base()
	{
		Vector<RE> v;
		v = new Vector<RE>(vec_1_2_2, getFactory());
		assertEquals(getFactory().get("1"), v.min());

		Assume.assumeTrue(dataTypeHasNegativeValues());
		v = new Vector<RE>(vec_m2_3_6, getFactory());
		assertEquals(getFactory().get("-2"), v.min());

	}

	/**
	 * Test method for {@link org.jlinalg.Vector#max()}.
	 */
	@Test
	public void testMax_base()
	{
		Vector<RE> v;
		if (dataTypeHasNegativeValues())
			v = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v = new Vector<RE>(vec_0_3_6, getFactory());
		assertEquals(getFactory().get("6"), v.max());

		Assume.assumeTrue(dataTypeHasNegativeValues());
		v = new Vector<RE>(new String[] {
				"0", "-3", "2", "0"
		}, getFactory());
		assertEquals(getFactory().get("2"), v.max());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#mean()}.
	 */
	@Test
	public void testMean_base()
	{
		Vector<RE> v = new Vector<RE>(new String[] {
				"0", "-10", "2", "0"
		}, getFactory());
		assertSimilar(getFactory().get("-2"), v.mean(), "0.0000001");
		Assume.assumeTrue(!dataTypeIsDiscreet());
		v = new Vector<RE>(vec_m2_3_6, getFactory());
		assertSimilar(getFactory().get("7/3"), v.mean(), "0.0000001");
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#find(org.jlinalg.IRingElement)}
	 * .
	 */
	@Test
	public void testFind_base()
	{
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		int[] found = v.find(getFactory().get("3"));
		assertEquals("found=" + found, 1, found.length);
		assertEquals("found=" + found, 2, found[0]);

		v = new Vector<RE>(vec_0_1_9_0, getFactory());
		found = v.find(getFactory().get("0"));
		assertEquals(2, found.length);
		assertTrue("found=" + found, found[0] == 1 || found[0] == 4);
		assertTrue("found=" + found, found[1] == 4 || found[1] == 1);
	}

	/**
	 * Test method for
	 * {@link org.jlinalg.Vector#setAll(org.jlinalg.IRingElement)}.
	 */
	@Test
	public void testSetAll_base()
	{
		Vector<RE> v = new Vector<RE>(vec_0_1_9_0, getFactory());
		RE r = getFactory().get("123");
		v.setAll(r);
		assertEquals(getLinAlgFactory().ones(4), v.eq(r));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#repmat(int)}.
	 */
	@Ignore
	@Test
	public void testRepmat_base()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#sort()}.
	 */
	@Test
	public void testSort_base()
	{
		Vector<RE> v = new Vector<RE>(vec_0_1_9_0, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"0", "0", "1", "9"
		}, getFactory());
		assertEquals(res, v.sort());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#multiply(org.jlinalg.Matrix)}.
	 */
	@Test
	public void testMultiplyMatrixOfRE_base()
	{
		Matrix<RE> m = getLinAlgFactory().identity(3);
		Vector<RE> v = new Vector<RE>(vec_m2_3_6, getFactory());
		assertEquals(v, v.multiply(m));
		m = new Matrix<RE>(MatrixTestBase.mat_1to9, getFactory());
		Vector<RE> res = new Vector<RE>(new String[] {
				"52", "59", "66"
		}, getFactory());
		assertEquals(res, v.multiply(m));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#cross(org.jlinalg.Vector)}.
	 */
	@Test
	public void testCross_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_1_2_2, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_m2_3_6, getFactory());
		Vector<RE> v3 = new Vector<RE>(new String[] {
				"6", "-10", "7"
		}, getFactory());
		assertEquals(v3, v1.cross(v2));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#isZero()}.
	 */
	@Test
	public void testIsZero_base()
	{
		String[] values = {
				"0", "0", "0", "0", "0"
		};
		Vector<RE> vec = new Vector<RE>(values, getFactory());
		assertTrue(vec.isZero());
		vec.set(1, getFactory().one());
		assertFalse(vec.isZero());
		vec.set(1, getFactory().zero());
		assertTrue(vec.isZero());
		vec.set(3, getFactory().one());
		assertFalse(vec.isZero());
		vec.set(3, getFactory().zero());
		assertTrue(vec.isZero());
		vec.set(values.length, getFactory().one());
		assertFalse(vec.isZero());
		vec.set(values.length, getFactory().zero());
		assertTrue(vec.isZero());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#elementProduct()}.
	 */
	@Test
	public void testElementProduct_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_m2_3_6, getFactory());
		assertEquals(getFactory().get("-36"), v1.elementProduct());
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#compareTo(org.jlinalg.Vector)}.
	 */
	@Test
	public void testCompareTo_base()
	{
		Vector<RE> v1;
		if (dataTypeHasNegativeValues())
			v1 = new Vector<RE>(vec_m2_3_6, getFactory());
		else
			v1 = new Vector<RE>(vec_0_3_6, getFactory());

		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		if (dataTypeHasNegativeValues()) assertEquals(-1, v1.compareTo(v2));
		assertEquals(1, v2.compareTo(v1));
		assertEquals(0, v1.compareTo(v1));
	}

	/**
	 * Test method for {@link org.jlinalg.Vector#set(org.jlinalg.Vector)}.
	 */
	@Test
	public void testSetVectorOfRE_base()
	{
		Vector<RE> v1 = new Vector<RE>(vec_m2_3_6, getFactory());
		Vector<RE> v2 = new Vector<RE>(vec_1_2_2, getFactory());
		v2.set(v1);
		assertEquals(v1, v2);
	}

}
