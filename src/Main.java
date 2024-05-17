import Attacks.AttackHelper;
import DB.DBHelpers;
import DB.LocalHostConnection;
import Main.*;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //Get Connection

        //Login/Sign-Up

        /*
        * 1. Create Cock
        * 2. Challange Player
        * 3. Get Challenges
        * 4. */

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

    //Changes from skeleton
    //Login User changed from returning User to returning boolean(isSuccess) data can be accessed on User.getCurrUser;
}
