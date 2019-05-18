package GUI;

import person.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by diana on 17.05.2019.
 */
public class ShowDoctors extends JFrame  {
    JPanel p1;
    private String[] columnNames = {"First Name",
                                    "Last Name",
                                    "Section",
                                    "Specialization"};
    String [][] data;
    JTable table;
    public ShowDoctors(ArrayList<Doctor> doctors) {
        super("List of doctors");
        data = new String[doctors.size()][4];
        int idx = 0;
        for(Doctor doctor: doctors) {
            String item[] = new String[4];
            item[0] = doctor.getFirstName();
            item[1] = doctor.getLastName();
            item[2] = doctor.getSection();
            item[3] = doctor.getSpecialization();
            data[idx] = item;
            idx++;
        }
        table = new JTable(data, columnNames);
        table.setRowHeight(60);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 4; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(table.getTableHeader(), BorderLayout.PAGE_START);
        p1.add(table);
        add(p1);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
