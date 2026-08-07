public class PrintSales extends Sales{
    public PrintSales(SalesModel model){
        super(model);
    }

    public void Print(){
        System.out.println("**************************************");
        System.out.println("SHOE SALES PRINTOUT");
        System.out.println("**************************************");
        System.out.println("SHOE BRAND:" + getShoeBrand());
        System.out.println("TOTAL SHOE SALES:" + getTotalSales());
        System.out.println("**************************************");
    }
}
