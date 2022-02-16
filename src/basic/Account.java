package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {

    private String log;
    private String password;
    private int balance;

    Account(String log) {
        this.log = log;
        //this.password = password;
    }

    public Boolean register () throws SQLException { //делаем регестрацию
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_demo", "root", "root"
            );
            String sql = "insert into users (log) values('" + this.log + "');";
            connection.createStatement().executeUpdate(sql);

            //last id
            String sql2 = "SELECT LAST_INSERT_ID();";
            ResultSet rs = connection.createStatement().executeQuery(sql2);

            int last_user_id = 0;
            while(rs.next()){
                last_user_id = rs.getInt(1);
            }

            String cardNum = this.generateNum();
            String code = this.generatePin();

            //card and pin
            String sql3 = "insert into card values ('" + last_user_id + "', '" + cardNum + "', '" + code +"');";
            connection.createStatement().executeUpdate(sql3);

            //balance
            String sql4 = "insert into balance values ('" + cardNum + "', " + "0" + ");";
            connection.createStatement().executeUpdate(sql4);

            connection.close();
            System.out.println("\n\nbasic.Account was successfully created.\n");
            System.out.println("Your card number: " + cardNum);
            System.out.println("Your pin: " + code);

        } catch (SQLException e) {
            System.out.println("Oops!, something went wrong");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String generateNum(){
        int length = 8;
        String passwordSet = "1234567890";
        char[] cardNum = new char[length];

        for (int i = 0; i < length; i++){
            int rand = (int) (Math.random() * passwordSet.length());
            cardNum[i] = passwordSet.charAt(rand);
        }
        return new String(cardNum);
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
}
