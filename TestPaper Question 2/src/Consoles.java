public abstract class Consoles implements IConsoles {
    private String deviceType;
    private String storeName;
    int amountOfSales;

    Consoles(String deviceType, String storeName, int amountOfSales) {
        this.deviceType = deviceType;
        this.storeName = storeName;
        this.amountOfSales = amountOfSales;
    }

    protected Consoles() {
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getAmountOfSales() {
        return amountOfSales;
    }
}
