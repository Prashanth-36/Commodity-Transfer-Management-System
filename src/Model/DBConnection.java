package Model;
import java.sql.*;
public class DBConnection {
    private static Connection con;
    private static Statement stmt;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commoditytransfermanagementsystem","root","root");
            stmt= con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DBConnection(){}
    public static ResultSet getAccess(String userName) throws SQLException {
        return stmt.executeQuery("SELECT NAME,ID,ROLE,CITY FROM EMPLOYEE WHERE USERNAME='"+userName+"'");
    }
    public static boolean validate(String user, String password) throws SQLException {
        ResultSet r=stmt.executeQuery("SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME='"+user+"' AND PASSWORD='"+password+"'");
        if(r.next()){
            if (r.getString(1).equals(user) && r.getString(2).equals(password))
                return true;
            else return false;
        }
        return false;
    }
    public static Connection getConnection() {
        return con;
    }
    public static Statement getStatement(){ return stmt;}
}
