package UI.View.Forms;

import javax.swing.*;
import java.awt.*;

public class ConflictInfoForm extends Form{

    public ConflictInfoForm(String formHeader, String field1Header, String field2Header, String field3Header) {
        super(formHeader, field1Header, field2Header, field3Header);

        this.editsPanel = createEditsPanel();
        this.add(editsPanel, BorderLayout.CENTER);
    }

    @Override
    protected JPanel createEditsPanel() {
        JLabel seminarConflictLbl = createFormLabel("\u25A1 Conflicting Seminars");
        seminarConflictLbl.setForeground(Color.orange);
        seminarConflictLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));
        JLabel attendeesConflictLbl = createFormLabel("\u25A1 Conflicting Attendees");
        attendeesConflictLbl.setForeground(Color.red);
        attendeesConflictLbl.setFont(new Font("TimesRoman", Font.BOLD, 16));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,50));
        panel.add(seminarConflictLbl);
        panel.add(attendeesConflictLbl);

        panel.setBackground(Color.black);

        return panel;
    }

    public JTable getInfoTable()
    {
        return this.table;
    }
}


