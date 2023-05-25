package bankSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountMenu {
    private static int ID = Authorization.getAccountID();

    public static void deposit() throws Exception{
        Scanner sc = new Scanner(System.in);

        Connection connection = DataBase.connection();
        String sql = "UPDATE bank_accounts SET total_amount = ? WHERE account_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.print("Enter the the amount to deposit: ");
        double deposit = sc.nextDouble();

        statement.setDouble(1,deposit);
        statement.setInt(2,ID);
        statement.executeUpdate();


        PreparedStatement statement2 = connection.prepareStatement("SELECT total_amount FROM bank_accounts WHERE account_id = ?");
        statement2.setInt(1,ID);
        ResultSet resultSet = statement2.executeQuery();


        double balance = 0;
        while(resultSet.next()){
            balance = resultSet.getDouble(1);
        }
        System.out.println("------------------------------------------");
        System.out.println("Your balance now: " + balance);
        System.out.println("------------------------------------------");
    }

    public static void withdraw() throws Exception{
        Scanner sc = new Scanner(System.in);

        double balance = 0;
        Connection connection = DataBase.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT total_amount FROM bank_accounts WHERE account_id = ?");

        statement.setInt(1,ID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            balance = resultSet.getDouble(1);
        }

        System.out.print("Enter the amount to withdraw: ");
        double toWithdraw = sc.nextDouble();
        double newBalance = balance - toWithdraw;
        if (newBalance < 0){
            System.out.println("Wrong amount to withdraw!");
        }else{
            PreparedStatement statement2 = connection.prepareStatement("UPDATE bank_accounts SET total_amount = ? WHERE account_id = ?");
            statement2.setDouble(1,newBalance);
            statement2.setInt(2,ID);
            statement2.executeUpdate();
        }

        PreparedStatement statement3 = connection.prepareStatement("SELECT total_amount FROM bank_accounts WHERE account_id = ?");
        statement3.setInt(1,ID);
        ResultSet set = statement3.executeQuery();
        while(set.next()){
            balance = set.getDouble(1);
        }
        System.out.println("------------------------------------------");
        System.out.println("Your balance now: " + balance);
        System.out.println("------------------------------------------");
    }

    public static void getBalance() throws Exception{
        double balance = 0;
        Connection connection = DataBase.connection();

        PreparedStatement statement = connection.prepareStatement("SELECT total_amount FROM bank_accounts WHERE account_id = ?");
        statement.setInt(1,ID);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            balance = set.getDouble(1);
        }
        System.out.println("------------------------------------------");
        System.out.println("Your balance " + balance);
        System.out.println("------------------------------------------");
    }

}
