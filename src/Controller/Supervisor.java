package Controller;
import Objects.Delivery;
import Objects.DriverData;
import Objects.Order;
import Model.SupervisorDB;
import View.View;
import java.sql.SQLException;
import java.util.Scanner;
public class Supervisor extends User{
    View view;
    SupervisorDB supervisor;
    public Supervisor(String name, int id, String city, String role) {
        super(name, id, city, role);
        view=new View();
        supervisor=new SupervisorDB();
    }
    public void controller() throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("***** Supervisor:"+getName()+" City:"+getCity()+" *****\n1.Add Driver\n2.Remove Driver\n3.Display Drivers" +
                    "\n4.Place Order\n5.Start Delivery\n6.Generate Delivery Report\n7.Log out");
            Scanner sc=new Scanner(System.in);
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1: addDriver();break;
                case 2: removeDriver(); break;
                case 3: getDriver(); break;
                case 4: addOrder();break;
                case 5: addDelivery();break;
                case 6: generateDeliveryReport();break;
                case 7:return;
                default: System.out.println("Enter valid choice");
            }
        }
    }
    private void addDriver() throws SQLException {
        DriverData driver=view.getNewDriver();
        supervisor.addDriver(driver,getId(),getCity());
    }
    private void addDelivery() throws SQLException {
        Delivery d=view.getDeliveryDetails(supervisor.getDeliveryDestinations(getId()),supervisor.getAvailabeDrivers(getCity()),getCity());
        supervisor.addDelivery(d,getId());
    }
    private void addOrder() throws SQLException {
        Order order=view.getOrder();
        supervisor.addOrder(getCity(),order,getId());
    }

    private void getDriver() throws SQLException {
        view.printResultSet(supervisor.getDriver(getId()));
    }

    private void removeDriver() throws SQLException {
        getDriver();
        int id=view.getRemoveId();
        supervisor.removeDriver(id);
    }

    private void generateDeliveryReport() throws SQLException {
        view.printResultSet(supervisor.generateDeliveryReport(getId()));
    }
}
