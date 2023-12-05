package network;

public class NetworkComponent {
    protected entities.Entity[] occupants;

    public void addOccupant(entities.Entity occupant) {
        // check if occupant is already in occupants
        for (entities.Entity e : occupants) {
            if (e == occupant) {
                return;
            }
        }

        // create new array with length + 1 TODO dynamic-ify
        entities.Entity[] newOccupants = new entities.Entity[occupants.length + 1];

        // copy
        System.arraycopy(occupants, 0, newOccupants, 0, occupants.length);


        // append
        newOccupants[occupants.length] = occupant;
        occupants = newOccupants;


    }

    public void removeOccupant(entities.Entity occupant) {
        // check if occupant is already in occupants
        for (entities.Entity e : occupants) {
            if (e == occupant) {
                return;
            }
        }

        // create new array with length - 1 TODO dynamic-ify
        entities.Entity[] newOccupants = new entities.Entity[occupants.length - 1];

        for (int i = 0; i < occupants.length; i++) {
            if (occupants[i] != occupant) {
                newOccupants[i] = occupants[i];
            }
        }


    }

    public entities.Entity[] getOccupants(){
        return occupants;
    }


}
