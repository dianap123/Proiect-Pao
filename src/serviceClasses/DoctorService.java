package serviceClasses;


import clinicServices.ConsultationResult;
import clinicServices.Medicine;
import clinicServices.Prescription;
import person.Doctor;
import person.Patient;
import person.Person;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
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
        System.out.println("Enter '+' to add a new medicine, 'End' to stop adding medicines");
        Scanner input = new Scanner(System.in);
        String option;
        option = input.next();
        Map<String, Double> medicines = new HashMap<>();
        if (option.equals("+")) {
            do {
                System.out.println("Name: ");
//                input.nextLine();
                String name = input.nextLine();
                input.nextLine();
                System.out.println("Quantity: ");
                Double quantity = input.nextDouble();
                medicines.put(name, quantity);
                System.out.println("Enter '+' to add a new medicine, 'End' to stop adding medicines");
                option = input.next();
            } while (option.equals("+"));
        }
        System.out.println("Other observations: ");
        input.nextLine();
        String obs = input.nextLine();
        Prescription prescription = new Prescription(medicines, obs);
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
            consultationResult = new ConsultationResult(disease, prescription);
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
        ArrayList<String> vaccines = patient.getVaccines();
        vaccines.add(name);
        patient.setVaccines(vaccines);
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

        /*Create instances of Patient used for testing*/
        /*Useful for services where a doctor adds medical information for a specific patient*/

        ArrayList<Patient> aux;
        aux = patientService.getPatients();
        CsvParser csvParser = new CsvParser();
        Path path = Paths.get("/home/diana/An2/PAO/proiect/Files/patients.csv");
        Map<String, List<String>> patientsInfo= csvParser.read(path);
        int numberOfPatients = patientsInfo.get("firstName").size();
        for (int i = 0; i < numberOfPatients; i++) {
            String firstname = patientsInfo.get("firstName").get(i);
            String lastName = patientsInfo.get("lastName").get(i);
            String CNP = patientsInfo.get("CNP").get(i);
            String telephoneNumber = patientsInfo.get("telephoneNumber").get(i);
            String emailAddress = patientsInfo.get("emailAddress").get(i);
            char gender = patientsInfo.get("gender").get(i).charAt(0);
            LocalDate date1 = LocalDate.parse(patientsInfo.get("date").get(i));
            String address = patientsInfo.get("address").get(i);
            Patient p = new Patient(firstname, lastName, CNP, telephoneNumber, emailAddress, gender, date1, address);
            aux.add(p);
        }
        patientService.setPatients(aux);

        /*Create an instance of doctor used for testing*/
        path = Paths.get("/home/diana/An2/PAO/proiect/Files/doctors.csv");
        Map<String, List<String>> doctorsInfo = csvParser.read(path);
        int numberOfDoctors= doctorsInfo.get("firstName").size();
        for (int i = 0; i < numberOfDoctors; i++) {
            String firstname = doctorsInfo.get("firstName").get(i);
            String lastName = doctorsInfo.get("lastName").get(i);
            String CNP = doctorsInfo.get("CNP").get(i);
            String telephoneNumber = doctorsInfo.get("telephoneNumber").get(i);
            String emailAddress = doctorsInfo.get("emailAddress").get(i);
            char gender = doctorsInfo.get("gender").get(i).charAt(0);
            LocalDate date1 = LocalDate.parse(doctorsInfo.get("date").get(i));
            String address = doctorsInfo.get("address").get(i);
            String section = doctorsInfo.get("section").get(i);
            String specialization = doctorsInfo.get("specialization").get(i);
            Map<String, Double> servicesMap = new HashMap<>();
            String services = doctorsInfo.get("services").get(i);
            String [] pairs = services.split(" ");
            for (String pair: pairs) {
                String [] service = pair.split("-");
                servicesMap.put(service[0], Double.parseDouble(service[1]));
            }
            Doctor d = new Doctor(firstname, lastName, CNP, telephoneNumber, emailAddress, gender, date1, address, section, specialization, servicesMap);
            /*Add here in order to be found at log in*/
            this.addPerson(d);
            this.doctors.add(d);
        }

        Scanner input = new Scanner(System.in);
        int option;
        Doctor currentDoctor;
        if (!this.askForAccount()) {
            csvParser.write("DoctorService: Create account", new Timestamp(System.currentTimeMillis()));
            currentDoctor = this.createAccount();
        }
        else {
            csvParser.write("DoctorService: Login", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("DoctorService: Cancel an appointment", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("DoctorService: Add the result of a consultation for a pacient", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("DoctorService: Add a vaccine in medical history for a pacient", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("DoctorService: Show medical history for a pacient", new Timestamp(System.currentTimeMillis()));
                    break;
                }
            }
        } while (option >= 0 && option < 5);
    }
}

