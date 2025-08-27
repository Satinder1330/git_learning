import java.sql.*;
import java.util.Scanner;

class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/bank";
    private static final String username="root";
    private static final String password ="Basoli@1313";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(
                    url, username, password);
            connection.setAutoCommit(false);
            String query1 = "UPDATE bank_account SET balance =(balance-?) WHERE acc_num =?";
            System.out.println("enter account number");
            int accountDebit = input.nextInt();
            System.out.println("enter amount");
            int amount  = input.nextInt();
            PreparedStatement debit = connection.prepareStatement(query1);
            debit.setInt(1,amount);
            debit.setInt(2,accountDebit);
            String query2 = "UPDATE bank_account SET balance =(balance+?) WHERE acc_num =?";
            PreparedStatement credit = connection.prepareStatement(query2);
           credit.setInt(1,amount);
            System.out.println("account number do you want to transfer the money");
            int accountCredit  = input.nextInt();
           credit.setInt(2,accountCredit);
            debit.executeUpdate();
            credit.executeUpdate();
           if(isbalance(connection,accountDebit,amount)){
               connection.commit();
               System.out.println("succesfully transfered");
           }else{
               connection.rollback();
               System.out.println("failed and you are not charged");
           }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static  boolean isbalance(Connection connection,int accountDebit, int amount ){
        String query = "Select balance from bank_account WHERE acc_num = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,accountDebit);
         ResultSet resultSet =  preparedStatement.executeQuery();
         int balance = 0;
         if(resultSet.next()){
              balance = resultSet.getInt("balance");
         }
            if (amount<=balance){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("exception "
                    + e.getMessage());
        }return false;
    }
}