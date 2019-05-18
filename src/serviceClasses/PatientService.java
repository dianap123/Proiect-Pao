package serviceClasses;


import GUI.MakeAppointment;
import GUI.MedicalHIstory;
import GUI.ShowDoctors;
import clinicServices.Appointment;
import clinicServices.Medicine;
import org.omg.PortableInterceptor.INACTIVE;
import person.Doctor;
import person.Patient;
import person.Person;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class PatientService extends Service{
    /*List of all the pacients in the application*/
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public ArrayList<Patient> getPatients() {

        return new ArrayList<>(patients);
    }

    public void setPatients(ArrayList<Patient> patients) {

        this.patients = new ArrayList<>(patients);
    }

    /*creates account for a pacient*/
    public Patient createAccount() {
        Person newPerson = super.createAccount();
        Patient newPatient = new Patient(newPerson);
        this.patients.add(newPatient);
        System.out.println("Account created.");
        return newPatient;
    }

    /*log in for patients*/
    public Patient login() {
        Person person = super.login();
        if (person != null) {
            Patient patient = new Patient(person);
            System.out.println("You are logged in.");
            return patient;
        }
        return null;
    }

    /*Method used for returning a Doctor object when a patient enters only the name of the doctor*/
    /*If a doctor with that name is not found, the method return null and this helps at displaying an error message*/
    public Doctor getDoctorByName(String firstname, String lastname, ArrayList<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            if (doctor.getLastName().equals(lastname) && doctor.getFirstName().equals(firstname))
                return doctor;
        }
        return null;
    }

    /*Show list of doctor names to help a patient when he/she doesn't type a correct name*/
    public void showListOfDoctors(ArrayList<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            System.out.println(doctor.getFirstName() + " " + doctor.getLastName());
        }
    }

    public void showListOfMedicines() {
        CsvParser csvParser = new CsvParser();
        Path path = Paths.get("/home/diana/An2/PAO/proiect/Files/medicines.csv");
        Map<String, List<String>> medicinesFile = csvParser.read(path);
        int numberOfMedicines = medicinesFile.get("name").size();
        for (int i = 0; i < numberOfMedicines; i++) {
            String name = medicinesFile.get("name").get(i);
            String effect = medicinesFile.get("effect").get(i);
            String administration = medicinesFile.get("administration").get(i);
            Medicine m = new Medicine(name, effect, administration);
            System.out.println(m.toString());
        }
    }

    /*Method used by patients to make an appointment. They have to enter the name of the doctor, date and time.*/
    /*If the doctor is not available at that time, i.e. there is another appointment at the exact same time, an error message is displayed*/
    public void makeAppointment(Patient patient, ArrayList<Doctor> doctors) {
        MakeAppointment makeAppointment = new MakeAppointment();
        Scanner input = new Scanner(System.in);
        System.out.println("At which doctor do you want to make an appointment?");
        System.out.println("First name: ");
        String firstName = input.nextLine();
        System.out.println("Last name: ");
            String lastName = input.nextLine();
        Doctor doctorSearched = getDoctorByName(firstName, lastName, doctors);
        if (doctorSearched == null) {
            do {
                System.out.println("Doctor name does not exist");
                System.out.println("List of doctors: ");
                showListOfDoctors(doctors);
                System.out.println("First name: ");
                firstName = input.next();
                System.out.println("Last name: ");
                lastName = input.next();
                doctorSearched = getDoctorByName(firstName, lastName, doctors);
            }
            while (doctorSearched == null);
        }
        System.out.println("Enter date");
        System.out.println("Day: ");
        int day = input.nextInt();
        System.out.println("Month: ");
        int month = input.nextInt();
        System.out.println("Year: ");
        int year = input.nextInt();

        System.out.println("Hour: ");
        int hour = input.nextInt();
        System.out.println("Minutes: ");
        int minutes = input.nextInt();

        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minutes);

        ArrayList<Appointment> doctorApp = doctorSearched.getAppointments();
        for (Appointment app: doctorApp) {
            if (date.compareTo(app.getDate()) == 0) {
                System.out.println("This date is not available. Please try again.");
                return;
            }
        }
        Appointment appointment = new Appointment(doctorSearched, patient, date);
        patient.addAppointment(appointment);
        doctorSearched.addAppointment(appointment);
        System.out.println("Appointment created.");
    }


    /*Shows services a doctor provides and their prices*/
    public void checkServicesandPrices(Doctor doctor) {
        Map<String, Double> services = doctor.getServices();
        for (Map.Entry<String, Double> entry: services.entrySet()) {
            System.out.println(entry.getKey() + ", pret: " + entry.getValue());
        }

    }

    /*For every doctor, shows name, section and specialization*/
    public void showDoctors(ArrayList<Doctor> doctors) {
        for (Doctor doctor: doctors) {
            System.out.println("Name: " + doctor.getFirstName() + ' ' + doctor.getLastName());
            System.out.println("Section: " + doctor.getSection());
            System.out.println("Specialization: " + doctor.getSpecialization() + "\n");
        }
    }

    /*Run the program*/
    public void start() {



        /*Create an instance of patient for testing*/

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
            patients.add(p);
            super.addPerson(p);
        }

          /*Create an instance of DoctorService to access the list of all doctors*/
        DoctorService doctorService = new DoctorService();
        ArrayList<Doctor> doctors = doctorService.getDoctors();
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
            doctors.add(d);
        }
        doctorService.setDoctors(doctors);

        Scanner input = new Scanner(System.in);
        Patient currentPacient;
        if (this.askForAccount()) {
            csvParser.write("PatientService: Login", new Timestamp(System.currentTimeMillis()));
            currentPacient = this.login();
            if (currentPacient == null) {
                System.out.println("This account does not exist. Please connect again.");
                return;
            }
        }
        else {
            csvParser.write("PatientService: Create account", new Timestamp(System.currentTimeMillis()));
            currentPacient = this.createAccount();
        }
            int option;
            do {
                System.out.println("Enter your option: ");
                System.out.println("1: Make an appointment;");
                System.out.println("2: Cancel an appointment;");
                System.out.println("3: Show medical history;");
                System.out.println("4: Show list of services and prices for a doctor");
                System.out.println("5: Show list of doctors;");
                System.out.println("6: Show list of medicines;");
                System.out.println("7: Exit.");
                option = input.nextInt();
                switch (option) {
                    case 1: {
                        MakeAppointment ma = new MakeAppointment();
//                        makeAppointment(currentPacient, doctorService.getDoctors());
//                        csvParser.write("PatientService.MakeAnAppointment", new Timestamp(System.currentTimeMillis()));
                        break;
                    }
                    case 2: {
                        System.out.println("At which doctor did you have an appointment?");
                        System.out.println("First name: ");
                        String fname = input.next();
                        System.out.println("Last name: ");
                        String lname = input.next();
                        Doctor doctor;
                        doctor = this.getDoctorByName(fname, lname, doctorService.getDoctors());
                        if (doctor != null)
                            this.cancelAppointment(currentPacient, doctor);
                        else {
                            System.out.println("This doctor does not exist in our list. List of doctors: ");
                            showListOfDoctors(doctorService.getDoctors());
                        }
                        csvParser.write("PatientService: Cancel an appointment", new Timestamp(System.currentTimeMillis()));
                        break;
                    }
                    case 3: {
                          MedicalHIstory mh = new MedicalHIstory(currentPacient.getHeight(), currentPacient.getWeight(), currentPacient.getConsultationResults());
//                        showMedicalHistory(currentPacient);
//                        csvParser.write("PatientService: Show medical history", new Timestamp(System.currentTimeMillis()));
                        break;
                    }
                    case 4: {
                        System.out.println("Please enter doctor's name: ");
                        System.out.println("First name: ");
                        String fname = input.next();
                        System.out.println("Last name: ");
                        String lname = input.next();
                        Doctor doctor;
                        doctor = this.getDoctorByName(fname, lname, doctorService.getDoctors());
                        if (doctor != null) {
                            this.checkServicesandPrices(doctor);
                        }
                        else {
                            System.out.println("This doctor does not exist in our list. List of doctors: ");
                            showListOfDoctors(doctorService.getDoctors());
                        }
                        csvParser.write("PatientService: Show list of services and prices for a doctor", new Timestamp(System.currentTimeMillis()));
                        break;
                    }
                    case 5: {
//                        this.showDoctors(doctorService.getDoctors());
//                        csvParser.write("PatientService: Show list of doctors", new Timestamp(System.currentTimeMillis()));
                        ShowDoctors sd = new ShowDoctors(doctorService.getDoctors());
                        break;
                    }
                    case 6: {
                        this.showListOfMedicines();
                        csvParser.write("PatientService: Show list of medicines", new Timestamp(System.currentTimeMillis()));
                        break;
                    }
                }
            } while (option >= 0 && option <= 6);
        }
    }

