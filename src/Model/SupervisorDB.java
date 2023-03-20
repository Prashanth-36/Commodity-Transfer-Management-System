package Model;
import Objects.DriverData;
import Objects.Order;
import Objects.Delivery;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SupervisorDB {
    Statement stmt= DBConnection.getStatement();
    public void addDriver(DriverData driver,  int s_id, String city) throws SQLException {
        ResultSet id = stmt.executeQuery("SELECT USERNAME FROM EMPLOYEE WHERE ROLE='driver' ORDER BY ID DESC LIMIT 1");
        String uname="dv";
        if (id.next())
            uname +=Integer.parseInt(id.getString(1).replace("dv",  "")) + 1;
        else uname += 1;
        stmt.execute("START TRANSACTION");
        int usr = stmt.executeUpdate("INSERT INTO USERS VALUES('" + uname + "', 'driver')");
        int emp = stmt.executeUpdate("INSERT INTO EMPLOYEE(NAME, CITY, NUMBER, ROLE, SALARY, USERNAME) VALUES('" + driver.name + "', '" + city + "', " + driver.num + ", 'driver', " + driver.salary + ", '" + uname + "')");
        int licen = stmt.executeUpdate("INSERT INTO DRIVER_DETAILS(ID, LICENSE_NO, SUPERVISOR_ID, CURRENT_CITY) VALUES((select last_insert_id() from employee limit 1), '" + driver.license + "', "+s_id+", '"+city+"')");
        stmt.execute("COMMIT");
        if (usr>0 && emp>0 && licen>0) System.out.println("New Driver Added successfully.");
    }
    public void addOrder(String city,  Order order, int id) throws SQLException {
        int r=stmt.executeUpdate("INSERT INTO ORDERS(BRANCH, DESTINATION, AMOUNT, SV_ID) VALUES('"+city+"', '"+order.destination+"', "+order.price+", "+id+")");
        if (r>0) System.out.println("Delivery added successfully");
    }

    public void addDelivery(Delivery d, int id) throws SQLException {
        Connection con=DBConnection.getConnection();
        stmt.execute("START TRANSACTION");
        stmt.executeUpdate("INSERT INTO DELIVERY(DRIVER_ID, STARTING_CITY, DESTINATION_CITY, SV_ID) VALUES("+d.id+", '"+d.city+"', '"+d.destination+"', "+id+")");
        stmt.executeUpdate("UPDATE ORDERS SET STATUS='in progress' WHERE BRANCH='"+d.city+"' AND DESTINATION='"+d.destination+"'");
        stmt.executeUpdate("UPDATE DRIVER_DETAILS SET AVAILABLE='no' WHERE ID="+d.id);
        int r=stmt.executeUpdate("COMMIT");
        if(r==0) System.out.println("Delivery Started Successfully.");
    }

    public void removeDriver(int id) throws SQLException {
        int r=stmt.executeUpdate("DELETE FROM USERS WHERE USERNAME=(SELECT USERNAME FROM EMPLOYEE WHERE ID="+id+")");
        if(r>0) System.out.println("Driver removed successfully");
    }
    public ResultSet getDriver(int id) throws SQLException {
        ResultSet r=stmt.executeQuery("SELECT E.*, L.LICENSE_NO, L.AVAILABLE, L.CURRENT_CITY FROM EMPLOYEE E INNER JOIN DRIVER_DETAILS L ON E.ID=L.ID WHERE ROLE='driver' AND SUPERVISOR_ID="+id);
        System.out.println("********************************************************************DRIVERS*********************************************************************");
        return r;
    }
    public ResultSet getAvailabeDrivers(String city) throws SQLException {
        Connection con=DBConnection.getConnection();
        Statement st=con.createStatement();
        return st.executeQuery("SELECT E.ID, NAME, NUMBER, SUPERVISOR_ID FROM DRIVER_DETAILS D JOIN EMPLOYEE E ON D.ID=E.ID WHERE CURRENT_CITY='"+city+"' AND AVAILABLE='yes' ");
    }
    public ResultSet getDeliveryDestinations(int id) throws SQLException {
        return stmt.executeQuery("SELECT DISTINCT DESTINATION FROM ORDERS WHERE SV_ID="+id+" AND STATUS='pending'");
    }
    public ResultSet generateDeliveryReport(int id) throws SQLException {
        System.out.println("*****************************************DELIVERY REPORT*****************************************");
        return stmt.executeQuery("SELECT * FROM ORDERS WHERE SV_ID="+id);
    }
}
