//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    SalesModel model = new SalesModel();
    model.shoeBrand = "NIKE";
    model.brandSales = 100;

    PrintSales printSales = new PrintSales(model);
    printSales.Print();
}
