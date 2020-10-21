package org.geogebra.common.kernel.interval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

public class IntervalTest {

	@Test
	public void testValidInterval() {
		Interval interval = new Interval(1, 2);
		assertTrue(interval.getLow() == 1 && interval.getHigh() == 2);
	}

	@Test
	public void testInvalidIntervals() {
		Interval interval = new Interval(2, 1);
		assertTrue(interval.isEmpty());
	}

	@Test
	public void testAdd() {
		assertEquals(interval(1, 9),
				interval(-3, 2)
						.add(interval(4, 7)));
	}

	static Interval interval(double low, double high) {
		return new Interval(low, high);
	}

	static void shouldEqual(Interval interval1, Interval interval2) {
		assertTrue(interval1.almostEqual(interval2));
	}

	@Test
	public void testSub() {
		assertEquals(interval(-5, 5),
				interval(-1, 3)
						.subtract(interval(-2, 4)));
	}

	@Test
	public void isWhole() {
		Interval interval = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		assertTrue(interval.isWhole());
	}

	@Test
	public void testSingleton() {
		Interval interval = new Interval(2);
		assertTrue(interval.isSingleton());
	}

	@Test
	public void testWholeIsNotSingleton() {
		assertFalse(IntervalConstants.WHOLE.isSingleton());
	}

	@Test
	public void testHasZero() {
		assertTrue(new Interval(-1, 1).hasZero());
		assertTrue(new Interval(0, 1).hasZero());
		assertTrue(new Interval(-1, 0).hasZero());
		assertTrue(IntervalConstants.WHOLE.hasZero());
		assertTrue(IntervalConstants.ZERO.hasZero());
	}

	@Test
	public void testHasNotZero() {
		assertFalse(new Interval(2, 6).hasZero());
		assertFalse(new Interval(-2, -0.1).hasZero());
		assertFalse(new Interval(Double.NEGATIVE_INFINITY, -2).hasZero());
		assertFalse(new Interval(1, Double.POSITIVE_INFINITY).hasZero());
	}

	@Test
	public void testEmpty() {
		assertTrue(new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY).isEmpty());
	}

	@Test
	public void testEmptyConstant() {
		assertTrue(IntervalConstants.empty().isEmpty());
	}

	@Test
	public void testOverlap() {
		Interval a = new Interval(-1, 1);
		Interval b = new Interval(-0.5, 0.5);
		Interval c = new Interval(0.6, 1.5);
		assertTrue(a.isOverlap(b));
		assertTrue(a.isOverlap(c));
		assertFalse(b.isOverlap(c));
	}

	@Test
	public void testNotOverlapWithEmptyInterval() {
		Interval a = new Interval(-1, 1);
		assertFalse(a.isOverlap(IntervalConstants.empty()));
		assertFalse(IntervalConstants.empty().isOverlap(a));
	}

	@Test
	public void testNegative() {
		shouldEqual(interval(-3, -2), interval(2, 3).negative());
		shouldEqual(interval(-2, 1), interval(-1, 2).negative());
		shouldEqual(interval(2, 3), interval(-3, -2).negative());
		assertTrue(IntervalConstants.WHOLE.negative().isWhole());
	}

	@Test
	public void testIntervalToString() {
		assertEquals("Interval [-1.0, 1.0]", interval(-1, 1).toString());
	}

	@Test
	public void testIntervalSingletonToString() {
		assertEquals("Interval [-1.0]", new Interval(-1).toString());
	}

	@Test
	public void testEmptyIntervalToString() {
		assertEquals("Interval []", IntervalConstants.empty().toString());
	}

	@Test
	public void testWholeIntervalToString() {
		assertEquals("Interval [-Infinity, Infinity]", IntervalConstants.WHOLE.toString());
	}

	@Test
	public void testHashCode() {
		assertEquals(Objects.hash(1d, 2d),
				interval(1, 2).hashCode());
	}
}
