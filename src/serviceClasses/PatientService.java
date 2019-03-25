package serviceClasses;


import clinicServices.Appointment;
import org.omg.PortableInterceptor.INACTIVE;
import person.Doctor;
import person.Patient;
import person.Person;

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

    /*Method used by patients to make an appointment. They have to enter the name of the doctor, date and time.*/
    /*If the doctor is not available at that time, i.e. there is another appointment at the exact same time, an error message is displayed*/
    public void makeAppointment(Patient patient, ArrayList<Doctor> doctors) {
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

        /*Create an instance of DoctorService to access the list of all doctors*/
        DoctorService doctorService = new DoctorService();

        /*Create an instance of Doctor for testing*/
        /*It is necessary when displaying list of doctors, list of prices etc*/
        Map<String, Double> services = new HashMap<>();
        services.put("Consultatie generala", 100.0);
        services.put("Consultatie simpla", 50.0);
        LocalDate date = LocalDate.of(2000, 1,1);
        Doctor doctor = new Doctor("Andreea", "Calinescu", "2345678912345", "075231479", "address@email", 'f', date, "address", "Dermatologie", "Dermatolog", services);
        ArrayList<Doctor> doctors = doctorService.getDoctors();
        doctors.add(doctor);
        doctorService.setDoctors(doctors);

        /*Create an instance of patient for testing*/
        Patient p1 = new Patient("Ioana", "Marinescu", "2222222222222", "0712345698", "address@email", 'f', date, "address");
        patients.add(p1);
        /*Add here in order to be found at log in*/
        super.addPerson(p1);
        Scanner input = new Scanner(System.in);
        Patient currentPacient;
        if (this.askForAccount()) {
            currentPacient = this.login();
            if (currentPacient == null) {
                System.out.println("This account does not exist. Please connect again.");
                return;
            }
        }
        else {
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
                System.out.println("6: Exit.");
                option = input.nextInt();
                switch (option) {
                    case 1: {
                        makeAppointment(currentPacient, doctorService.getDoctors());
                        break;
                    }
                    case 2: {
                        System.out.println("At which doctor did you have an appointment?");
                        System.out.println("First name: ");
                        String fname = input.next();
                        System.out.println("Last name: ");
                        String lname = input.next();
                        doctor = this.getDoctorByName(fname, lname, doctorService.getDoctors());
                        if (doctor != null)
                            this.cancelAppointment(currentPacient, doctor);
                        else {
                            System.out.println("This doctor does not exist in our list. List of doctors: ");
                            showListOfDoctors(doctorService.getDoctors());
                        }
                        break;
                    }
                    case 3: {
                        showMedicalHistory(currentPacient);
                        break;
                    }
                    case 4: {
                        System.out.println("Please enter doctor's name: ");
                        System.out.println("First name: ");
                        String fname = input.next();
                        System.out.println("Last name: ");
                        String lname = input.next();
                        doctor = this.getDoctorByName(fname, lname, doctorService.getDoctors());
                        if (doctor != null) {
                            this.checkServicesandPrices(doctor);
                        }
                        else {
                            System.out.println("This doctor does not exist in our list. List of doctors: ");
                            showListOfDoctors(doctorService.getDoctors());
                        }
                        break;
                    }
                    case 5: {
                        this.showDoctors(doctorService.getDoctors());
                        break;
                    }
                }
            } while (option >= 0 && option <= 5);
        }
    }

