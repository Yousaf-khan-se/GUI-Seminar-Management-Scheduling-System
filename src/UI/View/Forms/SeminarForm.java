package UI.View.Forms;

import javax.swing.*;

import java.awt.*;

public class SeminarForm extends Form {
    public JButton uploadFileBtn;
    public JLabel filePathLbl;

    public JTextField seminarNameField;
    public JTextField speakerNameField;
    public JTextField attendeesListField;

    public JButton addSeminarBtn;

    public SeminarForm(String formHeader, String field1Header, String field2Header, String field3Header) {
        super(formHeader, field1Header, field2Header, field3Header);

        uploadFileBtn = createFormButton("Upload File");
        filePathLbl = createFormLabel("Path: ____________________________");

        seminarNameField = createFormTextField();
        speakerNameField = createFormTextField();
        attendeesListField = createFormTextField();
        attendeesListField.setPreferredSize(new Dimension(300, 25));

        addSeminarBtn = createFormButton("Add or Update Seminar");

        this.editsPanel = createEditsPanel();
        this.add(editsPanel, BorderLayout.CENTER);
    }

    @Override
    protected JPanel createEditsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);

        panel.add(getComponentsRow(new JComponent[]{filePathLbl, uploadFileBtn}));
        panel.add(getComponentsRow(new JComponent[]{createFormLabel("Seminar Name: "), seminarNameField, createFormLabel("   "),createFormLabel("Speaker Name: "), speakerNameField}));
        panel.add(getComponentsRow(new JComponent[]{createFormLabel("Attendees ID's (Format: 123, 122, 121)"), attendeesListField, createFormLabel("      "), addSeminarBtn}));

        return panel;
    }

}
