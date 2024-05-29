package Globals;

import DB.DBConnection;
import DB.AttackHelper;
import Attacks.AttackModule;
import Builders.AttackModuleBuilder;
import Main.*;

import java.sql.*;
import java.util.*;

public class DBHelpers {

//    global static variables
    public static HashMap<Integer, Attack> allAtcks = null;
    public static User currentUser = null;
    public static Boolean cockData = null;
    public static Integer cockID = null;
    public static HashMap<Integer, Cock> allCockData = null;
    public static Boolean isPlayerChallenged = null;
    public static Boolean isInviteAccepted = null;
    public static Boolean isMatchCreated = null;
    public static Boolean isWinnerSet = null;
    public static Boolean isBatchWinnerSet = null;
    public static Boolean isPreviousMatchDeleted = null;
    public static ArrayList<MatchFacade> allUnverifiedMatches = null;
    public static ArrayList<Integer> allChallenges = null;
    public static Boolean isDetailsUpdated = null;
    public static String displayName = null;
    public static Integer invitorCockID = null;
    public static Boolean isMatchInserted = null;
    public static Integer createAccID = null;
    public static Integer getUserID = null;
    public static Boolean isAttackSent = null;
    public static Boolean isToggleDisabled = null;
    public static HashMap<Integer,String> allDisplayNames = null;

    private static int transaction_count = 0;
    private static final int max_transaction_limt = 10;






    /**is an interface to allow modularity in connections**/
    private static DBConnection dbConnection;
    /**to allow a unified connection when constructing DBHelpers <br> DBHelpers dbh = new DBHelpers(DBHelpers.getGlobalConnection()) <br> So that we can easily change from supabase to localhost for testing<**/
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
        if(!hasReachedTransactionLimit()){
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
                    atk.setAttackID(rs.getInt("attackID"));
                    atk.setIsDisabled(rs.getBoolean("isDisabled"));
                    allAttacks.put(Id, atk);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            allAtcks = allAttacks;

        }
        return allAtcks;

    }

    public User LoginUser(String Username, String Password) {
        if(!hasReachedTransactionLimit()){
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
            currentUser = User.getCurrUser();

        }
        return currentUser;
    }

    public boolean SendCockData(Cock cock) {
        if(!hasReachedTransactionLimit()){
            ArrayList<Attack> lists = cock.getAttackList();
            boolean result = false;
            try (Connection c = dbConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement("INSERT INTO `tblcock` (`UserID`, `CockName`, `Attack1ID`, `Attack2ID`, `Attack3ID`, `Attack4ID`) VALUES (?, ?, ? , ? , ? , ?)")) {
                int startInd = 3;
                ps.setInt(1, cock.getOwnerID());
                ps.setString(2, cock.getName());
                for (Attack atk : lists) {
                    int atkID = AttackHelper.attackModuleToInt(atk.getAttackModule());
                    ps.setInt(startInd++, atkID);
                }
                while (startInd-2 <= Cock.MAX_ATTACKS) {
                    ps.setInt(startInd++, 0);
                }
                System.out.println(ps.toString());
                ps.execute();
                System.out.println("Cock Insert Successfull");
                // fetch cockdata
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cockData = result;

        }
        return cockData;
    }

//    public
//    int userID
    public Map<Integer , List<Integer>> getUserMatches(int userId){
        try (Connection c = dbConnection.getConnection()){
            PreparedStatement ps = c.prepareStatement("SELECT * FROM tblmatch " +
                    "WHERE (tblmatch.invitorCockID = tblcock.CockID OR tblmatch.inviteeCockID = tblcock.CockID) " +
                    "AND tblcock.UserID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            Map<Integer , List<Integer>> map = new HashMap<>();
            while(rs.next()){
                int match_id = rs.getInt("matchID");
                List<Integer> cockIDs = new ArrayList<>();
                cockIDs.add(rs.getInt("invitorCockID"));

                cockIDs.add(rs.getInt("inviteeCockID"));

                cockIDs.add(rs.getInt("winner"));
                map.put(match_id,cockIDs);
            }
            return map;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public int getCockID(Cock cock){
        if(!hasReachedTransactionLimit()){
            ArrayList<Attack> lists = cock.getAttackList();
            int res = 0;
            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("SELECT CockID FROM tblcock WHERE UserID = ? AND CockName = ? AND Attack1ID = ? AND Attack2ID = ? AND Attack3ID = ? AND Attack4ID = ?")) {
                ps.setInt(1,cock.getOwnerID());
                ps.setString(2,cock.getName());
                int startInd = 3;
                for(Attack atk: lists){
                    ps.setInt(startInd++,AttackHelper.attackModuleToInt(atk.getAttackModule()));
                }
                while(startInd-2 <= Cock.MAX_ATTACKS){
                    ps.setInt(startInd++,0);
                }
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    cock.setCockID(rs.getInt("CockID"));
                }
                res = cock.getCockID();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cockID = res;
        }
        return cockID;

    }

    public HashMap<Integer, Cock> getAllCockData(){
        if(!hasReachedTransactionLimit()){
            HashMap<Integer, Cock> cockData = null;
            try(Connection c = dbConnection.getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM tblcock")){
                ResultSet rs = ps.executeQuery();
                cockData = new HashMap<>();
                while(rs.next()){
                    Cock cock = new Cock(
                            rs.getString("CockName"),
                            rs.getInt("UserID")
                    );
                    int[] AttackIDs = {rs.getInt("Attack1ID"),rs.getInt("Attack2ID"),rs.getInt("Attack3ID"),rs.getInt("Attack4ID")};
                    cock.setCockID(rs.getInt("CockID"));
                    HashMap<Integer,Attack> allAttack = AttackHelper.fetchAllAttack();
                    for(int AIDs: AttackIDs){
                        if(AIDs == 0) break;
                        Attack tempAtk = AttackHelper.cloneAttack(allAttack.get(AIDs));
                        cock.addAttack(tempAtk);
                    }
                    cockData.put(rs.getInt("CockID"),cock);
                }
                System.out.println("Cocks Fetched Successfully");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            allCockData = cockData;
        }
        return allCockData;

    }

    public boolean challengePlayer(int invitorCockID,int inviteeID, int invitorID){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();){
                PreparedStatement ps = C.prepareStatement("Insert into tblinvite(invitorCockID,inviteeID,invitorID) values(?,?,?)");
                ps.setInt(1,invitorCockID);
                ps.setInt(2,inviteeID);
                ps.setInt(3,invitorID);
                isPlayerChallenged = ps.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isPlayerChallenged;
    }

    public boolean acceptInvite(int inviteID, int inviteeCockID){
        if(!hasReachedTransactionLimit()){
            Boolean result = null;
            try(Connection C = dbConnection.getConnection();){
                PreparedStatement ps = C.prepareStatement("UPDATE tblinvite set isAccepted = 1 where InviteID = ?");
                ps.setInt(1,inviteID);
                boolean isSuccess =  ps.execute();

                if(isSuccess){
                    //createMatch
                    PreparedStatement ps1 = C.prepareStatement("Select invitorCockID from tblinvite where InviteID = ?");
                    ps1.setInt(1,inviteID);
                    ResultSet rs = ps1.executeQuery();
                    int invitorCockID = -1;
                    while(rs.next()){
                        invitorCockID = rs.getInt("invitorCockID");
                        break;
                    }

                    if(invitorCockID==-1){
                        System.out.println("An Error occured while getting the invitorCockID");
                        result = false;
                    }else{
                        createMatch(invitorCockID,inviteeCockID);
                        result = true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            result = false;
            isInviteAccepted = result;
        }
        return isInviteAccepted;

    }

    public boolean createMatch(int invitorCockID, int inviteeCockID){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();){
                PreparedStatement ps = C.prepareStatement("Insert into tblmatch(invitorCockID,inviteeCockID) values (?,?)");
                ps.setInt(1,invitorCockID);
                ps.setInt(2,inviteeCockID);
                isMatchCreated = ps.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isMatchCreated;
    }

    public boolean setWinner(int matchID, int WinnerID){
        if(!hasReachedTransactionLimit()){
            Boolean res = false;
            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("UPDATE tblmatch SET winner = ? WHERE matchID = ?")){
                ps.setInt(1,WinnerID);
                ps.setInt(2,matchID);
                res = ps.execute();
                if(res){
                    res = res && DeletePreviousMatch(matchID);
                    isWinnerSet = res;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isWinnerSet;
    }

    public boolean batchSetWinner(HashMap<Integer,Integer> reses){
        if(!hasReachedTransactionLimit()){
            Boolean res;
            try(Connection C = dbConnection.getConnection();){
                StringBuilder switchCase = new StringBuilder();
                for(int matchID : reses.keySet()){
                    String CaseStament = String.format(" WHEN matchID = %d THEN %d",matchID,reses.get(matchID));
                    switchCase.append(CaseStament);
                }
                switchCase.append(" ELSE winner");
                Iterator<Integer> it= reses.keySet().iterator();
                Integer prevs = null;
                StringBuilder InsideSet = new StringBuilder();
                while(it.hasNext()){
                    Integer currs = it.next();
                    prevs = currs;
                    InsideSet.append(prevs);
                    if(it.hasNext()){
                        InsideSet.append(",");
                    }
                }
                Statement st = C.createStatement();
                String query = String.format("UPDATE tblmatch SET winner = CASE %s END WHERE matchID IN (%s)",switchCase,InsideSet);
                System.out.println(query);
                isBatchWinnerSet = st.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isBatchWinnerSet;
    }

    private boolean DeletePreviousMatch(int matchID){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("DELETE FROM tblmatch WHERE matchID = ?")){
                ps.setInt(1,matchID);
                isPreviousMatchDeleted = ps.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isPreviousMatchDeleted;
    }

    public ArrayList<MatchFacade> getAllUnverifiedMatches(){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("SELECT * FROM tblmatch WHERE winner = 0")){
                ResultSet res = ps.executeQuery();
                HashMap<Integer,Cock> allCocks = getAllCockData();
                ArrayList<MatchFacade> Matches = new ArrayList<>();
                while(res.next()){
                    int invtrID = res.getInt("invitorCockID");
                    int invteID = res.getInt("inviteeCockID");
                    MatchFacade mf = new MatchFacade(res.getInt("matchID"),invtrID,invteID);
                    Matches.add(mf);
                }
                allUnverifiedMatches = Matches;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return allUnverifiedMatches;

    }

    public ArrayList<Integer> getChallenges(int userID){
        if(!hasReachedTransactionLimit()){
            ArrayList<Integer> inviteIds = new ArrayList<>();

            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("Select InviteID from tblinvite where isChallenge = 1 and referenceID = ?")){
                ps.setInt(1,userID);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    inviteIds.add(rs.getInt("InviteID"));
                }
                allChallenges = inviteIds;
                return inviteIds;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return allChallenges;

    }

    public boolean updateDetails(int userID, String displayName, String username, String Password){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();
                PreparedStatement ps = C.prepareStatement("UPDATE tbluser Set DisplayName = ?, Username = ?, Password = ? where UserID = ?")){
                ps.setString(1,displayName);
                ps.setString(2,username);
                ps.setString(3,Password);
                ps.setInt(4,userID);
                isDetailsUpdated = ps.execute();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isDetailsUpdated;

    }

    public String getDisplayName(int userid){
        if(!hasReachedTransactionLimit()){
            try(Connection C = dbConnection.getConnection();){
                PreparedStatement ps = C.prepareStatement("Select DisplayName from tbluser where UserID = ?");
                ps.setInt(1,userid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    displayName =  rs.getString("DisplayName");
                    return displayName;
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return displayName;
    }

    public int getInvitorCockID(int inviteID){
        if(!hasReachedTransactionLimit()){
            try (Connection c = dbConnection.getConnection();) {
                PreparedStatement ps = c.prepareStatement("Select CockID from tblinvite where InviteID = ?");
                ps.setInt(  1, inviteID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    invitorCockID = rs.getInt("CockID");
                    return invitorCockID;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return -1; //returns -1 if error occurs
    }

    public boolean insertMatch(int invitorCockID, int inviteeCockId){
        if(!hasReachedTransactionLimit()){
            try (Connection c = dbConnection.getConnection();) {
                PreparedStatement ps = c.prepareStatement("Insert into tblmatch(invitorCockID, inviteeCockID) values (?,?)");
                ps.setInt(  1, invitorCockID);
                ps.setInt(  1, inviteeCockId);
                isMatchInserted = ps.execute();
                return isMatchInserted;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return isMatchInserted;

    }

//    this just increments transactions count
    public boolean valueExists(String tablename, String columnname, String value){
        try (Connection c = dbConnection.getConnection();) {
            transaction_count++;
            PreparedStatement ps = c.prepareStatement("Select * from ? where ? = ?");
            ps.setString(1,tablename);
            ps.setString(2, columnname);
            ps.setString(3,value);
            ResultSet rs = ps.executeQuery();
            int number_of_rows = rs.getRow();
            return number_of_rows!=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createAccount(String DisplayName,String Username, String Password){
        if(!hasReachedTransactionLimit()){
            try (Connection c = dbConnection.getConnection();) {
                boolean usernameExists = valueExists("tbluser","Username",Username);
                if(usernameExists ){
                    System.out.println("Username already exists");
                    return -1;
                }else{
                    PreparedStatement ps = c.prepareStatement("Insert into tbluser(Displayname,Username,Password) values (?,?,?)");
                    ps.setString(1,DisplayName);
                    ps.setString(2,Username);
                    ps.setString(3,Password);
                    ps.execute();
                    createAccID = getUserId(Username);

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return createAccID;

    }

    public int getUserId(String username){
        if(!hasReachedTransactionLimit()){
            try (Connection c = dbConnection.getConnection();) {
                PreparedStatement ps = c.prepareStatement("Select UserID from tbluser where Username=?");
                ps.setString(1,username);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    getUserID = rs.getInt("UserID");
                    return getUserID;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return -1; //returns -1 if error occurs
    }

    public boolean sendAttack(Attack atk){
        if(!hasReachedTransactionLimit()){
            boolean res = false;
            try(Connection c = dbConnection.getConnection();
                PreparedStatement ps = c.prepareStatement("INSERT INTO tblattack(name,speed,damage,damageMultiplier,attackModule) values (?,?,?,?,?)")){
                ps.setString(1,atk.getName());
                ps.setInt(2,atk.getSpeed());
                ps.setInt(3,atk.getDamage());
                ps.setDouble(4,atk.getDamageMultiplier());
                ps.setInt(5,AttackHelper.attackModuleToInt(atk.getAttackModule()));
                res = ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            isAttackSent = res;
        }
        return isAttackSent;
    }

    public boolean toggleIsDisabled(int AttackID, boolean isDisabledState){
        if(!hasReachedTransactionLimit()){
            boolean res = false;
            try(Connection c = dbConnection.getConnection();
                PreparedStatement ps = c.prepareStatement("UPDATE tblattack SET isDisabled = ? WHERE attackID = ?")){
                ps.setBoolean(1,isDisabledState);
                ps.setInt(2,AttackID);
                res = ps.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            isToggleDisabled = res;
        }
        return isToggleDisabled;
    }

    public HashMap<Integer,String> getAllDIsplayNames(){
        if(!hasReachedTransactionLimit()){
            HashMap<Integer,String> Unames = new HashMap<>();
            try(Connection C = dbConnection.getConnection();){
                Statement st = C.createStatement();
                String querry = "SELECT UserID,DisplayName FROM tbluser";
                ResultSet rs =  st.executeQuery(querry);
                while (rs.next()){
                    Unames.put(rs.getInt("UserID"), rs.getString("DisplayName"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            allDisplayNames = Unames;
        }
        return allDisplayNames;
    }

    private boolean hasReachedTransactionLimit(){
        transaction_count++;
        if(transaction_count>=max_transaction_limt){
            transaction_count=0; //im not sure if this resets to zero
            return true;
        }else{
            return false;
        }
    }
}
