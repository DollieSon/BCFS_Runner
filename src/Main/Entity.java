package Main;

import java.util.ArrayList;
import java.util.Collections;

public class Entity {
    public static int MAXATTACKS = 3;
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
        this.AttackList = new ArrayList<>();
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
    public Entity addAttack(Attack newAttack) throws ArrayIndexOutOfBoundsException{
        if(AttackList.size() > MAXATTACKS){
            throw new ArrayIndexOutOfBoundsException("AttackList is Full");
        }
        AttackList.add(newAttack);
        newAttack.setOwner(this);
        return this;
    }

    public String getName() {
        return Name;
    }
    public Entity resetCurrent(){
        int[][] AllStats = {Damage,Health,Speed};
        for(int[] specStat : AllStats){
            specStat[StatType.Current.ordinal()] =  specStat[StatType.Base.ordinal()] +  specStat[StatType.Bonus.ordinal()];
        }
        for(Attack atk: AttackList){
            atk.resetCurrSpeed();
        }
        return this;
    }
    @Override
    public String toString(){
        StringBuilder AttackString = new StringBuilder();
        for(Attack attack: AttackList){
            AttackString.append("\t\t" + attack.toString() + "\n");
        }
        return String.format("Name: %s \n\tHP: %d/%d \n\tDamage: %d\t%d\t%d\n\tSpeed: %d\t%d\t%d\n",
                Name,getStat(StatName.Health,StatType.Current),getStat(StatName.Health,StatType.Base) + getStat(StatName.Health,StatType.Bonus),
                //Damage
                Damage[StatType.Base.ordinal()],Damage[StatType.Bonus.ordinal()],Damage[StatType.Current.ordinal()],
                //Speed
                Speed[StatType.Base.ordinal()],Speed[StatType.Bonus.ordinal()],Speed[StatType.Current.ordinal()]
        )
                + AttackString;
    }
}
