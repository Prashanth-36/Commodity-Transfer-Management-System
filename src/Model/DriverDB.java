package Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DriverDB {
    Statement stmt=DBConnection.getStatement();
    public ResultSet viewOrders(int id) throws SQLException {
        System.out.println("******************************************Orders******************************************");
        return stmt.executeQuery("SELECT O.ORDER_DATE,O.ID,D.STARTING_CITY,D.DESTINATION_CITY,D.SV_ID,O.STATUS " +
                "FROM ORDERS O JOIN DELIVERY D ON O.BRANCH=D.STARTING_CITY AND O.DESTINATION=D.DESTINATION_CITY " +
                "WHERE DRIVER_ID="+id+" AND O.STATUS='in progress'");
    }
    public void delivered(int id) throws SQLException {
        stmt.execute("START TRANSACTION");
        boolean r1=stmt.execute("UPDATE DELIVERY SET STATUS='delivered' WHERE DRIVER_ID="+id);
        boolean r2=stmt.execute("UPDATE DRIVER_DETAILS SET AVAILABLE='yes',CURRENT_CITY=IFNULL((SELECT DESTINATION_CITY FROM DELIVERY WHERE DRIVER_ID="+id+" ORDER BY ENTRY_NO DESC LIMIT 1)," +
                " (SELECT CITY FROM EMPLOYEE WHERE ID="+id+")) WHERE ID="+id);
        ResultSet rs=stmt.executeQuery("SELECT DESTINATION_CITY,SV_ID FROM DELIVERY WHERE DRIVER_ID="+id+" ORDER BY ENTRY_NO DESC LIMIT 1");
        if(rs.next()) {
            int s_id = rs.getInt("sv_id");
            String dest = rs.getString("destination_city");
            boolean r3 = stmt.execute("UPDATE ORDERS SET STATUS='delivered' WHERE DESTINATION='"+dest +"' AND STATUS='in progress' AND SV_ID="+s_id);
        }
        stmt.execute("COMMIT");
        if (!(r1||r2)) System.out.println("Status updated");
    }

}
