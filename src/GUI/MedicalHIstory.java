package GUI;

import clinicServices.ConsultationResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by diana on 17.05.2019.
 */
public class MedicalHIstory extends JFrame {
    JPanel p1;
    private JLabel heightLabel, weightLabel, height, weight;
    private String[] consultationColNames = {"Consultation no.",
                                             "Disease",
                                             "Prescription",
                                             "Conclusion."};
    private String [][] consultationData;
    private JTable consultations;
    public MedicalHIstory(Double height, Double weight, ArrayList<ConsultationResult> consultationResults) {
        super("Medical History");
//        p1 = new JPanel();
        heightLabel = new JLabel("Height:");
        this.height = new JLabel(height.toString());
        weightLabel = new JLabel("Weight:");
        this.weight = new JLabel(weight.toString());
        heightLabel.setBounds(250, 10, 200, 30);
        this.height.setBounds(320, 10, 30, 30);
        weightLabel.setBounds(250, 50, 200, 30);
        this.weight.setBounds(320, 50, 30, 30);

        if (consultationResults.size() != 0)
            System.out.println("DOne");
        consultationData = new String[consultationResults.size()][4];
        for (int i = 0; i < consultationResults.size(); i++) {
            String row[] = new String[4];
            row[0] = String.valueOf(i + 1);
            if (consultationResults.get(i).getDisease() != null)
                row[1] = consultationResults.get(i).getDisease();
            else
                row[1] = "-";
            if (consultationResults.get(i).getPrescription() != null)
                row[2] = "Yes";
            else
                row[2] = "No";
            if (consultationResults.get(i).getConclusion() != null)
                row[3] = consultationResults.get(i).getConclusion();
            else
                row[3] = "-";
            consultationData[i] = row;
        }
        consultations = new JTable(consultationData, consultationColNames);
        consultations.setBounds(0, 100, 500, 300);

        add(heightLabel);
        add(this.height);
        add(weightLabel);
        add(this.weight);
//        add(p1);
//        p1.setLayout(new BorderLayout());
//        p1.add(consultations.getTableHeader(), BorderLayout.NORTH);
//        p1.add(consultations);
        JScrollPane scrollPane = new JScrollPane(consultations);
        scrollPane.getViewport().setViewPosition(new Point(0, 0));
        add(scrollPane, BorderLayout.CENTER);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
