package data_structures.network;


public record Location(NetworkComponent currentNetLocation) {

    @Override
    public String toString() {
        return this.currentNetLocation.getId();
    }

}
