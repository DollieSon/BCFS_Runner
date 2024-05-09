package Attacks;

import Main.Attack;
import Main.Entity;

public class Heal extends Attack {

    public Heal(String name, int speed, int damage, double damageScaler) {
        super(name, speed, damage, damageScaler);
    }

    @Override
    public void apply(Entity Owner, Entity Target) {
        int damage =this.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * this.getDamageScaler());
        int newHealth = Owner.getStat(Entity.StatName.Health, Entity.StatType.Current) + damage;
        Owner.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        this.incrementCurrSpeed();
    }
}
