package dispatch;

import entities.Party;
import entities.Taxi;
import lists.DynamicArray;
import network.Network;
import not_implemented_yet.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class DispatchTest {

    @Test
    public void getVehicleFromReg() {
        DynamicArray<Taxi> allVehicles = new DynamicArray<Taxi>();

        Taxi taxi1 = new Taxi(4, "ABC", 1, null);
        Taxi taxi2 = new Taxi(4, "DEF", 1, null);
        Taxi taxi3 = new Taxi(4, "GHI", 1, null);

        allVehicles.append(taxi1);
        allVehicles.append(taxi2);
        allVehicles.append(taxi3);

        Dispatch dispatch = new Dispatch();

        dispatch.registerVehicle(taxi1);
        dispatch.registerVehicle(taxi2);
        dispatch.registerVehicle(taxi3);

        assertEquals(dispatch.getVehicleFromReg("ABC"), taxi1);
        assertEquals(dispatch.getVehicleFromReg("DEF"), taxi2);
        assertEquals(dispatch.getVehicleFromReg("GHI"), taxi3);



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
        Taxi taxi1 = new Taxi(4, "ABC", 1, Nodeloc1);
        Taxi taxi2 = new Taxi(4, "DEF", 1, EdgeLoc1);
        Taxi taxi3 = new Taxi(4, "GHI", 1, EdgeLoc1);


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
    }
}