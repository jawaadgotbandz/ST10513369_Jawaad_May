public abstract class WeightLoss implements IWeightLoss {

    String CustomerName;
    double WeightLoss;

    public WeightLoss(WeightLossModel model){
        this.CustomerName = CustomerName;
        this.WeightLoss = WeightLoss;
    }

    @Override
    public String getCustomerName(){
        return CustomerName;
    }

    @Override
    public double getWeightLoss(){
        return WeightLoss;
    }
}
