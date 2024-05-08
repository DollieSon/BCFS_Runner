package Main;

import java.sql.SQLOutput;
import java.util.Collections;

public class Helpers {
    public static void Fight(Entity Player,Entity Enemy){
        //reset Player Health
        Player.setStat(Entity.StatName.Health, Entity.StatType.Current,
                        Player.getStat(Entity.StatName.Health, Entity.StatType.Base)
                );
        //reset Enemy Health
        Enemy.setStat(Entity.StatName.Health, Entity.StatType.Current,
                Enemy.getStat(Entity.StatName.Health, Entity.StatType.Base)
        );
        while(true){
            if(Player.getStat(Entity.StatName.Health, Entity.StatType.Current) <= 0){
                System.out.println("Player Died");
                return;
            }
            if(Enemy.getStat(Entity.StatName.Health, Entity.StatType.Current) <= 0){
                System.out.println("Enemy Died");
                return;
            }
            Attack PlayerAttack = Player.getAttack();
            Attack EnemyAttack = Enemy.getAttack();
            int PAttackSpeed = PlayerAttack.getCurrSpeed() + Player.getStat(Entity.StatName.Speed, Entity.StatType.Current);
            int EAttackSpeed = EnemyAttack.getCurrSpeed() + Enemy.getStat(Entity.StatName.Speed, Entity.StatType.Current);
            if(PAttackSpeed <= EAttackSpeed){
                System.out.println(Player.getName() + "Attacks with " + PlayerAttack.getName());
                PlayerAttack.apply(Player,Enemy);
            }else{
                System.out.println(Enemy.getName() + "Attacks with " + EnemyAttack.getName());
                EnemyAttack.apply(Enemy,Player);
            }
            System.out.println(Player.toString());
            System.out.println(Enemy.toString());
        }
    }
}
