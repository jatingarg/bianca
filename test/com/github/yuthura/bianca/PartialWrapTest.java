package com.github.yuthura.bianca;

import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import org.junit.runners.*;

import com.github.yuthura.bianca.test.*;

import static org.junit.Assert.*;

/**
 * <p>
 * Complex tests that assert the workings of {@link Partial#wrap(Object)}. More simple tests for the {@link Partial} class
 * reside in {@link PartialTest}.
 * </p>
 * <p>
 * This is a parameterized test that checks to see if {@link Partial#wrap(Object)} returns proper implementations for objects
 * of the following types:
 * </p>
 * <ul>
 *   <li><code>java.lang.Integer</code></li>
 *   <li><code>java.lang.Long</code></li>
 *   <li><code>java.lang.Float</code></li>
 *   <li><code>java.lang.Double</code></li>
 *   <li><code>java.lang.Boolean</code></li>
 *   <li><code>java.lang.String</code></li>
 *   <li><code>java.time.LocalDate (will be wrapped as <code>java.sql.Date</code>)</code></li>
 *   <li><code>java.time.LocalTime (will be wrapped as <code>java.sql.Time</code>)</code></li>
 *   <li><code>java.time.LocalDateTimem (will be wrapped as <code>java.sql.Timestamp</code>)</code></li>
 * </ul>
 * <p>
 * None of these tests may throw a {@link CoercionException}.
 * </p>
 *
 * @see PartialTest
 *
 * @author Yuthura
 */
@RunWith(Parameterized.class)
public class PartialWrapTest {
	/**
	 * <p>
	 * JUnit helper to help assert features that throw an exception. By default expects no exceptions to be thrown during the
	 * run of a test.
	 * </p>
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * <p>
	 * The prepared statement that may be used by tests to prepare a query (to test
	 * {@link Partial#prepareStatement(PreparedStatement, int)}). Will be reset before each test run.
	 * </p>
	 */
	protected TestPreparedStatement ps;

	/**
	 * <p>
	 * Resets the state of <code>this</code> test so that the fields may be reused. This may not be needed if JUnit creates a
	 * new instance for each test run.
	 * </p>
	 */
	@Before
	public void setup() {
		ps = new TestPreparedStatement();
	}

	/**
	 * <p>
	 * Constructs the seed data used by JUnit to run the tests of this class. The seed data contains an array of arrays, where
	 * each subarray contains the object passed to {@link Partial#wrap(Object)} in element <code>[0]</code> and the object to
	 * compare with the prepared statement in element <code>[1]</code>. The first six elements contain wrappers of the
	 * supported primitives (integer, long, float, double, boolean) and a string. The last three elements contain date, time
	 * and datetime from the <code>java.time</code> package, which are converted to their respective <code>java.sql</code>
	 * package variants.
	 * </p>
	 *
	 * @return the seed data to run the tests of this class with
	 */
	@Parameterized.Parameters
	public static Iterable<Object[]> data() {
		Object[][] array = new Object[][]{
			{ Integer.valueOf(0)    , null },
			{ Long.valueOf(0L)      , null },
			{ Float.valueOf(0.0F)   , null },
			{ Double.valueOf(0.0)   , null },
			{ Boolean.valueOf(true) , null },
			{ "string"              , null },
			{ LocalDate.now()       , null },
			{ LocalTime.now()       , null },
			{ LocalDateTime.now()   , null }
		};

		// the types for these set the primitive value or the actual object, so the actual will have the same value as the initial
		array[0][1] = array[0][0];
		array[1][1] = array[1][0];
		array[2][1] = array[2][0];
		array[3][1] = array[3][0];
		array[4][1] = array[4][0];
		array[5][1] = array[5][0];

		// the types for these auto-wrap into sql compatible types, so we need the actual values the statement is going to expect
		array[6][1] = Date.valueOf((LocalDate)array[6][0]);
		array[7][1] = Time.valueOf((LocalTime)array[7][0]);
		array[8][1] = Timestamp.valueOf((LocalDateTime)array[8][0]);

		return Arrays.asList(array);
	}

	/**
	 * <p>
	 * The object to be passed to {@link Partial#wrap(Object)} during the test.
	 * </p>
	 */
	@Parameterized.Parameter(value=0)
	public Object object;

	/**
	 * <p>
	 * The object to compare in the prepared statement to check if {@link Partial#wrap(Object)} functions properly.
	 * </p>
	 */
	@Parameterized.Parameter(value=1)
	public Object actual;

	/**
	 * <p>
	 * Asserts that an object of a supported type other than <code>null</code>, {@link Partial} or {@link Partiable} is
	 * properly wrapped into a {@link Partial} instance, and that that partial behaves as expected for the given object.
	 * Must not throw a {@link CoercionException}.
	 * </p>
	 * <p>
	 * This test is run for each element pair in the seed data as returned by {@link #data()} (initialized by JUnit). For each
	 * run, element <code>[0]</code> will be sent to {@link Partial#wrap(Object)} and the returned partial will be told to
	 * prepare a statement. It is then asserted if the partial instance has properly set the parameter to element
	 * <code>[1]</code> on the prepared statement on the expected index.
	 * </p>
	 *
	 * @throws SQLException never ({@link TestPreparedStatement} throws no such exceptions)
	 */
	@Test
	public void wrapKnownTypeTest() throws SQLException {
		Partial partial = Partial.wrap(object);

		int index = partial.prepareStatement(ps, 0);
		assertEquals(index, 1);

		assertEquals(1, ps.params.size());
		assertEquals(actual, ps.params.get(Integer.valueOf(0)));
	}
}
