package Attacks;

import Main.Attack;
import Main.Entity;
import Main.Helpers;

public class SingleAttack extends Attack {
    public SingleAttack(String name, int speed, int damage, int damageScaler) {
        super(name, speed, damage, damageScaler);
    }

    @Override
    public void apply(Entity Owner, Entity Target) {
        int damage = (this.getDamage() + Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current)) * this.getDamageScaler();
        int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Current) - damage;
        //Apply Damage
        Target.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
    }
}
