package time;

import dispatch.Dispatch;
import entities.Party;


// needs to ask dispatch to assign a taxi to the party, then schedule the taxi to move to the party
public class PartyRequestTaxi extends Event{

    private final Party party;
    private final Dispatch dispatch;

    public PartyRequestTaxi(Tick tick, Party party, Dispatch dispatch) {
        super(tick);
        this.party = party;
        this.dispatch = dispatch;
    }

    @Override
    public void execute() {
        dispatch.handlePartyRequest(party);
    }
}
