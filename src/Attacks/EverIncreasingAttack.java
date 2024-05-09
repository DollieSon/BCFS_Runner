package Attacks;

import Main.Attack;
import Main.Entity;

public class EverIncreasingAttack extends Attack {

    private int BonusDamage;
    private int currBonus;
    public EverIncreasingAttack(String name, int speed, int damage, double damageScaler,int BonusDamage) {
        super(name, speed, damage, damageScaler);
        this.BonusDamage = BonusDamage;
    }

    @Override
    public Attack resetCurrSpeed() {
         super.resetCurrSpeed();
         currBonus =0;
         return this;
    }

    @Override
    public void apply(Entity Owner, Entity Target) {
        int damage =this.currBonus + this.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * this.getDamageScaler());
        int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Current) - damage;
        //Apply Damage
        Target.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        this.incrementCurrSpeed();
        currBonus+=BonusDamage;
    }
}
