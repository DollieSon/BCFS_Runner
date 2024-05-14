package DB;

import java.sql.*;

public class SupabaseConnection  implements DBConnection {
    String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres?user=postgres.kqwlpqapoaozazzhxgpv&password=JAVlovers220369330";
    String USERNAME = "";
    String PASSWORD = "";

    public Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DB Connection Success");

            String sql = "SELECT * FROM tblBCFS";
            try (Statement statement = c.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                // Process the result set
                while (resultSet.next()) {
                    // Assuming tblCock has columns: id, name, and type
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    System.out.println("ID: " + id + ", Name: " + name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
