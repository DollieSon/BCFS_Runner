package Attacks;

import DB.DBHelpers;
import Main.Attack;

import java.sql.Connection;
import java.util.HashMap;

public class AttackHelper {
    public static HashMap<Integer, Attack> AllAttack;
    public static HashMap<Integer,Attack> getAllAttack(){
        if(AllAttack == null) throw new IllegalStateException();
        return AllAttack;
    }
    public static HashMap<Integer,Attack> fetchAllAttack(){
        DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection());
        AllAttack = dbh.getAllAttacks();
        return AllAttack;
    }
    public static int attackModuleToInt(AttackModule am){
        if(am instanceof SingleAttack){
            return 1;
        }else if(am instanceof Heal){
            return 2;
        }else if(am instanceof Leech){
            return 3;
        }
        return 0;
    }
    public static Attack cloneAttack(Attack atk){
        Attack res = new Attack(atk.getName(), atk.getSpeed(), atk.getDamage(), atk.getDamageMultiplier(), atk.getAttackModule(), atk.getAttackID()); // Lazy Clone
        return res;
    }
}
