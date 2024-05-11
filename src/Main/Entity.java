package Main;

import java.util.ArrayList;
import java.util.Collections;

public class Entity {
    public static int MAX_ATTACKS = 3;
    private String name;
    private ArrayList<Attack> attacklist;
    private int[] health;
    private int[] speed;
    private int[] damage;

    public enum StatName {
        HEALTH,
        SPEED,
        DAMAGE
    }
    public enum StatType {
        BASE,
        BONUS,
        CURRENT,
    }

    public Entity(String Name, int Health, int Speed, int Damage){
        this.name = Name;
        //Base,Bonus,Curr
        this.health = new int[]{Health, 0, Health};
        this.speed = new int[]{Speed, 0, Speed};
        this.damage = new int[]{Damage, 0, Damage};
        this.attacklist = new ArrayList<>();
    }

    public int getStat(StatName statName, StatType statType){
        int[] Stat;
        switch (statName){
            case HEALTH:
                Stat = health;
                break;
            case SPEED:
                Stat = speed;
                break;
            case DAMAGE:
                Stat = damage;
                break;
            default:
                //throw error
                return 0;
        }
        return Stat[statType.ordinal()];
    }

    public Entity setStat(StatName statName, StatType statType, int number){
        int[] Stat;
        switch (statName) {
            case HEALTH:
                Stat = health;
                break;
            case SPEED:
                Stat = speed;
                break;
            case DAMAGE:
                Stat = damage;
                break;
            default:
                //throw error
                return this;
        }
        Stat[statType.ordinal()] = number;
        if (statType == StatType.BONUS) Stat[StatType.CURRENT.ordinal()] = Stat[StatType.BASE.ordinal()] + Stat[StatType.BONUS.ordinal()];
        return this;
    }

    public Entity addStat(StatName statName, StatType statType, int number){
        int[] Stat;
        switch (statName){
            case HEALTH:
                Stat = health;
                break;
            case SPEED:
                Stat = speed;
                break;
            case DAMAGE:
                Stat = damage;
                break;
            default:
                //throw error
                return this;
        }
        Stat[statType.ordinal()] += number;
        if(statType == StatType.BONUS) Stat[StatType.CURRENT.ordinal()] = Stat[StatType.BASE.ordinal()] + Stat[StatType.BONUS.ordinal()];
        return this;
    }
    public Attack getAttack(){
        Collections.sort(attacklist,new Attack.sortBySpeed());
        return attacklist.get(0);
    }
    public Entity addAttack(Attack newAttack) throws ArrayIndexOutOfBoundsException{
        if(attacklist.size() > MAX_ATTACKS){
            throw new ArrayIndexOutOfBoundsException("AttackList is Full");
        }
        attacklist.add(newAttack);
        newAttack.setOwner(this);
        return this;
    }

    public String getName() {
        return name;
    }
    public Entity resetCurrent(){
        int[][] AllStats = {damage, health, speed};
        for(int[] specStat : AllStats){
            specStat[StatType.Bonus.ordinal()] = 0;
            specStat[StatType.CURRENT.ordinal()] =  specStat[StatType.BASE.ordinal()] +  specStat[StatType.BONUS.ordinal()];
        }
        for(Attack atk: attacklist){
            atk.resetCurrSpeed();
        }
        return this;
    }
    @Override
    public String toString(){
        StringBuilder AttackString = new StringBuilder();
        for(Attack attack: attacklist){
            AttackString.append("\t\t" + attack.toString() + "\n");
        }
        return String.format("Name: %s \n\tHP: %d/%d \n\tDamage: %d\t%d\t%d\n\tSpeed: %d\t%d\t%d\n",
                name,getStat(StatName.HEALTH,StatType.CURRENT),getStat(StatName.HEALTH,StatType.BASE),
                //Damage
                damage[StatType.BASE.ordinal()], damage[StatType.BONUS.ordinal()], damage[StatType.CURRENT.ordinal()],
                //Speed
                speed[StatType.BASE.ordinal()], speed[StatType.BONUS.ordinal()], speed[StatType.CURRENT.ordinal()]
        )
                + AttackString;
    }
}
