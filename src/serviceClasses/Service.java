package serviceClasses;


import clinicServices.Appointment;
import clinicServices.ConsultationResult;
import clinicServices.Prescription;
import person.Doctor;
import person.Patient;
import person.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/*Service class which provides common services for all types of persons. It is extended by DoctorService, PacientService and NurseService*/

public class Service {
    /*list of all users of the application, where I search if an account exists (by CNP) and where I add new users*/
    private ArrayList<Person> users = new ArrayList<Person>();

    /*check if a user that opens the application has an account*/
    public boolean askForAccount() {
        System.out.println("Do you have an account? Yes/No");
        Scanner input = new Scanner(System.in);
        String response = input.next();
        response = response.toLowerCase();
        if (!response.equals("yes") && !response.equals("no")) {
            do {
                System.out.println("Please enter a valid option");
                response = input.next();
                response = response.toLowerCase();
            } while (!response.equals("yes") && !response.equals("no"));
        }
        if (response.equals("yes"))
            return true;
        return false;
    }

    /*do some basic validations regarding CNP*/
    protected boolean validateCNP(String CNP) {
        if (CNP.length() != 13 || (CNP.charAt(0) != '1' && CNP.charAt(0) != '2'))
            return false;
        return true;
    }

    /*method for creating an account, which is overridden in derived classes*/
    protected Person createAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("First name: ");
        String firstName = input.nextLine();

        System.out.println("Last name: ");
        String lastName = input.next();

        System.out.println("CNP: ");
        String CNP = input.next();

        if (!this.validateCNP(CNP)) {
            do {

                System.out.println("Enter a valid CNP: ");
                CNP = input.next();
            }
            while (!this.validateCNP(CNP));
        }

        System.out.println("Telephone: ");
        String telephoneNumber = input.next();

        System.out.println("Email: ");
        String emailAdress = input.next();

        System.out.println("Gender: ");
        char gender = input.next().charAt(0);

        System.out.println("Enter your birthday:");
        System.out.println("Day: ");
        int day = input.nextInt();
        System.out.println("Month: ");
        int month = input.nextInt();
        System.out.println("Year: ");
        int year = input.nextInt();
        LocalDate birthday = LocalDate.of(year, month, day);

        System.out.println("Enter your adress: ");
        input.nextLine();
        String adress = input.nextLine();

        Person newUser = new Person(firstName, lastName, CNP, telephoneNumber, emailAdress, gender,
                birthday, adress);
        users.add(newUser);
        return newUser;
    }

    /*Log in is made with CNP.*/
    /*This method is also overridden in derived classes*/
    protected Person login() {
//        LoginForm loginForm = new LoginForm();
        System.out.println("Please enter your CNP:");
        Scanner input = new Scanner(System.in);
        String CNP = input.next();
        for (Person person : users)
            if (person.getCNP().equals(CNP))
                return person;
        return null;
    }


    /*This method is used in the same way by patients and by doctors*/
    /*It searches a specific appointment in both patient's and doctor's list and deletes it*/
    public void cancelAppointment(Patient patient, Doctor doctor) {
        String patientFname = patient.getFirstName();
        String patientLname = patient.getLastName();
        String doctorLname = doctor.getLastName();
        String doctorFname = doctor.getFirstName();
        ArrayList<Appointment> patientApp = patient.getAppointments();
        ArrayList<Appointment> doctorApp = doctor.getAppointments();
        boolean found = false;
        for (Appointment app : patientApp) {
            if (app.getDoctor().getFirstName().equals(doctorFname) &&
                    app.getDoctor().getLastName().equals(doctorLname)) {
                patientApp.remove(app);
                found = true;
                patient.setAppointments(patientApp);
                break;
            }
        }
        if (!found) {
            System.out.println("This appointment does not exist.");
            return;
        }
        for (Appointment app : doctorApp) {
            if (app.getPatient().getLastName().equals(patientLname) &&
                    app.getPatient().getFirstName().equals(patientFname)) {
                doctorApp.remove(app);
                doctor.setAppointments(doctorApp);
                System.out.println("Appointment cancelled.");
                return;
            }
        }
    }

    /*This method shows medical history of a pacient and is used in the same way by all users*/
    public void showMedicalHistory(Patient patient) {
        System.out.println("Height: ");
        System.out.println(patient.getHeight());
        System.out.println("Weight: ");
        System.out.println(patient.getWeight());
        System.out.println("Consultation results:");
        ArrayList<ConsultationResult> consultationResults = patient.getConsultationResults();
        if (consultationResults.size() == 0) {
            System.out.println("None");
        }
        else {
            for (int i = 0; i < consultationResults.size(); i++) {
                System.out.println(i + 1 + ": ");
                String disease = consultationResults.get(i).getDisease();
                if (disease != null) {
                    System.out.println("Disease: " + disease);
                    Prescription prescription = consultationResults.get(i).getPrescription();
                    System.out.println("Presctiption:");
                    Map<String, Double> medicines = prescription.getMedicines();
                    System.out.println(medicines);
                    System.out.println("Other obs.:");
                    System.out.println(prescription.getObs());

                }
                else {
                    System.out.println("Conclusion: " + consultationResults.get(i).getConclusion());
                }
            }
        }
        System.out.println("Vaccines: ");
        ArrayList<String> vaccines = patient.getVaccines();
        if (vaccines.size() > 0) {
            for (String elem: vaccines)
                System.out.println(elem);
        }
        else
            System.out.println("None");
        System.out.println("Injections: ");
        ArrayList<String> injections = patient.getInjections();
        if (injections.size() > 0) {
            for (String elem: injections)
                System.out.println(elem);
        }
        else System.out.println("None");
    }

    public void addPerson (Person person) {
        users.add(person);
    }
}
