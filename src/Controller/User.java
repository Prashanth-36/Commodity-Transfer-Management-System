package Controller;
import java.sql.SQLException;
abstract class User {
    private String name;
    private int id;
    private String city;
    private String role;
    public User(String name,int id,String city,String role){
        this.name=name;
        this.city=city;
        this.id=id;
        this.role=role;
    }
    abstract protected void controller() throws SQLException;

    public String getName() { return name; }
    public int getId() { return id; }
    public String getCity() { return city; }
    public String getRole() { return role; }
}
