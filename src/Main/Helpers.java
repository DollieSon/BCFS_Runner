package Main;

public class Helpers {
    public static int Fight(Cock player, Cock enemy){
        int rounds = 0;
        //reset Player Health
        player.resetCurrent();
        //reset Enemy Health
        player.resetCurrent();
        while(true) {
            if(player.getStat(Cock.StatName.HEALTH, Cock.StatType.CURRENT) <= 0){
                System.out.println("Player Died");
                return -1;
            }
            if(enemy.getStat(Cock.StatName.HEALTH, Cock.StatType.CURRENT) <= 0){
                System.out.println("Enemy Died");
                return 1;
            }
            Attack PlayerAttack = player.getAttack();
            Attack EnemyAttack = enemy.getAttack();
            int player_attackSpeed = PlayerAttack.getCurrSpeed() + player.getStat(Cock.StatName.SPEED, Cock.StatType.CURRENT);
            int entity_attackSpeed = EnemyAttack.getCurrSpeed() + enemy.getStat(Cock.StatName.SPEED, Cock.StatType.CURRENT);
            if(player_attackSpeed <= entity_attackSpeed){
                System.out.println(player.getName() + "Attacks with " + PlayerAttack.getName());
                PlayerAttack.apply(player,enemy);
                if(rounds > 75) player.addStat(Cock.StatName.HEALTH, Cock.StatType.BONUS,-(rounds * rounds));
            }else{
                System.out.println(enemy.getName() + "Attacks with " + EnemyAttack.getName());
                EnemyAttack.apply(enemy,player);
                if(rounds > 75) enemy.addStat(Cock.StatName.HEALTH, Cock.StatType.BONUS,-(rounds * rounds));
            }
            System.out.println(player.toString());
            System.out.println(enemy.toString());
            rounds++;
        }
    }
}
