package dispatch;

import entities.*;
import data_structures.lists.DynamicArray;
import data_structures.network.Edge;
import data_structures.network.Location;
import data_structures.network.Node;
import time.Scheduler;

import java.util.Optional;


@SuppressWarnings("unused")
public final class Dispatch implements VehicleHiringTest {

    private final DynamicArray<Vehicle> allVehicles = new DynamicArray<>();
    private final DynamicArray<Vehicle> vehiclesOnMap = new DynamicArray<>();
    private final DynamicArray<Party> allParties = new DynamicArray<>();
    private final DynamicArray<Party> partiesOnMap = new DynamicArray<>();


    // ======================== PRIVATE METHODS ========================

    /**
     * Returns whether the given vehicle is present
     * on the map.
     *
     * @param vehicle Vehicle to check presence of.
     */
    private boolean isVehicleOnMap(Vehicle vehicle) {
        return this.vehiclesOnMap.contains(vehicle);
    }

    // ======================== GETTERS ========================

    public DynamicArray<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public DynamicArray<Vehicle> getVehiclesOnMap() {
        return vehiclesOnMap;
    }

    public Party[] getAllParties() {
        Party[] parties = new Party[allParties.size()];
        for (int i = 0; i < allParties.size(); i++) {
            parties[i] = allParties.get(i);
        }
        return parties;
    }

    // ======================== ADD METHODS ========================

    /**
     * Registers the given vehicle.
     *
     * @param vehicle Vehicle to be registered.
     */
    public void registerVehicle(Vehicle vehicle) {
        allVehicles.addIfNotPresent(vehicle);
    }

    /**
     * Registers the given party.
     *
     * @param party Party to be registered.
     */
    public void registerParty(Party party) {
        allParties.addIfNotPresent(party);
    }

    // ======================== PUBLIC METHODS ========================

    /**
     * Returns the registered vehicle whose registration number
     * matches the given one.
     *
     * @param reg Registration number of vehicle.
     */
    public Optional<Vehicle> getVehicleFromReg(String reg){
        return this.allVehicles.getFirstMatch(
                (v) -> v.getRegistrationNumber().equals(reg)
        );
    }

    public void pickUpParty(Vehicle taxi,Party party) {
        if (taxi.getLocation().currentNetLocation().equals(party.getLocation().currentNetLocation())) {
            taxi.setParty(party);
            party.setAssigned(true);
            party.getLocation().currentNetLocation().removeOccupant(party);

        }
        else throw new IllegalArgumentException("Taxi is not at the same location as the party");
    }

    public void dropOffParty(Vehicle taxi,Party party) {
        if (taxi.getLocation().currentNetLocation().equals(party.getDestination().currentNetLocation())) {
            taxi.setParty(null);
            // remove party from map
            party.setLocation(null);
            party.setAssigned(false);
            // remove party from list
            this.allParties.removeIfPresent(party);
            this.partiesOnMap.removeIfPresent(party);
        }
        else {
            throw new IllegalArgumentException("Taxi is not at the same location as the party");
        }

    }

    public Taxi findNearestApplicableTaxi(Party party) {
        int range = 5;
        int maxRange = 100; // Set a maximum range to prevent infinite loop

        while (range <= maxRange) {
            DynamicArray<String> vehiclesInRange = testGetVehiclesInRange(party.getLocation(), range);
            for (String reg : vehiclesInRange) {
                Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);
                if (vehicle != null && !vehicle.isOccupied()) {
                    if (vehicle.getCapacity() >= party.getCount()) {
                        if (vehicle.getParty() == null) {
                            party.setAssigned(true);
                            return (Taxi) vehicle;
                        }
                    }
                }
            }
            range++; // Increase the range if no suitable taxi is found
        }
        return null;
    }

    public void handlePartyRequest(Party party) {
        Taxi taxi = findNearestApplicableTaxi(party);
        if (taxi != null) {
            Scheduler.scheduleJourney(taxi, party, (Node) party.getDestination().currentNetLocation());
        }
        else {throw new IllegalArgumentException("No taxi found");}
    }

    // ======================== MANDATORY TESTING METHODS ========================

    @Override
    public boolean testAddToMap(String reg, Location loc) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null)  // If registered
            if (this.vehiclesOnMap.addIfNotPresent(vehicle)) {  // If vehicle not on map (could add)
                loc.currentNetLocation().addOccupant(vehicle);  // Add to map
                return true;
            }

        return false;
    }

    @Override
    public boolean testMoveVehicle(String reg, Location loc) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null) {  // If registered

            int index = this.vehiclesOnMap.indexOf(vehicle);

            if (index != -1) { // If on map
                vehicle.getLocation().currentNetLocation().removeOccupant(vehicle);  // Remove from old location
                vehicle.setLocation(loc);  // Set new location
                loc.currentNetLocation().addOccupant(vehicle);  // Add to new location
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean testRemoveVehicle(String reg) {

        Vehicle vehicle = this.getVehicleFromReg(reg).orElse(null);

        if (vehicle != null)  // If registered
            if (this.vehiclesOnMap.removeIfPresent(vehicle)) {  // If on map (could remove)
                vehicle.getLocation().currentNetLocation().removeOccupant(vehicle);
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
        Edge[] edgesFromLoc = loc.currentNetLocation().getEdgesInRange(r);

        DynamicArray<Entity> entitiesInNode = loc.currentNetLocation().getOccupants();

        for (Entity e : entitiesInNode) {
            try {
                regNumbers.addIfNotPresent(((Vehicle) e).getRegistrationNumber());
            } catch (ClassCastException ignored) {}
        }

        for (Edge e : edgesFromLoc) {
            for (Vehicle vehicle : this.vehiclesOnMap) {
                if (vehicle.getLocation().currentNetLocation().equals(e.getEnd()) || vehicle.getLocation().currentNetLocation().equals(e.getStart())) {
                    regNumbers.addIfNotPresent(vehicle.getRegistrationNumber());
                }
            }
        }

        return regNumbers;
    }

}
