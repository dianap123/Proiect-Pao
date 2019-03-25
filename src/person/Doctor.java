package person;

import clinicServices.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*A doctor has a section, a specialization and a list of services she/he provides (e.g. types of consultations) that must be declared when account is created*/
/*A doctor has one or more nurses, which are added after the account is created, as well as the appointments list*/
public class Doctor extends Person {
    private String section;
    private String specialization;
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    private Map<String, Double> services = new HashMap<String, Double>();
    private ArrayList<Nurse> nurses = new ArrayList<Nurse>();

    public Doctor(Person person) {
        super(person);
    }

    public Doctor(String firstName, String lastName, String CNP, String telephoneNumber, String emailAdress, char gender, LocalDate birthday, String address, String section, String specialization, Map<String, Double> services) {
        super(firstName, lastName, CNP, telephoneNumber, emailAdress, gender, birthday, address);
        this.section = section;
        this.specialization = specialization;
        this.services = services;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<>(this.appointments);
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = new ArrayList<>(appointments);
    }

    public Map<String, Double> getServices() {

        return services;
    }

    public void setServices(Map<String, Double> services) {
        this.services = services;
    }

    public ArrayList<Nurse> getNurses() {

        return new ArrayList<>(nurses);
    }

    public void setNurses(ArrayList<Nurse> nurses) {

        this.nurses = new ArrayList<>(nurses);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}
