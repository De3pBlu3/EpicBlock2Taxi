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

    /**
     *  Remove the vehicle with the specified reg number from the map if it is registered
     *  and return true. If vehicle is not registered or is not on map the method returns false.
     * @param reg
     * @return
     */
    public boolean testRemoveVehicle(String reg) {
        return false;
    }

    /**
     * Return the location of vehicle specified by the reg number if it is registered and added
     * to the map, null otherwise.
     * @param reg
     * @return
     */
    public Location testGetVehicleLoc(String reg) {
        return null;
    }

    /**
     * Return a list of all vehicles registration numbers located within a square of side 2*r
     * centered at location loc (inclusive of the boundaries).
     * @param loc
     * @param r
     * @return
     */
    public List<String> testGetVehiclesInRange(Location loc, int r) {
        return null;
    }
}
