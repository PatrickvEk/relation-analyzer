package org.osmsurround.ra.analyzer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.TestUtils;
import org.osmsurround.ra.data.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class SegmentFactoryTest extends TestBase {

	@Autowired
	private SegmentFactory segmentFactory;

	@Test
	public void testCreateMember() throws Exception {

		assertEqualClass(FlexibleOrderWay.class, segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), ""))
				.getClass());

		assertEqualClass(FixedOrderWay.class,
				segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), "forward")).getClass());
		assertEqualClass(FixedOrderWay.class,
				segmentFactory.createSegment(new Member(TestUtils.asWay(1, 2, 3), "backward")).getClass());
		assertEqualClass(FlexibleRoundaboutWay.class,
				segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "")).getClass());
		assertEqualClass(FixedRoundaboutWay.class,
				segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "forward")).getClass());
		assertEqualClass(FixedRoundaboutWay.class,
				segmentFactory.createSegment(new Member(TestUtils.asWay(10, 11, 12, 13, 10), "backward")).getClass());
	}

	private void assertEqualClass(Class<?> expected, Class<?> actual) {
		assertEquals(expected.getName(), actual.getName());
	}
}
