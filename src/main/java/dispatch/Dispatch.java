package dispatch;

import entities.*;
import lists.DynamicArray;
import network.Edge;
import network.Location;
import network.Node;
import time.Scheduler;

import java.util.Optional;


public final class Dispatch implements VehicleHiringTest {

    private final DynamicArray<Vehicle> allVehicles = new DynamicArray<>();
    private final DynamicArray<Vehicle> vehiclesOnMap = new DynamicArray<>();

    private final DynamicArray<Party> allParties = new DynamicArray<>();
    private final DynamicArray<Party> partiesOnMap = new DynamicArray<>();



    // ======================== PRIVATE METHODS ========================
    private boolean isVehicleRegistered(Vehicle vehicle) {
        return this.allVehicles.contains(vehicle);
    }

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
    public void registerVehicle(Vehicle vehicle) {
        allVehicles.addIfNotPresent(vehicle);
    }

    public void registerParty(Party party) {
        allParties.addIfNotPresent(party);
    }

    // ======================== PUBLIC METHODS ========================
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
      
    public void pickUpParty(Vehicle taxi,Party party) {
        if (taxi.getLocation().getCurrentNetLocation().equals(party.getLocation().getCurrentNetLocation())) {
            taxi.setParty(party);
            party.setAssigned(true);

        }
        else throw new IllegalArgumentException("Taxi is not at the same location as the party");
    }
      
    public void dropOffParty(Vehicle taxi,Party party) {
        if (taxi.getLocation().getCurrentNetLocation().equals(party.getDestination().getCurrentNetLocation())) {
            taxi.setParty(null);
            // remove party from map
            party.getLocation().getCurrentNetLocation().removeOccupant(party);
            party.setNode(null);
            party.setLocation(null);
            party.setAssigned(false);
            // remove party from list
            this.allParties.removeIfPresent(party);
            this.partiesOnMap.removeIfPresent(party);

        }
        else throw new IllegalArgumentException("Taxi is not at the same location as the party");



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
            Scheduler.scheduleJourney(taxi, party, (Node) party.getDestination().getCurrentNetLocation());
        }
        else {throw new IllegalArgumentException("No taxi found");}
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
