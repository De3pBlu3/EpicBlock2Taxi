package dispatch;

import entities.Taxi;
import lists.DynamicArray;
import network.Network;
import network.Location;
import network.Node;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DispatchTest {

    @Test
    public void getVehicleFromReg() {
        DynamicArray<Taxi> allVehicles = new DynamicArray<Taxi>();

        Taxi taxi1 = new Taxi(1, "ABC", null);
        Taxi taxi2 = new Taxi(1, "DEF", null);
        Taxi taxi3 = new Taxi(1, "GHI", null);

        allVehicles.append(taxi1);
        allVehicles.append(taxi2);
        allVehicles.append(taxi3);

        Dispatch dispatch = new Dispatch();

        dispatch.registerVehicle(taxi1);
        dispatch.registerVehicle(taxi2);
        dispatch.registerVehicle(taxi3);

        dispatch.getVehicleFromReg("ABC").ifPresent(
                (value) -> assertEquals(value, taxi1)
        );

        dispatch.getVehicleFromReg("DEF").ifPresent(
                (value) -> assertEquals(value, taxi2)
        );

        dispatch.getVehicleFromReg("GHI").ifPresent(
                (value) -> assertEquals(value, taxi3)
        );


    }

    @Test
    public void testAddToMap() {
        Network testNet = new Network();
        Dispatch dispatch = new Dispatch();

        // set up network
        testNet.addEdge("first", "second", 5);
        testNet.addEdge("second", "third", 5);
        testNet.addEdge("third", "first", 5);

        // set up loc
        Location Nodeloc1 = new Location(testNet.getNode("first"));     // node 1
        Location Nodeloc2 = new Location(testNet.getNode("second"));    // node 2

        Location EdgeLoc1 = new Location(testNet.getEdge("first", "second"));   // edge 1
        Location EdgeLoc2 = new Location(testNet.getEdge("second", "third"));   // edge 2


        // set up taxis
        Taxi taxi1 = new Taxi(1, "ABC", Nodeloc1);
        Taxi taxi2 = new Taxi(1, "DEF", EdgeLoc1);
        Taxi taxi3 = new Taxi(1, "GHI", EdgeLoc1);


        // ==================== TAXI 1 - NODE 1 ====================
        assertFalse(dispatch.testAddToMap("ABC", Nodeloc1));    // can't add taxi1 to map because it's not registered

        dispatch.registerVehicle(taxi1);

        assertTrue(dispatch.testAddToMap("ABC", Nodeloc1));
        assertFalse(dispatch.testAddToMap("ABC", Nodeloc1));
        assertFalse(dispatch.testAddToMap("ABC", Nodeloc2));

        // test if taxi is on map
        assertArrayEquals(testNet.getNode("first").getOccupants().toArray(), new Taxi[]{taxi1});


        // ==================== TAXI 2 - EDGE 1 ====================
        dispatch.registerVehicle(taxi2);

        assertTrue(dispatch.testAddToMap("DEF", EdgeLoc1));
        assertFalse(dispatch.testAddToMap("DEF", EdgeLoc1));
        assertFalse(dispatch.testAddToMap("DEF", EdgeLoc2));
        assertArrayEquals(testNet.getEdge("first", "second").getOccupants().toArray(), new Taxi[]{taxi2});


        // ==================== TAXI 3 - EDGE 1 (multiple occupants) ====================
        dispatch.registerVehicle(taxi3);
        assertTrue(dispatch.testAddToMap("GHI", EdgeLoc1));
        assertFalse(dispatch.testAddToMap("GHI", EdgeLoc1));
        assertFalse(dispatch.testAddToMap("GHI", EdgeLoc2));

        assertArrayEquals(testNet.getEdge("first", "second").getOccupants().toArray(), new Taxi[]{taxi2, taxi3});






    }

    @Test
    public void testMoveVehicle() {
    }

    @Test
    public void testRemoveVehicle() {
    }

    @Test
    public void testGetVehicleLoc() {
    }

    @Test
    public void testGetVehiclesInRange() {
        Network testNet = new Network();
        Node node1 = new Node("A", 0,0);
        Node node2 = new Node("B", 0,0);
        Node node3 = new Node("C", 0,0);
        Node node4 = new Node("D", 0,0);
        Node node5 = new Node("E", 0,0);
        Node node6 = new Node("F", 0,0);
        Node node7 = new Node("G", 0,0);

        testNet.addEdge(node1, node3, 6);
        testNet.addEdge(node1, node2, 2);
        testNet.addEdge(node2, node4, 5);
        testNet.addEdge(node3, node4, 8);
        testNet.addEdge(node4, node5, 10);
        testNet.addEdge(node4, node6, 15);
        testNet.addEdge(node6, node7, 6);
        testNet.addEdge(node5, node7, 2);

        // set up loc
        Location Nodeloc1 = new Location(testNet.getNode("A"));     // node 1
        Location Nodeloc2 = new Location(testNet.getNode("B"));    // node 2
        Location Nodeloc3 = new Location(testNet.getNode("C"));     // node 3
        Location Nodeloc4 = new Location(testNet.getNode("D"));    // node 4
        Location Nodeloc5 = new Location(testNet.getNode("E"));     // node 5
        Location Nodeloc6 = new Location(testNet.getNode("F"));    // node 6
        Location Nodeloc7 = new Location(testNet.getNode("G"));     // node 7

        // set up taxis
        Taxi taxi1 = new Taxi(1, "ABC", Nodeloc1);
        Taxi taxi2 = new Taxi(1, "DEF", Nodeloc2);
        Taxi taxi3 = new Taxi(1, "GHI", Nodeloc3);
        Taxi taxi4 = new Taxi(1, "JKL", Nodeloc4);
        Taxi taxi5 = new Taxi(1, "MNO", Nodeloc5);
        Taxi taxi6 = new Taxi(1, "PQR", Nodeloc6);
        Taxi taxi7 = new Taxi(1, "STU",  Nodeloc7);


        Dispatch dispatch = new Dispatch();
        dispatch.registerVehicle(taxi1);
        dispatch.registerVehicle(taxi2);
        dispatch.registerVehicle(taxi3);
        dispatch.registerVehicle(taxi4);
        dispatch.registerVehicle(taxi5);
        dispatch.registerVehicle(taxi6);
        dispatch.registerVehicle(taxi7);

        // add vehicles to map
        dispatch.testAddToMap("ABC", Nodeloc1);
        dispatch.testAddToMap("DEF", Nodeloc2);
        dispatch.testAddToMap("GHI", Nodeloc3);
        dispatch.testAddToMap("JKL", Nodeloc4);
        dispatch.testAddToMap("MNO", Nodeloc5);
        dispatch.testAddToMap("PQR", Nodeloc6);
        dispatch.testAddToMap("STU", Nodeloc7);



        System.out.println(dispatch.testGetVehiclesInRange(Nodeloc1, 0));
        DynamicArray<String> taxisInRange = new DynamicArray<>();
        taxisInRange.append(taxi1.getRegistrationNumber());

        // ==================== TAXI 1 - NODE 1 ====================
        assertEquals(dispatch.testGetVehiclesInRange(Nodeloc1, 0).get(0), taxisInRange.get(0));

        // ==================== All ====================
        taxisInRange.append(taxi2.getRegistrationNumber());
        taxisInRange.append(taxi3.getRegistrationNumber());
        taxisInRange.append(taxi4.getRegistrationNumber());
        taxisInRange.append(taxi5.getRegistrationNumber());
        taxisInRange.append(taxi6.getRegistrationNumber());
        taxisInRange.append(taxi7.getRegistrationNumber());


        for (int i = 0; i < 7; i++ ){
        }

    }

    private void assertListsEqualsIgnoreOrder(List<String> strings, List<String> taxisInRange) {
        assertEquals(strings.size(), taxisInRange.size());
        for (String s : strings) {
            assertTrue(taxisInRange.contains(s));
        }
    }


}