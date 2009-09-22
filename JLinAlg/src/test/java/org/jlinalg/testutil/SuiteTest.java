package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.jlinalg.testutil.Suite.SuiteClasses;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

/**
 * Test the include/exclude feature of {@link Suite}
 * 
 * @author Georg Thimm
 */
public class SuiteTest
{
	/**
	 * The fixture for {@link #testGetAnnotatedClasses()}.
	 */
	@RunWith(Suite.class)
	@SuiteClasses(include = "org\\.jlinalg.*Suite.*", exclude = "")
	public class TestSuiteTest1
	{
	}

	/**
	 * The fixture for {@link #testGetAnnotatedClasses()}.
	 */
	@RunWith(Suite.class)
	@SuiteClasses(include = "org\\.jlinalg.*Suite.*", exclude = ".*\\$.*")
	public class TestSuiteTest2
	{
	}

	/**
	 * The fixture for {@link #testGetAnnotatedClasses()}.
	 */
	@RunWith(Suite.class)
	@SuiteClasses(SuiteTest.class)
	public class TestSuiteTest3
	{
	}

	@Test
	public void testGetAnnotatedClasses1() throws InitializationError,
			ClassNotFoundException
	{
		Class<?> suite = ClassLoader.getSystemClassLoader().loadClass(
				"org.jlinalg.testutil.SuiteTest$TestSuiteTest1");
		List<Class<?>> set = Arrays.asList(Suite.getAnnotatedClasses(suite));
		assertEquals("found " + set, 6, set.size());
		assertTrue("missing " + Suite.class, set.contains(Suite.class));
		assertTrue("missing " + Suite.class, set.contains(Suite.class));
	}

	@Test
	public void testGetAnnotatedClasses2() throws InitializationError,
			ClassNotFoundException
	{
		Class<?> suite = ClassLoader.getSystemClassLoader().loadClass(
				"org.jlinalg.testutil.SuiteTest$TestSuiteTest2");
		List<Class<?>> set = Arrays.asList(Suite.getAnnotatedClasses(suite));
		assertEquals("found " + set, 2, set.size());
		assertTrue("missing " + Suite.class, set.contains(Suite.class));
		assertTrue("missing " + Suite.class, set.contains(Suite.class));
	}

	@Test
	public void testGetAnnotatedClasses3() throws InitializationError,
			ClassNotFoundException
	{
		Class<?> suite = ClassLoader.getSystemClassLoader().loadClass(
				"org.jlinalg.testutil.SuiteTest$TestSuiteTest3");
		List<Class<?>> set = Arrays.asList(Suite.getAnnotatedClasses(suite));
		assertEquals("found " + set, 1, set.size());
	}

	@Test
	public void testIsTestClass()
	{
		String fileName = "bin/org/jlinalg/testutil/SuiteTest.class";
		String packageName = "org.jlinalg.testutil";
		String include = ".*Suite.*";
		String exclude = "";
		File f = new File(fileName);
		assertTrue("cannot find file " + fileName, f.exists());
		assertEquals("org.jlinalg.testutil.SuiteTest", Suite.isTestClass(
				new File(fileName), packageName, Pattern.compile(include),
				Pattern.compile(exclude)));
		exclude = ".*Test";
		assertNull(Suite.isTestClass(new File(fileName), packageName, Pattern
				.compile(include), Pattern.compile(exclude)));
		exclude = "nothing";
		assertEquals("org.jlinalg.testutil.SuiteTest", Suite.isTestClass(
				new File(fileName), packageName, Pattern.compile(include),
				Pattern.compile(exclude)));
		include = ".*XXXX.*";
		assertNull(Suite.isTestClass(new File(fileName), packageName, Pattern
				.compile(include), Pattern.compile(exclude)));
		include = ".*Suite.*";
		exclude = "";
		fileName = "/no/where";
		assertNull(Suite.isTestClass(new File(fileName), packageName, Pattern
				.compile(include), Pattern.compile(exclude)));
	}
}
