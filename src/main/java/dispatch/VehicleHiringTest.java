package dispatch;

import data_structures.lists.DynamicArray;
import data_structures.network.Location;

public sealed interface VehicleHiringTest permits Dispatch {

    /**
     * Inserts the vehicle with the registration number {@code reg} to the map
     * at location {@code loc} if it has not already been added to the map.
     *
     * @param reg Registration number of the vehicle.
     * @param loc Location of the vehicle.
     *
     * @return {@code false} if vehicle is not registered or is already on the map;
     * {@code true} otherwise.
     */
    boolean testAddToMap(String reg, Location loc);

    /**
     * Updates the location of the vehicle with the specified registration number {@code reg}
     * to location {@code loc} if it exists on the map.
     *
     * @param reg Registration number of the vehicle.
     * @param loc Location of the vehicle.
     *
     * @return {@code false} if vehicle is not registered or is not on the map;
     * {@code true} otherwise.
     */
    boolean testMoveVehicle(String reg, Location loc);

    /**
     * Removes the vehicle with the specified registration number {@code reg} from the map if it
     * exists on the map.
     *
     * @param reg Registration number of the vehicle.
     * @return {@code false} if vehicle is not registered or is not on the map;
     * {@code true} otherwise.
     */
    boolean testRemoveVehicle(String reg);

    /**
     * Returns the location of the vehicle with the specified registration number if it is
     * registered and on the map; null otherwise.
     *
     * @param reg Registration number of the vehicle.
     */
    Location testGetVehicleLoc(String reg);

    /**
     * Returns a list of registration numbers for all vehicles located within a square of
     * side length {@code 2*r} centered at location {@code loc} (inclusive of the boundaries).
     *
     * @param loc Location that is the center of the bounded area.
     * @param r Radius of the square (1/2 the side length).
     */
    DynamicArray<String> testGetVehiclesInRange(Location loc, int r);

}
