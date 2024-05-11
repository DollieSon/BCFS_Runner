package Attacks;

import Main.*;

public class Leech implements AttackModule{

    public void apply(Entity owner, Entity target, Attack parent) {
        int damage = parent.getDamage() + (int) (owner.getStat(Entity.StatName.DAMAGE, Entity.StatType.CURRENT) * parent.getDamageMultiplier());
        int newHealth = target.getStat(Entity.StatName.HEALTH, Entity.StatType.BONUS) - damage;
        //Apply Damage
        target.setStat(Entity.StatName.HEALTH, Entity.StatType.BONUS,newHealth);
        // Heal Owner
        owner.setStat(Entity.StatName.HEALTH, Entity.StatType.BONUS,damage);
    }


}
