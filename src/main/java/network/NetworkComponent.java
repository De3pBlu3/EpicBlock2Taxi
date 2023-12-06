package network;

import entities.Entity;
import lists.DynamicArray;

public class NetworkComponent {
    DynamicArray<Entity> occupants = new DynamicArray<Entity>();

    /**
     * Returns all edges within a certain range of this node.
     * If the weightLimit is 0, then nothing is returned.
     * @param weightLimit how far down the network to go
     * @return an array of edges within range
     */
    public Edge[] getEdgesInRange(int weightLimit) {
        System.out.println("getEdgesInRange not implemented for generic and edge classes");
        return null;
    }

    public void addOccupant(entities.Entity occupant) {
        // check if occupant is already in occupants
        for (Entity e : occupants) {
            if (e == occupant) {
                return;
            }
        }

        occupants.append(occupant);

    }

    public void removeOccupant(Entity occupant) {
        // check if occupant is already in occupants
        for (entities.Entity e : occupants) {
            if (e == occupant) {
                return;
            }
        }

        occupants.removeAllOccurrences(occupant);

    }

    public DynamicArray<Entity> getOccupants(){
        return occupants;
    }


}
