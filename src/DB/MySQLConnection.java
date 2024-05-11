package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    public static final String URL =
            "jdbc:mysql://192.168.1.13:3306/bcfs";
    public static final String USERNAME = "client";
    public static final String PASSSWORD = "";

    private static Connection connection;
    private MySQLConnection(){}
    public static Connection getConnection(){
        if(connection != null) return connection;
        Connection c = null;
        try{
            c= DriverManager.getConnection(URL, USERNAME,PASSSWORD);
            System.out.println("DB Connection Success");
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection = c;
        return connection;
    }

    public static void main(String[] args) {
        Connection c = getConnection();
        try{
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}