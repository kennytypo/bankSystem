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

    public static void transferMoney(int accountID,double amountToTransfer) throws Exception{
        double firstAccountBalance = 0;
        Connection connection = DataBase.connection();
        int result = 0;
        PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM bank_accounts WHERE account_id = ?");
        statement.setInt(1,accountID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            result = resultSet.getInt(1);
        }

        if (result == 1){
            String sql = "SELECT total_amount FROM bank_accounts WHERE account_id = ?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1,ID);
            ResultSet set = statement1.executeQuery();
            while (set.next()){
                firstAccountBalance = set.getDouble(1);
            }

            double secondAccountBalance = 0;
            PreparedStatement statement4 = connection.prepareStatement("SELECT total_amount FROM bank_accounts WHERE account_id = ?");
            statement4.setInt(1,accountID);
            ResultSet set1 = statement4.executeQuery();
            while (set1.next()){
                secondAccountBalance = set1.getDouble(1);
            }

            secondAccountBalance = secondAccountBalance + amountToTransfer;
            firstAccountBalance  = firstAccountBalance - amountToTransfer;
            if (firstAccountBalance < 0){
                System.out.println("----------Error! Not enough money! Try other amount!------------");
            }else{
                PreparedStatement statement2 = connection.prepareStatement("UPDATE bank_accounts SET total_amount = ? WHERE account_id = ?");
                statement2.setDouble(1, firstAccountBalance);
                statement2.setInt(2, ID);
                statement2.executeUpdate();
                PreparedStatement statement3 = connection.prepareStatement("UPDATE bank_accounts SET total_amount = ? WHERE account_id = ?");
                statement3.setDouble(1,secondAccountBalance);
                statement3.setInt(2,accountID);
                statement3.executeUpdate();
                getBalance();
            }
        }else{
            System.out.println("Wrong account ID!");
        }
    }



}
