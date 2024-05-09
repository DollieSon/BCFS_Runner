package Attacks;

import Main.Attack;
import Main.Entity;

public class MultiAttack extends Attack {

    int numberOfAttack;
    public MultiAttack(String name, int speed, int damage, double damageScaler,int numberOfAttack) {
        super(name, speed, damage, damageScaler);
        this.numberOfAttack = numberOfAttack;

    }

    @Override
    public void apply(Entity Owner, Entity Target) {
        int damage =this.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * this.getDamageScaler());
        for(int x = 0;x<numberOfAttack;x++){
            int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Current) - damage;
            //Apply Damage
            Target.setStat(Entity.StatName.Health, Entity.StatType.Current,newHealth);
        }
        this.incrementCurrSpeed();
    }
}
