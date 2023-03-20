package Controller;
import Model.ManagerDB;
import Objects.Delivery;
import Objects.DriverData;
import Objects.Order;
import Objects.SupervisorDetails;
import View.View;
import java.sql.SQLException;
import java.util.Scanner;
public class Manager extends User{
    ManagerDB manager;
    View view;
    public Manager(String name, int id, String city, String role) {
        super(name, id, city, role);
        manager=new ManagerDB();
        view=new View();
    }
    Scanner sc=new Scanner(System.in);
    public void controller() throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("1.Add Supervisor\n2.Replace Supervisor\n3.Remove Supervisor\n4.Display Supervisors\n5.Generate Branch Report" +
                    "\n6.Add Driver\n7.Remove Driver\n8.Display Drivers\n9.Add Order\n10.Start Delivery\n11.Generate Delivery Report\n12.Log out");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1: addSupervisor();break;
                case 2: replaceSupervisor();break;
                case 3: removeSupervisor(); break;
                case 4: getSupervisor(); break;
                case 5: generateReport(); break;
                case 6: addDriver();break;
                case 7: removeDriver(); break;
                case 8: getDriver(); break;
                case 9: addOrder();break;
                case 10: addDelivery();break;
                case 11: generateDeliveryReport();break;
                case 12:return;
                default: System.out.println("Enter valid choice");
            }
        }
    }

    private void addDelivery() throws SQLException {
        Delivery d=view.getDeliveryDetails(manager.getDeliveryDestinations(getId()),manager.getAvailabeDrivers(getCity()),getCity());
        manager.addDelivery(d,getId());
    }

    private void addSupervisor() throws SQLException {
        SupervisorDetails supervisor=view.getNewSupervisor();
        manager.addSupervisor(supervisor);
    }
    private void addDriver() throws SQLException {
        DriverData driver=view.getNewDriver();
        manager.addDriver(driver,getId(),getCity());
    }

    private void addOrder() throws SQLException {
        Order order=view.getOrder();
        manager.addOrder(getCity(),order,getId());
    }

    private void replaceSupervisor() throws SQLException {
        view.printResultSet(manager.getSupervisor());
        System.out.print("Enter id to be replaced: ");
        int id=sc.nextInt();
        sc.nextLine();
        SupervisorDetails supervisor=view.getReplacementSupervisor();
        manager.replaceSupervisor(id,supervisor);
    }

    private void removeSupervisor() throws SQLException {
        view.printResultSet(manager.getSupervisor());
        int id=view.getRemoveId();
        manager.removeSupervisor(id);
    }

    private void removeDriver() throws SQLException {
        view.printResultSet(manager.getDriver());
        int id=view.getRemoveId();
        manager.removeDriver(id);
    }
    private void getSupervisor() throws SQLException {
        view.printResultSet(manager.getSupervisor());
    }
    private void getDriver() throws SQLException {
        view.printResultSet(manager.getDriver());
    }
    private void generateReport() throws SQLException {
        view.printResultSet(manager.generateReport());
    }

    private void generateDeliveryReport() throws SQLException {
        view.printResultSet(manager.generateDeliveryReport());
    }
}
