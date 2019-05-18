package GUI;

import sun.util.locale.provider.JRELocaleConstants;

import javax.swing.*;

/**
 * Created by diana on 17.05.2019.
 */
public class AddConsultationResult extends JFrame {
    private JLabel firstNameLabel, lastNameLabel, checkResult, conclusion;
    private JTextField firstName, lastName;
    private JButton next1, next2;
    private JRadioButton yes, no;
    private ButtonGroup answer;
    public AddConsultationResult() {
        super("Add Consultation Result");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        firstName = new JTextField();
        lastName = new JTextField();
        next1 = new JButton("Next");

        firstNameLabel.setBounds(200, 50, 200, 30);
        firstName.setBounds(290, 50, 200, 30);
        lastNameLabel.setBounds(200, 100, 200, 30);
        lastName.setBounds(290, 100, 200, 30);
        next1.setBounds(500, 500, 80, 30);
        next1.addActionListener(ev -> checkForResult());

        add(firstNameLabel);
        add(firstName);
        add(lastNameLabel);
        add(lastName);
        add(next1);

        setLayout(null);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }


    public void checkForResult() {
        dispose();
        JFrame frame = new JFrame("Add Consultation Result");
        checkResult = new JLabel("Did you establish any disease?");
        next2 = new JButton("Next");
        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");
        answer = new ButtonGroup();
        answer.add(yes);
        answer.add(no);

        checkResult.setBounds(200, 100, 400, 30);
        yes.setBounds(230, 180, 50, 50);
        no.setBounds(300, 180, 50, 50);
        next2.setBounds(500, 500, 80, 30);

        frame.add(checkResult);
        frame.add(yes);
        frame.add(no);
        frame.add(next2);

        frame.setLayout(null);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public void addConclusion() {
        dispose();
        JFrame frame = new JFrame("Add Consultation Result");
        conclusion = new JLabel("Enter the conclusion of the consultation:");
        conclusion.setBounds(100, 300, 200, 3);

        frame.add(conclusion);

        frame.setLayout(null);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
   }

}
