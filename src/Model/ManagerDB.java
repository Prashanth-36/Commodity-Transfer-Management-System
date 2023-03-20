package Model;
import Objects.SupervisorDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ManagerDB extends SupervisorDB{
    Statement stmt = DBConnection.getStatement();
    public void addSupervisor(SupervisorDetails supervisor) throws SQLException {
        ResultSet id = stmt.executeQuery("SELECT USERNAME FROM EMPLOYEE WHERE ROLE='supervisor' ORDER BY ID DESC LIMIT 1");
        String uname="sv";
        if (id.next())
            uname +=Integer.parseInt(id.getString(1).replace("sv",  "")) + 1;
        else
            uname+=1;
        stmt.execute("START TRANSACTION");
        boolean r = stmt.execute("INSERT INTO USERS VALUES('"+uname+"', 'supervisor')");
        boolean r2=stmt.execute("INSERT INTO EMPLOYEE(NAME, CITY, NUMBER, ROLE, SALARY, USERNAME) VALUES('" + supervisor.name + "', '" + supervisor.city + "', " + supervisor.num + ", 'supervisor', " + supervisor.salary + ", '"+uname+"')");
        stmt.execute("COMMIT");
        if (!r && !r2) System.out.println("New Employee Added successfully.");
    }
    public void replaceSupervisor(int id,  SupervisorDetails supervisor) throws SQLException {
        int r=stmt.executeUpdate("UPDATE EMPLOYEE SET NAME='"+supervisor.name+"', NUMBER="+supervisor.num+", SALARY="+supervisor.salary+" WHERE ID="+id);
        if (r>0) System.out.println("Replaced Details successfully");
    }
    public void removeSupervisor(int id) throws SQLException {
        boolean r=stmt.execute("DELETE FROM USERS WHERE USERNAME=(SELECT USERNAME FROM EMPLOYEE WHERE ID="+id+")");
        if(!r) System.out.println("Employee removed successfully");
    }

    public ResultSet getSupervisor() throws SQLException {
        ResultSet r=stmt.executeQuery("SELECT ID, NAME, CITY, NUMBER, SALARY, USERNAME FROM EMPLOYEE WHERE ROLE='supervisor'");
        System.out.println("****************************************SUPERVISOR****************************************");
        return r;
    }

    public ResultSet getDriver() throws SQLException {
        ResultSet r=stmt.executeQuery("SELECT E.ID, NAME, CITY, NUMBER, SALARY, USERNAME, LICENSE_NO, AVAILABLE, CURRENT_CITY, SUPERVISOR_ID FROM EMPLOYEE E INNER JOIN DRIVER_DETAILS D ON E.ID=D.ID WHERE ROLE='driver'");
        System.out.println("*********************************************************************DRIVERS*********************************************************************");
        return r;
    }
    public ResultSet generateReport() throws SQLException {
        ResultSet r=stmt.executeQuery("SELECT BRANCH, SUM(AMOUNT) AS BRANCH_REVENUE FROM ORDERS GROUP BY BRANCH");
        System.out.println("************REPORT************");
        return r;
    }
    public ResultSet generateDeliveryReport() throws SQLException {
        System.out.println("************************************************DELIVERY REPORT************************************************");
        return stmt.executeQuery("SELECT * FROM ORDERS");
    }

}
