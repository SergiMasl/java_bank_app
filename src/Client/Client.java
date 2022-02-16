package Client;
public class Client {
    private int id;
    private String userName;
    private String email;
    private String pin;
    private String fname;
    private String lname;

    public Client(){

    }

    public Client(String pin, String userName ) {
        this.pin = pin;
        this.userName = userName;
    }

    public Client(int id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getLname() {
        return lname;
    }
    public String getFname() {
        return fname;
    }
    public String getPin() {
        return pin;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }
    public void setPin(String pin) {
        this.pin  = pin ;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
