package admin;

import Client.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AdminDao {

    void addNewClientAccount(Admin admin) throws SQLException;
    boolean logIn(Admin admin) throws SQLException;

    void createNewAccount(int paID) throws SQLException;
    void disAprove(int paID) throws SQLException;
    void checkAccountOfClient(int accOfClent) throws SQLException;
    void showDisApproveAccounts() throws SQLException;
    void transactionsList(int accOfClent) throws SQLException;



}
