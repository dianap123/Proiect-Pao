package clinicServices;

/*Class for the result of a consultation*/
/*If a disease was found, the result consists of the name of the disease, the treatment and other obs.*/
/*Otherwise, it contains a simple conclusion of the consultation*/
public class ConsultationResult {
    private String disease;
    private Prescription prescription;
    String otherObs;

    public ConsultationResult(String disease, Prescription prescription, String otherObs) {
        this.disease = disease;
        this.prescription = prescription;
        this.otherObs = otherObs;
    }

    public ConsultationResult(String otherObs) {
        this.otherObs = otherObs;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getOtherObs() {
        return otherObs;
    }

    public void setOtherObs(String otherObs) {
        this.otherObs = otherObs;
    }
}
