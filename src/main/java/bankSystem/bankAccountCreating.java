package bankSystem;
import java.sql.*;

public class bankAccountCreating {
    private static int accountId;
    private static String accountPassword;

    private static String cardHolderName;
    private static String cardHolderSurname;


    public static void setCardHolderName(String cardHolderName) {
        bankAccountCreating.cardHolderName = cardHolderName;
    }

    public static void setCardHolderSurname(String cardHolderSurname) {
        bankAccountCreating.cardHolderSurname = cardHolderSurname;
    }

    public static String getCardHolderName() {
        return cardHolderName;
    }

    public static String getCardHolderSurname() {
        return cardHolderSurname;
    }

    public static void createAccount() throws Exception{
        try{
            accountId = getAccountId();
            accountId++;
            accountPassword = generatePassword();
            String name = getCardHolderName();
            name = name.substring(0,1).toUpperCase() + name.substring(1);
            String surname = getCardHolderSurname();
            surname = surname.substring(0,1).toUpperCase() + surname.substring(1);

            Connection connection = DataBase.connection();
            String sql = "INSERT INTO bank_accounts(account_id,first_name,second_name,password,total_amount) VALUES(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,accountId);
            statement.setString(2,name);
            statement.setString(3,surname);
            statement.setString(4,accountPassword);
            statement.setInt(5,0);
            statement.executeUpdate();
            System.out.println("---------------Registration is done!----------------");
            System.out.println("Your account ID (save it): " + accountId);
            System.out.println("Your pin-code (save it): " + accountPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static int getAccountId() throws Exception{
        int size = 0;
        Connection connection = DataBase.connection();
        String sql = "SELECT account_id FROM bank_accounts";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            size++;
        }
        return size;
    }

    public static String generatePassword(){
        Integer length = 4;
        String passSet = "1234567890";
        char[] cardNumber = new char[length];

        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random()*passSet.length());
            cardNumber[i] = passSet.charAt(rand);
        }
        return new String(cardNumber);
    }
}


/*
    НЕ ЗАБЫТЬ!!!!!!!
    кароче пытался засунуть в таблицу хоть какие-нибудь данные,но ничего не получилось следовательно:
    1. разобраться как сунуть данные в таблу - DONE!
    2. разобраться как доставать данные из таблицы - DONE!
    3. Дописать регистрацию аккаунта. - DONE!
    4. Если получится все, что идет перед этим пунктом, приступить к созданию класса, отвечающего за банковские операции.
    5. Прописать мейн класс который и будет запускать программу(сделать более менее адекватный вывод информации)+(цикл)
    6. Забилдить программу, и попытаться запустить ее в консоле.
    7. Если все окей, залить программу на Github, чтобы все поняли, что я крутой Java Dev
*/
