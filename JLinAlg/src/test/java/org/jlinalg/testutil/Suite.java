package org.jlinalg.testutil;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Using <code>Suite</code> as a runner allows you to manually
 * build a suite containing tests from many classes. It is the JUnit 4
 * equivalent of the JUnit 3.8.x
 * static {@link junit.framework.Test} <code>suite()</code> method. To use it,
 * annotate a class
 * with <code>@RunWith(Suite.class)</code> and
 * <code>@SuiteClasses([{TestClass1.class, ...}][,include="<em>regexp</em>"[,exclude="<em>regexp</em>"]])</code>
 * .
 * When you run this class, it will run all specified tests classes. The
 * following rules apply:
 * <ul>
 * <li>The explicitly specified classes (if any) are always run.</li>
 * <li>Any file in the class path (imported or not) having the postfix <
 * {@code .class} with its full class name matching the include-regular
 * expression is included in the test, except if its class name also matches the
 * exclude-regular expression.</li>
 * <li>No effort is taken to exclude files which are not JUnit tests (or not
 * even java class files).</li>
 * <li>The regular expression must match the entire class name. E.g. the class
 * {@code my.example.exampleTest}, matches the following: "{@code .*Test}", "
 * {@code .*example.*}", or "{@code .*}, but not "{@code Test}".</li>
 * <li>Classes stored in jar-files are disregarded.</li>
 * </ul>
 */
public class Suite
		extends ParentRunner<Runner>
{
	/**
	 * Returns an empty suite.
	 */
	public static Runner emptySuite()
	{
		try {
			return new Suite((Class<?>) null, new Class<?>[0]);
		} catch (InitializationError e) {
			throw new RuntimeException("This shouldn't be possible");
		}
	}

	/**
	 * The <code>SuiteClasses</code> annotation specifies the classes to be run
	 * when a class
	 * annotated with <code>@RunWith(Suite.class)</code> is run.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public @interface SuiteClasses {
		/**
		 * @return the classes to be run irrespective of the settings of
		 *         {@code #include()} and {@code exclude}
		 */
		public Class<?>[] value() default {};

		/**
		 * @return the regular expression a class name has to match in order to
		 *         be included into the test. Classes which match
		 *         {@code include} <em>AND</em> {@code exclude} are <em>NOT</em>
		 *         included.
		 */
		public String include() default "";

		/**
		 * @return the regular expression a class name may not match.
		 */
		public String exclude() default "";
	}

	static Class<?>[] getAnnotatedClasses(Class<?> klass)
			throws InitializationError
	{
		SuiteClasses annotation = klass.getAnnotation(SuiteClasses.class);
		if (annotation == null)
			throw new InitializationError(String.format(
					"class '%s' must have a SuiteClasses annotation",
					klass.getName()));
		Pattern includePattern = Pattern.compile(annotation.include());
		Pattern excludePattern = Pattern.compile(annotation.exclude());
		if (annotation.include() != null && annotation.include().length() > 0) {
			List<String> paths = Arrays.asList(System.getProperty(
					"java.class.path").split("[:;]"));
			HashSet<String> names = new HashSet<String>();
			for (String p : paths) {
				File file = new File(p);
				String testClass = isTestClass(file, "", includePattern,
						excludePattern);
				if (testClass != null)
					names.add(testClass);
				else {
					if (file.isDirectory()) {
						collectClassNames(file, "", includePattern,
								excludePattern, names);
					}
				}
			}
			// System.err.println("Tests matching the pattern: " + names);
			ArrayList<Class<?>> classes = new ArrayList<Class<?>>(
					Arrays.asList(annotation.value()));
			for (String name : names) {
				try {
					classes.add(ClassLoader.getSystemClassLoader().loadClass(
							name));
				} catch (ClassNotFoundException e) {
					System.err.println("Could not load class " + name);
				}
			}
			Class<?>[] classes_ = new Class[classes.size()];
			classes.toArray(classes_);
			return classes_;
		}
		return annotation.value();
	}

	/**
	 * Tests file names for whether they represent a test
	 * 
	 * @param file
	 *            a potential class-file
	 * @param packageName
	 *            The name of the package the {@code file} would be part of.
	 * @param include
	 *            the pattern the class name must match to be included.
	 * @param exclude
	 *            the pattern excluding classes.
	 * @return the equivalent class name if the file can be assumed to be a
	 *         class file (i.e. is a plain file
	 *         and has a postfix {@code .class} and the class name matches
	 *         {@link SuiteClasses#include()} but not
	 *         {@link SuiteClasses#exclude()}.
	 */
	static String isTestClass(File file, String packageName, Pattern include,
			Pattern exclude)
	{
		if (!file.isFile() || !file.getName().matches(".*\\.class"))
			return null;
		String klassName = (packageName.length() > 0) ? (packageName + "." + file
				.getName().replace(".class", "")) : file.getName().replace(
				".class", "");
		if (include.matcher(klassName).matches()
				&& !exclude.matcher(klassName).matches()) return klassName;
		return null;

	}

	/**
	 * search for classes with names matching the given pattern.
	 * 
	 * @param dir
	 *            the directory which is recursively searched.
	 * @param packageName
	 *            the current package name (as based on the search through the
	 *            deirctory hierarchy
	 * @param includePattern
	 *            a regular expression matched against fully quallified class
	 *            names.
	 * @param excludePattern
	 *            The pattern used to exclude classes.
	 * @param names
	 *            the collection of found class names.
	 */
	private static void collectClassNames(final File dir,
			final String packageName, final Pattern includePattern,
			Pattern excludePattern, final Set<String> names)
	{
		for (File file : dir.listFiles()) {
			String testClass = isTestClass(file, packageName, includePattern,
					excludePattern);
			if (testClass != null) {
				names.add(testClass);
			}
			else {
				if (file.isDirectory()) {
					collectClassNames(
							file,
							(packageName.length() > 0) ? (packageName + "." + file
									.getName()) : file.getName(),
							includePattern, excludePattern, names);
				}
			}
		}
	}

	private final List<Runner> fRunners;

	/**
	 * Called reflectively on classes annotated with
	 * <code>@RunWith(Suite.class)</code>
	 * 
	 * @param klass
	 *            the root class
	 * @param builder
	 *            builds runners for classes in the suite
	 * @throws InitializationError
	 */
	public Suite(Class<?> klass, RunnerBuilder builder)
			throws InitializationError
	{
		this(builder, klass, getAnnotatedClasses(klass));
	}

	/**
	 * Call this when there is no single root class (for example, multiple class
	 * names
	 * passed on the command line to {@link org.junit.runner.JUnitCore}
	 * 
	 * @param builder
	 *            builds runners for classes in the suite
	 * @param classes
	 *            the classes in the suite
	 * @throws InitializationError
	 */
	public Suite(RunnerBuilder builder, Class<?>[] classes)
			throws InitializationError
	{
		this(null, builder.runners(null, classes));
	}

	/**
	 * Call this when the default builder is good enough. Left in for
	 * compatibility with JUnit 4.4.
	 * 
	 * @param klass
	 *            the root of the suite
	 * @param suiteClasses
	 *            the classes in the suite
	 * @throws InitializationError
	 */
	protected Suite(Class<?> klass, Class<?>[] suiteClasses)
			throws InitializationError
	{
		this(new AllDefaultPossibilitiesBuilder(true), klass, suiteClasses);
	}

	/**
	 * Called by this class and subclasses once the classes making up the suite
	 * have been determined
	 * 
	 * @param builder
	 *            builds runners for classes in the suite
	 * @param klass
	 *            the root of the suite
	 * @param suiteClasses
	 *            the classes in the suite
	 * @throws InitializationError
	 */
	protected Suite(RunnerBuilder builder, Class<?> klass,
			Class<?>[] suiteClasses) throws InitializationError
	{
		this(klass, builder.runners(klass, suiteClasses));
	}

	/**
	 * Called by this class and subclasses once the runners making up the suite
	 * have been determined
	 * 
	 * @param klass
	 *            root of the suite
	 * @param runners
	 *            for each class in the suite, a {@link Runner}
	 * @throws InitializationError
	 */
	protected Suite(Class<?> klass, List<Runner> runners)
			throws InitializationError
	{
		super(klass);
		fRunners = runners;
	}

	@Override
	protected List<Runner> getChildren()
	{
		return fRunners;
	}

	@Override
	protected Description describeChild(Runner child)
	{
		return child.getDescription();
	}

	@Override
	protected void runChild(Runner runner, final RunNotifier notifier)
	{
		runner.run(notifier);
	}
}
