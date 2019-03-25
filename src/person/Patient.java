package person;


import clinicServices.Appointment;
import clinicServices.ConsultationResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/*A patient can't add medical details*/
/*Height, weight, fatPercentage, injections can be added only by nurses*/
/*Consultation results, vaccines can be added only by doctors*/
/*Appointments can pe added only after log in or after the account is created*/

public class Patient extends Person {
    private double height;
    private double weight;
    private double fatPercentage;
    private ArrayList<ConsultationResult> consultationResults = new ArrayList<ConsultationResult>();
    private ArrayList<String> vaccines = new ArrayList<String>();
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    private ArrayList<String> injections = new ArrayList<String>();

    public Patient() {
    }

    public Patient(String firstName, String lastName, String CNP, String telephoneNumber, String emailAdress, char gender, LocalDate birthday, String address) {
        super(firstName, lastName, CNP, telephoneNumber, emailAdress, gender, birthday, address);
    }

    public Patient(Person person) {
        super(person);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public ArrayList<ConsultationResult> getConsultationResults() {

        return new ArrayList<>(consultationResults);
    }

    public void setConsultationResults(ArrayList<ConsultationResult> consultationResults) {
        this.consultationResults = new ArrayList<>(consultationResults);
    }

    public ArrayList<String> getVaccines() {

        return new ArrayList<>(vaccines);
    }

    public void setVaccines(ArrayList<String> vaccines) {

        this.vaccines = new ArrayList<>(vaccines);
    }

    public ArrayList<Appointment> getAppointments() {

        return new ArrayList<>(appointments);
    }

    public void setAppointments(ArrayList<Appointment> appointments) {

        this.appointments = new ArrayList<>(appointments);
    }

    public ArrayList<String> getInjections() {

        return new ArrayList<>(injections);
    }

    public void setInjections(ArrayList<String> injections) {

        this.injections = new ArrayList<>(injections);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void addConsultationResult(ConsultationResult result) {
        this.consultationResults.add(result);
    }

    public void addInjection(String name) {
        this.injections.add(name);
    }

    public void sortArrays() {
        Collections.sort(vaccines);
        Collections.sort(injections);
    }
}
