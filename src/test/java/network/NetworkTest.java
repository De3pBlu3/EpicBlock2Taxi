package network;

import org.junit.Test;

import static org.junit.Assert.*;

public class NetworkTest {

    @Test
    public void addEdge() {
        // add edge to network with nodes
        Network testNet = new Network();
        Node node1 = new Node("first", 0,0);
        Node node2 = new Node("second", 1,0);
        testNet.addEdge(node1, node2);

        assertEquals(1, testNet.getEdges().length);
        assertEquals(node1, testNet.getEdges()[0].start);
        assertEquals(node2, testNet.getEdges()[0].end);
        assertEquals(node1.edges[0], testNet.getEdges()[0]);
        assertEquals(node2.edges[0], testNet.getEdges()[0]);

    }

    @Test
    public void testAddEdge() {
        // add edge to network with node IDs
        Network testNet = new Network();
        testNet.addEdge("first", "second");

        assertEquals(1, testNet.getEdges().length);
        assertEquals("first", testNet.getEdges()[0].start.id);
        assertEquals("second", testNet.getEdges()[0].end.id);
        assertEquals(testNet.getEdges()[0].start.edges[0], testNet.getEdges()[0]);
        assertEquals(testNet.getEdges()[0].end.edges[0], testNet.getEdges()[0]);
    }

    @Test
    public void testAddEdge1() {
        // add edge to network with node IDs and weight
        Network testNet = new Network();
        testNet.addEdge("first", "second", 5);

        assertEquals(1, testNet.getEdges().length);
        assertEquals("first", testNet.getEdges()[0].start.id);
        assertEquals("second", testNet.getEdges()[0].end.id);
        assertEquals(5, testNet.getEdges()[0].weight);
        assertEquals(testNet.getEdges()[0].start.edges[0], testNet.getEdges()[0]);
        assertEquals(testNet.getEdges()[0].end.edges[0], testNet.getEdges()[0]);

    }

    @Test
    public void testAddEdge2() {
        // add edge to network with node IDs and weight
        Network testNet = new Network();
        testNet.addEdge("first", "second", 5);
        testNet.addEdge("first", "second", 5);

        assertEquals(1, testNet.getEdges().length);
        assertEquals("first", testNet.getEdges()[0].start.id);
        assertEquals("second", testNet.getEdges()[0].end.id);
        assertEquals(5, testNet.getEdges()[0].weight);
        assertEquals(testNet.getEdges()[0].start.edges[0], testNet.getEdges()[0]);
        assertEquals(testNet.getEdges()[0].end.edges[0], testNet.getEdges()[0]);
    }

    @Test
    public void testAddEdgeUnhappy() {
        // add edge to network with node IDs and weight
        Network testNet = new Network();
        testNet.addEdge("first", "second", 5);
        testNet.addEdge("first", "second", 5);
        testNet.addEdge("first", "second", 5);

        assertEquals(2, testNet.getNodes().length);
        assertEquals(1, testNet.getEdges().length);

        assertEquals("first", testNet.getEdges()[0].start.id);
        assertEquals("second", testNet.getEdges()[0].end.id);
        assertEquals(5, testNet.getEdges()[0].weight);
        assertEquals(testNet.getEdges()[0].start.edges[0], testNet.getEdges()[0]);
        assertEquals(testNet.getEdges()[0].end.edges[0], testNet.getEdges()[0]);
    }



    @Test
    public void getNodes() {
        Network testNet = new Network();
        Node node1 = new Node("first", 0,0);
        Node node2 = new Node("second", 1,0);
        assertEquals(0, testNet.getNodes().length);

        testNet.addEdge(node1, node2);

        assertEquals(2, testNet.getNodes().length);
        assertArrayEquals(new Node[]{node1, node2}, testNet.getNodes());
        testNet.addEdge(node1, node2);
        assertEquals(2, testNet.getNodes().length);
        assertArrayEquals(new Node[]{node1, node2}, testNet.getNodes());


    }

    @Test
    public void getEdges() {
        Network testNet = new Network();
        Node node1 = new Node("first", 0,0);
        Node node2 = new Node("second", 1,0);
        assertEquals(0, testNet.getEdges().length);

        testNet.addEdge(node1, node2);

        assertEquals(1, testNet.getEdges().length);
        assertEquals(node1, testNet.getEdges()[0].start);
        assertEquals(node2, testNet.getEdges()[0].end);
        assertEquals(node1.edges[0], testNet.getEdges()[0]);
        assertEquals(node2.edges[0], testNet.getEdges()[0]);

        testNet.addEdge(node1, node2);
        assertEquals(1, testNet.getEdges().length);
        assertEquals(node1, testNet.getEdges()[0].start);
        assertEquals(node2, testNet.getEdges()[0].end);
        assertEquals(node1.edges[0], testNet.getEdges()[0]);
        assertEquals(node2.edges[0], testNet.getEdges()[0]);

        testNet.addEdge("first", "second");
        assertEquals(1, testNet.getEdges().length);
        assertEquals(node1, testNet.getEdges()[0].start);
        assertEquals(node2, testNet.getEdges()[0].end);
        assertEquals(node1.edges[0], testNet.getEdges()[0]);
        assertEquals(node2.edges[0], testNet.getEdges()[0]);

    }

    @Test
    public void FindPathWithNode() {
        Network testNet = new Network();
        Node node1 = new Node("first", 0,0);
        Node node2 = new Node("second", 0,0);
        Node node3 = new Node("third", 0,0);
        Node node4 = new Node("fourth", 0,0);
        Node node5 = new Node("fifth", 0,0);
        Node node6 = new Node("sixth", 0,0);
        Node node7 = new Node("seventh", 0,0);

        testNet.addEdge(node1, node3, 6);
        testNet.addEdge(node1, node2, 2);
        testNet.addEdge(node2, node4, 5);
        testNet.addEdge(node3, node4, 8);
        testNet.addEdge(node4, node5, 10);
        testNet.addEdge(node4, node6, 15);
        testNet.addEdge(node6, node7, 6);
        testNet.addEdge(node5, node7, 2);


        Node[] path = testNet.findPath(node1, node7);
        assertArrayEquals(new Node[]{node1, node2, node4, node5, node7}, path);

    }

    @Test
    public void testFindPathWithString() {
        Network network = new Network();
        network.addEdge("0", "2", 6);
        network.addEdge("0", "1", 2);
        network.addEdge("1", "3", 5);
        network.addEdge("2", "3", 8);
        network.addEdge("3", "4", 10);
        network.addEdge("3", "5", 15);
        network.addEdge("5", "6", 6);
        network.addEdge("4", "6", 2);
        assertArrayEquals(network.findPath("0", "6"), new Node[]{network.getNode("0"), network.getNode("1"), network.getNode("3"), network.getNode("4"), network.getNode("6")});
    }

    @Test
    public void testFindPathExceptions(){
        Network network = new Network();
        network.addEdge("0", "2", 6);
        network.addEdge("0", "1", 2);
        network.addEdge("1", "3", 5);
        network.addEdge("2", "3", 8);
        network.addEdge("3", "4", 10);
        network.addEdge("3", "5", 15);
        network.addEdge("5", "6", 6);
        network.addEdge("4", "6", 2);
        try{
            network.findPath("0", "7");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Destination node not found in network", e.getMessage());
        }
        try{
            network.findPath("7", "0");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch(IllegalArgumentException e){
            assertEquals("Source node not found in network", e.getMessage());
        }
    }

    @Test
    public void testFindPathUnhappy(){
        Network test_net1 = new Network();
        test_net1.addEdge("0", "1", 1);

        Node[] path = test_net1.findPath("0", "1");
        assertArrayEquals(new Node[]{test_net1.getNode("0"), test_net1.getNode("1")}, path);
    }
}