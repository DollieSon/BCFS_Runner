package Attacks;

import Main.Attack;
import Main.Entity;

public class EverIncreasingSpeed extends Attack {
    int BonusSpeed;
    int currBonus;
    public EverIncreasingSpeed(String name, int speed, int damage, double damageScaler,int bonusSpeed) {
        super(name, speed, damage, damageScaler);
        BonusSpeed = bonusSpeed;
    }

    @Override
    public Attack resetCurrSpeed() {
        currBonus = 0;
        super.resetCurrSpeed();
        return this;
    }
    public int getCurrBonus(){
        return super.getCurrSpeed()+currBonus;
    }
    @Override
    public void apply(Entity Owner, Entity Target) {
        int damage =this.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * this.getDamageScaler());
        int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Current) - damage;
        //Apply Damage
        Target.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        currBonus += BonusSpeed;
        this.incrementCurrSpeed();
    }
}
