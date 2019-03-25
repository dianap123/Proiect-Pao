package serviceClasses;


import person.Doctor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option: Patient/Doctor/Nurse: ");
        String personType = input.nextLine();
        personType = personType.replaceAll("\\s+","");
        personType = personType.toLowerCase();
        if (!personType.equals("patient") && !personType.equals("doctor") && !personType.equals("nurse")) {
            do {
                System.out.println("Enter a valid option: ");
                personType = input.nextLine();
                personType = personType.replaceAll("\\s+","");
                personType = personType.toLowerCase();
            } while (!personType.equals("patient") && !personType.equals("doctor") && !personType.equals("nurse"));
        }
        if (personType.equals("patient")) {
            PatientService patientService = new PatientService();
            patientService.start();
        }
        else if (personType.equals("doctor")) {
            DoctorService doctorService = new DoctorService();
            doctorService.start();
        }
            else {
            NurseService nurseService = new NurseService();
            nurseService.start();
        }
    }
}
