package Attacks;

import Main.Attack;
import Main.Entity;
import Main.Helpers;

public class SingleAttack implements AttackModule{
    @Override
    public void apply(Entity Owner, Entity Target,Attack parent) {
        int damage =parent.getDamage() + (int) (Owner.getStat(Entity.StatName.Damage, Entity.StatType.Current) * parent.getDamageScaler());
        Target.addStat(Entity.StatName.Health, Entity.StatType.Bonus,damage * -1);
    }
}
