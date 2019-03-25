package clinicServices;


import person.Doctor;
import person.Patient;
import java.time.LocalDateTime;

/*Class for appointments. It has the same structures for patients and doctors*/
public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime date;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
