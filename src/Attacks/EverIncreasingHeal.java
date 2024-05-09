package Attacks;

import Main.Attack;
import Main.Entity;

public class EverIncreasingHeal extends Attack {
    int currBonus;
    int BonusHeal;
    public EverIncreasingHeal(String name, int speed, int damage, double damageScaler,int BonusHeal) {
        super(name, speed, damage, damageScaler);
        this.BonusHeal = BonusHeal;
        this.currBonus = 0;
    }

    @Override
    public Attack resetCurrSpeed() {
        super.resetCurrSpeed();
        currBonus = 0;
        return this;
    }

    @Override
    public void apply(Entity Owner, Entity Target) {
        int heal = currBonus + getDamage() + (int)(Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * getDamageScaler());
        int newHealth = Owner.getStat(Entity.StatName.Health, Entity.StatType.Current) + heal;
        Owner.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        this.incrementCurrSpeed();
    }
}
