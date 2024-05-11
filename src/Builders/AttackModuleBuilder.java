package Builders;

import Attacks.AttackModule;
import Attacks.SingleAttack;

public class AttackModuleBuilder {
    public static AttackModule buildAttackModule(int type){
        AttackModule res;
        switch (type){
            case 1:
                res = new SingleAttack();
                break;
            default:
                throw new IllegalStateException();
        }
        return res;
    }
}
