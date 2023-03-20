import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Model.DBConnection;
import Controller.*;
public class App {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println();
            System.out.println("*****LOGIN*****");
            System.out.print("Enter user name: ");
            String userName=sc.next();
            System.out.print("Password : ");
            String password=sc.next();
            App login=new App();
            try {
                if(DBConnection.validate(userName, password))
                    login.gainAccess(userName);
                else
                    System.out.println("Enter valid user name and password.");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    void gainAccess(String userName) throws SQLException {
        ResultSet r=DBConnection.getAccess(userName);
        if(r.next()){
            String name=r.getString(1);
            int id=r.getInt(2);
            String role=r.getString(3);
            String city=r.getString(4);
            switch (role){
                case "manager":
                    Manager manager=new Manager(name,id,city,role);
                    manager.controller();
                    break;
                case "supervisor":
                    Supervisor supervisor=new Supervisor(name,id,city,role);
                    supervisor.controller();
                    break;
                case "driver":
                    DriverRole driver=new DriverRole(name,id,city,role);
                    driver.controller();
                    break;
            }
        }
    }
}



