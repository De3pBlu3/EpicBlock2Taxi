package time;

import dispatch.Dispatch;
import entities.Party;
import entities.Taxi;

public class DropOff extends Event {
    private final Taxi taxi;
    private final Party party;
    private final Dispatch dispatch;

    public DropOff(Tick tick, Taxi taxi, Party party, Dispatch dispatch) {
        super(tick);
        this.taxi = taxi;
        this.party = party;
        this.dispatch = dispatch;
    }

    @Override
    public void execute() {
        try {
        dispatch.dropOffParty(taxi, party);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}