import DB.AttackHelper;
import Globals.DBHelpers;
import DB.LocalHostConnection;
import Globals.Helpers;
import Main.*;
import Threading.MotherThreadController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
//        verifyAllMatchTest();

    }

    public static void verifyAllMatchTest(){
        DBHelpers.setGlobalConnection(new LocalHostConnection());
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());

//        try(Connection C = DBHelpers.getGlobalConnection().getConnection()){
//            Statement st = C.createStatement();
//            st.execute("UPDATE tblmatch SET winner = 0 WHERE 1");
//            Thread.sleep(1000);
//        } catch (SQLException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        ArrayList<MatchFacade> Matches = dbh.getAllUnverifiedMatches();
        System.out.println("Done Getting Fights");
        HashMap<Integer,Cock> allcocks = dbh.getAllCockData();
        MotherThreadController MTC = new MotherThreadController(Matches,5);
        Thread MotherThread = new Thread(MTC);
        MotherThread.start();
        while(MotherThread.isAlive()){
            System.out.println("Mother is Looping");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void Test2(){
        DBHelpers dbh = new DBHelpers(new LocalHostConnection());
        //dbh.CreateAccount("PcRestarting","PcResting","12345"); // Create Account
    }
    public void Test3(){
        //Sending cock data
        DBHelpers.setGlobalConnection(new LocalHostConnection());
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());

        HashMap<Integer,Attack> allAttacks = dbh.getAllAttacks();
        dbh.LoginUser("PcResting","12345");
        Attack Bash = AttackHelper.cloneAttack(allAttacks.get(1));
        Cock cock = new Cock("Cockers",100,10,5,User.getCurrUser().getUserID());
        cock.addAttack(Bash);
        dbh.SendCockData(cock);

    }

    //Changes from skeleton
    //Login User changed from returning User to returning boolean(isSuccess) data can be accessed on User.getCurrUser;
}
