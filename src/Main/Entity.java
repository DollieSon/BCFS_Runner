package Main;

import java.util.ArrayList;
import java.util.Collections;

public class Entity {
    private String Name;
    private ArrayList<Attack> AttackList;
    private int[] Health;
    private int[] Speed;
    private int[] Damage;

    public enum StatName{
        Health,
        Speed,
        Damage
    }
    public enum StatType{
        Base,
        Bonus,
        Current
    }

    public Entity(String Name,int Health,int Speed,int Damage){
        this.Name = Name;
        //Base,Bonus,Curr
        this.Health = new int[]{Health,0,Health};
        this.Speed = new int[]{Speed,0,Speed};
        this.Damage = new int[]{Damage,0,Damage};
    }

    public int getStat(StatName Sn,StatType St){
        int[] Stat;
        switch (Sn){
            case Health:
                Stat = Health;
                break;
            case Speed:
                Stat = Speed;
                break;
            case Damage:
                Stat = Damage;
                break;
            default:
                //throw error
                return 0;
        }
        return Stat[St.ordinal()];
    }

    public Entity setStat(StatName Sn, StatType st, int number){
        int[] Stat;
        switch (Sn){
            case Health:
                Stat = Health;
                break;
            case Speed:
                Stat = Speed;
                break;
            case Damage:
                Stat = Damage;
                break;
            default:
                //throw error
                return this;
        }
        Stat[st.ordinal()] = number;
        return this;
    }
    public Attack getAttack(){
        Collections.sort(AttackList,new Attack.sortBySpeed());
        return AttackList.get(0);
    }
}
