package data_structures.network;

import entities.Entity;
import data_structures.lists.DynamicArray;
import static misc.Util.randInt;

import java.util.Objects;


public abstract class NetworkComponent {

    final String id;

    DynamicArray<Entity> occupants = new DynamicArray<>();

    public NetworkComponent(String id) {
        // Set random ID if id is null
        this.id = Objects.requireNonNullElseGet(
                id, () -> String.valueOf(randInt(1, Integer.MAX_VALUE))
        );
    }

    public NetworkComponent() {
        this.id = String.valueOf(randInt(1, Integer.MAX_VALUE));
    }

    public String getId() {
        return this.id;
    }

    public DynamicArray<Entity> getOccupants(){
        return occupants;
    }

    /**
     * Returns all edges within a certain range of this node.
     * If the weightLimit is 0, then nothing is returned.
     *
     * @param weightLimit how far down the network to go
     * @return an array of edges within range
     */
    public Edge[] getEdgesInRange(int weightLimit) {
        System.out.println("getEdgesInRange not implemented for generic and edge classes");
        return null;
    }

    /**
     * Adds an entity to the network component if
     * not already present.
     *
     * @param occupant Entity to be added.
     */
    public void addOccupant(Entity occupant) {
        // check if occupant is already in occupants
        for (Entity e : occupants) {
            if (e == occupant) {
                return;
            }
        }

        occupants.append(occupant);
    }

    /**
     * Removes an entity from the network component.
     *
     * @param occupant Entity to be removed.
     */
    public void removeOccupant(Entity occupant) {
        occupants.removeAllOccurrences(occupant);
    }

    abstract public String toString();

    abstract public boolean equals(Object o);

}
