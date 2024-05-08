package Attacks;

import Main.*;

public class Leech extends Attack {
    public Leech(String name, int speed, int damage, double damageScaler) {
        super(name, speed, damage, damageScaler);
    }

    public void apply(Entity Owner, Entity Target) {
        int damage =this.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * this.getDamageScaler());
        int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Current) - damage;
        //Apply Damage
        Target.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        // Heal Owner
        Owner.setStat(Entity.StatName.Health, Entity.StatType.Current,damage);
        this.incrementCurrSpeed();
    }


}
