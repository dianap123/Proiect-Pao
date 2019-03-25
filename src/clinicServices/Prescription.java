package clinicServices;


import java.util.ArrayList;

public class Prescription {
    private ArrayList<Medicine> medicines = new ArrayList<Medicine>();
    String obs;

    public Prescription() {}

    public Prescription(ArrayList<Medicine> medicines, String obs) {
        this.medicines = medicines;
        this.obs = obs;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
