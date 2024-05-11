package Attacks;

import Main.Attack;
import Main.Entity;

public interface AttackModule {
    public void apply(Entity Owner, Entity Target, Attack parent);
}
