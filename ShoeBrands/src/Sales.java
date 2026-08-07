public abstract class Sales implements ISales {

    String shoeBrand;
    int totalSales;

    public Sales(SalesModel model){
        this.shoeBrand = model.shoeBrand;
        this.totalSales = model.brandSales;
    }

    @Override
    public String getShoeBrand(){
        return shoeBrand;
    }

    @Override
    public int getTotalSales(){
        return totalSales;
    }
}
