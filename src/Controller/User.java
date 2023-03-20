package Controller;
import java.sql.SQLException;
abstract class User {
    private String name;
    private int id;
    private String city;
    public User(String name,int id,String city,String role){
        this.name=name;
        this.city=city;
        this.id=id;
    }
    abstract protected void controller() throws SQLException;

    public String getName() { return name; }
    public int getId() { return id; }
    public String getCity() { return city; }
}
