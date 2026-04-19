package UI.View;

import BusinessLogic.Model.ConflictingAttendees;
import BusinessLogic.Model.ConflictingSeminars;
import BusinessLogic.Model.Schedule;
import BusinessLogic.Model.Seminar;

import java.util.ArrayList;

public interface IView {

    void loadSeminarFormTable(ArrayList<Seminar> seminarList);
    void showErrorMessage(String errorMessage);
    boolean showConfirmationMessage(String confirmationMessage);

    void loadSchedulingFormTable(ArrayList<Schedule> schedules);

    void loadConflictsFormTable(ArrayList<Seminar> seminarList, ArrayList<ConflictingSeminars> conflictingSeminarNames, ArrayList<ConflictingAttendees> ConflictingAttendeesIDs);



}
