package serviceClasses;


import person.Doctor;
import person.Nurse;
import person.Patient;
import person.Person;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

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

    public Doctor getDoctorByName(String firstname, String lastname, ArrayList<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            if (doctor.getLastName().equals(lastname) && doctor.getFirstName().equals(firstname))
                return doctor;
        }
        return null;
    }

    public void start() {

        DoctorService doctorService = new DoctorService();

        /*Create an instance of doctor at which the nurse is assigned*/

        CsvParser csvParser = new CsvParser();
        Path path = Paths.get("/home/diana/An2/PAO/proiect/Files/doctors.csv");
        Map<String, List<String>> doctorsInfo = csvParser.read(path);
        ArrayList<Doctor> doctors = doctorService.getDoctors();
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
            doctors.add(d);
        }
        doctorService.setDoctors(doctors);

        /*Nurse*/
        path = Paths.get("/home/diana/An2/PAO/proiect/Files/nurses.csv");
        Map<String, List<String>> nursesInfo= csvParser.read(path);
        int numberOfNurses= nursesInfo.get("firstName").size();
        for (int i = 0; i < numberOfNurses; i++) {
            String firstname = nursesInfo.get("firstName").get(i);
            String lastName = nursesInfo.get("lastName").get(i);
            String CNP = nursesInfo.get("CNP").get(i);
            String telephoneNumber = nursesInfo.get("telephoneNumber").get(i);
            String emailAddress = nursesInfo.get("emailAddress").get(i);
            char gender = nursesInfo.get("gender").get(i).charAt(0);
            LocalDate date1 = LocalDate.parse(nursesInfo.get("date").get(i));
            String address = nursesInfo.get("address").get(i);
            String doctorFname = nursesInfo.get("doctorFname").get(i);
            String doctorLname = nursesInfo.get("doctorLname").get(i);
            Doctor d = getDoctorByName(doctorFname, doctorLname, doctors);
            String grade = nursesInfo.get("grade").get(i);
            Nurse n = new Nurse(firstname, lastName, CNP, telephoneNumber, emailAddress, gender, date1, address, d, grade);
            nurses.add(n);
            this.addPerson(n);
        }


        /*Pacient*/
        PatientService patientService = new PatientService();

        ArrayList<Patient> patients = patientService.getPatients();
        path = Paths.get("/home/diana/An2/PAO/proiect/Files/patients.csv");
        Map<String, List<String>> patientsInfo= csvParser.read(path);
        int numberOfPatients = patientsInfo.get("firstName").size();
        for (int i = 0; i < numberOfPatients; i++) {
            String firstname = patientsInfo.get("firstName").get(i);
            String lastName = patientsInfo.get("lastName").get(i);
            String CNP = patientsInfo.get("CNP").get(i);
            String telephoneNumber = patientsInfo.get("telephoneNumber").get(i);
            String emailAddress = patientsInfo.get("emailAddress").get(i);
            char gender = patientsInfo.get("gender").get(i).charAt(0);
            LocalDate date = LocalDate.parse(patientsInfo.get("date").get(i));
            String address = patientsInfo.get("address").get(i);
            Patient p = new Patient(firstname, lastName, CNP, telephoneNumber, emailAddress, gender, date, address);
            this.addPerson(p);
            patients.add(p);
        }
        patientService.setPatients(patients);

        patientService.setPatients(patients);

        Scanner input = new Scanner(System.in);
        int option;
        Nurse currentNurse;
        if (!askForAccount()) {
            csvParser.write("NurseService: Create account", new Timestamp(System.currentTimeMillis()));
            currentNurse = this.createAccount();
        }
        else {
            csvParser.write("NurseService: Login", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("NurseService: Add height, weigh and fat percentage for a pacient", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("NurseService: Add an injection for a pacient", new Timestamp(System.currentTimeMillis()));
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
                    csvParser.write("NurseService: Show medical history for a pacient", new Timestamp(System.currentTimeMillis()));
                }
            }
        } while (option == 1 || option == 2 || option == 3);
    }
}
