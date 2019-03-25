package serviceClasses;


import person.Doctor;
import person.Nurse;
import person.Patient;
import person.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NurseService extends Service {

    private ArrayList<Nurse> nurses = new ArrayList<Nurse>();

    public ArrayList<Nurse> getNurses() {
        return new ArrayList<>(nurses);
    }

    public Nurse createAccount() {
        Person newPerson = super.createAccount();
        Nurse newNurse = new Nurse(newPerson);
        if (newNurse != null)
            this.nurses.add(newNurse);
        return newNurse;
    }

    public Nurse login() {
        Person person = super.login();
        if (person != null) {
            Nurse nurse = new Nurse(person);
            return nurse;
        }
        return null;
    }

    public void addHeightWeightFat (Patient patient) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the height: ");
        double height = input.nextDouble();
        System.out.println("Please enter the weight: ");
        double weight = input.nextDouble();
        System.out.println("Please enter the fat percentage: ");
        double fatPercentage = input.nextDouble();
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setFatPercentage(fatPercentage);
    }

    public void addInjection (Patient patient) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the substance: ");
        String name = input.next();
        patient.addInjection(name);
    }
    public Patient getPatientByName(String firstname, String lastname, ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            if (patient.getFirstName().equals(firstname) && patient.getLastName().equals(lastname))
                return patient;
        }
        return null;
    }
    public void start() {

        DoctorService doctorService = new DoctorService();

        /*Create an instance of doctor at which the nurse is assigned*/
        Map<String, Double> services = new HashMap<>();
        services.put("Consultatie generala", 100.0);
        services.put("Consultatie simpla", 50.0);
        LocalDate date1 = LocalDate.of(1998, 5,3);
        Doctor d1 = new Doctor("Andreea", "Mihai", "2345678912345", "072342424234", "address@email", 'f', date1, "address", "Dermatologie", "Dermatolog", services);
        ArrayList<Doctor> aux = doctorService.getDoctors();
        aux.add(d1);
        doctorService.setDoctors(aux);

        /*Nurse*/
        Nurse n1 = new Nurse("Camelia", "Popescu", "234567891234", "075112394", "adresa@email", 'f', date1, "adresa", d1);
        nurses.add(n1);
        this.addPerson(n1);

        /*Pacient*/
        PatientService patientService = new PatientService();
        Patient p1 = new Patient("Mihai", "Nastase", "1111111111111", "0723213131", "address@email", 'm', date1, "address");
        ArrayList<Patient> patients = patientService.getPatients();
        patients.add(p1);
        patientService.setPatients(patients);
        this.addPerson(p1);

        Scanner input = new Scanner(System.in);
        int option;
        Nurse currentNurse;
        if (!askForAccount()) {
            currentNurse = this.createAccount();
        }
        else {
            currentNurse = this.login();
            if (currentNurse == null) {
                System.out.println("This account does not exist. Please connect again.");
                return;
            }
        }
        do {
            System.out.println("Please enter your option: ");
            System.out.println("1: Add height, weigh and fat percentage for a pacient;");
            System.out.println("2: Add an injection for a pacient: ");
            System.out.println("3: Show medical history for a pacient");
            System.out.println("4: Exit.");
            option = input.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name:");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        this.addHeightWeightFat(patient);
                    else
                        System.out.println("This patient does not exist in our system. Please try again.");
                    break;
                }
                case 2:{
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name:");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        this.addInjection(patient);
                    else
                        System.out.println("This patient does not exist in our system. Please try again.");
                    break;
                }
                case 3: {
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name:");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        showMedicalHistory(patient);
                    else
                        System.out.println("This patient does not exist in our system. Please try again.");
                }
            }
        } while (option == 1 || option == 2 || option == 3);
    }
}
