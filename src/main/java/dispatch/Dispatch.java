package dispatch;

import lists.DynamicArray;
import not_implemented_yet.Location;
import entities.Taxi;
import entities.Party;

import java.util.List;

public class Dispatch implements testing.VehicleHiringTest{

    private DynamicArray<Taxi> allVehicles = new DynamicArray<Taxi>();
    private DynamicArray<Taxi> vehiclesOnMap = new DynamicArray<Taxi>();

    private DynamicArray<Party> allParties = new DynamicArray<Party>();
    private DynamicArray<Party> partiesOnMap = new DynamicArray<Party>();

    public void registerVehicle(Taxi vehicle){
        allVehicles.append(vehicle);
    }


    public Taxi getVehicleFromReg(String reg){
        for (Taxi vehicle : allVehicles) {
            if (vehicle.getRegistrationString().equals(reg)) {
                return vehicle;
            }
        }
        return null;
    }

    private boolean isVehicleRegistered(String reg){
        for (Taxi vehicle : allVehicles) {
            if (vehicle.getRegistrationString().equals(reg)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVehicleOnMap(String reg){
        for (Taxi vehicle : vehiclesOnMap) {
            if (vehicle.getRegistrationString().equals(reg)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Inserts the vehicle with registration number reg to the map at location loc
     * if it has not been already added to map. It should return false if the vehicle
     * is not registered or is already on map.
     * @param reg
     * @param loc
     * @return
     */
    public boolean testAddToMap(String reg, Location loc) {
        // TODO could be made more efficient? looping over twice
        // use list.foreach()?
        if (!isVehicleRegistered(reg) || isVehicleOnMap(reg)) {
            return false;
        }

        loc.getCurrentNetLocation().addOccupant(getVehicleFromReg(reg));
        vehiclesOnMap.append(getVehicleFromReg(reg));
        return true;
    }

    /**
     * Update the location of the vehicle with the specified reg number to location loc
     * if vehicle exists and return true. Return false if vehicle not registered or has
     * not been added to the map.
     * @param reg
     * @param loc
     * @return boolean
     */
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
        if (isVehicleRegistered(reg)) {
            isVehicleOnMap(reg);
        }

        Taxi vehicle = getVehicleFromReg(reg);

        Location loc = vehicle.getLoc();

        loc.getCurrentNetLocation().addOccupant(vehicle);
        vehiclesOnMap.removeAllOccurrences(vehicle);
        return true;
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
