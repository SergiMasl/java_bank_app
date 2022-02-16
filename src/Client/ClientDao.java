package Client;

import java.sql.SQLException;

public interface ClientDao {

    void addNewClient(Client client) throws SQLException;
     boolean logIn(Client client) throws SQLException;
    void showAccounts(Client client) throws SQLException;
    int showBalance(Client client) throws SQLException;
    void createNewAccount(Client client) throws SQLException;
    void showPendings(Client client) throws SQLException;
    void depOrWithd(int balance, Client client) throws SQLException;
    void clean(Transactions newTrans) throws SQLException;
    void generateTransactions(Transactions newTrans) throws SQLException;
    void showALLBalance(Client client2) throws SQLException;


}
