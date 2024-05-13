package DB;

import Attacks.AttackModule;
import Builders.AttackModuleBuilder;
import Main.Attack;
import Main.User;

import java.sql.*;
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

    public boolean CreateAccount(String DisplayName,String Username,String Password){
        boolean isSuccessful = false;
        try(Connection c = dbConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO `tbluser` (`DisplayName`, `Username`, `Password`) VALUES ( ?, ?, ?)")){
            ps.setString(1,DisplayName);
            ps.setString(2,Username);
            ps.setString(3,Password);
            isSuccessful = ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
        }
        return isSuccessful;
    }

    public User LoginUser(String Username, String Password){
        User result = null;
        try(Connection c = dbConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT UserID,DisplayName FROM tbluser WHERE Username = ? AND Password = ?")){
            ps.setString(1,Username);
            ps.setString(2,Password);
            ResultSet rs =  ps.executeQuery();
            rs.next();
            int UID = rs.getInt("UserID");
            String DisplayName = rs.getString("DisplayName");
            result = new User(DisplayName,UID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
