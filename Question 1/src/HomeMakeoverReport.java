
import java.util.Scanner;

public class HomeMakeoverReport {

    public static void main(String[] args) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN"};
        int[][] data = {
                {8, 2, 5}, // JAN
                {7, 4, 5}, // FEB
                {5, 5, 2}, // MAR
                {2, 2, 3}, // APR
                {7, 7, 9}, // MAY
                {7, 8, 5} // JUN
        };
        String[] categories = {"Bathrooms", "Kitchens", "Garden"};

        // Print header
        System.out.println("****************************************");
        System.out.println("HOME MAKEOVER REPORT");
        System.out.println("****************************************");
        System.out.println();
        System.out.printf("%-8s %-12s %-12s %-12s\n", "Month", "Bathrooms", "Kitchens", "Garden");

        // Print table and calculate totals
        int[] monthlyTotals = new int[6];

        for(int i = 0; i < months.length; i++) {
            System.out.printf("%-8s", months[i]);
            int total = 0;
            for(int j = 0; j < 3; j++) {
                System.out.printf(" %-12d", data[i][j]);
                total += data[i][j];
            }
            monthlyTotals[i] = total;
            System.out.println();
        }

        // Print Monthly Totals
        System.out.println("*************************************");
        System.out.println("MONTHLY TOTALS");
        System.out.println("*************************************");
        System.out.printf("%-8s %-8s\n", "Month", "Total");

        for(int i = 0; i < months.length; i++) {
            System.out.printf("%-8s %-8d", months[i], monthlyTotals[i]);
            // Add *** if total >= 15
            if(monthlyTotals[i] >= 15) {
                System.out.print(" ***");
            }
            System.out.println();
        }
    }
}

