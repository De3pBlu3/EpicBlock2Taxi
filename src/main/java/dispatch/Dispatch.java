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

    public void registerParty(Party party) {
        allParties.addIfNotPresent(party);
    }


    public Optional<Vehicle> getVehicleFromReg(String reg){
        return this.allVehicles.getFirstMatch(
                (v) -> v.getRegistrationNumber().equals(reg)
        );
    }

    private boolean isVehicleRegistered(Vehicle vehicle) {
        return this.allVehicles.contains(vehicle);
    }

    private boolean isVehicleOnMap(Vehicle vehicle) {
        return this.vehiclesOnMap.contains(vehicle);
    }

    public DynamicArray<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public DynamicArray<Vehicle> getVehiclesOnMap() {
        return vehiclesOnMap;
    }

    public void pickUpParty(Vehicle taxi,Party party) {}
    public void dropOffParty(Vehicle taxi,Party party) {}

    public void findNearestApplicableTaxi(Party party) {
        DynamicArray<String> vehiclesInRange = testGetVehiclesInRange(party.getLocation(), 5);
        for (String reg : vehiclesInRange) {
            Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);
            if (vehicle != null) {
                if (vehicle.getCapacity() >= party.getHeadcount()) {
                    if (vehicle.getParty() == null) {
                        party.setAssigned(true);
                        break;
                    }
                }
            }
        }
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

    public Party[] getAllParties() {
        return allParties.toArray();
    }


}
