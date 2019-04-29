package clinicServices;

/*Class for the result of a consultation*/
/*If a disease was found, the result consists of the name of the disease, the treatment and other obs.*/
/*Otherwise, it contains a simple conclusion of the consultation*/
public class ConsultationResult {
    private String disease;
    private Prescription prescription;
    private String conclusion;

    public ConsultationResult(String disease, Prescription prescription) {
        this.disease = disease;
        this.prescription = prescription;
    }

    public ConsultationResult(String conclusion) {
        this.conclusion = conclusion;
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

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
