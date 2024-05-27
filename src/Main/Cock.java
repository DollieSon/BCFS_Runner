package Main;

import java.util.ArrayList;
import java.util.Collections;

public class Cock implements Cloneable{
    public static int MAX_ATTACKS = 4;
    private String name;
    private ArrayList<Attack> attackList;
    private int[] health;
    private int[] speed;
    private int[] damage;
    private int OwnerID;
    private int CockID;
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

    public Cock setOwnerID(int ownerID) {
        OwnerID = ownerID;
        return this;
    }

    public int getCockID() {
        return CockID;
    }

    public Cock setCockID(int cockID) {
        CockID = cockID;
        return this;
    }

    public Cock(String name, int health, int speed, int damage, int ownerID){
        this.name = name;
        //Base,Bonus,Curr
        this.health = new int[]{health, 0, health};
        this.speed = new int[]{speed, 0, speed};
        this.damage = new int[]{damage, 0, damage};
        this.attackList = new ArrayList<>();
        OwnerID = ownerID;
        CockID = 0;
    }
    public Cock(String Name, int OwnerID){
        this(Name,1000,5,5,OwnerID);
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

    public Cock setStat(StatName statName, StatType statType, int number){
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

    public Cock addStat(StatName statName, StatType statType, int number){
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
        Collections.sort(attackList,new Attack.sortBySpeed());
        return attackList.get(0);
    }

    public boolean setAttack(Attack atk,int index){
        try {
            attackList.set(index, atk);
            return true;
        }catch (IndexOutOfBoundsException e){
            attackList.add(atk);
            return false;
        }
    }

    public Cock setName(String CockName){
        name = CockName;
        return this;
    }

    public Cock addAttack(Attack newAttack) throws ArrayIndexOutOfBoundsException{
        if(attackList.size() > MAX_ATTACKS){
            throw new ArrayIndexOutOfBoundsException("AttackList is Full");
        }
        attackList.add(newAttack);
        newAttack.setOwner(this);
        return this;
    }

    public String getName() {
        return name;
    }
    public Cock resetCurrent(){
        int[][] AllStats = {damage, health, speed};
        for(int[] specStat : AllStats){
            specStat[StatType.BONUS.ordinal()] = 0;
            specStat[StatType.CURRENT.ordinal()] =  specStat[StatType.BASE.ordinal()] +  specStat[StatType.BONUS.ordinal()];
        }
        for(Attack atk: attackList){
            atk.resetCurrSpeed();
        }
        return this;
    }
    @Override
    public String toString(){
        StringBuilder AttackString = new StringBuilder();
        for(Attack attack: attackList){
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
    public ArrayList<Attack> getAttackList(){
        return attackList;
    }
    public int getOwnerID(){
        return OwnerID;
    }


    @Override
    public Cock clone() {
        try {
            Cock cloned = (Cock) super.clone();
            // Deep clone the attackList
            cloned.attackList = new ArrayList<>(this.attackList.size());
            for (Attack attack : this.attackList) {
                cloned.attackList.add(attack.clone()); // Assuming Attack implements Cloneable
                attack.setOwner(cloned);
            }
            // Deep clone the arrays
            cloned.health = this.health.clone();
            cloned.speed = this.speed.clone();
            cloned.damage = this.damage.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }
}
