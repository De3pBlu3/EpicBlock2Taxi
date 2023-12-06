package network;

import entities.Entity;
import lists.DynamicArray;

public class NetworkComponent {
    DynamicArray<Entity> occupants = new DynamicArray<Entity>();

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
