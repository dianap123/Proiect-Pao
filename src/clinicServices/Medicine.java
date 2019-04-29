package clinicServices;

/*Class for medicines used in a prescription*/
public class Medicine {
    private String name;
    private String effect;
    private String administration;

    public Medicine(String name, String effect, String administration) {
        this.name = name;
        this.effect = effect;
        this.administration = administration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nEffect: " + this.effect + "\nAdministation: " + this.administration;
    }
}
