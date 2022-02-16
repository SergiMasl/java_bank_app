package loggin;
import Client.MainClient;
import admin.MainAdmin;

import java.util.Scanner;

public class Main {
    public static int firstStep;

    public static void main(String[] args) throws Exception {
        int option = 0;
        Scanner sc = new Scanner(System.in);

        while (option == 0){
            System.out.println("===Select an option===\n");
            System.out.println("1. Create new account");
            System.out.println("2. Sign In");
            System.out.println("3. Sign In as admin");

            while (option < 1 || option > 3){
                option = sc.nextInt();
            }

            if(option == 1){
                MainClient mainClient = new MainClient();
                mainClient.userChoose(option);
            }
            if(option == 2){
                MainClient mainClient = new MainClient();
                mainClient.userChoose(option);
            }
            if(option == 3){
                MainAdmin mainAdmin = new MainAdmin();
                mainAdmin.adminChoose(option);
            }



        }
    }
}
