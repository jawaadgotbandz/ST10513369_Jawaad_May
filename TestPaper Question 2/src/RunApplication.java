import java.util.Scanner;

public class RunApplication {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("ENTER DEVICE TYPE:");
        String type = scanner.nextLine();
        System.out.println("ENTER STORE NAME:");
        String storeName = scanner.nextLine();
        System.out.println("ENTER TOTAL AMOUNT OF SALES:");
        int sales = Integer.parseInt(scanner.nextLine().trim());

        ConsolesSales obj = new ConsolesSales(type, storeName, sales);

        scanner.close();
    }
}