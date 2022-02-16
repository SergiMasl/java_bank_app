package admin;

public class Admin {
    private int id;
    private String userName;
    private String email;
    private String pin;
    private String accountNumber;

    public Admin(){

    }

    public Admin(String pin, String userName ) {
        this.pin = pin;
        this.userName = userName;
    }

    public Admin(int id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getPin() {
        return pin;
    }
    public String getAccountNumber () {
        return accountNumber ;
    }

    public void setId(int id) {
        this.id = id;
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
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
