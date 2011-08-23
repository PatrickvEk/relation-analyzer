package org.osmsurround.ra.web;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.HelperService;
import org.osmsurround.ra.SegmentsBuilder;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IntersectionNodeWebCreatorTest extends TestBase {

	@Autowired
	private HelperService helperService;

	@Test(expected = AnalyzerException.class)
	public void testCreateWebOneSegment() throws Exception {
		executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2, 3, 4));
	}

	@Test
	public void testCreateWebTwoWay() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(2, 3));
		assertContainsOnlyNodeIds(leaves, 1, 3);
	}

	@Test
	public void testCreateWebTwoWayFixed() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 2)
				.appendFixed(2, 3));
		assertContainsOnlyNodeIds(leaves, 1, 3);
	}

	@Test
	@Ignore
	public void testCreateWebEdgeOrder() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(4, 5)
				.appendFlexible(4, 3).appendFlexible(3, 2).appendFlexible(2, 1));

		assertEquals(2, leaves.size());
		Iterator<IntersectionNode> it = leaves.iterator();

		IntersectionNode node1 = it.next();
		IntersectionNode node2 = it.next();

		assertEquals(getNode(1), node1.getNode());
		assertEquals(getNode(5), node2.getNode());

	}

	@Test
	public void testCreateWebCircle() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(2, 3).appendFlexible(3, 1));
		assertEquals(1, leaves.size());
	}

	@Test
	public void testCreateWebStarFlexible() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 4)
				.appendFlexible(4, 5).appendFlexible(4, 6));
		assertContainsOnlyNodeIds(leaves, 1, 5, 6);
	}

	@Test
	public void testCreateWebStarFixed() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 4)
				.appendFixed(5, 4).appendFixed(4, 6));
		assertContainsOnlyNodeIds(leaves, 1, 5, 6);
	}

	@Test
	public void testCreateWebDoubleStarFlexible() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFlexible(3, 2).appendFlexible(2, 4).appendFlexible(5, 4));
		assertContainsOnlyNodeIds(leaves, 1, 5, 3);
	}

	@Test
	public void testCreateWebDoubleStarFixed() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFixed(1, 2)
				.appendFixed(3, 2).appendFixed(2, 4).appendFixed(5, 4));
		assertContainsOnlyNodeIds(leaves, 1, 5, 3);
	}

	@Test
	public void testCreateWebDoubleLaneMixed() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFixed(2, 3).appendFixed(3, 4).appendFlexible(4, 5).appendFixed(4, 6).appendFixed(6, 2));
		assertContainsOnlyNodeIds(leaves, 1, 5);
	}

	@Test
	public void testCreateWebRoundabout() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(5, 4)
				.appendRoundabout(10, 5, 11, 6, 10).appendFlexible(6, 7));
		assertContainsOnlyNodeIds(leaves, 4, 7);
	}

	@Test
	public void testCreateWebLineFlexible() throws Exception {
		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2, 3)
				.appendFlexible(4, 3).appendFlexible(5, 6, 1));
		assertContainsOnlyNodeIds(leaves, 4, 5);
	}

	@Test
	public void testCreateWebDoubleLaneRoundabout() throws Exception {

		Collection<IntersectionNode> leaves = executeAndGetLeaves(SegmentsBuilder.create().appendFlexible(1, 2)
				.appendFixed(2, 5).appendFixed(5, 6).appendRoundabout(10, 4, 6, 7, 8, 9, 10).appendFixed(8, 12)
				.appendFixed(12, 13).appendFlexible(13, 14).appendFixed(13, 11).appendFixed(11, 9).appendFixed(4, 3)
				.appendFixed(3, 2));
		assertContainsOnlyNodeIds(leaves, 1, 14);
	}

	@Test
	public void testRelation12320() throws Exception {

		AnalyzerContext analyzerContext = helperService.createIntersectionWebContext(RELATION_12320_NECKARTAL_WEG);
		assertEquals(1, analyzerContext.getGraphs().size());
		Collection<IntersectionNode> leaves = analyzerContext.getGraphs().get(0).getLeaves();

		assertEquals(2, leaves.size());
		helperService.exportGpx(leaves, RELATION_12320_NECKARTAL_WEG);
	}

	@Test
	public void testRelation959757() throws Exception {

		AnalyzerContext analyzerContext = helperService.createIntersectionWebContext(RELATION_959757_LINE_10);
		assertEquals(1, analyzerContext.getGraphs().size());
		Collection<IntersectionNode> leaves = analyzerContext.getGraphs().get(0).getLeaves();

		assertContainsOnlyNodeIds(leaves, 418151004, 1025039190);// Böckingen, Frankenbach, Hoover-Siedlung (35974263)
		helperService.exportGpx(leaves, RELATION_959757_LINE_10);
	}
}
