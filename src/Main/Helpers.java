package Main;

public class Helpers {
    public static void Fight(Entity player, Entity enemy){
        //reset Player Health
        player.resetCurrent();
        //reset Enemy Health
        player.resetCurrent();
        while(true) {
            if(player.getStat(Entity.StatName.HEALTH, Entity.StatType.CURRENT) <= 0){
                System.out.println("Player Died");
                return;
            }
            if(enemy.getStat(Entity.StatName.HEALTH, Entity.StatType.CURRENT) <= 0){
                System.out.println("Enemy Died");
                return;
            }
            Attack PlayerAttack = player.getAttack();
            Attack EnemyAttack = enemy.getAttack();
            int player_attackSpeed = PlayerAttack.getCurrSpeed() + player.getStat(Entity.StatName.SPEED, Entity.StatType.CURRENT);
            int entity_attackSpeed = EnemyAttack.getCurrSpeed() + enemy.getStat(Entity.StatName.SPEED, Entity.StatType.CURRENT);
            if(player_attackSpeed <= entity_attackSpeed){
                System.out.println(player.getName() + "Attacks with " + PlayerAttack.getName());
                PlayerAttack.apply(player,enemy);
            }else{
                System.out.println(enemy.getName() + "Attacks with " + EnemyAttack.getName());
                EnemyAttack.apply(enemy,player);
            }
            System.out.println(player.toString());
            System.out.println(enemy.toString());
        }
    }
}
