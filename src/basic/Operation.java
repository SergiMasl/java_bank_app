package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Operation {

    private String cardNum;
    private String pin;
    private Integer balance;

    public Operation(String cardNum, String pin){
        this.cardNum = cardNum;
        this.pin = pin;
    }

    public Operation(int showBalance) {
    }

    public Integer showBalance(String cardNum){
        try{
            Connection connection = DataBase.connection();
            String sql = "Select * from balance where cardNum = '"+ cardNum +"';";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while(rs.next()){
                this.balance = rs.getInt(2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.balance;
    }

    public void deposit(int amount, String cardNum){
        try{
            Connection connection = DataBase.connection();
            String sql = "UPDATE balance SET balance = balance + '"+ amount +"' WHERE cardNum = '" + cardNum +"'";
            connection.createStatement().executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMoneyToOther(Integer amount_other, String num_other, String cardNum){
        try{
            Connection connection = DataBase.connection();
            String sql = "UPDATE balance SET balance = balance + "+ amount_other +" WHERE cardNum = '" + num_other +"'";
            connection.createStatement().executeUpdate(sql);

            String sql2 = "UPDATE balance SET balance = balance - "+ amount_other +" WHERE cardNum = '" + cardNum +"'";
            connection.createStatement().executeUpdate(sql2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
