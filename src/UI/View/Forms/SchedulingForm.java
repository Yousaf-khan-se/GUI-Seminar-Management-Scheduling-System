package UI.View.Forms;

import javax.swing.*;
import java.awt.*;

public class SchedulingForm extends Form{
    public JButton uploadFileBtn = createFormButton("Upload File");
    public JLabel filePathLbl = createFormLabel("Path: ____________________________");

    public JButton addToRoomsBtn = createFormButton("Add to Room List");
    public JTextField roomTextField = createFormTextField();

    public JButton addToTimeSlotBth = createFormButton("Add to Time Slot List");
    public JTextField timeTextField = createFormTextField();

//    public JTextField seminarTextField = createFormTextField();
    public DefaultComboBoxModel<String> seminarsModel = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> roomSlotsModel = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<Integer> timeSlotsModel = new DefaultComboBoxModel<>();

    private JComboBox<String> roomSlotComboBox;
    private JComboBox<Integer> timeSlotComboBox;
    private JComboBox<String> seminarComboBox;

    public JButton addScheduleBth = createFormButton("Update or Add to Schedule List");

    public SchedulingForm(String formHeader, String field1Header, String field2Header, String field3Header) {
        super(formHeader, field1Header, field2Header, field3Header);

        this.editsPanel = createEditsPanel();
        this.add(editsPanel, BorderLayout.CENTER);

    }

    public String getSelectedRoomSlot()
    {
        if(roomSlotComboBox.getSelectedItem() != null)
            return roomSlotComboBox.getSelectedItem().toString().trim();
        else
            return null;
    }

    public Integer getSelectedTimeSlot()
    {
        if(timeSlotComboBox.getSelectedItem() != null)
            return Integer.parseInt(timeSlotComboBox.getSelectedItem().toString().trim());
        else
            return null;
    }

    public String getSelectedSeminarSlot()
    {
        if(seminarComboBox.getSelectedItem() != null)
            return seminarComboBox.getSelectedItem().toString().trim();
        else
            return null;
    }

    @Override
    protected JPanel createEditsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
//        panel.setSize(860, 100);
        roomSlotComboBox = createComboBox(roomSlotsModel);
        timeSlotComboBox = createComboBox(timeSlotsModel);
        seminarComboBox = createComboBox(seminarsModel);
        seminarComboBox.setSize(200, 10);

        panel.add(getComponentsRow(new JComponent[] {filePathLbl, uploadFileBtn}));
        panel.add(getComponentsRow(new JComponent[]{createFormLabel("OR")}));
        panel.add(getComponentsRow(new JComponent[]{addToRoomsBtn, roomTextField,createFormLabel("  "), addToTimeSlotBth, timeTextField}));
        panel.add(getComponentsRow(new JComponent[]{createFormLabel("Seminar: "), seminarComboBox/*seminarTextField*/}));
        panel.add(getComponentsRow(new JComponent[]{createFormLabel("Rooms: "), roomSlotComboBox, createFormLabel("Time Slots"), timeSlotComboBox, addScheduleBth}));

        return panel;
    }

}
