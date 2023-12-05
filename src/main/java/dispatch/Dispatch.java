package dispatch;

import not_implemented_yet.Location;

import java.util.List;

public class Dispatch implements testing.VehicleHiringTest{

    private entities.Taxi[] vehicles;

    private entities.Party[] parties;


    /**
     * Inserts the vehicle with registration number reg to the map at location loc
     * if it has not been already added to map. It should return false if the vehicle
     * is not registered or is already on map.
     * @param reg
     * @param loc
     * @return
     */
    public boolean testAddToMap(String reg, Location loc) {
        return false;
    }

    public boolean testMoveVehicle(String reg, Location loc) {
        return false;
    }

    public boolean testRemoveVehicle(String reg) {
        return false;
    }

    public Location testGetVehicleLoc(String reg) {
        return null;
    }

    public List<String> testGetVehiclesInRange(Location loc, int r) {
        return null;
    }
}
