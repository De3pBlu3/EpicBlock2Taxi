package time;

import dispatch.Dispatch;
import entities.Party;
import entities.Taxi;

public class Pickup extends Event {
    private final Taxi taxi;
    private final Party party;
    private final Dispatch dispatch;

    public Pickup(Tick tick, Taxi taxi, Party party, Dispatch dispatch) {
        super(tick);
        this.taxi = taxi;
        this.party = party;
        this.dispatch = dispatch;
    }

    @Override
    public void execute() {
        dispatch.pickUpParty(taxi, party);
    }
}