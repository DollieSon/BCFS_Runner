package DB;

import Attacks.AttackHelper;
import Attacks.AttackModule;
import Builders.AttackModuleBuilder;
import Main.Attack;
import Main.Entity;
import Main.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelpers {

    private DBConnection dbConnection;
    private static DBConnection globalConnection;
    public DBHelpers(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public static DBConnection getGlobalConnection(){
        return globalConnection;
    }

    public static void setGlobalConnection(DBConnection dbc){
        globalConnection = dbc;
    }

    public HashMap<Integer, Attack> getAllAttacks() {
        HashMap<Integer, Attack> allAttacks = new HashMap<>();
        try (Connection C = dbConnection.getConnection();
             Statement statement = C.createStatement()) {
            String querry = "SELECT * FROM tblattack";
            ResultSet rs = statement.executeQuery(querry);
            while (rs.next()) {
                String aName = rs.getString("name");
                int aSpeed = rs.getInt("speed");
                int aDamage = rs.getInt("damage");
                double adamageMult = rs.getDouble("damageMultiplier");
                int attackModule = rs.getInt("attackModule");
                int Id = rs.getInt("attackID");
                AttackModule AM = AttackModuleBuilder.buildAttackModule(attackModule);
                Attack atk = new Attack(aName, aSpeed, aDamage, adamageMult, AM,Id);
                allAttacks.put(Id, atk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAttacks;
    }

    public boolean CreateAccount(String DisplayName, String Username, String Password) {
        boolean isSuccessful = false;
        try (Connection c = dbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO `tbluser` (`DisplayName`, `Username`, `Password`) VALUES ( ?, ?, ?)")) {
            ps.setString(1, DisplayName);
            ps.setString(2, Username);
            ps.setString(3, Password);
            isSuccessful = ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
        return isSuccessful;
    }

    public User LoginUser(String Username, String Password) {
        try (Connection c = dbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT UserID,DisplayName FROM tbluser WHERE Username = ? AND Password = ?")) {
            ps.setString(1, Username);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int UID = rs.getInt("UserID");
            String DisplayName = rs.getString("DisplayName");
            User.setCurrentUser(DisplayName,UID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return User.getCurrUser();
    }

    public boolean SendCockData(Entity cock) {
        ArrayList<Attack> lists = cock.getAttackList();
        boolean result = false;
        try (Connection c = dbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO `tblcock` (`UserID`, `CockName`, `Attack1ID`, `Attack2ID`, `Attack3ID`, `Attaclk4ID`) VALUES (?, ?, ? , ? , ? , ?)")) {
            int startInd = 3;
            ps.setInt(1, cock.getOwnerID());
            ps.setString(2, cock.getName());
            for (Attack atk : lists) {
                int atkID = AttackHelper.attackModuleToInt(atk.getAttackModule());
                ps.setInt(startInd++, atkID);
            }
            while (startInd-2 <= Entity.MAX_ATTACKS) {
                ps.setInt(startInd++, 0);
            }
            System.out.println(ps.toString());
            ps.execute();
            System.out.println("Cock Insert Successfull");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public HashMap<Integer,Entity> getAllCockData(){
        HashMap<Integer,Entity> cockData = null;
        try(Connection c = dbConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM tblcock")){
            ResultSet rs = ps.executeQuery();
            cockData = new HashMap<>();
            while(rs.next()){
                Entity cock = new Entity(
                        rs.getString("CockName"),
                        rs.getInt("UserID")
                );
                int[] AttackIDs = {rs.getInt("Attack1ID"),rs.getInt("Attack2ID"),rs.getInt("Attack3ID"),rs.getInt("Attack4ID")};
                HashMap<Integer,Attack> allAttack = AttackHelper.fetchAllAttack();
                for(int AIDs: AttackIDs){
                    if(AIDs == 0) break;
                    Attack tempAtk = AttackHelper.cloneAttack(allAttack.get(AIDs));
                    cock.addAttack(tempAtk);
                }
                cockData.put(rs.getInt("CockID"),cock);
            }
            System.out.println("Fetched " + rs.getRow() + " Cocks");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cockData;
    }
}