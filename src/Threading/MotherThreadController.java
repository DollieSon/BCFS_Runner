package Threading;



import Globals.DBHelpers;
import Main.Cock;
import Main.MatchFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class MotherThreadController implements Runnable{
    private int num_threads;
    private ArrayList<MatchThread> ChildThreads;
    private HashMap<Integer,Integer> ThreadResults;
    private boolean isFinished;

    public MotherThreadController(ArrayList<MatchFacade> mfs, int threads){
        isFinished = false;
        num_threads = threads;
        ChildThreads = new ArrayList<>();
        ThreadResults = new HashMap<>();
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());
        HashMap<Integer, Cock> allcock = dbh.getAllCockData();
        prepareThreads(allcock,mfs);
    }

    public MotherThreadController(ArrayList<MatchFacade> mfs, int threads, HashMap<Integer,Cock> customC){
        isFinished = false;
        num_threads = threads;
        ChildThreads = new ArrayList<>();
        ThreadResults = new HashMap<>();
        prepareThreads(customC,mfs);

    }
    private void prepareThreads(HashMap<Integer,Cock> allcock,ArrayList<MatchFacade> mfs){
        for(int x=0;x<num_threads;x++){
            ChildThreads.add(new MatchThread(ThreadResults,allcock));
        }
        for(int x=0;x<mfs.size();x++){
            MatchFacade mf = mfs.get(x);
            System.out.println("Inserting Match: " + mf.getMatchID() );
            ChildThreads.get(x%num_threads).addTask(mf);
        }
    }

    public HashMap<Integer, Integer> getThreadResults() {
        return ThreadResults;
    }

    public boolean isFinished() {
        return isFinished;
    }

    private void startMatches(){
        ArrayList<Thread> runs = new ArrayList<>();
        boolean isempty = true;
        for(MatchThread mt: ChildThreads){
            Thread child = new Thread(mt);
            runs.add(child);
            child.start();
        }
        for(MatchThread mt:ChildThreads){
            if(!mt.isempty()) isempty = false;
        }
        if(isempty) return;
        do{
            isFinished = true;
            for(Thread run : runs){
                if(run.isAlive()){
                    isFinished = false;
                    break;
                }
            }
            for(int x=0;x<ChildThreads.size();x++){
                MatchThread child = ChildThreads.get(x);
                System.out.println(x+" Status: "+child.getCurrentItem());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }while (!isFinished);
        //Batch Send Result
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());
    }

    @Override
    public void run() {
        startMatches();
    }
}
