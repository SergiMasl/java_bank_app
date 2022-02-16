package Client;

import java.sql.SQLException;
import java.util.Scanner;

public class MainClient {

    public void userChoose(int option) throws SQLException {


        Scanner sc = new Scanner(System.in);

        ClientDao dao = ClientDaoFactory.getClientDao();
        switch (option){
            case 1:
                System.out.println("===Create new account===\n");
                System.out.println("Enter your user name: ");
                String userName = sc.next();
                System.out.println("Enter your email: ");
                String email = sc.next();
                System.out.println("Enter First your name: ");
                String fname = sc.next();
                System.out.println("Enter your Last name: ");
                String lname = sc.next();

                Client newClient = new Client();
                newClient.setName(userName);
                newClient.setEmail(email);
                newClient.setFname(fname);
                newClient.setLname(lname);
                dao.addNewClient(newClient);
                break;
            case 2:
                boolean isLogin = false;

                System.out.println("\n===Sign In===\n");
                System.out.println("Enter user name: ");
                String userName2 = sc.next();
                System.out.println("Enter your pin: ");
                String pin = sc.next();

                Client client2 = new Client();
                client2.setName(userName2);
                client2.setPin(pin);

                isLogin = dao.logIn(client2);

                if(isLogin){

                    System.out.println("\n Login Success!");

                    int option_client = 0;
                    boolean isDone = false;
                    do {
                        System.out.println("\n\n1. Show my Balance");
                        System.out.println("2. Create new account");
                        System.out.println("3. Check Pending accounts");
                        System.out.println("4. Withdrawal or Deposit");
                        System.out.println("0. Log out\n");

                        System.out.println("Select an option");
                        option_client = sc.nextInt();
                        switch (option_client) {
                            case 1:
                                int option_client2 = 0;
                                System.out.println("1. Show specific account");
                                System.out.println("2. Show ALL accounts");
                                while (option_client2 < 1 || option_client2 > 2){
                                    option_client2 = sc.nextInt();
                                }
                                switch (option_client2) {
                                    case 1:
                                        System.out.println("=== Show my Balance===\n");
                                        dao.showAccounts(client2);
                                        dao.showBalance(client2);
                                        isDone = false;
                                        break;
                                    case 2:
                                        System.out.println("=== Show my Balances===\n");
                                        dao.showALLBalance(client2);
                                        isDone = false;
                                        break;
                                }
                            case 2:
                                System.out.println("=== Create new account ===\n");
                                dao.createNewAccount(client2);
                                isDone = false;
                                break;
                            case 3:
                                System.out.println("=== Check Pending accounts ===\n");
                                dao.showPendings(client2);
                                isDone = false;
                                break;
                            case 4:
                                System.out.println("=== Withdrawal or Deposit ===\n");
                                dao.showAccounts(client2);
                                int balance = dao.showBalance(client2);
                                dao.depOrWithd(balance, client2);
                                isDone = false;
                                break;
                            case 0:
                                isDone=true;
                                isLogin=true;
                                System.out.println("Log out");
                                break;
                        }
                    } while(!isDone);
                } else{
                    System.out.println("User name or password is wrong");
                }
                break;
        }


    }



}
