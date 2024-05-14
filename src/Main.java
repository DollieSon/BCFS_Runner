import Attacks.AttackHelper;
import DB.DBHelpers;
import DB.LocalHostConnection;
import Main.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        DBHelpers.setGlobalConnection(new LocalHostConnection());
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());

        ArrayList<Integer> challenges = dbh.getChallenges(2);
        System.out.println("Done");
    }
    public void Test2(){
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
    public void Test4(){
        DBHelpers.setGlobalConnection(new LocalHostConnection());
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());

        dbh.LoginUser("PcResting","12345");
        Cock mine = new Cock("Hello",100,10,1,User.getCurrUser().getUserID());
        HashMap<Integer,Attack> allAttacks = dbh.getAllAttacks();
        mine.addAttack(AttackHelper.cloneAttack(allAttacks.get(1)));
        mine.addAttack(AttackHelper.cloneAttack(allAttacks.get(2)));
        dbh.ChallangePlayer(true,mine,2);
    }
}
