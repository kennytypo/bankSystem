package bankSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authorization {
    private static int accountID;
    private static String password;
    static boolean isAuth;


    public static int getAccountID() {
        return accountID;
    }

    public static void setAccountID(int accountID) {
        Authorization.accountID = accountID;
    }

    public static void setPassword(String password) {
        Authorization.password = password;
    }

    public static void auth(){
        try{
            Connection connection = DataBase.connection();
            String query = "SELECT password FROM bank_accounts WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,accountID);
            ResultSet resultSet = statement.executeQuery();
            String pass = "";
            while (resultSet.next()){
                pass = resultSet.getString(1);
            }
            if (pass.equals(password)){
                System.out.println("-----------------Welcome!-----------------");
                isAuth = true;
            }else{
                System.out.println("Wrong password! Try again!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

/*
    ЧТО НУЖНО СДЕЛАТЬ:
    1. Добавить более-менее сностный вид авторизации
    2. Сделать максимум 3 попытки
    3. Сделать авторизацию в классе BankSystem
    4. Сделать класс операций, который будет доступен после авторизации!
    5. Сделать возможность пополнения, снятия, перевода средств внутри банковской системы!
*/
