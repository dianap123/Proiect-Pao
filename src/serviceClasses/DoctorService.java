package serviceClasses;


import clinicServices.ConsultationResult;
import clinicServices.Medicine;
import clinicServices.Prescription;
import person.Doctor;
import person.Patient;
import person.Person;

import java.time.LocalDate;
import java.util.*;

public class DoctorService extends Service{
    /*List of all doctors in the application*/
    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();

    public ArrayList<Doctor> getDoctors() {
        return new ArrayList<Doctor>(doctors);
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = new ArrayList<>(doctors);

    }

    /*Create a doctor account*/
    /*Besides information needed for a person, section and speciaization are necessary*/
    public Doctor createAccount() {
        Person newPerson = super.createAccount();
        Doctor newDoctor = new Doctor(newPerson);
        System.out.println("Please enter services you provide. Press '+' to add a new service, 'End', to stop adding.");
        Scanner input = new Scanner(System.in);
        String option = input.next();
        Map<String, Double> services = new HashMap<>();
        if (option.equals("+")) {
            do {
                System.out.println("Service: ");
                String name = input.next();
                System.out.println("Price: ");
                Double price = input.nextDouble();
                services.put(name, price);
                System.out.println("Please enter services you provide. Press '+' to add a new service, 'End', to stop adding.");
                option = input.next();
            } while (option.equals("+"));
        }
        newDoctor.setServices(services);
        this.doctors.add(newDoctor);
        return newDoctor;
    }

    /*Doctor log in*/

    public Doctor login() {
        Person person = super.login();
        if (person != null) {
            Doctor doctor = new Doctor(person);
            return doctor;
        }
       return null;
    }

    /*Prescribe a prescription. For every prescription, a list of medicines and other observations are necessary.*/

    public Prescription prescribePrescription() {
        Prescription prescription = new Prescription();
        System.out.println("Enter '+' to add a new medicine, 'End' to stop adding medicines");
        Scanner input = new Scanner(System.in);
        String option;
        option = input.next();
        if (option.equals("+")) {
            do {
                System.out.println("Name: ");
                input.nextLine();
                String name = input.nextLine();
                System.out.println("Quantity: ");
                double quantity = input.nextDouble();
                System.out.println("Number of days: ");
                int numberOfDays = input.nextInt();
                System.out.println("Administration/day: ");
                int itemsPerDay = input.nextInt();
                Medicine medicine = new Medicine(name, quantity, numberOfDays, itemsPerDay);
                prescription.getMedicines().add(medicine);
                System.out.println("Enter '+' to add a new medicine, 'End' to stop adding medicines");
                option = input.next();
            } while (option.equals("+"));
        }
        System.out.println("Other observations: ");
        input.nextLine();
        String obs = input.nextLine();
        prescription.setObs(obs);
        return prescription;
    }

    /*The result of a consultation can be a treatment if a disease is found, otherwise it is  a simple conclusion */
    public void addConsultationResult(Patient patient) {
        Scanner input = new Scanner(System.in);
        ConsultationResult consultationResult;
        System.out.println("Did you establish any disease? Yes/No");
        String response = input.next();
        response = response.toLowerCase();
        if (response.equals("yes")) {
            System.out.println("Please enter the name of the disease: ");
            input.nextLine();
            String disease= input.nextLine();
            System.out.println("Please enter the prescription: ");
            Prescription prescription = prescribePrescription();
            System.out.println("Please enter other observations: ");
            input.nextLine();
            String obs = input.nextLine();
            consultationResult = new ConsultationResult(disease, prescription, obs);
        }
        else {
            System.out.println("Please enter conclusion of the consultation: ");
            input.nextLine();
            String obs = input.nextLine();
            consultationResult = new ConsultationResult(obs);
        }
        patient.addConsultationResult(consultationResult);
    }

    /*Add vaccine made for a baby*/
    public void addVaccine(Patient patient) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the vaccine: ");
        String name = input.nextLine();
        patient.getVaccines().add(name);
    }

    /*Method used for returning a Patient object when a doctor enters only the name of the patient*/
    /*If a patient with that name is not found, the method return null and this helps at displaying an error message*/
    public Patient getPatientByName(String firstname, String lastname, ArrayList<Patient> patients) {
        for (Patient patient : patients) {
            if (patient.getFirstName().equals(firstname) && patient.getLastName().equals(lastname))
                return patient;
        }
        return null;
    }

    /*Run*/

    public void start() {

        /*Create an instance of PatientService to access the list of all patients*/
        PatientService patientService = new PatientService();

        /*Create an instance of Patient used for testing*/
        /*It is useful for services where a doctor adds medical information for a specific patient*/
        LocalDate date = LocalDate.of(2000, 1,1);
        Patient p1 = new Patient("Mihai", "Ionescu", "1111111111111", "0721329934", "email@addredd", 'm', date, "address");
        ArrayList<Patient> aux;
        aux = patientService.getPatients();
        aux.add(p1);
        patientService.setPatients(aux);

        /*Create an instance of doctor used for testing*/
        Map<String, Double> services = new HashMap<>();
        services.put("Consultatie generala", 100.0);
        services.put("Consultatie simpla", 50.0);
        Doctor d1 = new Doctor("Andreea", "Mihai", "2345678912345", "07343253453", "address@email", 'f', date, "address", "Dermatologie", "Dermatolog", services);

        /*Add here in order to be found at log in*/
        this.addPerson(d1);
        this.doctors.add(d1);
        Scanner input = new Scanner(System.in);
        int option;
        Doctor currentDoctor;
        if (!this.askForAccount()) {
            currentDoctor = this.createAccount();
        }
        else {
            currentDoctor = this.login();
            if (currentDoctor == null) {
                System.out.println("This account does not exist. Please connect again.");
                return;
            }
        }
        do {
            System.out.println("Enter your option: ");
            System.out.println("1: Cancel an appointment;");
            System.out.println("2: Add the result of a consultation for a pacient;");
            System.out.println("3: Add a vaccine in medical history for a pacient;");
            System.out.println("4: Show medical history for a pacient;");
            System.out.println("5: Exit.");
            option = input.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name: ");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        this.cancelAppointment(patient, currentDoctor);
                    else
                        System.out.println("You do not have this appointment in your list. Please try again.");
                    break;
                }
                case 2: {
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name:");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        this.addConsultationResult(patient);
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
                        this.addVaccine(patient);
                    else
                        System.out.println("This patient does not exist in our system. Please try again.");
                    break;
                }
                case 4: {
                    System.out.println("Please enter pacient's name: ");
                    System.out.println("First name:");
                    String fname = input.next();
                    System.out.println("Last name: ");
                    String lname = input.next();
                    Patient patient = this.getPatientByName(fname, lname, patientService.getPatients());
                    if (patient != null)
                        this.showMedicalHistory(patient);
                    else
                        System.out.println("This patient does not exist in our system. Please try again.");
                    break;
                }
            }
        } while (option >= 0 && option < 5);
    }
}

