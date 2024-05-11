package Attacks;

import Main.Attack;
import Main.Entity;

public class Heal implements AttackModule{

    @Override
    public void apply(Entity owner, Entity target, Attack parent) {
        int damage = parent.getDamage() + (int) (owner.getStat(Entity.StatName.DAMAGE, Entity.StatType.CURRENT) * parent.getDamageScaler());
        int newHealth = owner.getStat(Entity.StatName.HEALTH, Entity.StatType.BONUS) + damage;
        owner.setStat(Entity.StatName.HEALTH, Entity.StatType.BONUS, newHealth);
    }
}
