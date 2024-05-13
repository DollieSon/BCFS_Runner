package Main;

public class User {
    private String DisplayName;
    private  int UserID;
    private static User currUser;
    private User(String displayName,int userID) {
        DisplayName = displayName;
        UserID = userID;
    }
    public String getDisplayName() {
        return DisplayName;
    }

    public int getUserID() {
        return UserID;
    }

    public static User setCurrentUser(String displayName,int UserID){
        currUser = new User(displayName,UserID);
        return currUser;
    }

    public static User getCurrUser(){
        if(currUser == null) throw new IllegalStateException();
        return currUser;
    }
}
