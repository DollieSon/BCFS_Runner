package Attacks;

import Main.Attack;
import Main.Cock;

public interface AttackModule {
     void apply(Cock Owner, Cock Target, Attack parent);
}
