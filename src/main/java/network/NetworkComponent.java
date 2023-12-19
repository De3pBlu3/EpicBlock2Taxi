package network;

import entities.Entity;
import lists.DynamicArray;

import java.util.Objects;

import static other.Util.randInt;

public abstract class NetworkComponent {

    final String id;

    DynamicArray<Entity> occupants = new DynamicArray<>();

    public NetworkComponent(String id) {
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
        occupants.removeAllOccurrences(occupant);
    }

    abstract public String toString();

    abstract public boolean equals(Object o);

}
