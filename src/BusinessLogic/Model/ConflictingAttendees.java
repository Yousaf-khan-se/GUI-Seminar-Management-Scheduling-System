package BusinessLogic.Model;

import java.util.ArrayList;

public class ConflictingAttendees {
    public String seminarName1;
    public String seminarName2;
    public ArrayList<Integer> conflictingAttendeesIDs;

    ConflictingAttendees(String seminarName1, String seminarName2, ArrayList<Integer> conflictingAttendeesIDs)
    {
        this.seminarName1 = seminarName1;
        this.seminarName2 = seminarName2;
        this.conflictingAttendeesIDs = conflictingAttendeesIDs;
    }

    @Override
    public String toString() {
        return "ConflictingAttendees{" +
                "seminarName1='" + seminarName1 + '\'' +
                ", seminarName2='" + seminarName2 + '\'' +
                ", conflictingAttendeesIDs=" + conflictingAttendeesIDs +
                '}';
    }
}
