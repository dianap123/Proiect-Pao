package clinicServices;

/*Class for medicines used in a prescription*/
/*It contains the name of the medicine, the total quantity the patient must take during the treatment, the number of days it must be taken and the number of administrations/day*/
public class Medicine {
    protected String name;
    double quantity;
    int numberOfDays;
    int itemsPerDay;

    public Medicine(String name, double quantity, int numberOfDays, int itemsPerDay) {
        this.name = name;
        this.quantity = quantity;
        this.numberOfDays = numberOfDays;
        this.itemsPerDay = itemsPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getItemsPerDay() {
        return itemsPerDay;
    }

    public void setItemsPerDay(int itemsPerDay) {
        this.itemsPerDay = itemsPerDay;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nQuantity: " + this.quantity + "\nNumber of days: " + this.numberOfDays + "\nAdministrations/day: " + this.itemsPerDay;
    }
}
