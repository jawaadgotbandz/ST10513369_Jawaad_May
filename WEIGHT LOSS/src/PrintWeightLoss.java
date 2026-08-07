public class PrintWeightLoss extends WeightLoss{
    public PrintWeightLoss(WeightLossModel model){
        super (model);
    }

    public void Print(){
        System.out.println("********************************************");
        System.out.println("CUSTOMER WEIGHTLOSS PRINTOUT");
        System.out.println("********************************************");
        System.out.println("CUSTOMER NAME:" + getCustomerName());
        System.out.println("WEIGHT LOSS:" + getWeightLoss() + "KG");
        System.out.println("********************************************");
    }
}
