package Client;

import loggin.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientDaoImpl implements ClientDao {

    Connection connection;
    private int acc;

    private int balance_befor;
    private int id_client;
    private int id_other_client;
    private int balance_after;
    private int id_acc;
    Transactions newTrans = new Transactions(id_client, id_other_client , balance_befor, balance_after, id_acc);

    public ClientDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }


    @Override
    public void addNewClient(Client client) throws SQLException {
        String sql = "insert into clients (user_name, email, pin, isLogin, client_Fname, client_Lname) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getEmail());
        String pin = generatePin();
        preparedStatement.setString(3, pin);
        preparedStatement.setBoolean(4, true);
        preparedStatement.setString(5, client.getFname());
        preparedStatement.setString(6, client.getLname());
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("Client created!");
            System.out.println("***** YOUR PIN IS: " + pin + " *****");
        }else{
            System.out.println("Oops!, something went wrong");;
        }
    }



    @Override
    public boolean logIn(Client client2) throws SQLException {
        String sql ="select * from clients where user_name = '" + client2.getName() + "' and pin = '" + client2.getPin() + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        Boolean isLog = false;
        while(resultSet.next()){
            client2.setEmail(resultSet.getString(3));
            client2.setName(resultSet.getString(2));
            client2.setId(resultSet.getInt(1));
            isLog = resultSet.getBoolean(4);
        }
        return isLog;
    }



    public void showAccounts(Client client) throws SQLException {
        newTrans.setid_client(client.getId());
        String sql ="select * from accounts where id = '" + client.getId() + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        int accId = 0;
        while(resultSet.next()){
            System.out.println("Your accounts: " + resultSet.getInt(1) + ". Status is: " + resultSet.getBoolean(1));
            System.out.println("Choose your account for balance");
            accId = resultSet.getInt("id");
        }

        if(client.getId() == accId) {
            System.out.println("Choose your account for balance");
            Scanner sc2 = new Scanner(System.in);
            acc = sc2.nextInt();
        } else {
            System.out.println("Account not found.");
        }
    }

    @Override
    public int showBalance(Client client) throws SQLException {
        String sql ="select * from accounts where acc_id = '" + acc + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        int balance = 0;
        while(resultSet.next()){
            if(client.getId() == resultSet.getInt(3)) {
                System.out.println("Account number: " + resultSet.getInt(1) + " Balance: " + resultSet.getInt(2));
                balance = resultSet.getInt("balance");
            }else{
                    System.out.println("Account not found.");
                    balance = -1;
                }
        }
        return balance;
    }

    @Override
    public void createNewAccount(Client client) throws SQLException {
        String sql = "insert into PreAccounts (balance, id, isActiv) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 0);
        preparedStatement.setInt(2, client.getId());
        preparedStatement.setBoolean(3, false);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("Your account is created, manager will approve it shortly");
        }else{
            System.out.println("Oops!, something wen t wrong");;
        }
    }

    @Override
    public void showPendings(Client client) throws SQLException {
        String sql ="select * from PreAccounts where id = '" +  client.getId() + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while(resultSet.next()){
                System.out.println("Account number: "+ resultSet.getInt(1) + " Status: " + resultSet.getBoolean(4));
            }

    }

    @Override
    public void depOrWithd(int balance, Client client) throws SQLException {
        if(balance != -1){
        Scanner sc = new Scanner(System.in);
        int option = -1;
        int total = 0;
        boolean isOperation = false;
        System.out.println("\n1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Send to another client");
        System.out.println("0. Cancel");
        while (option < 0 || option > 3){
            option = sc.nextInt();
        }
        switch (option) {
            case 0:
                isOperation = false;
                break;
            case 1:
                total = deposit(balance);
                isOperation = true;
                break;
            case 2:
                total = withdraw(balance);
                isOperation = true;
                break;
            case 3:
                total = sendOther(balance);
                if(total == -1){
                    break;
                };
                int otherClientAcc = getOtherAcc();
                boolean check = check(otherClientAcc);
                if (check){
                    isOperation = true;
                    newTrans.setid_other_client(otherClientAcc);
                    String sql2 = "UPDATE accounts SET balance = balance + "+ total +" WHERE acc_id = '" + otherClientAcc +"'";
                    connection.createStatement().executeUpdate(sql2);
                    System.out.println("Success");
                } else{
                    System.out.println("Something wrong");
                }
                break;
        }
        if (isOperation){
            String sql = "UPDATE accounts SET balance = " + total +" WHERE acc_id = '" + acc +"'";
            connection.createStatement().executeUpdate(sql);
            newTrans.setid_acc(acc);
            generateTransactions(newTrans);
            clean(newTrans);
        }
    }
    }

    public String generatePin(){
        int length = 4;
        String passwordSet = "1234567890";
        char[] cardNum = new char[length];

        for (int i = 0; i < length; i++){
            int rand = (int) (Math.random() * passwordSet.length());
            cardNum[i] = passwordSet.charAt(rand);
        }
        return new String(cardNum);
    }

    public int deposit(int balance){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type amount: ");
        int amount = sc.nextInt();
        newTrans.setbalance_befor(balance);
        balance += amount;
        newTrans.setbalance_after(balance);
        return balance;
    }

    public int withdraw(int balance){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type amount: ");
        int amount = sc.nextInt();
        if(balance < amount){
            System.out.println("You cannot do it, your amount is greater than deposed");
        }else{
            newTrans.setbalance_befor(balance);
            balance -= amount;
            newTrans.setbalance_after(balance);
        }
        return balance;
    }
    public int sendOther(int balance){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type amount: ");
        int amount = sc.nextInt();
        if(balance < amount){
            System.out.println("You cannot do it, your amount is greater than deposed");
            balance = -1;
        }else{
            newTrans.setbalance_befor(balance);
            balance -= amount;
            newTrans.setbalance_after(balance);
        }
        return balance;
    }

    public int getOtherAcc(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type client's account: ");
        return sc.nextInt();
    }

    public boolean check(int otherClientAcc) throws SQLException {
        boolean isTrue = false;
        String sql ="select * from accounts where acc_id = '" +  otherClientAcc + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while(resultSet.next()){
            isTrue = resultSet.getBoolean(4);
        }
        System.out.println(isTrue);
        return isTrue;
    }

    @Override
    public void clean(Transactions newTrans) throws SQLException {
        newTrans.setid_client(0);
        newTrans.setbalance_befor(0);
        newTrans.setid_other_client(0);
        newTrans.setbalance_after(0);
        newTrans.setid_acc(0);
    }

    @Override
    public void generateTransactions(Transactions newTrans) throws SQLException {
        String sql = "insert into transactions (id_client, id_other_client , balance_befor, balance_after, acc_num) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, newTrans.getid_client());
        preparedStatement.setInt(2, newTrans.getid_other_client());
        preparedStatement.setInt(3, newTrans.getbalance_befor());
        preparedStatement.setInt(4, newTrans.getbalance_after());
        preparedStatement.setInt(5, newTrans.getid_acc());
        int count = preparedStatement.executeUpdate();
    }

    public void showALLBalance(Client client) throws SQLException {
      //  String sql = "select , disApproveAccounts .why from clients inner join disApproveAccounts on clients.id=disApproveAccounts.id_client;";

        String sql ="select * from accounts where id = '" + client.getId() + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while(resultSet.next()){
            if(client.getId() == resultSet.getInt("id")) {
                System.out.println("Account number: " + resultSet.getInt(1) + " Balance: " + resultSet.getInt(2));
            }else{
                System.out.println("Account not found.");
            }
        }
    }

}
