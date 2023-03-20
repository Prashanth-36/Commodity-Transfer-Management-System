package View;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Objects.*;
public class View {
    Scanner sc;
    public View(){
        sc=new Scanner(System.in);
    }
    public static void printResultSet(ResultSet r) throws SQLException {
        ResultSetMetaData rmd=r.getMetaData();
        int col=rmd.getColumnCount();
        for (int i = 1; i <= col; i++) {
            System.out.printf("%15s|",rmd.getColumnName(i).toUpperCase()+" ");
        }
        System.out.println();
        while(r.next()){
            for (int i = 1; i <= col; i++) {
                System.out.printf("%15s|",r.getString(i)+" ");
            }
            System.out.println();
        }
    }
    public DriverData getNewDriver(){
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        while(name.isEmpty()) name=sc.nextLine();
        System.out.print("Enter number: ");
        long num = sc.nextLong();
        while (!(num + "").matches("[0-9][0-9]{9}")) {
            System.out.println("Enter valid mobile number.");
            num = sc.nextLong();
        }
        sc.nextLine();
        System.out.print("Enter License number:");
        String license=sc.nextLine();
        while(license.isEmpty()) license=sc.nextLine();
        System.out.print("Enter salary: ");
        int salary = sc.nextInt();
        return  new DriverData(name,num,license,salary);
    }

    public int getRemoveId() {
        System.out.print("Enter id to be deleted: ");
        return sc.nextInt();
    }

    public Order getOrder() {
        System.out.print("Enter Destination City:");
        String destination=sc.next();
        System.out.print("Enter the charges:");
        int charges=sc.nextInt();
        return new Order(destination,charges);
    }

    public SupervisorDetails getNewSupervisor() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        while(name.isEmpty()) name=sc.nextLine();
        System.out.print("Enter number: ");
        long num = sc.nextLong();
        while (!(num + "").matches("[0-9][0-9]{9}")) {
            System.out.println("Enter valid mobile number.");
            num = sc.nextLong();
        }
        System.out.print("Enter city: ");
        String city = sc.next();
        System.out.print("Enter salary: ");
        int salary = sc.nextInt();
        return new SupervisorDetails(name,num,city,salary);
    }

    public SupervisorDetails getReplacementSupervisor() {
        System.out.print("Name:");
        String name=sc.nextLine();
        System.out.print("Number:");
        long num=sc.nextLong();
        System.out.print("Salary:");
        int sal=sc.nextInt();
        return new SupervisorDetails(name,num,sal);
    }
    public Delivery getDeliveryDetails(ResultSet destinations, ResultSet drivers,String city) throws SQLException {
        System.out.println("Available Drivers");
        printResultSet(drivers);
        System.out.print("Enter driver id: ");
        int id=sc.nextInt();
        sc.nextLine();
//        printResultSet(destinations);
        ArrayList<String> dest=new ArrayList<>();
        System.out.println("   DESTINATIONS|");
        while (destinations.next()){
            String s=destinations.getString(1);
            dest.add(s);
            System.out.println(dest.size()+"  "+s);
        }
        System.out.print("Enter Destination id: ");
        int destId=sc.nextInt();
        while (destId<1 || destId>dest.size()){
            System.out.println("Enter valid choice.");
            System.out.print("Enter Destination id: ");
            destId=sc.nextInt();
        }
//        String destination=sc.nextLine();
        return new Delivery(id,dest.get(destId-1),city);
    }
}
