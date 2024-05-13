import Attacks.Heal;
import Attacks.SingleAttack;
import DB.DBHelpers;
import DB.LocalHostConnection;
import Main.*;

public class Main {
    public static void main(String[] args) {
        DBHelpers dbh = new DBHelpers(new LocalHostConnection());
        dbh.getAllAttacks();
        dbh.LoginUser("PcResting","12345");
    }
    public void Test2(){
        DBHelpers dbh = new DBHelpers(new LocalHostConnection());
        //dbh.CreateAccount("PcRestarting","PcResting","12345"); // Create Account
    }

    public void Test1(){
        Attack Bash = new Attack("Bash",22,2,1.1,new SingleAttack());
        Attack Bite = new Attack("Bite",41,3,1.16,new Heal());
        Attack Bite2 = new Attack("Bite Lvl 2",10,2,1.33,new SingleAttack());
        Attack Bash2 = new Attack("Bash Lvl 2",40,4,1.2,new SingleAttack());
        Attack Eat = new Attack("Eat",2,1,1,new SingleAttack());
        Entity Player = new Entity("Co2",100,21,33);
        Player.addAttack(Bash2)
                .addAttack(Bite2);
        Entity Enemy = new Entity("Globlin",100,22,10);
        Enemy.addAttack(Bash)
                .addAttack(Bite);
        Helpers.Fight(Player,Enemy);
        Helpers.Fight(Player,Enemy);
    }
}
