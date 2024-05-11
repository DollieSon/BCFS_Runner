package Attacks;

import Main.*;

public class Leech implements AttackModule{

    public void apply(Entity Owner, Entity Target, Attack parent) {
        int damage =parent.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * parent.getDamageScaler());
        int newHealth = Target.getStat(Entity.StatName.Health, Entity.StatType.Bonus) - damage;
        //Apply Damage
        Target.setStat(Entity.StatName.Health, Entity.StatType.Bonus,newHealth);
        // Heal Owner
        Owner.setStat(Entity.StatName.Health, Entity.StatType.Bonus,damage);
    }


}
