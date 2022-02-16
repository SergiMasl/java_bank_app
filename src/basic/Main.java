package basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static boolean isLogin = false;

    public static void main(String[] args) throws Exception {
        int option = 0;
        Scanner sc = new Scanner(System.in);

        while (option == 0){
            System.out.println("===Select an option===\n");
            System.out.println("1. Create new account");
            System.out.println("2. Sign In\n");

            while (option < 1 || option > 2){
                option = sc.nextInt();
            }
        }

        switch (option){
            case 1:
                System.out.println("===Create new account===\n");
                System.out.println("Enter user name: ");
                String log = sc.next();

                Account acc = new Account(log);
                acc.register();
                break;
            case 2:
                System.out.println("\n\n\n===Sign In===\n");
                System.out.println("Enter your card number: ");
                String cardNum = sc.next();
                System.out.println("Enter your pin: ");
                String pin = sc.next();
                Operation operation = new Operation(cardNum, pin);

                try{
                    Connection connection = DataBase.connection();
                    String sql = "Select * from card where cardNum = '"+ cardNum +"' and '" + pin +"';";
                    ResultSet rs = connection.createStatement().executeQuery(sql);

                    if(rs.next()){
                        isLogin = true;
                        System.out.println("\n Login Success!");

                        System.out.println("1. Check my accounts");
                        System.out.println("2. Check balance");
                        System.out.println("3. Send money to other person");
                        System.out.println("4. Log out");


                        int option_user = 0;

                        boolean isDone = false;
                        do {
                            int balance = 0;
                            System.out.println("Select an option");
                            option_user = sc.nextInt();
                                switch (option_user) {
                                    case 1:
                                        System.out.println("===Show Balance===\n");
                                        balance = operation.showBalance(cardNum);
                                        System.out.println(balance + " $");
                                        isDone = false;
                                        break;
                                    case 2:
                                        System.out.println("===Make Deposit===\n");
                                        int amount = 0;
                                        while (amount <= 0) {
                                            System.out.println("Type amount:");
                                            amount = sc.nextInt();
                                        }

                                        operation.deposit(amount, cardNum);
                                        balance = operation.showBalance(cardNum);
                                        System.out.println("Current balance: " + balance + "$");
                                        isDone = false;
                                        break;
                                    case 3:
                                        System.out.println("===Send money===\n");
                                        System.out.println("Enter number of card of client\n");
                                        String numOfClent = sc.next();
                                        int amoutOfClient = 0;
                                        while (amoutOfClient <= 0) {
                                            System.out.println("Type amount:");
                                            amoutOfClient = sc.nextInt();
                                        }

                                        operation.sendMoneyToOther(amoutOfClient, numOfClent, cardNum);
                                        System.out.println("You sent " + amoutOfClient + "$ to " + numOfClent);
                                        isDone = false;
                                        break;
                                    case 4:
                                        isDone = true;
                                        break;
                                }
                        } while(!isDone);
                    } else {
                        System.out.println("Login fail");
                    }
                 } catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
}

