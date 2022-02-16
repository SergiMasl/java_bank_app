package admin;

import Client.Client;

import java.sql.SQLException;
import java.util.Scanner;

public class MainAdmin {

    public void adminChoose(int option) throws SQLException {
        Scanner sc = new Scanner(System.in);
        AdminDao dao = AdminDaoFactory.getAdminDao();

                boolean isLogin = false;
                System.out.println("\n===Sign In===\n");
                System.out.println("Enter user name: ");
                String adminName = sc.next();
                System.out.println("Enter your pin: ");
                String pin = sc.next();

                Admin admin = new Admin();
                admin.setName(adminName);
                admin.setPin(pin);

                isLogin = dao.logIn(admin);
                if (isLogin) {
                    System.out.println("\n Login Success!");

                    int option_admin = 0;
                    boolean isDone = false;
                    do {
                        System.out.println("\n\n1. Check new accounts");
                        System.out.println("2. Check account of client");
                        System.out.println("3. Check dis Approve accounts");
                        System.out.println("4. Check transactions");
                        System.out.println("0. Log out\n");

                        System.out.println("Select an option");
                        option_admin = sc.nextInt();
                        switch (option_admin) {
                            case 1:
                                System.out.println("=== Check new accounts ===\n");
                                dao.addNewClientAccount(admin);
                                break;
                            case 2:
                                System.out.println("=== Check accounts of client ===\n");
                                System.out.println("Type id of client: ");
                                int accOfClent = sc.nextInt();
                                dao.checkAccountOfClient(accOfClent);
                                break;
                            case 3:
                                System.out.println("=== Check dis Approve accounts ===\n");
                                dao.showDisApproveAccounts();
                                break;
                            case 4:
                                System.out.println("=== Check transactions ===\n");
                                System.out.println("Type id of client: ");
                                System.out.println("Or type '0' for see all transactions: ");
                                int transactions = sc.nextInt();
                                dao.transactionsList(transactions);
                                break;

                            case 0:
                                isDone = true;
                                isLogin = true;
                                System.out.println("Log out");
                                break;
                        }
                    } while (!isDone);
        }
    }

}
