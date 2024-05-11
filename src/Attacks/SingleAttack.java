package Attacks;

import Main.Attack;
import Main.Entity;

public class SingleAttack implements AttackModule{
    @Override
    public void apply(Entity owner, Entity entity, Attack parent) {
        int damage = parent.getDamage() + (int) (owner.getStat(Entity.StatName.DAMAGE, Entity.StatType.CURRENT) * parent.getDamageMultiplier());
        entity.addStat(Entity.StatName.HEALTH, Entity.StatType.BONUS,damage * -1);
    }
}
