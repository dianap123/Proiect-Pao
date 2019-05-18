package GUI;

import javax.swing.*;

/**
 * Created by diana on 17.05.2019.
 */
public class AddVaccine extends JFrame {
    private JLabel firstNameLabel, lastNameLabel, vaccineLabel;
    private JTextField firstName, lastName, vaccine;
    private JButton next;

    public void enterVaccine() {
        dispose();
        JFrame frame = new JFrame("Add Vaccine");
        vaccineLabel = new JLabel("Enter the name of the vaccine:");
        vaccine = new JTextField();
        vaccineLabel.setBounds(200, 100, 300, 30);
        vaccine.setBounds(200, 200, 200, 30);
        frame.add(vaccineLabel);
        frame.add(vaccine);

        frame.setLayout(null);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public AddVaccine() {
        super("Add Vaccine");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        firstName = new JTextField();
        lastName = new JTextField();
        next = new JButton("Next");


        firstNameLabel.setBounds(200, 100, 200, 30);
        firstName.setBounds(300, 100, 200, 30);
        lastNameLabel.setBounds(200, 150, 200, 30);
        lastName.setBounds(300, 150, 200, 30);
        next.setBounds(500, 500, 80, 30);
        next.addActionListener(ev -> enterVaccine());

        add(firstNameLabel);
        add(firstName);
        add(lastNameLabel);
        add(lastName);
        add(next);



        setLayout(null);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }


}
