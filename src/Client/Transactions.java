package Client;

public class Transactions {
    private int id_client;
    private int id_other_client;
    private int balance_befor;
    private int balance_after;
    private int id_acc;

    public Transactions(int id_client, int id_other_client ,int balance_befor, int balance_after, int id_acc ) {
        this.id_client = id_client;
        this.id_other_client = id_other_client;
        this.balance_befor = balance_befor;
        this.balance_after = balance_after;
        this.id_acc = id_acc;
    }

    public int getid_client() {
        return id_client;
    }
    public int getid_other_client() {
        return id_other_client;
    }
    public int getbalance_befor() {
        return balance_befor;
    }
    public int getbalance_after() {
        return balance_after;
    }
    public int getid_acc() {
        return id_acc;
    }

    public void setid_acc(int id_acc) {
        this.id_acc = id_acc;
    }
    public void setid_client(int id_client) {
        this.id_client = id_client;
    }
    public void setid_other_client(int id_other_client) {
        this.id_other_client = id_other_client;
    }
    public void setbalance_befor(int balance_befor) {
        this.balance_befor = balance_befor;
    }
    public void setbalance_after(int balance_after) {
        this.balance_after = balance_after;
    }

}
