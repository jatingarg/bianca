package com.github.yuthura.bianca;

import java.sql.*;

import org.junit.*;
import org.junit.rules.*;

import com.github.yuthura.bianca.test.*;

import static org.junit.Assert.*;

/**
 * <p>
 * Simple tests that asserts the workings of {@link Partial}. More complex tests reside in {@link PartialWrapTest}.
 * </p>
 *
 * @see PartialWrapTest
 *
 * @author Yuthura
 */
public class PartialTest {
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
	 * The string builder that may be used by tests to construct a query (to test
	 * {@link Partial#buildStatement(StringBuilder)}). Will be reset before each test run.
	 * </p>
	 */
	protected StringBuilder sb;

	/**
	 * <p>
	 * The prepared statement that may be used by tests to prepare a query (to test
	 * {@link Partial#prepareStatement(PreparedStatement, int)}). Will be reset before each test run.
	 * </p>
	 */
	protected TestPreparedStatement ps;

	/**
	 * <p>
	 * Resets the state of <code>this</code> test so that fields may be reused. This may not be needed if JUnit creates a
	 * new instance for each test run.
	 * </p>
	 */
	@Before
	public void setup() {
		sb = new StringBuilder();
		ps = new TestPreparedStatement();
	}

	/**
	 * <p>
	 * Asserts that the {@link Partial#NULL} constant behaves as an expected SQL NULL statement and sets no parameters on the
	 * prepared statement.
	 * </p>
	 *
	 * @throws SQLException
	 */
	@Test
	public void nullTest() throws SQLException {
		Partial nil = Partial.NULL;

		nil.buildStatement(sb);
		assertEquals("NULL", sb.toString());

		int index = nil.prepareStatement(ps, 0);
		assertEquals(0, index);

		assertTrue(ps.params.isEmpty());
	}

	/**
	 * <p>
	 * Asserts that the {@link Partial#ALL} constant behaves as an expect SQL * statement and sets no parameters on the
	 * prepared statement.
	 * </p>
	 *
	 * @throws SQLException
	 */
	@Test
	public void allTest() throws SQLException {
		Partial all = Partial.ALL;

		all.buildStatement(sb);
		assertEquals("*", sb.toString());

		int index = all.prepareStatement(ps, 0);
		assertEquals(0, index);

		assertTrue(ps.params.isEmpty());
	}

	/**
	 * <p>
	 * Asserts that {@link Partial#wrap(Object)} will return the {@link Partial#NULL} constant when called with a
	 * <code>null</code> value.
	 * </p>
	 */
	@Test
	public void wrapNullTest() {
		Partial nil = Partial.wrap(null);
		assertSame(Partial.NULL, nil);
	}

	/**
	 * <p>
	 * Asserts that {@link Partial#wrap(Object)} will return the object instance it receives if that object is a
	 * {@link Partial} instance. Must not throw a {@link CoercionException}.
	 * </p>
	 */
	@Test
	public void wrapPartialTest() {
		Object object = new FakePartial();

		Partial partial = Partial.wrap(object);
		assertSame(object, partial);
	}

	/**
	 * <p>
	 * Asserts that {@link Partial#wrap(Object)} will call {@link Partiable#toPartial()} on the object it receives if that
	 * object is a {@link Partiable} instance and that it returns the gotten value. Must not throw a {@link CoercionException}.
	 * </p>
	 */
	@Test
	public void wrapPartiableTest() {
		Partial fake = new FakePartial();
		Object object = new Partiable() {
			@Override
			public Partial toPartial() {
				return fake;
			}
		};

		Partial partial = Partial.wrap(object);
		assertSame(fake, partial);
	}

	/**
	 * <p>
	 * Asserts that {@link Partial#wrap(Object)} will throw a {@link CoercionException} if called with an object it does not
	 * know how to wrap.
	 * </p>
	 */
	@Test
	public void wrapUnknownTypeTest() {
		thrown.expect(CoercionException.class);
		thrown.expectMessage("java.lang.Object");

		Object object = new Object();
		Partial.wrap(object);
	}


	/**
	 * <p>
	 * Simple dummy implementation of {@link Partial} that does nothing; useful for testing methods that should do something
	 * on a specific partial object, regardless of the partial implementation.
	 * </p>
	 *
	 * @author Yuthura
	 */
	private static class FakePartial implements Partial {
		@Override
		public void buildStatement(StringBuilder sb) {
		}

		@Override
		public int prepareStatement(PreparedStatement ps, int i){
			return 0;
		}
	}
}
