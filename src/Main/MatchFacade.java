package Main;

import Globals.Helpers;

import java.util.HashMap;

public class MatchFacade {
    private int matchID;
    private int invitorCockID;
    private int inviteeCockID;

    public MatchFacade(int matchID, int invitorCockID, int inviteeCockID) {
        this.matchID = matchID;
        this.invitorCockID = invitorCockID;
        this.inviteeCockID = inviteeCockID;
    }
    public int playMatch(HashMap<Integer,Cock> allCocks){
        Cock cock1 = allCocks.get(invitorCockID).clone();
        Cock cock2 = allCocks.get(inviteeCockID).clone();
        return Helpers.Fight(cock1,cock2);

    }
    public int getMatchID() {
        return matchID;
    }
}
