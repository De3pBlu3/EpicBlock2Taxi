package dispatch;

import entities.*;
import lists.DynamicArray;
import network.Edge;
import network.Location;

import java.util.Optional;


public final class Dispatch implements VehicleHiringTest {

    private final DynamicArray<Vehicle> allVehicles = new DynamicArray<>();
    private final DynamicArray<Vehicle> vehiclesOnMap = new DynamicArray<>();

    private final DynamicArray<Party> allParties = new DynamicArray<>();
    private final DynamicArray<Party> partiesOnMap = new DynamicArray<>();

    public void registerVehicle(Vehicle vehicle) {
        allVehicles.addIfNotPresent(vehicle);
    }

    public Optional<Vehicle> getVehicleFromReg(String reg){
        return this.allVehicles.getFirstMatch(
                (v) -> v.getRegistrationNumber().equals(reg)
        );
    }

    public Vehicle[] getVehiclesOnMap() {
        Vehicle[] vehiclesOnMapArray = new Vehicle[this.vehiclesOnMap.length()];
        for (int i = 0; i < this.vehiclesOnMap.length(); i++) {
            vehiclesOnMapArray[i] = this.vehiclesOnMap.get(i);
        }
        return vehiclesOnMapArray;
    }

    private boolean isVehicleRegistered(Vehicle vehicle) {
        return this.allVehicles.contains(vehicle);
    }

    private boolean isVehicleOnMap(Vehicle vehicle) {
        return this.vehiclesOnMap.contains(vehicle);
    }

    private void MoveVehicleOneStep(Taxi vehicle, Location loc){
        Location oldLoc = vehicle.getLocation();

        // if vehicle is already at location, do nothing
        if (oldLoc.getCurrentNetLocation().equals(loc.getCurrentNetLocation())){
            return;
        }
        // if vehicle is at edge, do not move
        if (oldLoc.getCurrentNetLocation().getClass() == Edge.class){
            return;
        }

        // is there an edge between the two locations?

        oldLoc.getCurrentNetLocation().removeOccupant(vehicle);
        loc.getCurrentNetLocation().addOccupant(vehicle);
        vehicle.setLocation(loc);
    }

    // ======================== MANDATORY TESTING METHODS ========================

    @Override
    public boolean testAddToMap(String reg, Location loc) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null)  // If registered
            if (this.vehiclesOnMap.addIfNotPresent(vehicle)) {  // If vehicle not on map (could add)
                loc.getCurrentNetLocation().addOccupant(vehicle);  // Add to map
                return true;
            }

        return false;
    }

    @Override
    public boolean testMoveVehicle(String reg, Location loc) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null) {  // If registered

            int index = this.vehiclesOnMap.indexOf(vehicle);

            if (index != -1)  // If on map
                // TODO: Update current vehicle location to new location
                return true;
        }

        return false;
    }

    @Override
    public boolean testRemoveVehicle(String reg) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null)  // If registered
            if (this.vehiclesOnMap.removeIfPresent(vehicle)) {  // If on map (could remove)
                vehicle.getLocation().getCurrentNetLocation().removeOccupant(vehicle);
                return true;
            }

        return false;
    }

    @Override
    public Location testGetVehicleLoc(String reg) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null)  // If registered
            if (this.isVehicleOnMap(vehicle))  // If on map
                return vehicle.getLocation();

        return null;
    }

    @Override
    public DynamicArray<String> testGetVehiclesInRange(Location loc, int r) {

        DynamicArray<String> regNumbers = new DynamicArray<>();
        Edge[] edgesFromLoc = loc.getCurrentNetLocation().getEdgesInRange(r);

        DynamicArray<Entity> entitiesInNode = loc.getCurrentNetLocation().getOccupants();

        for (Entity e : entitiesInNode) {
            try {
                regNumbers.addIfNotPresent(((Vehicle) e).getRegistrationNumber());
            } catch (ClassCastException ignored) {}
        }

        for (Edge e : edgesFromLoc) {
            for (Vehicle vehicle : this.vehiclesOnMap) {
                if (vehicle.getLocation().getCurrentNetLocation().equals(e.getEnd()) || vehicle.getLocation().getCurrentNetLocation().equals(e.getStart())) {
                    regNumbers.addIfNotPresent(vehicle.getRegistrationNumber());
                }
            }
        }

        return regNumbers;
    }

}
