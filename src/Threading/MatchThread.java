package Threading;

import Main.Cock;
import Main.MatchFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchThread implements Runnable{
    private ArrayList<MatchFacade> matchQueue;
    private HashMap<Integer,Integer> TaskCheck;
    private HashMap<Integer,Cock> CockMap;
    private int qSize;
    private int currentItem;

    public MatchThread(HashMap<Integer,Integer> tc, HashMap<Integer, Cock> cockMap){
        TaskCheck = tc;
        matchQueue = new ArrayList<>();
        CockMap = cockMap;
    }

    public MatchThread addTask(MatchFacade mf){
        matchQueue.add(mf);
        qSize = matchQueue.size();
        return this;
    }

    public int getCurrentItem(){
        return currentItem;
    }
    public int getqSize(){
        return qSize;
    }
    public boolean isempty(){
        return matchQueue.isEmpty();
    }
    @Override
    public void run() {
        currentItem = 0;
        for(MatchFacade mf: matchQueue){
            int winner = mf.playMatch(CockMap);
            //System.out.println(mf.getMatchID() + "," + winner);
            TaskCheck.put(mf.getMatchID(),winner);
            currentItem++;
            /*try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
        }
    }
}
