package org.osmsurround.ra.segment;

import static org.junit.Assert.*;
import static org.osmsurround.ra.TestUtils.*;

import org.junit.Before;
import org.junit.Test;
import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;

public class FlexibleWayTest {

	private FlexibleWay flexibleWay;
	private Node firstNode;
	private Node lastNode;

	@Before
	public void setup() {
		firstNode = getNode(1);
		lastNode = getNode(4);
		flexibleWay = asFlexibleOrderWay(1, 2, 3, 4);
	}

	private void assertCommonNode(long expectedNodeId, long... otherWayNodeIds) {
		Node commonNode = flexibleWay.getCommonNode(asFlexibleOrderWay(otherWayNodeIds));
		assertEquals(getNode(expectedNodeId), commonNode);
	}

	private void assertCanConnect(boolean expected, long... otherWayNodeIds) {
		assertTrue(expected == flexibleWay.canConnect(asFlexibleOrderWay(otherWayNodeIds)));
	}

	@Test
	public void testGetEndPointNodes() throws Exception {
		ConnectableNode endpointNodesReverse = flexibleWay.getEndpointNodes();
		assertTrue(endpointNodesReverse.contains(getNode(1)));
		assertTrue(endpointNodesReverse.contains(getNode(2)));
		assertTrue(endpointNodesReverse.contains(getNode(3)));
		assertTrue(endpointNodesReverse.contains(getNode(4)));
	}

	@Test
	public void testGetSegmentNodes() throws Exception {
		SegmentNodes segmentNodes = flexibleWay.getSegmentNodes();
		assertEquals(firstNode, segmentNodes.getThisNode());
		assertEquals(lastNode, segmentNodes.getOtherNode());
	}

	@Test
	public void testGetCommonNode() throws Exception {
		assertCommonNode(4, 6, 4, 7);
		assertCommonNode(1, 1, 6, 7);
		assertCommonNode(4, 4, 6, 7);
		assertCommonNode(4, 5, 4, 7);
		assertCommonNode(3, 5, 3, 7);
	}

	@Test(expected = AnalyzerException.class)
	public void testGetCommonNodeFail() throws Exception {
		flexibleWay.getCommonNode(asFlexibleOrderWay(7, 8, 9));
	}

	@Test
	public void testCanConnect() throws Exception {
		assertCanConnect(true, 6, 4, 7);
		assertCanConnect(true, 1, 6, 7);
		assertCanConnect(true, 4, 6, 7);
		assertCanConnect(true, 5, 4, 7);
		assertCanConnect(true, 5, 3, 7);
		assertCanConnect(false, 7, 8, 9);
	}
}