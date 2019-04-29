package clinicServices;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Prescription {
    //contine medicamentul si cantitatea
    private Map<String, Double> medicines = new HashMap<>();
    private String obs;

    public Prescription() {}

    public Prescription(Map<String, Double> medicines, String obs) {
        this.medicines = medicines;
        this.obs = obs;
    }

    public Map<String, Double> getMedicines() {
        return medicines;
    }

    public void setMedicines(Map<String, Double> medicines) {
        this.medicines = medicines;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}

