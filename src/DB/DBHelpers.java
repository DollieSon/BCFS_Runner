package DB;

import Attacks.AttackModule;
import Builders.AttackModuleBuilder;
import Main.Attack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBHelpers {

    private DBConnection dbConnection;

    public DBHelpers(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public HashMap<Integer, Attack> getAllAttacks(){
        HashMap<Integer,Attack> allAttacks = new HashMap<>();
        try(Connection C = dbConnection.getConnection();
            Statement statement = C.createStatement()){
            String querry = "SELECT * FROM tblattack";
            ResultSet rs = statement.executeQuery(querry);
            while (rs.next()){
                String aName = rs.getString("name");
                int aSpeed = rs.getInt("speed");
                int aDamage = rs.getInt("damage");
                double adamageMult = rs.getDouble("damageMultiplier");
                int attackModule = rs.getInt("attackModule");
                AttackModule AM = AttackModuleBuilder.buildAttackModule(attackModule);
                Attack atk = new Attack(aName,aSpeed,aDamage,adamageMult,AM);
                int Id = rs.getInt("attackID");
                allAttacks.put(Id,atk);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allAttacks;
    }
}
