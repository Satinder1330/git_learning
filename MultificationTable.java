import java.util.Scanner;

public class MultificationTable {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("enter the number :");
        int num = input.nextInt();
        table(num);
    }


    public static void table(int num) {
        int i=1;
        while(i<=10){
            System.out.println(num+"x"+i+"="+(num*i));
            i++;

        }

    }
}
