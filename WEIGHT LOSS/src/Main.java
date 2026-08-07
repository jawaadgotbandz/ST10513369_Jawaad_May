//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    WeightLossModel model = new WeightLossModel();
    model.CustomerName = "Jawaad Ganief";
    model.WeightLoss = 20;

    PrintWeightLoss printWeightLoss = new PrintWeightLoss(model);
    printWeightLoss.Print();
}
