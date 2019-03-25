package person;


import java.time.LocalDate;

/* General details for all users*/


public class Person {
    protected String firstName;
    protected String lastName;
    protected String CNP;
    protected String telephoneNumber;
    protected String emailAdress;
    protected char gender;
    protected LocalDate birthday;
    protected String address;

    public Person () {}

    public Person(String firstName, String lastName, String CNP, String telephoneNumber, String emailAdress, char gender, LocalDate birthday, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.telephoneNumber = telephoneNumber;
        this.emailAdress = emailAdress;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }


    public Person(Person person) {
        if (person != null) {
            this.firstName = person.firstName;
            this.lastName = person.lastName;
            this.CNP = person.CNP;
            this.telephoneNumber = person.telephoneNumber;
            this.emailAdress = person.emailAdress;
            this.gender = person.gender;
            this.birthday = person.birthday;
            this.address = person.address;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCNP() {
        return CNP;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public char getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
