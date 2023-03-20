package Controller;
import java.sql.SQLException;
import java.util.Scanner;
import Model.DriverDB;
import View.View;

public class DriverRole extends User {
    DriverDB driver;
    Scanner sc;
    public DriverRole(String name, int id, String city, String role) {
        super(name, id, city, role);
        driver=new DriverDB();
        sc=new Scanner(System.in);
    }
    public void controller() throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("1.View Orders\n2.Delivery Completed\n5.Log out");
            Scanner sc = new Scanner(System.in);
            int ch = sc.nextInt();
            switch (ch) {
                case 1: viewOrders();break;
                case 2: delivered();break;
                case 3:return;
                default: System.out.println("Enter valid choice");
            }
        }
    }

    private void viewOrders() throws SQLException {
        View.printResultSet(driver.viewOrders(getId()));
    }

    private void delivered() throws SQLException {
        driver.delivered(getId());
    }
}
