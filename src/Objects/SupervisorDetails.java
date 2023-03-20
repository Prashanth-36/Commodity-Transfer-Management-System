package Objects;
public class SupervisorDetails {
    public String name;
    public long num;
    public String city;
    public int salary;

    public SupervisorDetails(String name, long num, String city, int salary) {
        this.name = name;
        this.num = num;
        this.city = city;
        this.salary = salary;
    }

    public SupervisorDetails(String name, long num, int salary) {
        this.name = name;
        this.num = num;
        this.salary = salary;
    }
}
