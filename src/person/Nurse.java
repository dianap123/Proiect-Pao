package person;

import java.time.LocalDate;

/*A nurse is assigned to a doctor*/
/*She/he can be assigned when the account is created or after that*/

public class Nurse extends Person {
    private Doctor assignedDoctor;

    public Nurse(String firstName, String lastName, String CNP, String telephoneNumber, String emailAdress, char gender, LocalDate birthday, String address, Doctor assignedDoctor) {
        super(firstName, lastName, CNP, telephoneNumber, emailAdress, gender, birthday, address);
        this.assignedDoctor = assignedDoctor;
    }

    public Nurse(String firstName, String lastName, String CNP, String telephoneNumber, String emailAdress, char gender, LocalDate birthday, String address) {
        super(firstName, lastName, CNP, telephoneNumber, emailAdress, gender, birthday, address);
    }

    public Nurse (Person person) {
        super(person);
    }
    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }
}
