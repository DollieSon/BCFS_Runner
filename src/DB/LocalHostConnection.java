package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalHostConnection implements DBConnection{
    public static final String URL = "jdbc:mysql://localhost:3306/bcfs";
    public static final String USERNAME = "root";
    public static final String PASSSWORD = "";

    public Connection getConnection(){
        Connection c = null;
        try{
            c= DriverManager.getConnection(URL, USERNAME,PASSSWORD);
            System.out.println("DB Connection Success");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }
}
