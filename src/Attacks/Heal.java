package Attacks;

import Main.Attack;
import Main.Entity;

public class Heal implements AttackModule{

    @Override
    public void apply(Entity Owner, Entity Target,Attack parent) {
        int damage =parent.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * parent.getDamageScaler());
        int newHealth = Owner.getStat(Entity.StatName.Health, Entity.StatType.Bonus) + damage;
        Owner.setStat(Entity.StatName.Health, Entity.StatType.Bonus,newHealth);
    }
}
