package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by diana on 17.05.2019.
 */
public class MakeAppointment extends JFrame {
    JLabel doctorInfo, firstNameLabel, lastNameLabel, dateLabel, dayLabel, monthLabel, yearLabel, hourLabel, minutesLabel;
    JTextField firstName, lastName, day, month, year, hour, minutes;
    JPasswordField p1, p2;
    JButton submit;

    public MakeAppointment() {
        JFrame frame = new JFrame();
        frame.setTitle("Make Appointment");
        doctorInfo = new JLabel("Doctor:");
        doctorInfo.setForeground(Color.black);
        doctorInfo.setFont(new Font("Serif", Font.BOLD, 15));
        doctorInfo.getHorizontalAlignment();
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        dateLabel = new JLabel("Date:");
        dayLabel = new JLabel("Day:");
        monthLabel = new JLabel("Month:");
        yearLabel = new JLabel("Year:");
        hourLabel = new JLabel("Hour:");
        minutesLabel = new JLabel("Minutes:");
        dateLabel.setFont(new Font("Serif", Font.BOLD, 15));
        firstName = new JTextField();
        lastName = new JTextField();
        day = new JTextField();
        month = new JTextField();
        year = new JTextField();
        hour = new JTextField();
        minutes = new JTextField();
        doctorInfo.setBounds(170, 50, 400, 30);
        firstNameLabel.setBounds(170, 120, 200, 30);
        firstName.setBounds(300, 120, 200, 30);
        lastNameLabel.setBounds(170, 150, 200, 30);
        lastName.setBounds(300, 150, 200, 30);
        dateLabel.setBounds(170, 220, 400, 30);
        dayLabel.setBounds(170, 290, 200, 30);
        day.setBounds(300, 290, 30, 30);
        monthLabel.setBounds(170, 330, 200, 30);
        month.setBounds(300, 330, 30, 30);
        yearLabel.setBounds(170, 370, 200, 30);
        year.setBounds(300, 370, 30, 30);
        hourLabel.setBounds(170, 410, 200, 30);
        hour.setBounds(300, 410, 30, 30);
        JButton submit = new JButton("Submit");
        submit.setBounds(280, 500, 100, 50);
        frame.add(doctorInfo);
        frame.add(firstNameLabel);
        frame.add(firstName);
        frame.add(lastNameLabel);
        frame.add(lastName);
        frame.add(dateLabel);
        frame.add(dayLabel);
        frame.add(day);
        frame.add(monthLabel);
        frame.add(month);
        frame.add(yearLabel);
        frame.add(year);
        frame.add(hourLabel);
        frame.add(hour);
        frame.add(submit);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
