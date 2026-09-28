public abstract class ConsolesSales extends Consoles {
    public ConsolesSales(String deviceType, String storeName, int totalSales) {
        super(deviceType, storeName, totalSales);
    }
    public void printReport(){
        System.out.println("\nConsole Sales Report");
        System.out.println("**********************");
        System.out.println("DEVICE TYPE:" + getConsoleType());
        System.out.println("STORE NAME:" + getStoreName());
        System.out.println("TOTAL SALES:" + getAmountOfSales());
    }
}
