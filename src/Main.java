import Attacks.Leech;
import Attacks.SingleAttack;
import Main.*;
public class Main {
    public static void main(String[] args) {
        SingleAttack Bash = new SingleAttack("Bash",22,2,1.1);
        SingleAttack Bite = new SingleAttack("Bite",41,3,1.16);
        SingleAttack Bite2 = new SingleAttack("Bite Lvl 2",10,2,1.33);
        SingleAttack Bash2 = new SingleAttack("Bash Lvl 2",40,4,1.2);
        SingleAttack Eat = new SingleAttack("Eat",2,1,1);
        Entity Player = new Entity("Co2",1000,21,33);
        Player.addAttack(Bash2)
                .addAttack(Bite2);

        Entity Enemy = new Entity("Globlin",450,22,10);
        Enemy.addAttack(Bash)
            .addAttack(Bite);
        Helpers.Fight(Player,Enemy);
    }
}
