package admin;

import loggin.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminDaoImpl implements AdminDao {

    Connection connection;
    private int acc;

    public AdminDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addNewClientAccount(Admin admin) throws SQLException {
        int idClient = 0;
        String sql ="select * from preAccounts;";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        System.out.println("New accounts:");
        System.out.println("***********");
        while(resultSet.next()){
            idClient = resultSet.getInt(3);
            System.out.println("preAccount Id: " + resultSet.getInt(1) + ". Id of client: " + resultSet.getInt(3));
        }
        System.out.println("***********");
        Scanner sc2 = new Scanner(System.in);
        boolean isDone = false;
        while(!isDone) {
            System.out.println("\nWould you like approve or withdraw some account?");
            System.out.println("if yes, type PreAccount id");
            System.out.println("if not, type '0'");
            int paID = sc2.nextInt();

            if (paID == 0) {
                isDone = true;
                break;
            }

            String sql2 = "select * from preAccounts where acc_id = '" + paID + "';";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            System.out.println("\nif Approve, type '1'");
            System.out.println("if disApprove, type '2'");
            System.out.println("For cancel, type '0'");
            boolean isApprove = false;
            do{
                int yesOrNot = sc2.nextInt();
                switch (yesOrNot) {
                    case 0:
                        isDone = true;
                        isApprove = true;
                        break;
                    case 1:
                        createNewAccount(paID);
                        isApprove = true;
                        isDone = true;
                        break;
                    case 2:
                        disAprove(paID);
                        isApprove = true;
                        isDone = true;
                        break;
                    default:
                        isApprove = true;
                        isDone = true;
                        System.out.println("something wrong");
                }
            } while (!isApprove);

        }
    }

    @Override
    public boolean logIn(Admin admin) throws SQLException {
        String sql ="select * from admins where user_name = '" + admin.getName() + "' and pin = '" + admin.getPin() + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        Boolean isLog = false;
        while(resultSet.next()){
            admin.setName(resultSet.getString(2));
            admin.setId(resultSet.getInt(1));
            isLog = resultSet.getBoolean(4);
        }
        return isLog;
    }

    public void createNewAccount(int paID) throws SQLException {
        String sql1 = "select * from preAccounts where acc_id = '" + paID + "';";
        ResultSet rs = connection.createStatement().executeQuery(sql1);
        while (rs.next()) {
            String sql = "insert into accounts (balance, id, isActiv) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, rs.getInt(3));
            preparedStatement.setBoolean(3, true);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("account approved");
                System.out.println(rs.getInt(1));
                String sql3 = "delete from preAccounts where acc_id = '" + paID + "';";
                connection.createStatement().executeUpdate(sql3);
            } else {
                System.out.println("Oops!, something wen t wrong");
                ;
            }
        }

    }

    @Override
    public void disAprove(int paID) throws SQLException {
        String sql1 = "select * from preAccounts where acc_id = '" + paID + "';";
        ResultSet rs = connection.createStatement().executeQuery(sql1);
        while (rs.next()) {
            String sql = "insert into disApproveAccounts (id_client, why) values (?, ?)";
            Scanner sc = new Scanner(System.in);
            System.out.println("Type reason: ");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rs.getInt(3));
            preparedStatement.setString(2, sc.nextLine());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("account dis approved");
                System.out.println(rs.getInt(1));
                String sql3 = "delete from preAccounts where acc_id = '" + paID + "';";
                connection.createStatement().executeUpdate(sql3);
            } else {
                System.out.println("Oops!, something wen t wrong");
            }
        }
    }

    @Override
    public void checkAccountOfClient(int accOfClent) throws SQLException {
        String sql = "select clients.id, clients.email, clients.user_name, accounts.acc_id, accounts.balance from clients inner join accounts USING (id) where id=" + accOfClent + ";";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while(resultSet.next()){
            System.out.println("User ID: "+ resultSet.getInt("id") + " | Account number: "+ resultSet.getInt("acc_id") + " | user name: " + resultSet.getString("user_name") + " | email of client: " + resultSet.getString("email")+ " | Balance: " + resultSet.getString("balance"));
        }



    }

    @Override
    public void showDisApproveAccounts() throws SQLException {
        String sql = "select clients.id, clients.email, clients.user_name, disApproveAccounts .why from clients inner join disApproveAccounts on clients.id=disApproveAccounts.id_client;";
        ResultSet rs = connection.createStatement().executeQuery(sql);
        while(rs.next()){
            System.out.println("Id of client: " + rs.getInt("id") + " | user name: " + rs.getString("user_name") + " | email of client: " + rs.getString("email") +" | Reason: "+ rs.getString("why"));
        }
    }

    @Override
    public void transactionsList(int transactions) throws SQLException {
        if (transactions == 0){
            String sql = "select * from transactions;";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println("Id: "+ rs.getInt(1) + " | Id of client: " + rs.getInt(2) + " | Id of other client: "+ rs.getInt(3) + " | balance before: "+ rs.getString(4) + " | balance after: "+ rs.getString(5) + " | Client account number: "+ rs.getString(6));
            }
        } else {
            String sql = "select * from transactions where id_client = '" + transactions + "';";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println("Id: "+ rs.getInt(1) + " | Id of client: " + rs.getInt(2) + " | Id of account of other client: "+ rs.getInt(3) + " | balance before: "+ rs.getString(4) + " | balance after: "+ rs.getString(5) + " | Client account number: "+ rs.getString(6));
            }
        }

    }
}
