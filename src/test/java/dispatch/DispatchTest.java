package dispatch;

import entities.Party;
import entities.Taxi;
import lists.DynamicArray;
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