package bankSystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class bankSystem {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        System.out.println("----------------BANK NAME---------------------");
        System.out.println("Hello! What need to do: ");
        System.out.println("1. Registration");
        System.out.println("2. Authorization");
        System.out.println("3. EXIT");
        System.out.print("Write your choice: ");
        int numberOfMove = sc.nextInt();
        if (numberOfMove == 1){
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("-------------Welcome to registration------------------");
                System.out.print("Type your first name: ");
                String firstName = reader.readLine();
                System.out.print("Type your last name: ");
                String lastName = reader.readLine();

                bankAccountCreating.setCardHolderName(firstName);
                bankAccountCreating.setCardHolderSurname(lastName);
                bankAccountCreating.createAccount();
            }
        } else if (numberOfMove == 2) {
            System.out.println("-------------Welcome to authorization!--------------");
            System.out.print("Enter account ID: ");
            int accountID = sc.nextInt();
            boolean contains = false;
            try{
                Connection connection = DataBase.connection();
                String sql = "SELECT 1 FROM bank_accounts WHERE account_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,accountID);
                ResultSet resultSet = statement.executeQuery();
                int result = 0;
                while (resultSet.next()) {
                    result = resultSet.getInt(1);
                }

                if (result == 1){
                    contains = true;
                    Authorization.setAccountID(accountID);
                }else {
                    System.out.println("Wrong account ID!");
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            if (contains){
                System.out.print("Enter your password: ");
                while (!Authorization.isAuth){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String pass = reader.readLine();
                    Authorization.setPassword(pass);
                    Authorization.auth();
                }
            }

            if (Authorization.isAuth){
                while (true){
                    System.out.println("1. Check balance");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. EXIT");
                    System.out.print("Select an action: ");
                    int action = sc.nextInt();

                    if (action == 1){
                        AccountMenu.getBalance();
                    } else if (action == 2) {
                        AccountMenu.deposit();
                    } else if (action == 3) {
                        AccountMenu.withdraw();
                    } else if (action == 4) {
                        break;
                    }
                }
            }
        }
    }
}
