package UI.View;

import BusinessLogic.Model.*;
import BusinessLogic.Presenter.Presenter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ViewController implements  IView{
    View view;
    Presenter presenter;

    public ViewController(View view) {
        this.view = view;
        SeminarFormContols();
        SchedulingFormControls();
        ConflictFormControls();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        this.loadSeminarFormTable(presenter.getSeminars());
        this.loadSchedulingFormTable(presenter.getScheduleList());
    }

    private void SeminarFormContols()
    {
        view.seminarForm.uploadFileBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select the Formatted Text File of Seminars");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files","txt"));
            fileChooser.setCurrentDirectory(new File(".").getAbsoluteFile());

            int userSelection = fileChooser.showOpenDialog(this.view.seminarForm);

            if(userSelection == JFileChooser.APPROVE_OPTION)
            {
                File fileToOpen = fileChooser.getSelectedFile();
                view.seminarForm.filePathLbl.setText("Path: ".concat(fileToOpen.getPath().toString()));

                presenter.getAndloadFileDatatoSeminarTable(fileToOpen);
            }
        });

        view.seminarForm.addSeminarBtn.addActionListener(e -> {
            if(view.seminarForm.seminarNameField.getText().trim().isEmpty())
            {
                showErrorMessage("Cannot leave Seminar Name field empty!");
            }
//            else if(view.seminarForm.speakerNameField.getText().trim().isEmpty())
//            {
//                showErrorMessage("Cannot leave Speaker Name field empty!");
//            }
//            else if(view.seminarForm.attendeesListField.getText().trim().isEmpty())
//            {
//                showErrorMessage("Cannot leave Attendees List field empty!");
//            }
            else
            {
                String semName = view.seminarForm.seminarNameField.getText().trim();
                String speakerName = view.seminarForm.speakerNameField.getText().trim();

                Seminar newSeminar = new Seminar(semName, new Speaker(speakerName));
                String attendeesListStr = view.seminarForm.attendeesListField.getText().trim();
                String []attendeesListArr = attendeesListStr.split(",");

                for(String alId : attendeesListArr)
                {
                    try{
                            if(!alId.trim().isEmpty())
                                newSeminar.addAttendee(new Attendee(Integer.parseInt(alId.trim())));
                    } catch (NumberFormatException en) {
                        showErrorMessage("Invalid entry entered in AttendeesID's text field! Please follow the given format. \n ");
                        view.seminarForm.attendeesListField.setText("");
                        return;
                    } catch (Exception ex){
                        showErrorMessage(ex.getMessage());
                        view.seminarForm.attendeesListField.setText("");
                        return;
                    }
                }
                presenter.addNewSeminar(newSeminar);
                // check

                loadSeminarFormTable(presenter.getSeminars());

                view.seminarForm.seminarNameField.setText("");
                view.seminarForm.speakerNameField.setText("");
                view.seminarForm.attendeesListField.setText("");


            }
        });

        view.seminarForm.table.addMouseListener(new MouseAdapter() {

            JTable table = view.seminarForm.table;
            DefaultTableModel tableModel = view.seminarForm.tableModel;
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    if (row >= 0 && col >= 0) {
                        System.out.println("row: " + row + " col: "+col+" is right clicked of the seminar talbe!");
                        String seminarName = tableModel.getValueAt(row, 0).toString();
                        boolean response = showConfirmationMessage(seminarName + " and it's details will be deleted from seminar table!");
                        if(response)
                        {
                            presenter.removeSeminar(seminarName);

                            loadSeminarFormTable(presenter.getSeminars());
                        }
                    }
                }
            }
        });
    }

    private void fillRoomAndTimeSlots()
    {
        for(Schedule sc : presenter.getScheduleList())
        {
            addToRoomSlots(sc.getRoom().getName());
            addToTimeSlots(sc.getTimeSlot().getValue());
        }
    }

    private void addToRoomSlots(String roomName) {
        DefaultComboBoxModel<String> rooms = view.schedulingForm.roomSlotsModel;

        boolean roomExists = false;
        for (int i = 0; i < rooms.getSize(); i++) {
            if (rooms.getElementAt(i).equals(roomName)) {
                roomExists = true;
                break;
            }
        }

        if (!roomExists) {
            rooms.addElement(roomName);
        }
    }

    private void addToTimeSlots(int timeSlot)
    {
        DefaultComboBoxModel<Integer> timeSlots = view.schedulingForm.timeSlotsModel;
        boolean timeSlotExist = false;
        for (int i = 0; i < timeSlots.getSize(); i++) {
            if (timeSlots.getElementAt(i).equals(timeSlot)) {
                timeSlotExist = true;
                break;
            }
        }
        if(!timeSlotExist) {
            timeSlots.addElement(timeSlot);
        }
    }

    private void SchedulingFormControls()
    {
        view.schedulingForm.uploadFileBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select the Formatted Text File of Seminar Schedules");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files","txt"));
            fileChooser.setCurrentDirectory(new File(".").getAbsoluteFile());

            int userSelection = fileChooser.showOpenDialog(this.view.schedulingForm);

            if(userSelection == JFileChooser.APPROVE_OPTION)
            {
                File fileToOpen = fileChooser.getSelectedFile();
                view.schedulingForm.filePathLbl.setText("Path: ".concat(fileToOpen.getPath().toString()));

                presenter.getAndloadFileDatatoScheduleTable(fileToOpen);
            }

            fillRoomAndTimeSlots();
        });

        view.navBar.schedulingInfoBtn.addActionListener(e -> {
            ArrayList<Seminar> semList = presenter.getSeminars();

            view.schedulingForm.seminarsModel.removeAllElements();
            for(Seminar sem : semList)
            {
                view.schedulingForm.seminarsModel.addElement(sem.getName());
            }
        });

        view.schedulingForm.addToRoomsBtn.addActionListener(e -> {
            if(!view.schedulingForm.roomTextField.getText().trim().isEmpty())
            {
                addToRoomSlots(view.schedulingForm.roomTextField.getText().trim());
                view.schedulingForm.roomTextField.setText("");
            }
        });

        view.schedulingForm.addToTimeSlotBth.addActionListener(e -> {
            if(!view.schedulingForm.timeTextField.getText().trim().isEmpty())
            {
                try{
                    addToTimeSlots(Integer.parseInt(view.schedulingForm.timeTextField.getText().trim()));
                } catch (NumberFormatException ex) {
                    showErrorMessage("Unable to parse int from the room slots text field");
                }
                view.schedulingForm.timeTextField.setText("");
            }
        });

        view.schedulingForm.addScheduleBth.addActionListener(e -> {
            if(view.schedulingForm.getSelectedSeminarSlot() == null)
            {
                showErrorMessage("No Slot is selected from the seminars List for the schedule!");
                return;
            }
            if(view.schedulingForm.getSelectedRoomSlot() == null)
            {
                showErrorMessage("No Slot is selected from the room List for the schedule!");
                return;
            }
            if(view.schedulingForm.getSelectedTimeSlot() == null)
            {
                showErrorMessage("No Slot is selected from the time slots List for the schedule!");
                return;
            }
            presenter.addNewSchedule(new Schedule(new Seminar(view.schedulingForm.getSelectedSeminarSlot(), new Speaker("Just for to find original Seminar")), new Room(view.schedulingForm.getSelectedRoomSlot()), new TimeSlot(view.schedulingForm.getSelectedTimeSlot())));
            this.loadSchedulingFormTable(presenter.getScheduleList());
        });


        view.schedulingForm.table.addMouseListener(new MouseAdapter() {

            JTable table = view.schedulingForm.table;
            DefaultTableModel tableModel = view.schedulingForm.tableModel;
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    if (row >= 0 && col >= 0) {
                        System.out.println("row: " + row + " col: "+col+" is right clicked of the schedule talbe!");
                        String seminarName = tableModel.getValueAt(row, 0).toString();
                        boolean response = showConfirmationMessage("Schedule of " + seminarName + " and it's details will be deleted from schedule table!");
                        if(response)
                        {
                            presenter.removeSchedule(seminarName);

                            loadSchedulingFormTable(presenter.getScheduleList());
                        }
                    }
                }
            }
        });

    }

    private void ConflictFormControls()
    {
        view.navBar.conflictsInfoBtn.addActionListener(e -> {
            loadConflictsFormTable(presenter.getSeminars(), presenter.getConflictingSeminarNames(), presenter.getConflictingAttendeesIDs());
        });
    }

    private void addDataToSeminarTable(String field1, String field2, String field3) {
        Object[] rowData = {field1, field2, field3};
        view.seminarForm.tableModel.addRow(rowData);
    }

    private void addDataToScheduleTable(String field1, String field2, String field3) {
        Object[] rowData = {field1, field2, field3};
        view.schedulingForm.tableModel.addRow(rowData);
    }

    private void addDataToConflictTable(String field1, String field2, String field3) {
        Object[] rowData = {field1, field2, field3};
        view.conflictsForm.tableModel.addRow(rowData);
    }


//    private void addDataToConflictTable(String field1, String field2, String field3) {
//        Object[] rowData = {field1, field2, field3};
//        view.conflictsForm.tableModel.addRow(rowData);
//    }

    @Override
    public void loadSeminarFormTable(ArrayList<Seminar> seminarList) {
        view.seminarForm.tableModel.setRowCount(0);
        for(Seminar sem : seminarList)
        {
            String AttendessIDs = sem.getAttendeesIDs().stream()
                    .map(String::valueOf) // Convert each Integer to String
                    .collect(Collectors.joining(", ")); // Join with commas
            AttendessIDs = " " + AttendessIDs + " ";
            addDataToSeminarTable(sem.getName(), sem.getSpeaker().getName(), AttendessIDs);
        }
    }

    @Override
    public void loadSchedulingFormTable(ArrayList<Schedule> schedules) {
        view.schedulingForm.tableModel.setRowCount(0);
        for(Schedule sc : schedules)
        {
            if(!sc.getRoom().getName().equals("Not Set!") && sc.getTimeSlot().value != -1)
                addDataToScheduleTable(sc.getSeminar().getName(), sc.getRoom().getName(), Integer.toString(sc.getTimeSlot().getValue()));
        }
    }

    private String highLightText(String text, String hexColor) {
        // Check if the text is already in the desired format
        if (text.startsWith("<font color='" + hexColor + "'>") && text.endsWith("</font>")) {
            return text;  // Return the text as is if it's already wrapped in <font>...</font>
        }

        // Check if the text contains a <font color='hexColor'>...<font> part already
        // If yes, combine the outside text and inner content into a single <font> tag
        if (text.contains("<font color='" + hexColor + "'>") && text.contains("</font>")) {
            // We need to find the position of the existing <font> tag and remove it from the text
            int startIndex = text.indexOf("<font color='" + hexColor + "'>") + ("<font color='" + hexColor + "'>").length();
            int endIndex = text.indexOf("</font>");

            // Extract the content inside the font tag
            String contentInsideFont = text.substring(startIndex, endIndex);

            // Combine the part before the font tag, the existing content, and wrap it in the font tag again
            String beforeFont = text.substring(0, startIndex - ("<font color='" + hexColor + "'>").length());
            String afterFont = text.substring(endIndex + "</font>".length());

            return "<font color='" + hexColor + "'>" + beforeFont + contentInsideFont + afterFont + "</font>";
        }

        // If the text is not in the desired format, wrap it with a new <font> tag
        return "<font color='" + hexColor + "'>" + text + "</font>";
    }


    @Override
    public void loadConflictsFormTable(ArrayList<Seminar> seminarList, ArrayList<ConflictingSeminars> conflictingSeminarNames, ArrayList<ConflictingAttendees> conflictingAttendeesList) {
        view.conflictsForm.tableModel.setRowCount(0);
        String redHexColor = "#FF0000";
        String orangeHexColor = "#FFA500";

        for(Seminar sem : seminarList)
        {
            String attendessIDs = sem.getAttendeesIDs().stream()
                    .map(String::valueOf) // Convert each Integer to String
                    .collect(Collectors.joining(" , ")); // Join with commas
            attendessIDs = " " + attendessIDs + " ";

            for(ConflictingAttendees ca : conflictingAttendeesList)
            {
                if(sem.getName().equals(ca.seminarName1) || sem.getName().equals(ca.seminarName2))
                {
                    ArrayList<String> caIDsInString = ca.conflictingAttendeesIDs
                            .stream()
                            .map(String::valueOf) // Convert each Integer to String
                            .collect(Collectors.toCollection(ArrayList::new)); // Collect directly into ArrayList

                    for(String caID : caIDsInString)
                    {
                        if(attendessIDs.contains(" "+caID+" "))
                        {
                            attendessIDs = attendessIDs.replace(" "+caID+" " , " "+highLightText(caID, redHexColor)+" ");
                        }
                    }
                }
            }

            attendessIDs = "<html>" + attendessIDs +"</html>";

            String seminarName = sem.getName();
//            if (conflictingSeminarNames.contains(seminarName)) {
//                seminarName = highLightText(seminarName, orangeHexColor);
//            }

            for(ConflictingSeminars cs : conflictingSeminarNames)
            {
                if(cs.equals(seminarName))
                {
                    seminarName = highLightText("(" + cs.id+ ") " + seminarName, orangeHexColor);
                }
            }

            seminarName = "<html>" + seminarName +"</html>";

            
            addDataToConflictTable(seminarName, sem.getSpeaker().getName(), attendessIDs);
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this.view, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public boolean showConfirmationMessage(String confirmationMessage) {
        int response = JOptionPane.showConfirmDialog(this.view, confirmationMessage + "\n Do you still Want to Proceed?", "Allow to proceed", JOptionPane.YES_NO_OPTION);

        if(response == JOptionPane.YES_OPTION)
            return true;
        else if(response == JOptionPane.NO_OPTION)
            return false;
        else return false;
    }

}
